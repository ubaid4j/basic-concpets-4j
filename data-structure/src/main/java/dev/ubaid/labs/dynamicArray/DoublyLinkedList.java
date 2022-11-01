package dev.ubaid.labs.dynamicArray;

import java.util.Iterator;
import java.util.Objects;

public class DoublyLinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;
    
    private static class Node<T> {
        T data;
        Node<T> prev, next;
        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return (Objects.isNull(prev) ? "null" : prev.data) + "<-" + data + "->" + (Objects.isNull(next) ? "null" : next.data);
        }
    }
    
    public void clear() {
        Node<T> trav = head;
        while (trav != null) {
            Node<T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next;
        }
        head = tail = trav = null;
        size = 0;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public void add(T item) {
        addLast(item);
    }
    
    public void addFirst(T item) {
        if (isEmpty()) {
            head = tail = new Node<>(item, null, null);
        } else {
            head.prev = new Node<>(item, null, head);
            head = head.prev;
        }
        size++;
    }
    
    public void addLast(T item) {
        if (isEmpty()) {
            head = tail = new Node<>(item, null, null);
        } else {
            tail.next = new Node<>(item, tail, null);
            tail = tail.next;
        }
        size++;
    }
    
    public T peekFirst() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        return head.data;
    }
    
    public T peekLast() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        return tail.data;
    }
    
    public T removeFirst() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        Node<T> temp = head;
        head = head.next;
        T data = temp.data;
        temp.next = null;
        temp.data = null;
        size--;
        if (isEmpty()) {
            tail = null;
        } else {
            head.prev = null;
        }
        return data;
    }
    
    public T removeLast() {
        if (isEmpty()) throw new RuntimeException("Empty list");
        Node<T> temp = tail;
        tail = tail.prev;
        T data = temp.data;
        temp.prev = null;
        temp.data = null;
        size--;
        if (isEmpty()) {
            head = null;
        } else {
            tail.next = null;
        }
        return data;
    }
    
    private T remove(Node<T> node) {
        if (node.prev == null) return removeFirst();
        if (node.next == null) return removeLast();
        
        node.next.prev = node.prev;
        node.prev.next = node.next;
        
        T data = node.data;
        
        node.data = null;
        node = node.prev = node.next = null;
        
        --size;
        return data;
    }
    
    public T removeAt(int index) {
        if (index < 0 || index >= size) throw new IllegalArgumentException();
        
        int i;
        Node<T> trav;
        
        if (index < size/2) {
            for (i = 0, trav = head; i != index; i++) {
                trav = trav.next;
            }
        } else {
            for (i = size-1, trav = tail; i != index; i--) {
                trav = trav.prev;
            }
        }
        return remove(trav);
    }
    
    public boolean remove(Object obj) {
        Node<T> trav = head;
        for (;trav != null; trav = trav.next) {
            if (Objects.isNull(trav.data) || Objects.equals(trav.data, obj)) {
                remove(trav);
                return true;
            }
        }
        return false;
    }
    
    public int indexOf(Object obj) {
        int index = 0;
        Node<T> trav = head;
        for (; trav != null; trav = trav.next, index++) {
            if (Objects.isNull(trav.data) || Objects.equals(obj, trav.data)) {
                return index;
            }
        }
        return -1;
    }
    
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }
    
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> trav = head;
            @Override
            public boolean hasNext() {
                return trav != null;
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
        sb.append("[");
        Node<T> trav = head;
        while (trav != tail) {
            sb.append(trav.data).append(", ");
            trav = trav.next;
        }
        sb.append(tail.data);
        sb.append("]");
        sb.append("\nDetail: ");
        sb.append("[");
        trav = head;
        while (trav != tail) {
            sb.append(trav).append(", ");
            trav = trav.next;
        }
        sb.append(tail);
        sb.append("]");
        return sb.toString();
    }
}
