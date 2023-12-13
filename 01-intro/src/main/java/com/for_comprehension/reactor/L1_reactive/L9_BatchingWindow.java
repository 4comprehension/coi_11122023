package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

class L9_BatchingWindow {
    public static void main(String[] args) {
        Flux<Long> ticker = Flux.interval(Duration.ofMillis(200));

        ticker
          .take(9)
          .concatWith(Mono.delay(Duration.ofHours(1)))
          .windowTimeout(10, Duration.ofSeconds(5))
          .flatMap(Flux::collectList)
          .doOnNext(System.out::println)
          .blockLast();
    }

    public static void buffering(String[] args) {
        Flux<Long> ticker = Flux.interval(Duration.ofMillis(200));

        ticker
          .buffer(Duration.ofSeconds(1), Duration.ofSeconds(2))
          .doOnNext(System.out::println)
          .blockLast();
    }
}
