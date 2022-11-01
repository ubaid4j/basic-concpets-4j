package com.ubaid.forj;

import java.util.Comparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 61: PREFER PRIMITIVE TYPES TO BOXED PRIMITIVES
public class Item61Test {

    private final static Comparator<Integer> naturalOrder = (i, j) -> {
        return i < j ? - 1 : (i == j ? 0 : 1);
    };
    
    private final static Comparator<Integer> naturalOrderE = (iBoxed, jBoxed) -> {
        int i = iBoxed;
        int j = jBoxed;
        return i < j ? - 1 : (i == j ? 0 : 1);
    };


    // comparator for ascending numerical order on Integers
    @Test
    void comparator1() {
        int actualResult = naturalOrder.compare(new Integer(42), new Integer(42));
        int expectedResult = 0;
        Assertions.assertNotEquals(expectedResult, actualResult);
        System.out.println("Actual Result: " + actualResult);
    }
    
    @Test
    void comparator1E() {
        int actualResult = naturalOrderE.compare(new Integer(42), new Integer(42));
        int expectedResult = 0;
        Assertions.assertEquals(expectedResult, actualResult);
        System.out.println("Actual Result: " + actualResult);
    }
    
    @Test
    void comparator2() {
        int actualResult = naturalOrder.compare(Integer.valueOf(42), Integer.valueOf(42));
        int expectedResult = 0;
        Assertions.assertEquals(expectedResult, actualResult);
        System.out.println("Actual Result: " + actualResult);
    }
    
    static Integer i;
    @Test
    void a() {
        Assertions.assertThrows(NullPointerException.class, () -> {
           if (i == 42) {
               System.out.println("unreachable");
           }
        });
    }
    
    static final Integer iFix = 42;
    @Test
    void aFix() {
        if (iFix == 42) {
            System.out.println("Unbelievable");
        }
    }
    
    @Test
    void sum() {
        Long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum +=i;
        }
        System.out.println(sum);
    }
    
    @Test
    void sumFix() {
        long sum = 0;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum +=i;
        }
        System.out.println(sum);
    }

}
