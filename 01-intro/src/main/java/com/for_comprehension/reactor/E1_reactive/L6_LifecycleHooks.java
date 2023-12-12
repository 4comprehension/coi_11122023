package com.for_comprehension.reactor.E1_reactive;

import com.for_comprehension.reactor.WorkshopUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.SignalType;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Hooks can be registered using .doOn* methods
 */
class L6_LifecycleHooks {

    /**
     * Set the flag to true as soon as something subscribes to the {@link Flux}
     */
    static Flux<Long> L1_newSubscriptionHook(Flux<Long> numbers, AtomicBoolean flag) {
        return WorkshopUtils.todo();
    }

    /**
     * Set the flag to true as soon as {@link Flux} is cancelled
     */
    static Flux<Long> L2_cancellationHook(Flux<Long> numbers, AtomicBoolean flag) {
        return WorkshopUtils.todo();
    }

    /**
     * Make sure that counter represents the number of elements that are flowing through {@link Flux}
     */
    static Flux<Long> L3_nextHook(Flux<Long> numbers, AtomicInteger counter) {
        return WorkshopUtils.todo();
    }

    /**
     * Ensure that all encountered error messages are stored in the errors list
     *
     * @implNote assume that the list is thread-safe
     */
    static Flux<Long> L4_errorHook(Flux<Long> numbers, List<String> errors) {
        return WorkshopUtils.todo();
    }

    /**
     * Register a callback action to be executed upon termination of the Flux processing.
     * The callback should be invoked upon completion or if an error occurs.
     */
    static Flux<Long> L5_terminationHook(Flux<Long> numbers, Runnable callback) {
        return WorkshopUtils.todo();
    }

    /**
     * Register a callback action to be executed when the upstream terminates for any reason, including cancellation.
     */
    static Flux<Long> L6_finallyHook(Flux<Long> numbers, Consumer<SignalType> callback) {
        return WorkshopUtils.todo();
    }

    /**
     * Using {@link Flux#doFirst(Runnable)}, add the following values to the list: 1,2,3
     *
     * @implNote use three separate doFirst calls with hardcoded values, this exercise is about execution order :)
     */
    static Flux<Long> L7_firstHooksSequence(Flux<Long> numbers, List<Integer> values) {
        return WorkshopUtils.todo();
    }

    /**
     * Explore {@link Hooks}, we'll be back to some of those later :)
     */
    static void L8_globalHook() {
    }
}
