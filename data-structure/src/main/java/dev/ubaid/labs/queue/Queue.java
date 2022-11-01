package dev.ubaid.labs.queue;

public interface Queue<T> extends Iterable<T> {
    T deque();
    void enqueue(T item);
    boolean isEmpty();
    int size();
}
