package com.for_comprehension.reactor.L0_threads;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L6_Future {

    public static void main(String[] args) throws Exception {
        try (var e = Executors.newFixedThreadPool(4)) {
            Future<Integer> submit = e.submit(() -> process(42));
            //...
            Integer result = submit.get();
            System.out.println(result);
        }
    }

    public static int process(int input) {
        System.out.println("processing " + input + " on " + Thread.currentThread().getName());
        sleep(5000);
        return input * 2;
    }
}
