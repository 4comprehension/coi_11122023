package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
class ReactiveMovieController {

    private final MovieService userService;

    ReactiveMovieController(MovieService userService) {
        this.userService = userService;
    }

    @GetMapping("/movies")
    public Flux<MovieService.MovieWithDescirption> getMovies() {
        return userService.getMovies();
    }
}
