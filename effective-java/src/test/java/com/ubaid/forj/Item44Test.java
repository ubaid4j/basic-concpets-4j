package com.ubaid.forj;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 44: FAVOR THE USE OF STANDARD FUNCTIONAL INTERFACES
public class Item44Test {
    
    @Test
    void testUnaryOperator() {
        var name = "Ubaid";
        var lowerCaseName = Optional
            .of(name)
            .map(String::toLowerCase)
            .orElseThrow();
        Assertions.assertEquals("ubaid", lowerCaseName);
    }
    
    @Test
    void testBinaryOperator() {
        var list = List.of(1, 2, 3, 4, 5);
        var sum = list.stream()
            .reduce(Integer::sum);
    }
    
    
    @Test
    void testPredicate() {
        var list = List.of(1, 2, 3, 4);
        var result = list
            .stream()
            .filter(i -> i == 4)
            .toList();

        System.out.println(result);

        Assertions.assertEquals(1, result.size(), "Size of arr should be 1");
    }
}
