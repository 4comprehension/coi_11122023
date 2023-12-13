package com.example.demo;

import reactor.core.publisher.Mono;

interface MovieDescriptionsRedisRepository {
    Mono<String> getForMovieId(int id);
}
