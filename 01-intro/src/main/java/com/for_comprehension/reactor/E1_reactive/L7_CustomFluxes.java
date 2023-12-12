package com.for_comprehension.reactor.E1_reactive;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.BaseStream;

class L7_CustomFluxes {
    /**
     * Return {@link Flux} representing Fibonacci sequence starting from 0
     * <p>
     * {@link Flux#generate}
     *
     * @implNote Fibonacci is: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144...
     */
    static Flux<Long> L12_fibonacci() {
        return Flux.generate(() -> Tuples.of(0L, 1L), (state, sink) -> {
            sink.next(state.getT1());
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });
    }

    /**
     * Read "file.txt"(on classpath) into a {@link Flux} and remember to clean up resources
     *
     * @implNote standard Java APIs are blocking, this needs to be addressed
     */
    static Flux<String> L2_readFile() {
        return Flux.using(() -> Files.lines(Path.of(L7_CustomFluxes.class.getClassLoader().getResource("file.txt")
            .toURI())), Flux::fromStream, BaseStream::close)
          .subscribeOn(Schedulers.boundedElastic());
    }
}
