package com.ubaid.forj;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;

// ITEM 47: PREFER COLLECTION TO STREAM AS A RETURN TYPE (gh-626)
public class Item47Test {

    @Test
    void test1() {
        AtomicInteger count = new AtomicInteger(0);
        for (ProcessHandle ph : ProcessHandle.allProcesses().toList()) {
            System.out.println("Process#" + count.incrementAndGet() + ": " + ph.info());
        }
        
        count.set(0);
        for (ProcessHandle ph: (Iterable<? extends ProcessHandle>) ProcessHandle.allProcesses()::iterator) {
            System.out.println("Process#" + count.incrementAndGet() + ": " + ph.info());
        }
    }
    
    @Test
    void test2() {
        AtomicInteger count = new AtomicInteger(0);
        for (ProcessHandle ph: (Iterable<? extends ProcessHandle>) ProcessHandle.allProcesses()::iterator) {
            System.out.println("Process#" + count.incrementAndGet() + ": " + ph.info());
        }
    }
    
    @Test
    void test3() {
        AtomicInteger count = new AtomicInteger(0);
        for (ProcessHandle ph: iterableOf(ProcessHandle.allProcesses())) {
            System.out.println("Process#" + count.incrementAndGet() + ": " + ph.info());
        }
    }
    
    @Test
    void test4() {
        Collection<Set<Integer>> powerSet = PowerSet.of(Set.of(1, 2, 3, 4, 5, 6));
        System.out.println(powerSet);
    }
    
    public static <E> Iterable<E> iterableOf(Stream<E> stream) {
        return stream::iterator;
    }
    
    public static <E> Stream<E> streamOf(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
    
    static class PowerSet {
        public static <E> Collection<Set<E>> of(Set<E> s) {
            List<E> src = new ArrayList<>(s);
            if (src.size() > 30) {
                throw new IllegalArgumentException("Set too big" + s);
            }
            
            return new AbstractList<>() {
                @Override
                public Set<E> get(int index) {
                    Set<E> result = new HashSet<>();
                    for (int i = 0; index != 0; index >>= 1) {
                        if ((index & 1) == 1) {
                            result.add(src.get(i));
                        }
                    }
                    return result;
                }

                @Override
                public int size() {
                    return 1 << src.size();
                }

                @Override
                public boolean contains(Object o) {
                    return o instanceof Set<?> && src.containsAll((Set<?>) o);
                }
            };
        }
    }
    
    static class SubLists {
        
        public static <E> Stream<List<E>> of(List<E> list) {
            return Stream
                .concat(
                    Stream.of(Collections.emptyList()),
                    prefixes(list).flatMap(SubLists::suffixes)
                );
        }
        
        private static <E> Stream<List<E>> prefixes(List<E> list) {
            return IntStream.rangeClosed(1, list.size())
                .mapToObj(end -> list.subList(0, end));
        }
        private static <E> Stream<List<E>> suffixes(List<E> list) {
            return IntStream
                .range(0, list.size())
                .mapToObj(start -> list.subList(start, list.size()));
        }
    }
}
