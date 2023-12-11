package com.for_comprehension.reactor.L0_threads;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L5_ParallelStreams {

    public static void main(String[] args) throws InterruptedException {
        new Thread(()-> {
            IntStream.range(0, 120).boxed()
              .parallel()
              .map(i -> {
                  sleep(Integer.MAX_VALUE);
                  return i;
              }).collect(Collectors.toList());
        }).start();

        Thread.sleep(500);

        List<Integer> list = IntStream.range(0, 120).boxed().toList();
        List<Integer> result = list.parallelStream()
          .map(i -> process(i))
          .toList();
    }

    public static <T> T process(T input) {
        System.out.println("processing " + input + " on " + Thread.currentThread().getName());
        sleep(5000);
        return input;
    }
}
