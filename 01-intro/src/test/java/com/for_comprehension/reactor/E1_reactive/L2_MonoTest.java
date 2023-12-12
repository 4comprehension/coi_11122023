package com.for_comprehension.reactor.E1_reactive;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class L2_MonoTest {

    @Test
    void test_L0_createEmptyMono() {
        StepVerifier.create(L2_Mono.L0_createEmptyMono()).verifyComplete();
    }

    @Test
    void test_L1_createEagerMono() {
        int value = 5;
        StepVerifier.create(L2_Mono.L1_createEagerMono(value))
          .expectNext(value)
          .verifyComplete();
    }

    @Test
    void test_L2_createLazyMono() {
        AtomicInteger counter = new AtomicInteger(0);
        var mono = L2_Mono.L2_createLazyMono(counter);

        StepVerifier.create(mono)
          .expectNextMatches(i -> counter.get() == 1 && i.equals(1))
          .verifyComplete();

        StepVerifier.create(mono)
          .expectNextMatches(i -> counter.get() == 2 && i.equals(2))
          .verifyComplete();
    }

    @Test
    void test_L3_createLazyMonoAndCache() {
        var counter = new AtomicInteger(0);
        var mono = L2_Mono.L3_createLazyMonoAndCache(counter);

        StepVerifier.create(mono)
          .expectNext(1)
          .verifyComplete();

        StepVerifier.create(mono)
          .expectNext(1)
          .verifyComplete();
    }

    @Test
    void test_L4_createLazyMonoAndCacheTTL() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);

        VirtualTimeScheduler vts = VirtualTimeScheduler.create();

        Mono<Integer> mono = L2_Mono.L4_createLazyMonoAndCacheTTL(counter, 2, vts);

        StepVerifier.create(mono)
          .expectNext(1)
          .verifyComplete();

        StepVerifier.create(mono)
          .expectNext(1)
          .verifyComplete();

        vts.advanceTimeBy(Duration.ofHours(1));

        StepVerifier.create(mono)
          .expectNext(2)
          .verifyComplete();
    }

    @Test
    void test_L5_getValue() {
        assertEquals(5, L2_Mono.L5_getValue(Mono.just(5)));
    }

    @Test
    void test_L6_getValueWithTimeout() {
        Assertions.assertThatThrownBy(() -> {
            L2_Mono.L6_getValueWithTimeout(Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(60).toMillis());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                return 42;
            }, Executors.newSingleThreadExecutor())), 1000);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void test_L7_getValueWithTimeoutOrElse() {
        assertEquals(5, L2_Mono.L7_getValueWithTimeoutOrElse(Mono.just(5), 5, 10));
        assertEquals(10, L2_Mono.L7_getValueWithTimeoutOrElse(Mono.delay(Duration.ofSeconds(5)).map(l -> 100), 2, 10));
    }

    @Test
    void test_L8_getCompletedFirst() {
        Integer block = L2_Mono.L8_getCompletedFirst(Mono.just(42), Mono.just(1).delayElement(Duration.ofSeconds(1)))
          .block();

        assertEquals(42, block);
    }
}
