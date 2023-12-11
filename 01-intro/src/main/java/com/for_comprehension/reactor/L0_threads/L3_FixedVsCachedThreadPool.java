package com.for_comprehension.reactor.L0_threads;

import com.for_comprehension.reactor.WorkshopUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L3_FixedVsCachedThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService e = Executors.newCachedThreadPool();

        for (int i = 0; i < 8; i++) {
            Thread.sleep(2000);
            e.submit(() -> {
                System.out.println("Hello!");
                sleep(30_000);
            });
        }

    }
}
