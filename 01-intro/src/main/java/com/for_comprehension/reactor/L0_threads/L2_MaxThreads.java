package com.for_comprehension.reactor.L0_threads;

class L2_MaxThreads {

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(finalI);
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    /*
    16352
[4.515s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 2048k, guardsize: 16k, detached.
[4.515s][warning][os,thread] Failed to start the native thread for java.lang.Thread "Thread-16353"
Exception in thread "main" java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
	at java.base/java.lang.Thread.start0(Native Method)
	at java.base/java.lang.Thread.start(Thread.java:1526)
	at com.for_comprehension.reactor.L0_threads.L2_MaxThreads.main(L2_MaxThreads.java:15)



     */
}
