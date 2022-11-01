package com.ubaid.forj;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 20: PREFER INTERFACES TO ABSTRACT CLASSES
public class Item20Test {

    @Test
    void testUtilsIntArrayAsList() {
        int[] arr = {1, 2, 3};
        List<Integer> list = Utils.intArrayAsList(arr);

        Assertions.assertEquals(arr[0], list.get(0));
        Assertions.assertEquals(arr[1], list.get(1));
        Assertions.assertEquals(arr[2], list.get(2));
        
    }
}

interface Movable {
    String D_S = "1";
    
    default void move() {
        System.out.println("moving");
    }
    
    private static void move_() {
        System.out.println("move_");
    }
    
    static void move_1() {
        
    } 
    
    private void move_3() {
        
    }
}

class Utils {
    
    static List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);
        
        return new AbstractList<>() {
            
            @Override
            public Integer get(int index) {
                return a[index];
            }
            
            @Override
            public int size() {
                return a.length;
            }
        };
    }
}

abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {
    
    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Entry<?, ?> e)) return false;
        return Objects.equals(e.getKey(), getKey()) && Objects.equals(e.getValue(), getValue());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }
    
    public String toString() {
        return getKey() + "=" + getValue();
    }
}
