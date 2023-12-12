package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L4_ParallelExecution {

    public static void main(String[] args) {
        List<Integer> result = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
          .publishOn(Schedulers.boundedElastic())
          .flatMapSequential(i -> Mono.fromCallable(() -> process(i)).subscribeOn(Schedulers.boundedElastic()), 20)
          .collectList()
          .block();

        System.out.println(result);
    }

    public static void parallel_flatmap_unordered(String[] args) {
        List<Integer> result = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
          .publishOn(Schedulers.boundedElastic())
          .flatMap(i -> Mono.fromCallable(() -> process(i)).subscribeOn(Schedulers.boundedElastic()), 20)
          .collectList()
          .block();

        System.out.println(result);
    }

    public static void parallel_cpu_bound(String[] args) {
        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
          .publishOn(Schedulers.boundedElastic())
          .parallel()
          .runOn(Schedulers.parallel())
          .map(i -> process(i))
          .sequential()
          .collectList()
          .block();
    }

    public static <T> T process(T input) {
        System.out.println("processing " + input + " on " + Thread.currentThread().getName());
        sleep(5000);
        return input;
    }
}
