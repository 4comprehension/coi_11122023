package com.for_comprehension.reactor.E1_reactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.scheduler.Schedulers;
import reactor.util.Loggers;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

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
          .flatMap(withMdc(i -> {
              log.info("map: " + i);
              return i;
          }))
          .flatMap(withThreadLocalValue(i -> {
              log.info("map: " + i);
              return i;
          }, ctx -> MDC.put("tid", ctx.get("tid")), ctx -> MDC.remove("tid")))
          .publishOn(Schedulers.boundedElastic())
          .flatMap(i -> Flux.just(i).subscribeOn(Schedulers.boundedElastic()))
          .filter(i -> true)
          .doOnEach(mdc(i -> log.info(i.toString())))
          .doOnEach(withThreadLocalValue(i -> {
              log.info("doOnEach: " + i);
          }, ctx -> MDC.put("tid", ctx.get("tid")), ctx -> MDC.remove("tid")))
          .map(i -> i)
          .contextWrite(Context.of("tid", MDC.get("tid")))
          .blockLast();
    }

    // recover "tid" value and put it in MDC, cleanup after
    private static <T, R> Function<T, Mono<R>> withMdc(Function<T, R> function) {
        return t -> Mono.deferContextual(ctx -> Mono.fromCallable(() -> {
            try (var __ = MDC.putCloseable("tid", ctx.get("tid"))) {
                return function.apply(t);
            }
        }));
    }

    private static <T, R> Function<T, Mono<R>> withThreadLocalValue(Function<T, R> function,
                                                                    Consumer<ContextView> setter,
                                                                    Consumer<ContextView> cleanup) {
        return t -> Mono.deferContextual(ctx -> Mono.fromCallable(() -> {
            setter.accept(ctx);
            try {
                return function.apply(t);
            } finally {
                cleanup.accept(ctx);
            }
        }));
    }

    private static <T> Consumer<Signal<T>> withThreadLocalValue(Consumer<T> consumer,
                                                                Consumer<ContextView> setter,
                                                                Consumer<ContextView> cleanup) {
        return signal -> {
            if (signal.isOnNext()) {
                setter.accept(signal.getContextView());
                try {
                    consumer.accept(signal.get());
                } finally {
                    cleanup.accept(signal.getContextView());
                }
            }
        };
    }

    private static <T> Consumer<Signal<T>> mdc(Consumer<T> consumer) {
        return signal -> {
            if (signal.isOnNext()) {
                try (var __ = MDC.putCloseable("tid", signal.getContextView().get("tid"))) {
                    consumer.accept(signal.get());
                }
            }
        };
    }
}
