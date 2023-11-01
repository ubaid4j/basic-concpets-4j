package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class InterruptionTest {
    
    @Test
    void differenceBetweenInterruptAndIsInterrupted() throws Exception {
        Thread t1 = new Thread(() -> {
            log.debug("---------t1");
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(3_000));
            } catch (InterruptedException e) {
                log.debug("t1 interrupted: {}", e.getMessage());
            }
        });
        
        Thread t2 = new Thread(() -> {
            log.debug("---------t2");
            try {
                log.debug("am I interrupted: {}", Thread.interrupted());
                Thread.sleep(ThreadLocalRandom.current().nextLong(3_000));
            } catch (InterruptedException e) {
                log.debug("t2 interrupted: {}", e.getMessage());
                log.debug("am I interrupted: {}", Thread.interrupted());
            }
        });
        
        t1.start();
        t2.start();
        
        log.debug("is t1 interrupted: {}", t1.isInterrupted());
        t1.interrupt();
        log.debug("is t1 interrupted: {}", t1.isInterrupted());
        t2.interrupt();
        log.debug("is t2 interrupted: {}", t2.isInterrupted());
        
        t1.join();
        t2.join();
    }
    
}
