package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadTest {
    
    @Test
    void testT1() {
        Thread t1 = new Thread(new T1());
        t1.start();
    }
}

@Slf4j
class T1 implements Runnable {
    
    @Override
    public void run() {
        log.debug("T1");
    }
}