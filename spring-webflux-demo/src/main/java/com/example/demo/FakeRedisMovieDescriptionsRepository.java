package com.example.demo;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
class FakeRedisMovieDescriptionsRepository implements MovieDescriptionsRedisRepository {
    @Override
    public Mono<String> getForMovieId(int id) {
        return switch (id) {
            case 1 -> Mono.just("Spiderman");
            case 2 -> Mono.just("Spiderman 2");
            case 3 -> Mono.just("Spiderman 3");
            default -> Mono.just("lorem ipsum");
        };
    }
}
