package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class L8_ResourcesManagement {

    public static void main(String[] args) {
        Flux.using(() -> Executors.newFixedThreadPool(4), Mono::just, ExecutorService::shutdown)
          .doOnNext(e -> e.submit(() -> {}))
          .blockLast();
    }
}
