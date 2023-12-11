package com.for_comprehension.reactor.L0_threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L3_ThreadPoolExecutor {

    public static void main(String[] args) throws InterruptedException {
        int threads = 4;
        int maxThreads = 8;
        int queueSize = 4;
        ExecutorService e = new ThreadPoolExecutor(threads, maxThreads,
          30L, TimeUnit.SECONDS,
          new LinkedBlockingQueue<>(queueSize),
          new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < maxThreads + queueSize + 1; i++) {
            int finalI = i;
            e.submit(() -> {
                System.out.println("Hello! " + finalI + " on thread: " + Thread.currentThread().getName());
                sleep(30_000);
            });
        }
    }
}
