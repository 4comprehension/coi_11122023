package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

class L10_Broadcast {

    public static void main(String[] args) throws InterruptedException {
        Flux<Long> source = Flux.interval(Duration.ofSeconds(1))
          .doOnNext(c -> System.out.println("source: " + c));

        source.subscribe();

        Thread.sleep(5000);

        Flux<Long> hotFlux = source
          .publish()
          .refCount(2);

        hotFlux.doOnNext(c -> System.out.println("received: " + c))
          .subscribeOn(Schedulers.boundedElastic())
          .subscribe();

        System.out.println("first sub connected");

        Thread.sleep(2000);

        hotFlux.doOnNext(c -> System.out.println("received: " + c))
          .subscribeOn(Schedulers.boundedElastic())
          .subscribe();

        System.out.println("second sub connected");

        Thread.sleep(100000);
    }
}
