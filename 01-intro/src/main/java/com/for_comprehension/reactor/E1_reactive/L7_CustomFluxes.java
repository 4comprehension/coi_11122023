package com.for_comprehension.reactor.E1_reactive;

import reactor.core.publisher.Flux;

import java.nio.file.Files;

class L7_CustomFluxes {
    /**
     * Return {@link Flux} representing Fibonacci sequence starting from 0
     * <p>
     * {@link Flux#generate}
     *
     * @implNote Fibonacci is: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144...
     */
    static Flux<Long> L12_fibonacci() {
        return null;
    }

    /**
     * Read "file.txt"(on classpath) into a {@link Flux} and remember to clean up resources
     *
     * @implNote standard Java APIs are blocking, this needs to be addressed
     */
    // Files.
    static Flux<String> L2_readFile() {
        return null;
    }
}
