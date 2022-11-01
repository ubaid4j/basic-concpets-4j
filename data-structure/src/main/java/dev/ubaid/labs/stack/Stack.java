package dev.ubaid.labs.stack;

// Last In, First out (LIFO)
public interface Stack<T> extends Iterable<T> {
    void push(T data);
    T pop();
    int size();
    boolean isEmpty();
}
