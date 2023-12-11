package com.for_comprehension.reactor.L0_threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L7_CompletableFuture {

    public static void main(String[] args) {
        try (var e = Executors.newFixedThreadPool(4)) {
            CompletableFuture.supplyAsync(() -> 1, e)
              .thenApplyAsync(i -> printThread(i), e)
              .thenApplyAsync(i -> printThread(i), e)
              .thenApplyAsync(i -> printThread(i), e)
              .thenApplyAsync(i -> printThread(i), e)
              .thenApplyAsync(i -> printThread(i), e)
              .thenApplyAsync(i -> printThread(i), e)
              .thenAccept(System.out::println);
        }
    }

    private static int printThread(int input) {
        System.out.println(Thread.currentThread().getName());
        return input;
    }

    private static int process(int input) {
        System.out.println("processing " + input + " on " + Thread.currentThread().getName());
        sleep(ThreadLocalRandom.current().nextInt(6000));
        return input;
    }
}
