package com.for_comprehension.reactor.L0_threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L4_ThreadFactory {

    public static void main(String[] args) throws InterruptedException {
        // before JDK21
//        ExecutorService e = Executors.newCachedThreadPool(named("mail-scheduler"));

        ExecutorService e = Executors.newCachedThreadPool(Thread.ofPlatform()
          .name("mail-scheduler-", 0)
          .factory());

        for (int i = 0; i < 2000; i++) {
            e.submit(() -> sleep(60_000));
        }
    }

    private static ThreadFactory named(String prefix) {
        return new ThreadFactory() {

            private final AtomicInteger seq = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, prefix + "-" + seq.getAndIncrement());
            }
        };
    }

    private static ThreadFactory prefixed(String prefix) {
        return new ThreadFactory() {

            private final ThreadFactory original = Executors.defaultThreadFactory();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = original.newThread(r);
                thread.setName(prefix + "-" + thread.getName());
                return thread;
            }
        };
    }
}
