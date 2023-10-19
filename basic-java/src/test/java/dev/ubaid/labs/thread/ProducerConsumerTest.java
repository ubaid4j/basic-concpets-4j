package dev.ubaid.labs.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ProducerConsumerTest {

    @Test
    void producerConsumer() throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(15);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        
        producer.start();
        consumer.start();

        Thread.sleep(ThreadLocalRandom.current().nextLong(2_0000));
        producer.interrupt();
        consumer.interrupt();
    }
    
}

@Slf4j
@RequiredArgsConstructor
class Producer extends Thread {

    final BlockingQueue<Integer> queue;
    
    @Override
    public void run() {
        while (true) {
            Integer integer = ThreadLocalRandom.current().nextInt();
            try {
                log.debug("Adding: {}", integer);
                queue.put(integer);
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
        while (true) {
            try {
                Integer item = queue.take();
                log.debug("consumed integer: {}", item);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
