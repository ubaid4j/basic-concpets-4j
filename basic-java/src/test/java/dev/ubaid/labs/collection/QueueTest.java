package dev.ubaid.labs.collection;

import java.util.ArrayDeque;
import java.util.Deque;
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
    
    @Test
    void stackTest() {
        Deque<Integer> stack = new LinkedList<>();
        
        stack.add(1);
        stack.add(2);
        stack.add(3);
        
        Integer three = stack.pollLast();
        Assertions.assertEquals(3, three);
        
        stack.add(2);
        
        Integer two = stack.pollLast();
        Assertions.assertEquals(2, two);
        
        two = stack.pollLast();
        Assertions.assertEquals(2, two);
        
        Integer one = stack.pollLast();
        Assertions.assertEquals(1, one);
        
        stack.add(6);
        stack.add(10);
        
        Integer ten = stack.pollLast();
        Assertions.assertEquals(10, ten);
    }
    
    @Test
    void queueTest() {
        Deque<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        
        Integer one = queue.poll();
        Assertions.assertEquals(1, one);
        
        Integer two = queue.poll();
        Assertions.assertEquals(2, two);
        
        queue.add(4);
        queue.add(5);
        
        Integer three = queue.poll();
        Assertions.assertEquals(3, three);
        
        Integer four = queue.poll();
        Assertions.assertEquals(4, four);
    }
    
    @Test
    void queueTest2() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        
        Integer one = queue.poll();
        Assertions.assertEquals(1, one);
        
        Integer two = queue.poll();
        Assertions.assertEquals(2, two);
        
        queue.add(4);
        queue.add(5);
        
        Integer three = queue.poll();
        Assertions.assertEquals(3, three);
        
        Integer four = queue.poll();
        Assertions.assertEquals(4, four);
    }
}
