package com.for_comprehension.reactor.L0_threads;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

class L0_ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        var concurrentProcessor = new ConcurrentProcessor();

        var t1 = startProcessingThread(concurrentProcessor, Duration.ofMillis(100));
        var t2 = startProcessingThread(concurrentProcessor, Duration.ofMillis(200));
        var t3 = startProcessingThread(concurrentProcessor, Duration.ofMillis(500));
        var t4 = startProcessingThread(concurrentProcessor, Duration.ofMillis(1000));
        var t5 = startProcessingThread(concurrentProcessor, Duration.ofMillis(2500));
        Thread.sleep(10_000);
        t1.interrupt();
        Thread.sleep(20_000);
        t2.interrupt();
        Thread.sleep(10_000);
        t3.interrupt();
        Thread.sleep(10_000);
        t4.interrupt();
        Thread.sleep(10_000);
        t5.interrupt();
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

    private static Thread startProcessingThread(ConcurrentProcessor concurrentProcessor, Duration processingTime) {
        var t = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("interrupted, closing gracefully...");
                    break;
                }
                try {
                    Thread.sleep(processingTime.toMillis());
                } catch (InterruptedException e) {
                    System.out.println("interrupted exception, closing gracefully...");
                    break;
                }

                concurrentProcessor.process(i);
            }
        });

        t.start();
        return t;
    }
}
