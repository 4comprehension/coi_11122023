package com.for_comprehension.reactor.E1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.for_comprehension.reactor.WorkshopUtils.todo;

class L3_Flux {

    /**
     * Create an empty {@link Flux}
     */
    static <T> Flux<T> L0_createEmptyFlux() {
        return todo();
    }

    /**
     * Create a {@link Flux} that returns three next increments of counter value <strong>eagerly</strong>
     */
    static Flux<Integer> L1_createEagerFlux(AtomicInteger counter) {
        return todo();
    }

    /**
     * Create a {@link Flux} that returns three next increments of counter value <strong>lazily</strong>
     */
    static Flux<Integer> L2_createLazyFlux(AtomicInteger counter) {
        return todo();
    }

    /**
     * Create a {@link Flux} that returns three next increments of counter value <strong>lazily</strong>
     *
     * @implNote values of the {@link Flux} needs to be cached so that subsequent calls do not result in counter increment
     */
    static Flux<Integer> L3_createLazyFluxAndCache(AtomicInteger counter) {
        return todo();
    }

    /**
     * Create a {@link Flux} that returns three next increments of counter value <strong>lazily</strong>
     *
     * @implNote values of the {@link Flux} needs to be cached so that subsequent calls do not result in counter increment <strong>(ttl should be set to a provided value)</strong>
     */
    static Flux<Integer> L4_createLazyFluxAndCacheWithTTL(AtomicInteger counter, int millis) {
        return todo();
    }

    /**
     * Get value from a {@link Flux} and collect them into a {@link List}
     *
     * @implNote don't worry about blocking, it's fine (for now :) )
     */
    static List<Integer> L5_getValue(Flux<Integer> flux) {
        return todo();
    }

    /**
     * Get value from a {@link Flux} and collect them into a {@link List} or throw an exception after provided timeout
     *
     * @implNote don't worry about blocking, it's fine (for now :) )
     */
    static List<Integer> L6_getValueWithTimeout(Flux<Integer> flux, int millis) {
        return todo();
    }

    /**
     * Get value from a {@link Flux} and collect them into a {@link List} or return the fallbackValue after provided timeout
     *
     * @implNote don't worry about blocking, it's fine (for now :) )
     */
    static List<Integer> L7_getValueWithTimeoutOrElse(Flux<Integer> flux, int seconds, int fallbackValue) {
        return todo();
    }

    /**
     * Get the first value from a {@link Flux}
     *
     * @implNote don't worry about blocking, it's fine (for now :) )
     */
    static Integer L8_getFirstValue(Flux<Integer> flux) {
        return todo();
    }

    /**
     * Fix the below code without resorting to any blocking {@link Flux} operators
     */
    static List<Integer> L9_fixMissingValues(Flux<Integer> flux) throws InterruptedException {
        var results = Collections.<Integer>synchronizedList(new ArrayList<>());
        flux.doOnNext(results::add);

        Thread.sleep(1000);
        return results;
    }

    /**
     * Fix the below code without resorting to any blocking {@link Flux} operators
     *
     * @implNote use idiomatic {@link Flux} and no {@link Flux#doOnNext(Consumer)}
     */
    static List<Integer> L10_fixMissingValues(Flux<Integer> flux) {
        var finished = new AtomicBoolean();
        var results = Collections.<Integer>synchronizedList(new ArrayList<>());

        while (!finished.get()) {
            Thread.onSpinWait();
        }
        return results;
    }

    /**
     * Convert {@link Mono} to {@link Flux} by repeating {@link Mono}'s value N times
     */
    static Flux<Integer> L11_repeatMono(Mono<Integer> mono, int n) {
        return todo();
    }
}
