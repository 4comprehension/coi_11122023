package com.example.demo;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class ParallelGetter {

    public static void main(String[] args) throws InterruptedException {
        RestClient client = RestClient.builder().build();
        ExecutorService e = Executors.newVirtualThreadPerTaskExecutor();
        List<HttpStatusCode> list = timed(() -> IntStream.range(0, 1000)
          .boxed()
          .map(i -> CompletableFuture.supplyAsync(() -> {
              try {
                  return client.get()
                    .uri("http://localhost:8080/users/42")
                    .retrieve()
                    .toBodilessEntity().getStatusCode();
              } catch (Exception ex) {
                  System.out.println(ex.getMessage());
                  throw new RuntimeException(ex);
              }
          }, e))
          .toList().stream()
          .map(CompletableFuture::join)
          .toList());

        System.out.println(list);
    }

    public static <T> T timed(Supplier<T> supplier) {
        Instant before = Instant.now();
        T result = supplier.get();
        Instant after = Instant.now();
        System.out.println("Duration: " + Duration.between(before, after).toMillis() + "ms");
        return result;
    }
}
