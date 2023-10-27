package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadTest {
    
    @Test
    void testT1() {
        Thread t1 = new Thread(new T1());
        t1.start();
        
        Thread t2 = new T2();
        t2.start();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(t1);
        executorService.submit(t2);
        executorService.shutdown();
        
        final ThreadFactory factory = Thread.ofVirtual().name("virtual-thread-", 0).factory();
        ExecutorService virtualThreadExecutor = Executors.newThreadPerTaskExecutor(factory);
        virtualThreadExecutor.submit(t1);
        virtualThreadExecutor.submit(t1);
        virtualThreadExecutor.submit(t1);
        virtualThreadExecutor.submit(t2);
        virtualThreadExecutor.submit(t2);
        virtualThreadExecutor.submit(t2);
        virtualThreadExecutor.shutdown();

        executorService.close();
        virtualThreadExecutor.close();
    }
}

@Slf4j
class T1 implements Runnable {
    
    @Override
    public void run() {
        log.debug("T1");
    }
}

@Slf4j
class T2 extends Thread {
    @Override
    public void run() {
        log.debug("T2");
    }
}