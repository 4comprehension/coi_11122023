package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

class L7_Retries {
    public static void main(String[] args) {
        List<String> result = Flux.interval(Duration.ofMillis(5))
          .take(500)
          .onBackpressureBuffer(300)
          .flatMapSequential(i -> getUserById(i).retryWhen(Retry.backoff(5, Duration.ofSeconds(1))))
          .log()
//          .onErrorContinue(NoSuchElementException.class, (throwable, o) -> {})
          .collectList()
          .subscribeOn(Schedulers.boundedElastic())
          .block();

        System.out.println(result);
    }

    public static Mono<String> getUserById(long id) {
        return Mono.fromCallable(() -> switch (ThreadLocalRandom.current().nextInt() % 2) {
            case 1 -> throw new NoSuchElementException();
            default -> "user-" + id;
        });
    }
}
