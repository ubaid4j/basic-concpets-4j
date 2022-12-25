package dev.ubaid.labs.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CyclicBarrierAndCountDownLatch {

    @SneakyThrows
    @Test
    void checkCyclicBarrierBehavior() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        
        Thread thread1 = new CyclicBarrierThread(cyclicBarrier);
        thread1.start();
        Thread thread2 = new CyclicBarrierThread(cyclicBarrier);
        thread2.start();
        Thread thread3 = new CyclicBarrierThread(cyclicBarrier);
        thread3.start();
        
        thread1.join();
        thread2.join();
        thread3.join();
    }
    
    @SneakyThrows
    @Test
    void checkCountDownBehavior() {
        CountDownLatch latch = new CountDownLatch(1);

        Thread thread1 = new CountDownThread(latch);
        thread1.start();
        Thread thread2 = new CountDownThread(latch);
        thread2.start();
        Thread thread3 = new CountDownThread(latch);
        thread3.start();
        
        Thread.sleep(ThreadLocalRandom.current().nextLong(5) * 1000);

        latch.countDown();
    }
    
    @Test
    @SneakyThrows
    void stopThread() {
        Thread th1 = new AwesomeThread();
        th1.start();
        Thread.sleep(5000);
        th1.interrupt();
        Thread.sleep(2000);
    }
}

@AllArgsConstructor
@Slf4j
class CyclicBarrierThread extends Thread {

    private final CyclicBarrier cyclicBarrier;
    
    @Override
    public void run() {
        log.debug("I am here");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(5) * 1000);
            cyclicBarrier.await();
            log.debug("All set");
        } catch (InterruptedException | BrokenBarrierException e) {
            log.error("error: ", e);
            throw new RuntimeException(e);
        }
    }
}

@AllArgsConstructor
@Slf4j
class CountDownThread extends Thread {
    
    private final CountDownLatch countDownLatch;
    
    @Override
    public void run() {
        log.debug("I am here");
        try {
            countDownLatch.await();
            log.debug("All set");
        } catch (InterruptedException e) {
            log.error("error: ", e);
            throw new RuntimeException(e);
        }
    }
}

@Slf4j
class AwesomeThread extends Thread {
    
    volatile boolean shouldRun = true;

    @Override
    public void run() {
        while (shouldRun) {
            log.debug("I am groot");
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(5) * 1000);
            } catch (InterruptedException e) {
                log.debug("Bye");
                shouldRun = false;
            }
        }
    }
}
