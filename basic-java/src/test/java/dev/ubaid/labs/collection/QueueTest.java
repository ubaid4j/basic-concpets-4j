package dev.ubaid.labs.collection;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class QueueTest {
    
    @Test
    void differenceBetweenRemoveAndPoll() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.remove();
        Assertions.assertDoesNotThrow(queue::poll);
        Assertions.assertThrows(NoSuchElementException.class, queue::remove);
    }
    
    @Test
    void whatIsHeaderInQueue() {
        Queue<String> q1 = new ArrayDeque<>();

        q1.add("Head");

        q1.add("Tail");
        
        log.debug("{}", q1);
        
        Assertions.assertEquals("Head", q1.poll());
    }
}
