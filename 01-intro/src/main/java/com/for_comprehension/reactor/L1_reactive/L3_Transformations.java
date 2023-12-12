package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.util.function.Tuple2;

import java.time.Duration;

class L3_Transformations {

    public static void main(String[] args) {

        // Optional<Integer> foo = Stream.of(1,2,3).findFirst()
        Mono<String> m1 = getUsers().next();
        Mono<Long> m2 = getUsers().count();
        Flux<String> m3 = getUsers().skip(1);
        Flux<String> m4 = getUsers().skip(Duration.ofSeconds(1));
        Flux<String> m5 = getUsers().take(4);
        Flux<String> m6 = getUsers().take(Duration.ofSeconds(5));
        Mono<String> m7 = getUsers().reduce((s1, s2) -> s1 + s2);
        Flux<Tuple2<String, Long>> m8 = getUsers().zipWith(Flux.interval(Duration.ofSeconds(1)));
        Flux<GroupedFlux<Integer, String>> m9 = getUsers().groupBy(i -> i.length());
        Flux<Tuple2<Long, String>> m10 = getUsers().index();
        Flux<Long> m11 = getUsers().groupBy(i -> i.length()).flatMap(flux -> flux.count());
        Flux<Signal<String>> m12 = getUsers().materialize();
        Mono<String> reduce = getUsers().reduce((s1, s2) -> s1 + s2);
        Flux<String> scan = getUsers().scan((s1, s2) -> s1 + s2);
        Mono<String> scanLikeReduce = getUsers().scan((s1, s2) -> s1 + s2).last();
        Flux<Integer> concat = Flux.concat(Mono.just(1), Mono.just(2));

        getUsers()
          .map(s -> (Object) s.toUpperCase())
          .cast(String.class)
          .filter(i -> i.charAt(1) != '3');
    }

    public static Flux<String> getUsers() {
        return Flux.just("u1", "u2", "u3", "u4");
    }
}
