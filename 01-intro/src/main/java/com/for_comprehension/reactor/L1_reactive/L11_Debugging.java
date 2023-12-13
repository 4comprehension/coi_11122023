package com.for_comprehension.reactor.L1_reactive;

import reactor.core.publisher.Flux;
import reactor.tools.agent.ReactorDebugAgent;

import java.time.Duration;

class L11_Debugging {

    public static void main(String[] args) {
//        Hooks.onOperatorDebug();
        ReactorDebugAgent.init();

        Flux.interval(Duration.ofSeconds(1))
          .map(i -> i)
          .filter(i -> true)
          .map(i -> {
              if (i == 10) {
                  throw new IllegalStateException();
              } else {
                  return i;
              }
          })
//          .checkpoint("checkpoint 1")
          .filter(i -> true)
          .log()
          .buffer(2)
          .filter(i -> true)
//          .checkpoint("checkpoint 2")
          .flatMapIterable(l -> l)
          .map(i -> true)
          .blockLast();
    }
}
