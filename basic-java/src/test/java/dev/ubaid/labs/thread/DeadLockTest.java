package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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


    @Test
    void avoidDeadLockByJoin() throws Exception {

        Thread t1 = new Thread(() -> {
            synchronized (R1) {
                log.debug("Thread T1 locked -> resource 1");
                synchronized (R2) {
                    log.debug("Thread T1 locked -> resource 2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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


    @Test
    void avoidDeadLockByLocking() throws Exception {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        log.debug("Thread T1 locked -> resource 1");
                        Thread.sleep(100);
                        if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                            try {
                                log.debug("Thread T1 locked -> resource 2");
                            } finally {
                                lock2.unlock();
                            }
                        } else {
                            log.debug("lock 2 is not available");
                        }
                    } finally {
                        lock1.unlock();
                    }
                } else {
                    log.debug("lock 1 is not available");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        log.debug("Thread T2 locked -> resource 2");
                        Thread.sleep(100);
                        if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                            try {
                                log.debug("Thread T2 locked -> resource 1");
                            } finally {
                                lock1.unlock();
                            }
                        } else {
                            log.debug("lock 1 is not available");
                        }
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    log.debug("Lock2 is not available");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        t1.start();
        t2.start();

        t1.join(Duration.ofSeconds(40));
        t2.join(Duration.ofSeconds(40));
    }
}
