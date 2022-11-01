package dev.ubaid.labs.queue;

import java.util.Iterator;
import java.util.Objects;

public class SinglyLinkedListQueueImpl<T> implements Queue<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;
    
    private static class Node<T> {
        private Node<T> next;
        private T data;

        public Node(Node<T> next, T data) {
            this.next = next;
            this.data = data;
        }
    }

    public SinglyLinkedListQueueImpl() {
        
    }

    @Override
    public T deque() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Empty Queue");
        }
        T data = head.data;
        Node<T> temp = head;
        head = head.next;
        temp.data = null;
        temp.next = null;
        return data;
    }

    @Override
    public void enqueue(T item) {
        Node<T> node = new Node<>(null, item);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        // 1 2 3 
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> trav = head;
            @Override
            public boolean hasNext() {
                return Objects.nonNull(trav.next);
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<T> iterator = iterator();
        sb.append("[");
        while(iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
