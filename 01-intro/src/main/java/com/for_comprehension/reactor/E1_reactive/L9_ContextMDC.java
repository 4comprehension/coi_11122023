package com.for_comprehension.reactor.E1_reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.scheduler.Schedulers;
import reactor.util.Loggers;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

class L9_ContextMDC {
    private static final Logger log = LoggerFactory.getLogger(L9_ContextMDC.class);

    public static void main(String[] args) {
        Loggers.useSl4jLoggers();
        MDC.put("tid", "Centralny Ośrodek Informatyki");
        log.info("");

        Flux.interval(Duration.ofSeconds(1))
          .take(5)
          .map(withMdc(i -> {
              log.info("map: " + i);
              return i;
          }))
          .publishOn(Schedulers.boundedElastic())
          .flatMap(i -> Flux.just(i).subscribeOn(Schedulers.boundedElastic()))
          .filter(i -> true)
          .doOnEach(mdc(i -> log.info(i.toString())))
          .log()
          .map(i -> i)
          .blockLast();

    }

    // recover "tid" value and put it in MDC, cleanup after
    private static <T, R> Function<T, Mono<R>> withMdc(Function<T, R> function) {
        return null; // TODO
    }

    private static <T> Consumer<Signal<T>> mdc(Consumer<T> consumer) {
        return null; // TODO
    }


}
