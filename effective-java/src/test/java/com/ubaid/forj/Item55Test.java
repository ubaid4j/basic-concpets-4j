package com.ubaid.forj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 55: RETURN OPTIONALS JUDICIOUSLY
public class Item55Test {
    
    @Test
    void testMax() {
        List<Integer> ints = List.of(1, 2, 3 ,4);
        assertEquals(4, maxNoOptional(ints));
    }

    @Test
    void testMaxException() {
        Collection<Integer> collection = List.of();
        assertThrows(IllegalArgumentException.class, () -> maxNoOptional(collection));
    }
    
    static <E extends Comparable<E>> E maxNoOptional(Collection<E> c) {
        if (c.isEmpty()) {
            throw new IllegalArgumentException("empty collection");
        }
        
        E result = null;
        for (E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }
        return result;
    }
    
    
    @Test
    void testMaxNew() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        Optional<Integer> max = max(list);
        Assertions.assertTrue(max.isPresent());
        Assertions.assertEquals(5, max.get());
    }
    
    @Test
    void testMaxNewEmpty() {
        Collection<Integer> list = List.of();
        Optional<Integer> max = max(list);
        Assertions.assertTrue(max.isEmpty());
    }
    
    static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
        if (c.isEmpty()) {
            return Optional.empty();
        }
        
        E result = null;
        for (E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }
        return Optional.of(result);
    }
    
    @Test
    void testMaxByStream() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        Optional<Integer> max = maxByStream(list);
        Assertions.assertTrue(max.isPresent());
        Assertions.assertEquals(5, max.get());
    }
    
    @Test
    void testMaxByStreamWhenCollectionEmpty() {
        Collection<Integer> list = List.of();
        Optional<Integer> max = max(list);
        Assertions.assertTrue(max.isEmpty());
    }
    
    static <E extends Comparable<E>> Optional<E> maxByStream(Collection<E> c) {
        return c
            .stream()
            .max(Comparator.naturalOrder());
    }
    
    @Test
    void testOptionalMethods() {
        ProcessHandle ph = ProcessHandle
            .allProcesses()
            .findAny()
            .orElseThrow();
        
        Optional<ProcessHandle> parentProcess = ph.parent();
        String pid = parentProcess
            .map(ProcessHandle::pid)
            .map(String::valueOf)
            .orElse("N/A");

        System.out.println("Process ID: " + ph.pid());
        System.out.println("Parent ID: " + pid);
    }
}
