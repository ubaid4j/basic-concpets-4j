package dev.ubaid.labs.stack;

import java.util.Iterator;
import java.util.LinkedList;

public class DoublyLinkedListStackImpl<T> implements Stack<T> {

    private LinkedList<T> list = new LinkedList<>();
    
    @Override
    public void push(T data) {
        list.push(data);
    }

    @Override
    public T pop() {
        return list.pop();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append("\n").append(list.get(i));
        }
        return sb.toString();
    }
}
