package com.for_comprehension.reactor.E1_reactive;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static com.for_comprehension.reactor.E1_reactive.L7_CustomFluxes.L12_fibonacci;
import static com.for_comprehension.reactor.E1_reactive.L7_CustomFluxes.L2_readFile;

class L7_CustomFluxesTest {
    @Test
    public void testL12_fibonacci() {
        StepVerifier.create(L12_fibonacci().take(10))
          .expectNext(0L)
          .expectNext(1L)
          .expectNext(1L)
          .expectNext(2L)
          .expectNext(3L)
          .expectNext(5L)
          .expectNext(8L)
          .expectNext(13L)
          .expectNext(21L)
          .expectNext(34L)
          .verifyComplete();
    }

    @Test
    public void testL2_readFile() {
        StepVerifier.create(L2_readFile())
          .expectNext("foo")
          .expectNext("bar")
          .expectNext("42")
          .verifyComplete();
    }
}
