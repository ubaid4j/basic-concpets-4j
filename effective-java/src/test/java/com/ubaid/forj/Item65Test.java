package com.ubaid.forj;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

// ITEM 65: PREFER INTERFACES TO REFLECTION
public class Item65Test {
 
    
    @Test
    void test1() {
        String comamnd = "java.util.HashSet c d a b";
        List<String> arrList = Arrays.stream(comamnd.split(" ")).toList();
        print(arrList);
    }
    
    @Test
    void test2() {
        String comamnd = "java.util.TreeSet c d a b";
        List<String> arrList = Arrays.stream(comamnd.split(" ")).toList();
        print(arrList);
    }

    private static void print(List<String> arrList) {
        Class<? extends Set<String>> cl = null;
        try {
            cl = (Class<? extends Set<String>>) Class.forName(arrList.get(0));
        } catch (ClassNotFoundException exp) {
            fetalError("Class not found");   
        }

        Constructor<? extends Set<String>> cons = null;
        try {
            cons = cl.getDeclaredConstructor();
        } catch (NoSuchMethodException exp) {
            fetalError("No parameterless constructor");
        }

        Set<String> s = null;

        try {
            s = cons.newInstance();
        } catch (IllegalAccessException e) {
            fetalError("Constructor not accessible");
        } catch (InstantiationException e) {
            fetalError("Class not instantiable");
        } catch (InvocationTargetException e) {
            fetalError("Constructor threw" + e.getCause());
        } catch (ClassCastException e) {
            fetalError("Class doesn't implement Set");
        }
        s.addAll(arrList.subList(1, arrList.size()));
        System.out.println(s);
    }

    private static void fetalError(String msg) {
        System.err.println(msg);
        System.exit(1);
    }
}
