package dev.ubaid.labs.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class SemaphoreTest {
    
    final String sharedRes = "Hello World";

    Semaphore semaphore = new Semaphore(2);

    @Test
    @SneakyThrows
    void test() {

        
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        Thread t4 = new Thread(task);
        Thread t5 = new Thread(task);
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }
    
    Runnable task = () -> {
        try {
            semaphore.acquire();
            log.debug("[after acquiring] available permits: {}", semaphore.availablePermits());
            Thread.sleep(ThreadLocalRandom.current().nextLong(3_000));
            log.debug("Shared Res: {}", sharedRes);
            semaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("[after release] available permits: {}", semaphore.availablePermits());
    };
    
}
