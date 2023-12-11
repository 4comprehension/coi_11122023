package com.for_comprehension.reactor;

import jdk.jshell.spi.ExecutionControl;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public final class WorkshopUtils {

    private WorkshopUtils() {
    }

    public static <T> T todo() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T timed(Supplier<T> supplier) {
        Instant before = Instant.now();
        T result = supplier.get();
        Instant after = Instant.now();
        System.out.println("Duration: " + Duration.between(before, after).toMillis() + "ms");
        return result;
    }
}
