package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L4_InterestingCaseOfAsync {
    public static void main(String[] args) {
        Mono.fromCallable(() -> calculate())
          .log()
          .publishOn(Schedulers.boundedElastic())
          .doOnNext(i -> System.out.println("thread: " + Thread.currentThread().getName()))
          .block();
    }

    private static String calculate() {
        sleep(1000);
        System.out.println(Thread.currentThread().getName());
        return "foo";
    }
}
