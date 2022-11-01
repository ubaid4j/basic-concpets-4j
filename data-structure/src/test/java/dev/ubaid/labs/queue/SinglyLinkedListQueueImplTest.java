package dev.ubaid.labs.queue;

import java.util.Hashtable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SinglyLinkedListQueueImplTest {
    private static final Logger logger = LoggerFactory.getLogger(SinglyLinkedListQueueImplTest.class);
    
    @Test
    public void test01() {
        Queue<Integer> queue = new SinglyLinkedListQueueImpl<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        logger.debug("Queue: {}", queue);
        Integer firstDequeue = queue.deque();
        Assertions.assertEquals(1, firstDequeue);
        logger.debug("After first Dequeue: {}", queue);
    }
}
