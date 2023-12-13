package com.for_comprehension.reactor.L1_reactive;

import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L13_Schedulers {
    public static void schedulers(String[] args) {
        // odpowiednik Executors.newCachedThreadPool
        // nadaje się do operacji blokujących
        // offloadujemy tam operacje blokujące, żeby zachowywały się jak nieblokujące
        Schedulers.boundedElastic();

        // najlepiej nigdy nie korzystać
        Schedulers.parallel();

//        Schedulers.immediate();
//        Schedulers.single();
    }

    public static void customParallelbulkhead() {
        Scheduler scheduler = Schedulers.newParallel("reactor-parallel", 4);
        Scheduler parallelSorting = Schedulers.newParallel("parallel-sorting", 4);

        Flux.interval(Duration.ofSeconds(1), scheduler)
          .cache(Duration.ofMillis(200), scheduler);

    }

    public static void blockingOperationOffload() {
        BlockHound.install();
        Flux.interval(Duration.ofSeconds(1))
          .flatMap(i -> Mono.fromCallable(() -> fetchUsers()).subscribeOn(Schedulers.boundedElastic()))
          .blockLast();
    }

    public static List<String> fetchUsers() {
        System.out.println(Thread.currentThread().getName());
        sleep(1000);
        return List.of("Adam", "Ewa");
    }
}
