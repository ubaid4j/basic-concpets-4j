package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ExecutorTests {
    
    @Test
    void diffBetweenExecuteAndSubmit() throws InterruptedException, ExecutionException {
        Worker callable = new Worker();
        final ThreadFactory factory = Thread.ofVirtual().name("virtual-thread-", 0).factory();
        ExecutorService virtualThreadExecutor = Executors.newThreadPerTaskExecutor(factory);
        
        Future<String> future = virtualThreadExecutor.submit(callable);
        
        while (!future.isDone()) {
            Thread.sleep(10);
            log.debug("Waiting for task to be done");
        }
        
        log.debug("Task result: {}", future.get());
    }
    
}

@Slf4j
class Worker implements Callable<String> {

    @Override
    public String call() {
        log.debug("Start Working");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(1_000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("Work done");
        return "All set";
    }
}
