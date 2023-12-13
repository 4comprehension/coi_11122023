CREATE TABLE rental_history
(
    id         serial PRIMARY KEY,
    event_type TEXT   NOT NULL,
    timestamp  TIMESTAMP DEFAULT NOW(),
    account_id BIGINT NOT NULL,
    movie_id   BIGINT NOT NULL
);
