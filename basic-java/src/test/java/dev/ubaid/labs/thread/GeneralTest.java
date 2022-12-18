package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    @Test
    void test() throws InterruptedException{
        App app = new App();
        app.run();
    }
    
}

@Slf4j
class Thread1 extends Thread {

    int sum = 0;
    
    @Override
    public void run() {
        synchronized (this) {
            log.debug("start calculation");
            
            for (int i = 0; i <= 100; i++) {
                sum += i;
            }

            log.debug("giving notification");
            this.notifyAll();
        }
    }
}

@Slf4j
class App {
    public void run() throws InterruptedException {
        Thread1 thread1 = new Thread1();
        thread1.start();
        
        synchronized (thread1) {
            log.debug("main thread waiting for child thread");
            thread1.wait();
            log.debug("main thread got notify");
            log.debug("sum: {}", thread1.sum);
        }
    }
}
