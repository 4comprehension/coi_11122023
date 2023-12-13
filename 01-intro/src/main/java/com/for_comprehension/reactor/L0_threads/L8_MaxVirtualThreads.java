package com.for_comprehension.reactor.L0_threads;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;
import static com.for_comprehension.reactor.WorkshopUtils.timed;

class L8_MaxVirtualThreads {
    // os threads
    // threads: 4 -> 10010ms
    // threads: 10 -> 10026ms
    // threads: 100 -> 10019ms
    // threads: 1000 -> 10071ms
    // threads: 10000 -> 11442ms
    // threads: 15000 -> 13583ms
    // threads: 20000 -> 'unable to create native thread'

    // virtual threads
    // threads: 4 -> 10017ms
    // threads: 10 -> 10015ms
    // threads: 100 -> 10018ms
    // threads: 1000 -> 10050ms
    // threads: 10000 -> 10321ms
    // threads: 15000 -> 11042ms
    // threads: 20000 -> 11076ms
    // threads: 50000 -> 11566ms
    // threads: 100000 -> 12520ms
    // threads: 200000 -> 14844ms
    // threads: 500000 -> 20544ms
    // threads: 1000000 -> 28825ms
    // threads: 2000000 -> 46386ms
    // threads: 4000000 -> 66771ms
    // threads: 8000000 -> 121968ms
    // threads: 16000000 -> 320717ms

    public static void main(String[] args) {
        ExecutorService e = Executors.newVirtualThreadPerTaskExecutor();

        List<Integer> list = timed(() -> IntStream.range(0, 1_000).boxed()
          .map(i -> CompletableFuture.supplyAsync(() -> process(i), e))
          .toList().stream().map(CompletableFuture::join)
          .toList());
    }

    private static <T> T process(T input) {
        System.out.printf("processing %s on %s%n", input, Thread.currentThread().getName());
        sleep(10_000);
        return input;
    }
}
