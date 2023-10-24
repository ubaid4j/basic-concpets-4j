package dev.ubaid.labs.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ProducerConsumerTest {

    @Test
    void producerConsumer() throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        
        producer.start();
        consumer.start();
        
        producer.join();
        consumer.join();

//        Thread.sleep(ThreadLocalRandom.current().nextLong(2_0000));
//        producer.interrupt();
//        consumer.interrupt();
    }
    
}

@Slf4j
@RequiredArgsConstructor
class Producer extends Thread {

    final BlockingQueue<Integer> queue;
    
    @Override
    public void run() {
        int iterations = 20;
        while (--iterations > 0) {
            Integer integer = ThreadLocalRandom.current().nextInt();
            try {
                log.debug("Adding: {}", integer);
                boolean isAdded = queue.offer(integer, 1, TimeUnit.SECONDS);
                log.debug("isAdded: {}", isAdded);
            } catch (InterruptedException e) {
                log.error("exception: ", e);
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep((long) (ThreadLocalRandom.current().nextDouble() * 1000));
            } catch (InterruptedException e) {
                log.error("exception: ", e);
                throw new RuntimeException(e);
            }
        }
    }
}

@Slf4j
@RequiredArgsConstructor
class Consumer extends Thread {

    final BlockingQueue<Integer> queue;

    @Override
    public void run() {
        int iterations = 20;
        while (--iterations > 0) {
            try {
                Integer item = queue.poll(2, TimeUnit.SECONDS);
                log.debug("consumed integer: {}", item);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
