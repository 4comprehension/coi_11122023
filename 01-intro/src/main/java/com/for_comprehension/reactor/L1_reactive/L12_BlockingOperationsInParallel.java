package com.for_comprehension.reactor.L1_reactive;

import com.for_comprehension.reactor.WorkshopUtils;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

class L12_BlockingOperationsInParallel {

    public static void main(String[] args) throws InterruptedException {
//        BlockHound.install();

        Flux.interval(Duration.ofSeconds(1))
          .take(20)
          .flatMap(i -> Mono.fromCallable(() -> {
              System.out.println(Thread.currentThread().getName());
              WorkshopUtils.sleep(Integer.MAX_VALUE);
              return i;
          }).subscribeOn(Schedulers.boundedElastic())).subscribe();

        Thread.sleep(12000);

        Flux.interval(Duration.ofSeconds(1))
          .doOnNext(System.out::println)
          .blockLast();
    }
}
