package dev.ubaid.labs.collection;

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
}
