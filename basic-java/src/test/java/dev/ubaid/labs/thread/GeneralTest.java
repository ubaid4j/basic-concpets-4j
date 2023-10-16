package dev.ubaid.labs.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class GeneralTest {
    
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    void test() throws InterruptedException{
        App app = new App();
        app.run();
    }
    
    @Test
    void callable() throws Exception {
        Callable<String> cal1 = () -> {
            Thread.sleep(2000);
            return "All set";  
        };

        FutureTask<String> task = new FutureTask<>(cal1);
        Thread thread = new Thread(task);
        thread.start();
        log.debug("task result: -> {}", task.get());
        thread.join();
    }
    
    @Test
    @SneakyThrows
    void whenExceptionOccurredInSynchronizedBlock() {
        Object lock = new Object();
        Thread t1 = new ThreadWithConditionalException(lock);
        t1.start();
        Thread t2 = new ThreadWithConditionalException(lock);
        t2.start();
        Thread t3 = new ThreadWithConditionalException(lock);
        t3.start();
        
        
        t1.join();
        t2.join();
        t3.join();
    }
    
    @Test
    @SneakyThrows
    void differenceBetweenThreadVsRunnable() {
        //Not able to submit in executor service
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            New new_ = new New();
            executorService.submit(new_);
            executorService.submit(new_);
            executorService.submit(new_);
            executorService.submit(new_);
            executorService.submit(new_);
            executorService.submit(new_);
        }
        
        Thread legacy = new Legacy();
        Thread legacy2 = new Legacy();
        Thread legacy3 = new Legacy();
        Thread legacy4 = new Legacy();
        Thread legacy5 = new Legacy();
        legacy.start();
        assertThrows(RuntimeException.class, legacy::start);
        legacy2.start();
        legacy3.start();
        legacy4.start();
        legacy5.start();
    }
    
    @Test
    @SneakyThrows
    void futureTask() {
        NewV2 new_ = new NewV2();
        FutureTask<String> task = new FutureTask<>(new_);
        executorService.submit(task);
        
        int count = 1000;
        while(!task.isDone() && count-- > 0) {
            log.debug("State: {}", task.state());
            Thread.sleep(1_000);
        }
        log.debug("State: {}", task.state());
        log.debug("Result: {}", task.get());
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

@Slf4j
@AllArgsConstructor
class ThreadWithConditionalException extends Thread {

    private final Object lock;
    private static final AtomicInteger counter = new AtomicInteger(0);
    
    @Override
    public void run() {
        synchronized (lock) {
            if (counter.getAndIncrement() == 0) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(5) * 1000);
                } catch (InterruptedException e) {
                    log.error("Interrupted Exception:");
                    throw new RuntimeException(e);
                }
                log.error("Some runtime exception occurred");
                throw new RuntimeException();
            }
            log.debug("All set");
        }
    }
}



//######################Difference between extends thread vs implements runnable

@Slf4j
class Legacy extends Thread {
    @Override
    public void run() {
        String name = currentThread().getName();
        log.debug("Thread name: {}", name);
    }
}

@Slf4j
class New implements Runnable {
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        log.debug("Thread name: {}", name);
    }
}

@Slf4j
class NewV2 implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(ThreadLocalRandom.current().nextLong(9_000));
        return Thread.currentThread().getName();
    }
}

