package dev.ubaid.labs.stack;

import java.util.Iterator;
import java.util.Objects;

public class SingleLinkedListStackImpl<T> implements Stack<T> {

    private Node<T> head = null;
    private int size = 0;
    
    private static class Node<T> {
        private Node<T> next;
        private T data;

        public Node(Node<T> next, T data) {
            this.next = next;
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public SingleLinkedListStackImpl() {
        
    }

    @Override
    public void push(T data) {
        Node<T> node = new Node<>(head, data);
        head = node;
        size++;
    }

    @Override
    public T pop() {
        T data = head.data;
        Node<T> temp = head;
        head = head.next;
        temp.next = null;
        temp.data = null;
        return data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
                Node<T> next = trav.next;
                trav = next;
                return next.data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append(head.data);
        Node<T> trav = head.next;
        while (Objects.nonNull(trav)) {
            builder.append("\n").append(trav.data);
            trav = trav.next;
        }
        
        return builder.toString();
    }
}
