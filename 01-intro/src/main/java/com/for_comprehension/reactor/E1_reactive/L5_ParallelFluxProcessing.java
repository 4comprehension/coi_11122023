package com.for_comprehension.reactor.E1_reactive;

import com.for_comprehension.reactor.WorkshopUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

class L5_ParallelFluxProcessing {

    public static void main(String[] args) {
        ExecutorService e1 = Executors.newCachedThreadPool();
        ExecutorService e2 = Executors.newCachedThreadPool();
        L5_ParallelFluxProcessing.L6_processStaged(Flux.just(1, 2, 3, 4), i -> process(i), i -> process2(i), e1, e2)
          .block();
    }

    /**
     * Process the Flux in parallel using the provided mapping function
     */
    static <T, R> Mono<Set<R>> L1_processParallel(Flux<T> flux, Function<T, Mono<R>> mapping, Executor ex) {
        return flux.flatMap(e -> mapping.apply(e).subscribeOn(Schedulers.fromExecutor(ex)))
          .collect(Collectors.toSet());
    }

    /**
     * Process the sequence in parallel (maintain ordering) and collect to list, max parallelism: 2
     */
    static <T, R> Mono<List<R>> L2_processParallelOrdered(Flux<T> flux, Function<T, Mono<R>> mapping, Executor ex) {
        return flux
          .flatMapSequential(e -> mapping.apply(e).subscribeOn(Schedulers.fromExecutor(ex)), 2)
          .collectList();
    }

    /**
     * Process the sequence in parallel and collect results produced by each thread into separate lists, parallelism 3
     */
    static <T, R> Flux<List<R>> L3_processInGroups(Flux<T> flux, Function<T, Mono<R>> mapping, Executor e) {
        return flux.parallel(3)
          .runOn(Schedulers.fromExecutor(e))
          .flatMap(mapping)
          .groups()
          .flatMap(Flux::collectList);
    }

    /**
     * Process the sequence in parallel and sum all the results
     */
    static Mono<Long> L4_flatMapReduce(Flux<Integer> flux, Function<Integer, Mono<Integer>> mapping, Executor executor) {
        return flux.flatMap(i -> mapping.apply(i).subscribeOn(Schedulers.fromExecutor(executor)))
          .reduce(0L, Long::sum);
    }

    /**
     * Process the sequence in parallel on executor 1 with max parallelism 3 and then on executor 2 with max parallelism 2
     */
    static <T, R, RR> Mono<List<RR>> L5_processOnTwoExecutors(Flux<T> flux, Function<T, Mono<R>> f1, Function<R, Mono<RR>> f2, Executor e1, Executor e2) {
        return flux
          .flatMapSequential(e -> f1.apply(e).subscribeOn(Schedulers.fromExecutor(e1)), 3)
          .flatMapSequential(e -> f2.apply(e).subscribeOn(Schedulers.fromExecutor(e2)), 2)
          .collectList();
    }

    /**
     * Process the sequence in parallel on executor 1 with max parallelism 4 and then on executor 2 with max parallelism 2
     * <p>
     * Ensure that this flux is processed in two stages:
     * - stage 1: all f1 operations
     * - stage 2: all f2 operations
     */
    static <T, R, RR> Mono<List<RR>> L6_processStaged(Flux<T> flux, Function<T, Mono<R>> f1, Function<R, Mono<RR>> f2, Executor e1, Executor e2) {
        return flux
          .flatMapSequential(e -> f1.apply(e).subscribeOn(Schedulers.fromExecutor(e1)), 3)
          .collectList().flatMapIterable(list -> list)
          .flatMapSequential(e -> f2.apply(e).subscribeOn(Schedulers.fromExecutor(e2)), 2)
          .collectList();
    }

    private static <T> Mono<T> process(T input) {
        return Mono.fromCallable(() -> {
            try {
                System.out.printf("Processing %s on %s%n", input.toString(), Thread.currentThread().getName());
                Thread.sleep(5000 + new Random().nextInt(5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return input;
        });
    }

    private static <T> Mono<T> process2(T input) {
        return Mono.fromCallable(() -> {
            try {
                System.out.printf("Processing2 %s on %s%n", input.toString(), Thread.currentThread().getName());
                Thread.sleep(1000 + new Random().nextInt(10000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return input;
        });
    }
}
