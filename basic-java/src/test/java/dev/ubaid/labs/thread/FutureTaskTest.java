package dev.ubaid.labs.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class FutureTaskTest {
    
    @Test
    void testFuture() throws ExecutionException, InterruptedException {

        Callable<Number> callable = () -> {
            log.debug("Computing");
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(3_000));
            } catch (Exception exp) {
                log.error("exp: ", exp);
            }
            log.debug("done");
            return 777;
        };
        
        
        FutureTask<Number> future = new FutureTask<>(callable);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(future);
        
        while(true) {
            if (future.isDone()) {
                Number result = future.get();
                log.debug("Task done: Result: {}", result);
                Assertions.assertEquals(777, result);
                break;
            } else {
                log.debug("Task not done, checking");
                Thread.sleep(ThreadLocalRandom.current().nextLong(200));
            }
        }
    }
    
    
    
}
