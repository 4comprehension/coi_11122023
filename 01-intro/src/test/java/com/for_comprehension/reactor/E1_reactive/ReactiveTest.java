package com.for_comprehension.reactor.E1_reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

class ReactiveTest {

    @Test
    void example_stepverifier() throws Exception {
        StepVerifier.create(getUsers())
          .expectNextMatches(s -> s.startsWith("user"))
          .expectNextMatches(s -> s.startsWith("user"))
          .expectNextMatches(s -> s.startsWith("user"))
          .verifyComplete();
    }

    @Test
    void example_virtual_time() throws Exception {
        StepVerifier.withVirtualTime(() -> getUsers())
          .thenAwait(Duration.ofSeconds(6))
          .expectNextMatches(s -> s.startsWith("user"))
          .thenAwait(Duration.ofSeconds(6))
          .expectNextMatches(s -> s.startsWith("user"))
          .thenAwait(Duration.ofSeconds(6))
          .expectNextMatches(s -> s.startsWith("user"))
          .verifyComplete();
    }

    @Test
    void example_manual_vt_scheduler() throws Exception {
        VirtualTimeScheduler vts = VirtualTimeScheduler.create();
        vts.advanceTimeBy(Duration.ofHours(1));
        Flux.interval(Duration.ofSeconds(1), vts)
          .take(10)
          .doOnEach(System.out::println)
          .subscribeOn(vts)
          .subscribe();

        vts.advanceTimeBy(Duration.ofHours(1));
    }

    private static Flux<String> getUsers() {
        return Flux.interval(Duration.ofSeconds(5))
          .zipWith(Flux.just("user1", "user2", "user3"), (__, string) -> string);
    }
}
