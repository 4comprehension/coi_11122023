package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

class L2_Flux {

    public static void main(String[] args) {
        List<String> block = getUsers()
          .collectList()
          .block();
    }

    public static Flux<String> getUsers() {
        return Flux.just("u1", "u2", "u3", "u4");
    }
}
