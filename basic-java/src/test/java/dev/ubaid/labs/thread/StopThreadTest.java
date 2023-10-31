package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public class StopThreadTest {
    
    @Test
    void stopThreadByInterrupting() throws Exception {
        Thread t1 = new Thread(new SimpleThread3());
        t1.start();
        Thread.sleep(ThreadLocalRandom.current().nextLong(6_000));
        t1.interrupt();
    }
    
    @Test
    void stopThreadByCustomTerminateMethod() throws Exception {
        SimpleThread4 r1 = new SimpleThread4();
        Thread t1 = new Thread(r1);        
        Thread t2 = new Thread(r1);        
        Thread t3 = new Thread(r1);
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(ThreadLocalRandom.current().nextLong(6_000));
        r1.terminate();
        t1.join();
        t2.join();
        t3.join();
    }
    
    @Test
    void stopThreadOnThrowingAnException() throws Exception {
        SimpleThread5 t5 = new SimpleThread5();
        Thread thread = new Thread(t5);
        thread.start();
        Thread.sleep(ThreadLocalRandom.current().nextLong(3_000));
        thread.join();
    }
    
}

@Slf4j
class SimpleThread3 implements Runnable {

    @Override
    public void run() {
        log.debug("---------Starting--------");
        while (true) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(3_000));
                log.debug("Running");
            } catch (InterruptedException e) {
                log.debug("Get interrupted signal, signing off");
                break;
            }
        }
    }
}

@Slf4j
class SimpleThread4 implements Runnable {
    
    private volatile boolean isRunning = true;
    
    public void terminate() {
        this.isRunning = false;
    }
    
    
    @Override
    public void run() {
        log.debug("---------Starting--------");
        while (isRunning) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(3_000));
                log.debug("Running");
            } catch (InterruptedException e) {
                log.debug("Get interrupted signal, signing off");
                break;
            }
        }
        log.debug("isRunning: {}", this.isRunning);
        log.debug("-----------Terminating--------------");
    }
}

@Slf4j
class SimpleThread5 implements Runnable {

    @Override
    public void run() {
        log.debug("---------Starting--------");
        while (true) {
            try {
                log.debug("Running");
                throw new RuntimeException("Awesome exception");
            } catch (Exception exp) {
                log.debug("An exception is being thrown");
                throw exp;
            }
        }
    }
}
