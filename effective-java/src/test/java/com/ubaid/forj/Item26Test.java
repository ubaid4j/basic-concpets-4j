package com.ubaid.forj;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

// ITEM 26: DON’T USE RAW TYPES
public class Item26Test {

    static App app = new App();


    @Test
    void errorAtRuntimeWithoutGeneric() {
        assertThrows(ClassCastException.class, () -> {
            String s1 = app.get(3);
        }, "As we are expecting string from app, but collection in app contains integers as well");
    }
    
    @Test
    void errorWhenWeAddDifferentType() {
        assertThrows(ClassCastException.class, () -> {
            app.add(4);
        }, "We only add string type");
    }
    
    @Test
    void unsafeAdd() {
        class App {
            private static void unsafeAdd(List list, Object o) {
                list.add(o);
            }
        }
        
        List<String> strings = new ArrayList<>();
        assertDoesNotThrow(() -> {
            App.unsafeAdd(strings, Integer.valueOf(42));
            System.out.println(strings);
        }, "Just warning, no exception will be thrown");

        assertThrows(ClassCastException.class, () -> {
            String s = strings.get(0);
            System.out.println(strings);
        }, "class cast exception will be thrown when getting from strings" 
            + " (remember integer is stored in strings)");
        
    }
    
    @Test
    void numOfElementsInCommon() {
        class App {
            private static int numOfElementsInCommon_legacy(Set s1, Set s2) {
                int result = 0;
                for (Object o1: s1)
                    if (s2.contains(s1))
                        result++;
                return result;
            }
            
            private static int numberOfElementsInCommon(Set<?> s1, Set<?> s2) {
                int result = 0;
                for (Object o1: s1)
                    if (s2.contains(s1))
                        result++;
                return result;
            }
        }
        
        Set s1 = new HashSet();
        s1.add(1);
        Set s2 = new HashSet();
        s1.add("1");
        
        assertNotEquals(1, App.numOfElementsInCommon_legacy(s1, s2), 
            "'1' and 1 are not common");

        Set<Integer> s3 = new HashSet<>();
        s3.add(1);
        Set<String> s4 = new HashSet<>();
        s4.add("1");
        assertNotEquals(1, App.numOfElementsInCommon_legacy(s3, s4),
            "'1' and 1 are not common");


    }
    
}

class App {
    private static final List stamps = new ArrayList();
    static {
        stamps.add("1");
        stamps.add("2");
        stamps.add("3");
        stamps.add(4);
    }
    
    String get(int index) {
        return (String) stamps.get(index);
    }
    
    private static final List<String> stampsV2 = new ArrayList<>();
    static {
        stampsV2.add("1");
        stampsV2.add("2");
        stampsV2.add("3");
//        stampsV2.add(4); compile time error
    }
    
    void add(Object s) {
        stampsV2.add((String) s);
    }
    
    String getV2(int index) {
        return stampsV2.get(index);
    }
}