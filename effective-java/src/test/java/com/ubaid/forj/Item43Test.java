package com.ubaid.forj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ITEM 43: PREFER METHOD REFERENCES TO LAMBDAS
public class Item43Test {
    
    static final Logger logger = LoggerFactory.getLogger(Item43Test.class);
    
    @Test
    void testWithLambda() {
        Map<Integer, Integer> map = new HashMap<>();
        map.merge(1, 1, (count, incr) -> count + incr);
        logger.debug("Map ---> {}", map);
    }
    
    @Test
    void testWithMethodReference() {
        Map<Integer, Integer> map = new HashMap<>();
        map.merge(1, 1, Integer::sum);
        logger.debug("Map ---> {}", map);
        map.merge(1, 5, Integer::sum);
        logger.debug("Map ---> {}", map);
        map.merge(1, 4, Integer::sum);
        logger.debug("Map ---> {}", map);
    }
    
    // static reference
    @Test
    void testLambdaVsMethodRef() {
        List<String> list = new ArrayList<>();
        list.add("ubaid");
        
        List<String> str = list.stream()
            .peek(Item43Test::logInfo)
            .toList();
    }
    
    private static void logInfo(String name) {
        logger.debug("Name -> {}", name);
    }
    
    // bound reference
    @Test
    void testBoundReference() {
        Service101 service101 = new Service101();
        List<String> list = new ArrayList<>();
        list.add("ubaid");

        List<String> results = list
            .stream()
            .map(service101::get)
            .toList();
    }
    
    // unbound reference 
    void testUnboundReference() {
        List<String> list = new ArrayList<>();
        list.add("ubaid");

        List<Integer> results = list
            .stream()
            .map(String::length)
            .toList();
    }
    
    class Service101 {
        String get(String name) {
            return "Hello " + name;
        }
    }
}
