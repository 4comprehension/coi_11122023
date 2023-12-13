package com.example.demo;

import jdk.jfr.ContentType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
class ReactiveMovieController {

    private final MovieService userService;

    ReactiveMovieController(MovieService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/movies-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MovieService.MovieWithDescription> getMovies() {
        return userService.getMovies().delayElements(Duration.ofSeconds(5));
    }

    @GetMapping(value = "/movies-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieService.MovieWithDescription> getMovies2() {
        return userService.getMovies().delayElements(Duration.ofSeconds(5));
    }

    @GetMapping(value = "/movies-ndjson", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MovieService.MovieWithDescription> getMovies3() {
        return userService.getMovies().delayElements(Duration.ofSeconds(5));
    }
}
