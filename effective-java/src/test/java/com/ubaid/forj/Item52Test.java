package com.ubaid.forj;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

// ITEM 52: USE OVERLOADING JUDICIOUSLY
public class Item52Test {
    
    @Test
    void testOverloading() {
        Collection<?>[] collections = {
            new HashSet<String>(),
            new ArrayList<BigInteger>(),
            new HashMap<String, String>().values()
        };
        
        for (Collection<?> c: collections) {
            System.out.println(CollectionClassifier.classify(c));
        }
    }
    
    @Test
    void testOverriding() {
        List<Water> waterList = List
            .of(
                new Water(),
                new PureWater(),
                new ColdWater()
            );
        
        for (Water water : waterList) {
            System.out.println(water.name());
        }
    }
    
    @Test
    void test3() {
        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();
        
        for (int i = -3; i < 3; i++) {
            set.add(i);
            list.add(i);
        }
        System.out.println("set: " + set);
        System.out.println("list: " + list);
        System.out.println("After removing");
        
        for (int i = 0; i < 3; i++) {
            set.remove(i);
            // explicit cast to remove integer
            // not remove at index
            list.remove((Integer) i);
        }

        System.out.println("set: " + set);
        System.out.println("list: " + list);
    }
    
    @Test
    void test4() {
        new Thread(System.out::println).start();

        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(() -> {
            return "Hello World";
        });
        exec.submit(() -> {
           System.out.println();
        });
    }
    
    
    static class CollectionClassifier {
        public static String classify(Set<?> s) {
            return "Set";
        }
        
        public static String classify(List<?> list) {
            return "List";
        }
        
        public static String classify(Collection<?> c) {
            return  c instanceof Set<?>  ? "Set"   :
                    c instanceof List<?> ? "List"  :
                    "Unknown Collection";
        }
    }
}

class Water {
    String name() {
        return "water";
    }
}

class PureWater extends Water {

    @Override
    String name() {
        return "pure water";
    }
}

class ColdWater extends PureWater {

    @Override
    String name() {
        return "cold water";
    }
}
