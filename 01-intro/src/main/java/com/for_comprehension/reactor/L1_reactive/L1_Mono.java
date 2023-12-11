package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

class L1_Mono {

    public static void main(String[] args) {
        Mono<String> user = Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
              try {
                  Thread.sleep(5000);
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              }
              return "U";
          }))
          .timeout(Duration.ofMillis(100))
          .doOnEach(System.out::println);
        String result = user.block(Duration.ofSeconds(1));

        System.out.println(result);
    }

    public static Mono<String> getUserById(int id) {
        return Mono.just("user" + id);
    }
}
