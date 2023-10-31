package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalTest {
    
    @Test
    void testThreadLocal() throws Exception {
        R1 r1 = new R1();

        Thread t1 = new Thread(() -> {
            r1.setNumber((short) 50);
            r1.printNumber();
        });

        Thread t2 = new Thread(() -> {
            r1.setNumber((short) 100);
            r1.printNumber();
        });
        
        t1.start();
        t2.start();
        
        t1.join();
        t2.join();

    }
}


@Slf4j
class R1 {
    private final ThreadLocal<Number> number = new ThreadLocal<>();

//    private Number number;
    
    
    public void setNumber(short number) {
        this.number.set(number);
//        this.number = number;
    }

    public void printNumber() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(1_000));
        } catch (Exception exp) {
            log.error("exp:", exp);
        }
        log.debug("Number: {}", this.number.get());
//        log.debug("Number: {}", this.number);
    }
}