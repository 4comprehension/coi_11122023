package com.for_comprehension.reactor.L0_threads;

class L0_Thread {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("Hello World from another thread: " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.start();

        Thread.sleep(10000);
    }
}
