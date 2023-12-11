package com.for_comprehension.reactor;

import jdk.jshell.spi.ExecutionControl;

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
}
