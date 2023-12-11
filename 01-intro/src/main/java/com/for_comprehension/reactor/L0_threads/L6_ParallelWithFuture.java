package com.for_comprehension.reactor.L0_threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L6_ParallelWithFuture {

    public static void main(String[] args) throws Exception {
        List<Integer> list = List.of(1, 2, 3, 4);
        List<Integer> result = Collections.synchronizedList(new ArrayList<>());
        try (var e = Executors.newFixedThreadPool(4)) {
        }

        System.out.println(result);
    }

    public static int process(int input) {
        System.out.println("processing " + input + " on " + Thread.currentThread().getName());
        sleep(5000);
        return input * 2;
    }
}
