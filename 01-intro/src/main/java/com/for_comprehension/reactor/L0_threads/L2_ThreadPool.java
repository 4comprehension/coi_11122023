package com.for_comprehension.reactor.L0_threads;

import java.util.concurrent.Executors;

class L2_ThreadPool {
    public static void main(String[] args) {
        try (var executor = Executors.newFixedThreadPool(10)) {
            executor.submit(() -> System.out.println("Hello!"));
        }
    }
}
