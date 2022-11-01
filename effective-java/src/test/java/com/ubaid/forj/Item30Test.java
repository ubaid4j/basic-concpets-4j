package com.ubaid.forj;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ITEM 30: FAVOR GENERIC METHODS
public class Item30Test {
    
    static Logger logger = LoggerFactory.getLogger(Item30Test.class);

    // method without generics
    // compiler producing warnings
    static Set unionLegacy(Set s1, Set s2) {
        // Unchecked call to 'HashSet(Collection<? extends E>)' as a member of raw type 'java.util.HashSet' 
        Set result = new HashSet(s1);
        // Unchecked call to 'addAll(Collection<? extends E>)' as a member of raw type 'java.util.Set'
        result.addAll(s2);
        return result;
    }
    
    // same method with generic
    // no warning
    // all is type safe
    static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }
    
    <E extends Integer> Set<E> intersection(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>();
        for (E element: s1) {
            if (s2.contains(element)) {
                result.add(element);
            }
        }
        return result;
    }
    
    static <E extends Comparable<E>> E max(Collection<E> c) {
        if (c.isEmpty()) {
            throw new IllegalArgumentException("Empty collection");
        }
        
        E result = null;
        for (E e: c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }
        return result;
    }
    
    @Test
    void testGenericUnionMethod() {
        Set<String> guys = Set.of("Tom", "Dick", "Harry");
        Set<String> stooges = Set.of("Larry", "Moe", "Curly");
        Assertions.assertDoesNotThrow(() -> {
            Set<String> aflCio = union(guys, stooges);
            logger.info("{}", aflCio);
        }, "Executed successfully");
    }
    
    @Test
    void testUnaryOperator() {
        String[] strings = {"jute", "hemp", "nylon"};
        UnaryOperator<String> sameString = UnaryOperator.identity();
        Assertions.assertDoesNotThrow(() -> {
            for (String string: strings) {
                logger.debug(sameString.apply(string));
            }
        });
        
        Number[] numbers = {1, 2.0, 3L};
        UnaryOperator<Number> sameNumber = UnaryOperator.identity();
        Assertions.assertDoesNotThrow(() -> {
            for (Number number: numbers) {
                logger.debug("{}", sameNumber.apply(number) );
            }
        });
    }
    
    @Test
    void testRecursiveGenericTypeMethod() {
        List<Integer> list = List.of(10, 22, 33, 1, 2, 4, 100, 22, 90, 5);
        Integer max = max(list);
        Assertions.assertEquals(100, max, "max should be 100");
    }
    
    @Test
    void testBoundedGenericTypeMethod() {
        Set<Integer> s1 = Set.of(1, 2, 3, 4);
        Set<Integer> s2 = Set.of(3, 4, 5, 6);
        Set<Integer> intersection = intersection(s1, s2);
        Set<Integer> expected = Set.of(3, 4);
        Assertions.assertEquals(expected, intersection);
    }
}
