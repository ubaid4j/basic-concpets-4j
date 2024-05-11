package com.ubaid.forj;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ITEM 32: COMBINE GENERICS AND VARARGS JUDICIOUSLY
public class Item32Test {
    
    static final Logger logger = LoggerFactory.getLogger(Item32Test.class);
    
    static void dangerous(List<String>... stringList) {
        List<Integer> intList = List.of(42);
        Object[] objects = stringList;
        objects[0] = intList;   // Heap pollution
        String s = stringList[0].get(0); //ClassCastException
    }
    
    @Test
    void testDangerous() {
        List<String> list = List.of("list");
        List<String> list2 = List.of("list2");
        List<String> list3 = List.of("list3");
        ClassCastException exp = Assertions.assertThrowsExactly(ClassCastException.class, () -> dangerous(list, list2, list3));
        Assertions.assertTrue(exp.getMessage().contains("class java.lang.Integer cannot be cast to class java.lang.String"));
    }
    
    static <T> T[] toArray(T... args) {
        return args;
    }
    
    @Test
    void testToArray() {
        String[] strings = toArray("1", "2", "3", "4");
        logger.debug("strings: {}", (Object) strings);
    }
    
    static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }
        throw new AssertionError();
    }
    
    @Test
    void testPickTwo() {
        Assertions.assertThrows(ClassCastException.class, () -> {
            String[] strings = pickTwo("A", "B", "C");
        }, "This method should throws class cast exception as at runtime it will return an array of object instead of array of string");
//        logger.debug("Strings: {}", (Object) pickTwo("A", "B", "C"));
    }
    
    @SafeVarargs
    static <T> List<T> flatten(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list: lists) {
            result.addAll(list);
        }
        return result;
    }
    
    @Test
    void testFlatten() {
        List<Integer> ints = List.of(1, 2, 3, 4);
        List<Double> doubles = List.of(1.1, 2.1, 3.1, 4.1);
        List<Number> result = flatten(ints, doubles);
        logger.debug("{}", result);
    }
    
    static <T> List<T> flatten(List<List<? extends T>> lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list: lists) {
            result.addAll(list);
        }
        return result;
    }
    
    @Test
    void testFlattenWithoutVarargs() {
        List<Integer> ints = List.of(1, 2, 3, 4);
        List<Double> doubles = List.of(1.1, 1.2, 1.3);
        List<List<? extends Number>> lists = List.of(ints, doubles);
        List<Number> result = flatten(lists);
        logger.debug("{}", result);
    }
    
    static <T> List<T> pickTwoWithList(T a, T b, T c) {
        int randVal = ThreadLocalRandom.current().nextInt(3);
        return switch (randVal) {
            case 0 -> List.of(a, b);
            case 1 -> List.of(a, c);
            case 2 -> List.of(b, c);
            default -> throw new IllegalStateException("Unexpected value: " + randVal);
        };
    }
    
    @Test
    void testPickTwoWithList() {
        List<String> attributes = pickTwoWithList("Good", "Fast", "Cheap");
        logger.debug("{}", attributes);
    }
}
