package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

class L4_AsyncExecution {

    public static void main(String[] args) {
        Flux.interval(Duration.ofSeconds(1))
          .publishOn(Schedulers.newSingle("first"))
          .map(i -> "map1: " + Thread.currentThread().getName())
          .log()
          .take(5)
          .publishOn(Schedulers.newSingle("second"))
          .map(i -> "map2: " + Thread.currentThread().getName())
          .publishOn(Schedulers.newSingle("third"))
          .log()
          .subscribeOn(Schedulers.boundedElastic())
          .publishOn(Schedulers.newSingle("fourth"))
          .map(i -> "map3: " + Thread.currentThread().getName())
          .publishOn(Schedulers.newSingle("fifth"))
          .log()
          .blockLast();
    }
}
