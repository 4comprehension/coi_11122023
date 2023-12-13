package com.example.demo;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
class MovieService {
    private final DatabaseClient databaseClient;
    private final TransactionalOperator transactionalOperator;
    private final MovieDescriptionsRedisRepository descriptions;

    MovieService(DatabaseClient databaseClient, TransactionalOperator transactionalOperator, MovieDescriptionsRedisRepository descriptions) {
        this.databaseClient = databaseClient;
        this.transactionalOperator = transactionalOperator;
        this.descriptions = descriptions;
    }

    Mono<Void> insertMovies() {
        Mono<Void> atomicOperation = databaseClient
          .sql("INSERT INTO movies (id, title, type) VALUES('42', 'Tenet', 'NEW')")
          .fetch().rowsUpdated()
//          .then(Mono.error(new NullPointerException()))
          .then(databaseClient
            .sql("INSERT INTO movies (id, title, type) VALUES('43', 'Tenet 2', 'NEW')")
            .then())
          .as(transactionalOperator::transactional);

        return atomicOperation;
    }

    Flux<MovieWithDescirption> getMovies() {
        return databaseClient.sql("SELECT * FROM movies")
          .map(row -> new Movie(row.get("id", Integer.class), row.get("title", String.class), row.get("type", String.class)))
          .all()
          .flatMap(m -> descriptions.getForMovieId(m.id)
            .map(desc -> new MovieWithDescirption(m, desc)));
    }

    Mono<Void> create() {
        return databaseClient.sql("""
            CREATE TABLE MOVIES
            (
                id    bigint primary key,
                title text not null,
                type  text not null
            );
            """)
          .then();
    }

    record Movie(int id, String title, String type) {

    }

    record MovieWithDescirption(Movie movie, String description) {

    }
}
