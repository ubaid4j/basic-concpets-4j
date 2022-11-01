package com.ubaid.forj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ITEM 42: PREFER LAMBDAS TO ANONYMOUS CLASSES
public class Item42Test {

    private static final Logger logger = LoggerFactory.getLogger(Item42Test.class);
    
    @Test
    void sortWithAnonymousClass() {
        List<String> words = new ArrayList<>(List.of("C", "AA", "B"));
        logger.debug("words: {}", words);
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o2.length(), o1.length());
            }
        });
        logger.debug("after sorting: words: {}", words);
        Assertions.assertEquals("AA", words.get(0));
    }
    
    @Test
    void sortWithLambda() {
        List<String> words = new ArrayList<>(List.of("C", "AA", "B"));
        logger.debug("words: {}", words);
        Collections.sort(words, (s1, s2) -> Integer.compare(s2.length(), s1.length()));
        logger.debug("after sorting: words: {}", words);
        Assertions.assertEquals("AA", words.get(0));
    }
    
    @Test
    void sortWithMethodReference() {
        List<String> words = new ArrayList<>(List.of("CCC", "AA", "BBBBB"));
        logger.debug("words: {}", words);
        Collections.sort(words, Comparator.comparingInt(String::length));
        logger.debug("after sorting: words: {}", words);
        Assertions.assertEquals("AA", words.get(0));
    }
    
    @Test
    void sortUsingJava8Feature() {
        List<String> words = new ArrayList<>(List.of("CCC", "AA", "BBBBB"));
        logger.debug("words: {}", words);
        words.sort(Comparator.comparingInt(String::length));
        logger.debug("after sorting: words: {}", words);
        Assertions.assertEquals("AA", words.get(0));
    }
    
    enum Operation {
        PLUS("+", (x, y) -> x+y),
        MINUS("-", (x, y) -> x-y),
        TIMES("*", (x, y) -> x*y),
        DIVIDE("/", (x, y) -> x/y);
        
        private final String symbol;
        private final DoubleBinaryOperator op;

        Operation(String symbol, DoubleBinaryOperator op) {
            this.symbol = symbol;
            this.op = op;
        }

        @Override
        public String toString() {
            return symbol;
        }
        
        public double apply(double x, double y) {
            return op.applyAsDouble(x, y);
        }
    }
}
