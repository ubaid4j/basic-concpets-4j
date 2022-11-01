package com.ubaid.forj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ITEM 31: USE BOUNDED WILDCARDS TO INCREASE API FLEXIBILITY
public class Item31Test {
    
    static Logger logger = LoggerFactory.getLogger(Item31Test.class);
    
    @Test
    void testInvariantErr() {
        class Stack<E> {
            private E[] elements;
            int size = 0;
            @SuppressWarnings("unchecked")
            public Stack() {
                this.elements = (E[]) new Object[20];
            }
            
            public void pushAll(Iterable<E> src) {
                for (E e: src) {
                    push(e);
                }
            }
            
            public void push(E e) {
                elements[size++] = e;
            }
            
            public E pop() {
                E e = elements[--size];
                elements[size] = null;
                return e;
            }
            
            public boolean isEmpty() {
                return size == 0;
            }
            
            public void popAll(Collection<E> collection) {
                while (!isEmpty()) {
                    collection.add(pop());
                }
            }
        }
        
        Stack<Number> stack = new Stack<>();
        Iterable<Integer> integers = List.of(1, 2, 3);
//        stack.pushAll(integers);
//        java: incompatible types: java.lang.Iterable<java.lang.Integer> cannot be converted to java.lang.Iterable<java.lang.Number>
        
        Collection<Object> coll = new ArrayList<>();
//        stack.popAll(coll);
//        java: incompatible types: java.util.Collection<java.lang.Object> cannot be converted to java.util.Collection<java.lang.Number>
    }
    
    @Test
    void testBoundedWildCardType() {
        class Stack<E> {
            private final E[] elements;
            int size = 0;
            
            @SuppressWarnings("unchecked")
            public Stack() {
                this.elements = (E[]) new Object[20];
            }
            
            public void pushAll(Iterable<? extends E> src) {
                for (E e: src) {
                    push(e);
                }
            }
            
            public void push(E e) {
                elements[size++] = e;
            }
            
            public E pop() {
                E e = elements[--size];
                elements[size] = null;
                return e;
            }
            
            public boolean isEmpty() {
                return size == 0;
            }
            
            public void popAll(Collection<? super E> dst) {
                while (!isEmpty()) {
                    dst.add(pop());
                }
            }

            @Override
            public String toString() {
                return "Stack{" +
                    "elements=" + Arrays.toString(elements) +
                    '}';
            }
        }
        
        Stack<Number> stack = new Stack<>();
        Iterable<Integer> integers = List.of(1, 2, 3);
        stack.pushAll(integers);
        logger.debug("{}", stack);
        
        Collection<Object> dst = new ArrayList<>();
        stack.popAll(dst);
        logger.debug("after pop all: {}", stack);
        logger.debug("dst: {}", dst);
    }

    static <E extends Number> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }
    
    @Test
    void testUnion() {
        Set<Integer> integers = Set.of(1, 2, 3);
        Set<Double> doubles = Set.of(2D, 3.0D, 4.002D);
        Set<Number> numbers = union(integers, doubles);
        logger.debug("union: {}", numbers);
    }

    static <E extends Comparable<? super E>> E max(Collection<? extends E> c) {
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
    void testMax() {
        Collection<Integer> integers = List.of(1, 2, 3, 4, 100, 50);
        Integer max = max(integers);
        Assertions.assertEquals(100, max, "max should be 100");
    }

    static void swap(List<?> list, int i, int j) {
//        list.set(i, list.set(j, list.get(i)));
        swapHelper(list, i, j);
    }
    
    static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
    
    @Test
    void testSwap() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3));
        logger.debug("List before swap: {}", list);
        swap(list, 1, 2);
        logger.debug("List after swap: {}", list);
    }

}
