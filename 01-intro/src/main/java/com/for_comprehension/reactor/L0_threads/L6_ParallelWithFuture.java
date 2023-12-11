package com.for_comprehension.reactor.L0_threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;
import static com.for_comprehension.reactor.WorkshopUtils.timed;

class L6_ParallelWithFuture {

    public static void main(String[] args) throws Exception {
        List<Integer> output = timed(() -> {
            List<Integer> list = List.of(1, 2, 3, 4);
            List<Integer> result = Collections.synchronizedList(new ArrayList<>());
            try (var e = Executors.newFixedThreadPool(4)) {
                List<Future<Integer>> results = new ArrayList<>();
                for (Integer i : list) {
                    results.add(e.submit(() -> process(i)));
                }
                for (Future<Integer> f : results) {
                    try {
                        result.add(f.get());
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            return result;
        });

        System.out.println(output);
    }

    public static int process(int input) {
        System.out.println("processing " + input + " on " + Thread.currentThread().getName());
        sleep(5000);
        return input * 2;
    }
}
