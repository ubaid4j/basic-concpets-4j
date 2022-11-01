package com.ubaid.forj;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.junit.jupiter.api.Test;

// ITEM 38: EMULATE EXTENSIBLE ENUMS WITH INTERFACES
public class Item38Test {
    
    
    interface Operation {
        double apply(double x, double y);
    }
    
    enum BasicOperation implements Operation {
        
        PLUS("+") {
            @Override
            public double apply(double x, double y) {
                return x + y;
            }
        },
        MINUS("-") {
            @Override
            public double apply(double x, double y) {
                return x - y;
            }
        },
        TIMES("*") {
            @Override
            public double apply(double x, double y) {
                return x * y;
            }
        },
        DIVIDE("/") {
            @Override
            public double apply(double x, double y) {
                return x/y;
            }
        };
        
        private final String symbol;
        
        BasicOperation(String symbol) {
            this.symbol = symbol;
        }


        @Override
        public String toString() {
            return symbol;
        }
    }
    
    enum ExtendedOperation implements Operation {
        EXP("^") {
            @Override
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        },
        REMAINDER("%") {
            @Override
            public double apply(double x, double y) {
                return x % y;
            }
        };
        
        private final String symbol;

        ExtendedOperation(String symbol) {
            this.symbol = symbol;
        }


        @Override
        public String toString() {
            return symbol;
        }
    }
    
    private static <T extends Enum<T> & Operation> void 
    test(Class<T> opEnumType, double x, double y) {
        for (Operation op : opEnumType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f %n",
                x, op, y, op.apply(x, y));
        }
    }
    
    @Test
    void test_test() {
        test(ExtendedOperation.class, 20, 9);
    }
    
    private static void testWithCollection(Collection<? extends Operation> opSet, double x, double y) {
        for (Operation operation : opSet) {
            System.out.printf("%f %s %f = %f %n",
                x, operation, y, operation.apply(x, y));
        }
    }
    
    @Test
    void test_testWithCollection() {
        testWithCollection(Arrays.asList(ExtendedOperation.values()), 20, 9);
    }
}
