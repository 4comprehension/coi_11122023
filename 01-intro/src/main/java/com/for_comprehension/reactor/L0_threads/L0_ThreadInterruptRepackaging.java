package com.for_comprehension.reactor.L0_threads;

class L0_ThreadInterruptRepackaging {

    public static void main(String[] args) {
        try {
            processEmails();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public static void processEmails() throws InterruptedException {
        // ...
    }
}
