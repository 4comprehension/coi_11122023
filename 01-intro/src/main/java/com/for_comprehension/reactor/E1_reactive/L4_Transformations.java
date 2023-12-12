package com.for_comprehension.reactor.E1_reactive;

import com.for_comprehension.reactor.WorkshopUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.Map;

import static com.for_comprehension.reactor.WorkshopUtils.todo;

class L4_Transformations {

    /**
     * Increment each element in the given Flux of Integers by 1
     * Bonus question: what thread is it executed on?
     */
    static Flux<Integer> L0_incrementAll(Flux<Integer> integers) {
        return todo();
    }

    /**
     * Convert each incoming element into a Integer
     */
    static Flux<Integer> L1_castToInteger(Flux<Object> integers) {
        return todo();
    }

    /**
     * Convert a {@link Flux} of integers into a {@link Mono} containing its sum
     */
    static Mono<Integer> L2_sumAll(Flux<Integer> integers) {
        return todo();
    }

    /**
     * Filter out all the numbers greater than 10 in the incoming {@link Flux}
     */
    static Flux<Integer> L3_discardFluxItems(Flux<Integer> numbers) {
        return todo();
    }

    /**
     * Return a Mono that represents the first element in the incoming {@link Flux}
     */
    static Mono<Integer> L4_takeNext(Flux<Integer> numbers) {
        return todo();
    }

    /**
     * Merge the two incoming {@link Flux}s into one ([1,2] + [3, 4] -> [1, 2, 3, 4])
     *
     * @implNote it's not about zipping, a solution in similar to combining two Java Streams together
     */
    static Flux<Integer> L5_combineIntoOne(Flux<Integer> first, Flux<Integer> second) {
        return todo();
    }

    static Flux<Integer> L5_combineIntoOne2(Flux<Integer> first, Flux<Integer> second) {
        return todo();
    }

    /**
     * Transform the incoming {@link Flux} to a Mono that represents the number of elements in the {@link Flux}
     */
    static Mono<Long> L6_countAllFluxItems(Flux<Integer> numbers) {
        return todo();
    }

    /**
     * Flatten the incoming {@link Flux} of Lists of Integers to a {@link Flux} of Integers
     */
    static Flux<Integer> L7_flatten(Flux<List<Integer>> flux) {
        return todo();
    }

    /**
     * Merge two {@link Flux}s into a single one by taking an element from each, and creating a "-" separated String
     * <p>
     * For example, if the first one returns ["foo", "bar"] and the second [1, 2], the output should return ["foo-1", "bar-2"]
     */
    static Flux<String> L8_combineTwoFluxes(Flux<String> first, Flux<Integer> second) {
        return todo();
    }

    /**
     * Suppress the first N elements in the incoming {@link Flux}
     */
    static Flux<Integer> L9_ignoreElements(Flux<Integer> numbers, int n) {
        return todo();
    }

    /**
     * Take only the first N elements in the incoming {@link Flux}
     */
    static Flux<Integer> L10_limitFluxSize(Flux<Integer> numbers, int n) {
        return todo();
    }

    /**
     * Take elements from the incoming {@link Flux} until an even number is encountered.
     */
    static Flux<Integer> L11_stopProcessingAfterCondition(Flux<Integer> numbers) {
        return todo();
    }

    /**
     * As you know, there are not just items flowing through a flux, but varius signals... let's materialize them
     *
     * @apiNote try to figure out how to reverse it
     */
    static <T> Flux<Signal<T>> L12_convertSignalsIntoItems(Flux<T> flux) {
        return todo();
    }

    /**
     * Emit tuples where first element is an index
     */
    static <T> Flux<Tuple2<Long, T>> L13_zipWithIndex(Flux<T> flux) {
        return todo();
    }

    /**
     * Get number of occurrences of each word
     */
    static Mono<Map<String, Long>> L14_wordCount(Flux<String> words) {
        return todo();
    }

    /**
     * Generate a stream of partial sums accumulated along the way
     */
    static Flux<Integer> L15_extractPartialSums(Flux<Integer> integers) {
        return todo();
    }
}
