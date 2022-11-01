package com.ubaid.forj;

import java.util.Arrays;
import java.util.EmptyStackException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Item29Test {
    
    @Test
    void testNotGenericStack() {
        class Stack {
            private Object[] elements;
            private int size;
            private static final int DEFAULT_INITIAL_CAPACITY = 16;

            public Stack() {
                elements = new Object[DEFAULT_INITIAL_CAPACITY];
            }

            public void push(Object e) {
                ensureCapacity();
                elements[size++] = e;
            }

            public Object pop() {
                if (size == 0) {
                    throw new EmptyStackException();
                }

                Object result = elements[--size];
                elements[size] = null;
                return result;
            }

            public boolean isEmpty() {
                return size == 0;
            }

            private void ensureCapacity() {
                if (elements.length == size) {
                    elements = Arrays.copyOf(elements, 2 * size + 1);
                }
            }
        }
        
        Stack stack = new Stack();
        stack.push("2323");

        Assertions.assertThrows(ClassCastException.class, () -> {
            int item = (int) stack.pop();
        }, "It should throws exception as we are mistakenly casting object to integer");
    }
    
    @Test
    void testGenericStack() {
        class Stack<E> {
            private E[] elements;
            private int size;
            private static final int DEFAULT_INITIAL_CAPACITY = 16;
            
            // The elements array will contain only E instances from push(E)
            // This is sufficient to ensure type safety, but the runtime type
            // of the array will not be E[], it will always be Object[]!
            @SuppressWarnings("unchecked")
            public Stack() {
                //error cannot initialize not-reified arr
//                elements = new E[DEFAULT_INITIAL_CAPACITY];
                //Unchecked cast: 'java.lang.Object[]' to 'E[]'
                elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
            }
            
            public void push(E e) {
                ensureCapacity();
                elements[size++] = e;
            }
            
            public E pop() {
                if (size == 0) {
                    throw new EmptyStackException();
                }
                
                E result = elements[--size];
                elements[size] = null;
                return result;
            }
            
            public boolean isEmpty() {
                return size == 0;
            }
            
            private void ensureCapacity() {
                if (elements.length == size) {
                    elements = Arrays.copyOf(elements, 2 * size + 1);
                }
            }
        }
        
        Stack<String> stack = new Stack<>();
        stack.push("mvn");
        stack.push(" ");
        stack.push("clean");
        
        Assertions.assertDoesNotThrow(() -> {
            //compiler error (the stack will pop only string)
            //            int item = stack.pop();
            String item = stack.pop();
            Assertions.assertEquals(item, "clean", "Item should be clean");
        }, "We are not getting any class cast exception in case of generics");
    }

}
