package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

class L0_ReactorThreadPoolPollution {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            polluteReactorThreadPool().subscribe();
        }

        Flux.interval(Duration.ofSeconds(1))
          .elapsed()
          .map(i -> "user: " + i)
          .doOnNext(System.out::println)
          .blockLast();
    }

    private static Flux<Long> polluteReactorThreadPool() {
        return Flux.interval(Duration.ofSeconds(1))
          .map(i -> {
              System.out.println(Thread.currentThread().getName());
              try {
                  Thread.sleep(1000000);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
              return i;
          })
          .doOnNext(System.out::println);
    }

    private static Flux<String> processUsers(Flux<String> users) {
        List<String> uppercased = users
          .map(e -> e.toUpperCase())
          .collectList()
          .block();

        // ...

        return Flux.fromIterable(uppercased);
    }

    private static Flux<String> users() {
        return Flux.just("foo", "bar");
    }
}



