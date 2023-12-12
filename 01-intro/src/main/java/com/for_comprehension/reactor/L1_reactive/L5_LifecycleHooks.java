package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.time.Duration;

import static com.for_comprehension.reactor.WorkshopUtils.sleep;

class L5_LifecycleHooks {
    public static void main(String[] args) {
        Flux<Tuple2<Integer, Long>> flux = Flux.just(1, 2, 3, 4)
          .zipWith(Flux.interval(Duration.ofSeconds(1)))
          .concatWith(Mono.error(new NullPointerException()));

        Flux<Tuple2<Integer, Long>> flux2 = flux.map(i -> i)
          .doFirst(() -> System.out.println("doFirst 1"))
          .doFirst(() -> System.out.println("doFirst 2"))
          .doFirst(() -> System.out.println("doFirst 3"))
          .doOnCancel(() -> System.out.println("doOnCancel()"))
          .takeUntil(i -> i.getT1().equals(3))
          .doOnEach(e -> System.out.println("doOnEach: " + e))
          .doOnDiscard(Object.class, o -> System.out.println("doOnDiscard(): " + o))
          .doOnNext(e -> System.out.println("doOnNext: " + e))
          .doOnError(e -> System.out.println("doOnError:" + e))
          .doOnTerminate(() -> System.out.println("doOnTerminate() 1"))
          .doOnTerminate(() -> System.out.println("doOnTerminate() 2"))
          .doAfterTerminate(() -> System.out.println("doAfterTerminate() 1"))
          .doAfterTerminate(() -> System.out.println("doAfterTerminate() 2"))
          .doFinally(e -> System.out.println("doFinally() 1: " + e.toString()))
          .doFinally(e -> System.out.println("doFinally() 2: " + e.toString()))
          .doOnComplete(() -> System.out.println("doOnComplete()"))
          .doOnRequest(r -> System.out.println("doOnRequest: " + r));

        flux2.subscribe();
        sleep(500);
        flux2.cancelOn(Schedulers.boundedElastic());

        flux.blockLast();
    }
}
