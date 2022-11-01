package dev.ubaid.labs.dynamicArray;

import java.util.Iterator;
import java.util.Objects;

public class ArrayList<T> implements Iterable<T>{

    private T[] arr;
    private int len = 0;
    private int capacity = 0;

    public ArrayList() {
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }
    
    public int size() {return len;}
    
    public boolean isEmpty() {return size() == 0;}
    
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            arr[i] = null;
        }
        len = 0;
    }

    @SuppressWarnings("unchecked")
    public void add(T item) {
        if (len + 1 >= capacity) {
            if (capacity == 0) capacity = 1;
            else capacity *= 2;
            T[] newArr = (T[]) new Object[capacity];
            for (int i = 0; i < len; i++) {
                newArr[i] = arr[i];
            }
            arr = newArr;
        }
        arr[len++] = item;
    }
    
    @SuppressWarnings("unchecked")
    public T removeAt(int index) {
        if (index >= len && index < 0) throw new IndexOutOfBoundsException();
        T data = arr[index];
        T[] newArr = (T[]) new Object[len - 1];
        for (int i = 0, j = 0; i < len; i++, j++) {
            if (i == index) j--;
            else newArr[j] = arr[i];
        }
        arr = newArr;
        capacity = --len;
        return data;
    }
    
    public boolean remove(Object obj) {
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(obj)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }
    
    public int indexOf(Object obj) {
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(obj)) {
                return i;
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
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < len;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    @Override
    public String toString() {
        if (len == 0) return "[]";
        StringBuilder sb = new StringBuilder(len).append("[");
        for (int i = 0;  i < len-1; i++) {
            sb.append(arr[i]).append(", ");
        }
        return sb.append(arr[len - 1]).append("]").toString();
    }
}
