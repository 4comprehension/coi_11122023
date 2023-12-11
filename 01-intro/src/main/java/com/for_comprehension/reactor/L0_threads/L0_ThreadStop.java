package com.for_comprehension.reactor.L0_threads;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

class L0_ThreadStop {

    // Run on JDK 17
    public static void main(String[] args) throws InterruptedException {
        var concurrentProcessor = new ConcurrentProcessor();

        var t1 = processingThread(concurrentProcessor, Duration.ofMillis(100));
        var t2 = processingThread(concurrentProcessor, Duration.ofMillis(200));
        var t3 = processingThread(concurrentProcessor, Duration.ofMillis(500));
        Thread.sleep(1000);

        t1.stop();
        t3.stop();
    }

    public static class ConcurrentProcessor {

        private final AtomicBoolean processing = new AtomicBoolean(false);

        synchronized void process(int i) {
            if (processing.getAndSet(true)) {
                throw new IllegalStateException("this can't happen!");
            }

            System.out.println("Processing " + i + " on " + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            processing.set(false);
        }

    }
    private static Thread processingThread(ConcurrentProcessor concurrentProcessor, Duration processingTime) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                try {
                    Thread.sleep(processingTime.toMillis());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                concurrentProcessor.process(i);
            }
        });

        t1.start();
        return t1;
    }
}
