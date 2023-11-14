package dev.ubaid.labs.iterator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class IteratorTest {
    
    @Test
    void failFastIterator() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        Iterator<Integer> iterator = list.iterator();

        Assertions.assertThrowsExactly(ConcurrentModificationException.class, () -> {
            while (iterator.hasNext()) {
                Integer val = iterator.next();
                log.debug("value: {}", val);
                list.add(6);
            }
        });
    }
    
    @Test
    void failSafeIterator() {
        List<Integer> list = new CopyOnWriteArrayList<>(List.of(1, 2, 3, 4));
        Iterator<Integer> iterator = list.iterator();
        
        Assertions.assertDoesNotThrow(() -> {
            while (iterator.hasNext()) {
                Integer val = iterator.next();
                log.debug("value: {}", val);
                list.add(5);
            }
        });
        log.debug("list: {}", list);
    }
}
