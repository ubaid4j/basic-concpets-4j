package com.ubaid.forj;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

// ITEM 81: PREFER CONCURRENCY UTILITIES TO WAIT AND NOTIFY
public class Item81Test {
    
    
    public static long time(Executor executor, int concurrency, Runnable action) 
    throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);
        
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown(); // tell timer we are ready
                try {
                    start.await(); // wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }
        
        ready.await(); // wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown();
        done.await();
        return System.nanoTime() - startNanos;
    }
}
