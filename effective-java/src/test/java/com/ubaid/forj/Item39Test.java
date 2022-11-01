package com.ubaid.forj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

// ITEM 39: PREFER ANNOTATIONS TO NAMING PATTERNS
public class Item39Test {
    
    @org.junit.jupiter.api.Test
    void testSample() throws Exception {
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Sample.class;
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (InvocationTargetException wrappedException) {
                    Throwable exp = wrappedException.getCause();
                    System.out.println(m + " failed: " + exp);
                } catch (Exception exp) {
                    System.out.println("Invalid @Test: " + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }

    @org.junit.jupiter.api.Test
    void testSample2() {
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Sample2.class;
        for (Method m : testClass.getDeclaredMethods()) {
            ++tests;
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                try {
                    m.invoke(null );
                    System.out.printf("Test %s failed: no exception%n", m);
                } catch (InvocationTargetException wrappedExp) {
                    Throwable exp = wrappedExp.getCause();
                    int oldPassed = passed;
                    Class<? extends Exception>[] expTypes = m.getAnnotation(ExceptionTest.class).value();
                    for (Class<? extends Exception> expType: expTypes) {
                        if (expType.isInstance(exp)) {
                            passed++;
                            break;
                        }                        
                    }
                    if (passed == oldPassed) {
                        System.out.printf(
                            "Test %s failed: %s%n",
                            m, exp
                        );
                    }
                } catch (Exception exp) {
                    System.out.println("Invalid @Test: " + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
    
    @org.junit.jupiter.api.Test
    void testSample2WithRepeatedAnnotation() {
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Sample2.class;
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTestV2.class) 
                ||
                m.isAnnotationPresent(ExceptionTestContainer.class)
            ) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception %n", m);
                } catch (Throwable wrappedExp) {
                    Throwable exp = wrappedExp.getCause();
                    int oldPassed = passed;
                    ExceptionTestV2[] expTests = m.getAnnotationsByType(ExceptionTestV2.class);
                    for (ExceptionTestV2 expTest: expTests) {
                        if (expTest.value().isInstance(exp)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("Test %s failed: %s %n", m, exp);
                    }
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }


    static class Sample {

        @Test
        public static void m1() {
            
        }
        
        @Test
        public static void m2() {
            
        }
        
        @Test
        public static void m3() {
            throw new RuntimeException("Boom");
        }
        
        public static void m4() {
            
        }
        
        // INVALID used (instance method)
        @Test
        public void m5() {
            
        }
        
        public static void m6() {
            
        }
        
        @Test
        public static void m7() {
            throw new RuntimeException("crash");
        }
        
    }
    
    class Sample2 {

        @ExceptionTest(ArithmeticException.class)
        public static void m1() {
            int i = 0;
            i = i / i;
        }
        
        @ExceptionTest(ArithmeticException.class)
        public static void m2() {
            int[] a = new int[0];
            int i = a[1];
        }
        
        @ExceptionTest(ArithmeticException.class)
        public static void m3() {
            
        }
        
        @ExceptionTest({
            IndexOutOfBoundsException.class,
            NullPointerException.class,
        })
        public static void doublyBad() {
            List<String> list = new ArrayList<>();
            list.addAll(5, null);
        }
        
        @ExceptionTestV2(IndexOutOfBoundsException.class)
        @ExceptionTestV2(NullPointerException.class)
        public static void doublyBad2() {
            List<String> list = new ArrayList<>();
            list.addAll(5, null);
        }
        
    }

    /**
     * use only on parameterless static methods
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Test {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface ExceptionTest {
        Class<? extends Exception>[] value();
    }
    
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(ExceptionTestContainer.class)
    @interface ExceptionTestV2 {
        Class<? extends Exception> value();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface ExceptionTestContainer {
        ExceptionTestV2[] value();
    }
}