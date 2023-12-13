package com.for_comprehension.reactor.L0_threads;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;
import static com.for_comprehension.reactor.WorkshopUtils.timed;

class L9_VirtualThreadsPitfalls {
    public static void main(String[] args) {
        ExecutorService e = Executors.newVirtualThreadPerTaskExecutor();

        List<Integer> list = timed(() -> IntStream.range(0, 1_000).boxed()
          .map(i -> CompletableFuture.supplyAsync(() -> process(i), e))
          .toList().stream().map(CompletableFuture::join)
          .toList());
    }

    private static <T> T process(T input) {
        synchronized (new Object()) {
            System.out.printf("processing %s on %s%n", input, Thread.currentThread().getName());
            sleep(10_000);
            return input;
        }
    }
}
