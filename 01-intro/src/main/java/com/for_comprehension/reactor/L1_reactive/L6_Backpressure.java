package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;

import java.time.Duration;

class L6_Backpressure {
    public static void main(String[] args) {
        Flux<Long> f1 = Flux.interval(Duration.ofMillis(10));
        Flux<Long> f2 = Flux.interval(Duration.ofSeconds(2));

        f1
          .onBackpressureLatest()
          .zipWith(f2, 5)
          .log()
          .blockLast();

    }
}
