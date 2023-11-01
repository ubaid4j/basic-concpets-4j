package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
public class DeadLockTest {
    
    final static String R1 = "Resource 1";
    final static String R2 = "Resource 2";
    
    @Test
    void testDeadLock() throws Exception {
        
        Thread t1 = new Thread(() -> {
            synchronized (R1) {
                log.debug("Thread T1 locked -> resource 1");
                synchronized (R2) {
                    log.debug("Thread T1 locked -> resource 2");
                }
            }
        });
        
        Thread t2 = new Thread(() -> {
           synchronized (R2) {
               log.debug("Thread T2 locked -> resource 2");
               synchronized (R1) {
                   log.debug("Thread T2 locked -> resource 1");
               }
           }
        });
        
        t1.start();
        t2.start();
        
        t1.join(Duration.ofSeconds(40));
        t2.join(Duration.ofSeconds(40));
    }
    
}
