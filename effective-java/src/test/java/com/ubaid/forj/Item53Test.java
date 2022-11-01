package com.ubaid.forj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 53: USE VARARGS JUDICIOUSLY
public class Item53Test {

    @Test
    void test() {
        assertEquals(0, sum());
        assertEquals(6, sum(1, 2, 3));
        assertThrows(IllegalArgumentException.class, Item53Test::minByWrongWay);
        assertEquals(-2, minByWrongWay(-1, -2, 1, 2));
        assertEquals(-5, min(-5, 1, 2, 3));
    }
    
    
    static int sum(int... args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }
    
    static int minByWrongWay(int... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Too few arguments");
        }
        int min = args[0];
        for (int i = 1; i < args.length; i++) {
            if (args[i] < min) {
                min = args[i];
            }
        }
        return min;
    }
    
    static int min(int firstArg, int... remainingArgs) {
        int min = firstArg;
        for (int arg: remainingArgs) {
            if (arg < min) {
                min = arg;
            }
        }
        return min;
    }
}
