package dev.ubaid.labs.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class ListTest {
    
    @Test
    void failFast() {
        List<Integer> failFastList = Collections.synchronizedList(new ArrayList<>());
        failFastList.add(1);
        failFastList.add(2);
        failFastList.add(3);

        // fail fast
        Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            log.debug("fail fast list: {}", failFastList);
            Iterator<Integer> itr = failFastList.iterator();
            log.debug("Iterating through fail fast list");
            while(itr.hasNext()) {
                Integer item = itr.next();
                log.debug("fail fast list item: {}", item);
                failFastList.add(4);
            }
        });
    }
    
    @Test
    void failSafe() {
        List<Integer> failSafeList = new CopyOnWriteArrayList<>();
        failSafeList.add(1);
        failSafeList.add(2);
        failSafeList.add(3);

        Assertions.assertDoesNotThrow(() -> {
            log.debug("fail safe list: {}", failSafeList);
            Iterator<Integer> itr = failSafeList.iterator();
            log.debug("Iterating through fail safe list");
            while(itr.hasNext()) {
                Integer item = itr.next();
                log.debug("fail safe list item: {}", item);
                failSafeList.add(4);
            }
            log.debug("fail safe list after modification list: {}", failSafeList);
        });
    }
    
    @Test
    void listIterator() {
        List<Integer> failSafeList = new ArrayList<>();
        failSafeList.add(1);
        failSafeList.add(2);
        failSafeList.add(3);

        Assertions.assertDoesNotThrow(() -> {
            log.debug("list: {}", failSafeList);
            ListIterator<Integer> itr = failSafeList.listIterator();
            log.debug("Iterating list");
            while(itr.hasNext()) {
                Integer item = itr.next();
                log.debug("item: {}", item);
                itr.set(item + 10);
            }
            log.debug("list after modification: {}", failSafeList);
        });
        
        Assertions.assertEquals(List.of(11, 12, 13), List.copyOf(failSafeList));
    }
    
    @Test
    void subListTest() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        list.subList(1, 9).clear();
        Assertions.assertEquals(new ArrayList<>(List.of(1, 10)), list);
    }
}
