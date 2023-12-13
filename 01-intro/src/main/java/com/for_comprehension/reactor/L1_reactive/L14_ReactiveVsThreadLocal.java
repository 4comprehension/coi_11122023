package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

class L14_ReactiveVsThreadLocal {

    public static void main(String[] args) {
        ThreadLocal<String> currentTx = new ThreadLocal<>();
        currentTx.set("tx id: 42");

        Flux.just(1, 2, 3, 4)
          .subscribeOn(Schedulers.boundedElastic())
          .flatMap(txid -> Mono.fromCallable(() -> {
              currentTx.set(String.valueOf(txid));
              return txid;
          }).subscribeOn(Schedulers.boundedElastic()))
          .blockLast();

        Flux.just(1, 2, 3, 4)
          .doOnNext(e -> System.out.println(currentTx.get()))
          .publishOn(Schedulers.boundedElastic())
          .doOnNext(e -> System.out.println(currentTx.get()))
          .flatMap(i -> Flux.just(i).subscribeOn(Schedulers.boundedElastic()))
          .doOnNext(e -> System.out.println(currentTx.get()))
          .filter(i -> true)
          .doOnNext(e -> System.out.println(currentTx.get()))
          .map(i -> i)
          .blockLast();
    }

    public static void threadLocalDemo() {
        ThreadLocal<Integer> tl = new ThreadLocal<>();

        tl.set(42);
        System.out.println(tl.get());

        new Thread(() -> {
            tl.set(1);
            System.out.println(tl.get());
        }).start();
        new Thread(() -> {
            tl.set(2);
            System.out.println(tl.get());
        }).start();

        System.out.println(tl.get());
    }
}
