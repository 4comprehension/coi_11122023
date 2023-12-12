package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

class L7_RetriesOnErrorResume {
    public static void main(String[] args) throws InterruptedException {
        List<String> block = Flux.interval(Duration.ofMillis(500))
          .take(100)
          .flatMap(i -> getUserById(i))
          .onErrorResume(NoSuchElementException.class, throwable -> Flux.just("a", "b", "c"))
//          .onErrorReturn("fallback")
          .log()
          .collectList()

          .subscribeOn(Schedulers.boundedElastic())
          .block();

        System.out.println(block);
    }

    public static Mono<String> getUserById(long id) {
        return Mono.fromCallable(() -> switch (ThreadLocalRandom.current().nextInt() % 2) {
            case 1 -> throw new NoSuchElementException();
            default -> "user-" + id;
        });
    }
}
