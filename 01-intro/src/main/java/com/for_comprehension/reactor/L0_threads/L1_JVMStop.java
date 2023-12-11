package com.for_comprehension.reactor.L0_threads;

class L1_JVMStop {

    // jvm zamyka się, kiedy wszystkie wątki niedemonowe się zakończą
    public static void main(String[] args) throws InterruptedException {
        var t = new Thread(() -> {
            System.out.println("Hello!");
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t.setDaemon(true);
        t.start();
    }
}
