package com.for_comprehension.reactor.E1_reactive;

import com.for_comprehension.reactor.WorkshopUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

class L8_Batching {

    /**
     * Buffer the incoming {@link Flux} into lists of N items and then emit these lists
     */
    static Flux<List<Integer>> L1_buffer(Flux<Integer> numbers, int n) {
        return WorkshopUtils.todo();
    }

    /**
     * Buffer the incoming {@link Flux} into lists of max N items... but do not wait more than provided timeout
     */
    static Flux<List<Integer>> L2_bufferOrTimeout(Flux<Integer> numbers, int n, Duration timeout) {
        return WorkshopUtils.todo();
    }

    /**
     * Create a sliding window that buffers items from the incoming {@link Flux}.
     * Each buffered list contains N items and starts a new buffer window every M items.
     * For example, if maxSize is 5 and skip is 3:
     * Input: 1, 2, 3, 4, 5, 6, 7, 8, 9
     * Output: [1, 2, 3, 4, 5], [4, 5, 6, 7, 8], [7, 8, 9]
     * <p>
     * Note how values overlap
     * <p>
     * Bonus: buffer into LinkedLists
     */
    static Flux<List<Integer>> L3_overlappingBuffer(Flux<Integer> numbers, int maxSize, int skip) {
        return WorkshopUtils.todo();
    }
}
