package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

class L15_Context {

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
          .doOnEach(e -> {
              if (e.isOnNext()) {
                  System.out.println(e.getContextView().<String>get("tid"));
              }
          })
          .publishOn(Schedulers.boundedElastic())
          .doOnEach(e -> {
              if (e.isOnNext()) {
                  System.out.println(e.getContextView().<String>get("tid"));
              }
          })
          .flatMap(i -> Flux.just(i).subscribeOn(Schedulers.boundedElastic()))
          .doOnEach(e -> {
              if (e.isOnNext()) {
                  System.out.println(e.getContextView().<String>get("tid"));
              }
          })
          .filter(i -> true)
          .doOnEach(e -> {
              if (e.isOnNext()) {
                  System.out.println(e.getContextView().<String>get("tid"));
              }
          })
          .flatMap(i -> Mono.deferContextual(ctx -> Mono.fromSupplier(() -> {
              System.out.println("map: " + ctx.get("tid"));
              return i;
          })))
          .contextWrite(Context.of("tid", currentTx.get()))
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
