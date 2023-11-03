package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLockTest {
    
    @Test
    void testIfThreadHoldLock() throws Exception {
        SomeWork worker = new SomeWork();
        Thread t1 = new Thread(worker);

        
        
    }

    @Slf4j
    static class SomeWork implements Runnable {
    
        ReentrantLock lock = new ReentrantLock();

        public void doWork() {
            
            try {
                SomeWork.log.debug("Starting Work");
                lock.lock();
                Thread.sleep(2_000);
                lock.unlock();
                SomeWork.log.debug("Work done");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            boolean isCurrentThreadHoldLock = false;
            do {
                isCurrentThreadHoldLock = Thread.holdsLock(this);
                SomeWork.log.debug("Current thread holds lock: {}", isCurrentThreadHoldLock);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (!isCurrentThreadHoldLock);
            doWork();
        }
    }


}
