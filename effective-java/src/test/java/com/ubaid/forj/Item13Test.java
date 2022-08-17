package com.ubaid.forj;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ubaid.forj.HashTableP.Entry;
import com.ubaid.forj.util.PhoneNumber;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//ITEM 13: OVERRIDE CLONE JUDICIOUSLY
public class Item13Test {
    
    private static final Logger logger = LoggerFactory.getLogger(Item13Test.class);

    @Test
    void cloneTest() throws CloneNotSupportedException {
        PhoneNumber phoneNumber = new PhoneNumber("ubaid", "111222333");
        PhoneNumber newPhoneNumber = phoneNumber.clone();
        assertNotSame(phoneNumber, newPhoneNumber);
        assertEquals(phoneNumber, newPhoneNumber);
    }
    
    @Test
    void stackTest() {
        Stack stack = new Stack();
        stack.push("Hello");
        logger.debug("stack: {}", stack);
        
        Stack stack2 = stack.clone();
        logger.debug("stack2: {}", stack2);
        
        assertNotSame(stack, stack2);
        assertEquals(stack, stack2);
        
        assertNotSame(stack.elements, stack2.elements);
        assertArrayEquals(stack.elements, stack2.elements);
        
    }
    
    @Test
    void hashTableWithProblemTest() {
        HashTableP hashTableP = new HashTableP();
        logger.debug("Original: {}", hashTableP);
        
        HashTableP hashTableP1 = hashTableP.clone();
        logger.debug("Copied: {}", hashTableP1);

        assertNotSame(hashTableP, hashTableP1);
        assertEquals(hashTableP, hashTableP1);
        
        HashTableP.Entry entry = hashTableP1.buckets[0];
        entry.value = "Kashif";
        logger.debug("\naltered the copied one\n");
        
        logger.debug("Original: {}", hashTableP);
        logger.debug("Copied: {}", hashTableP1);
        assertNotSame(hashTableP, hashTableP1);
        assertThrows(AssertionFailedError.class, () -> assertNotEquals(hashTableP, hashTableP1), 
            "As I altered the copied one, it should not equal to its original one, but it throws exception as both are equal. So, there is problem in hash table as cloning is not working as expected. the cloned object is not altered one");

    }
    @Test
    void hashTableTest() {
        HashTable hashTable = new HashTable();
        logger.debug("Original: {}", hashTable);
        
        HashTable hashTable2 = hashTable.clone();
        logger.debug("Copied: {}", hashTable2);

        assertNotSame(hashTable, hashTable2);
        assertEquals(hashTable, hashTable2);
        
        HashTableP.Entry entry = hashTable2.buckets[0];
        entry.value = "Kashif";
        logger.debug("\naltered the copied one\n");
        
        logger.debug("Original: {}", hashTable);
        logger.debug("Copied: {}", hashTable2);
        assertNotSame(hashTable, hashTable2);
        assertDoesNotThrow(() -> assertNotEquals(hashTable, hashTable2), 
            "As I altered the copied one, it should not equal to its original one");

    }
}

class Stack implements Cloneable {
    Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    
    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
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
    
    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stack stack = (Stack) o;
        return size == stack.size && Arrays.equals(elements, stack.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    @Override
    public Stack clone() {
        try {
            Stack stack = (Stack) super.clone();
            stack.elements = elements.clone();
            return stack;
        } catch (CloneNotSupportedException exp) {
            throw new AssertionError(exp);
        }
        
    }

    @Override
    public String toString() {
        return "Stack{" +
            "elements=" + Arrays.toString(elements) +
            ", size=" + size +
            '}';
    }
}

class HashTableP implements Cloneable {
    Entry[] buckets = new Entry[2];

    {
        Entry entry1 = new Entry(2, "attiq", null);
        Entry entry = new Entry(1, "ubaid", entry1);
        buckets[0] = entry;
    }
    
    static class Entry {
        final Object key;
        Object value;
        Entry next;
        
        Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        Entry deepCopy() {
            Entry result = new Entry(key, value, next);
            for (Entry p = result; p.next != null; p = p.next) {
                p.next = new Entry(p.next.key, p.next.value, p.next.next);
            }
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry entry = (Entry) o;
            return key.equals(entry.key) && value.equals(entry.value) && Objects.equals(next, entry.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value, next);
        }

        @Override
        public String toString() {
            return "Entry{" +
                "key=" + key +
                ", value=" + value +
                ", next=" + next +
                '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashTableP that = (HashTableP) o;
        return Arrays.equals(buckets, that.buckets);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(buckets);
    }

    @Override
    public String toString() {
        return "HashTableP{" +
            "buckets=" + Arrays.toString(buckets) +
            '}';
    }

    @Override
    public HashTableP clone() {
        try {
            HashTableP hashTableP = (HashTableP) super.clone();
            hashTableP.buckets = buckets.clone();
            return hashTableP;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

class HashTable implements Cloneable {
    Entry[] buckets = new Entry[2];
    
    {
        Entry entry1 = new Entry(2, "attiq", null);
        Entry entry = new Entry(1, "ubaid", entry1);
        buckets[0] = entry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashTable hashTable = (HashTable) o;
        return Arrays.equals(buckets, hashTable.buckets);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(buckets);
    }

    @Override
    public String toString() {
        return "HashTable{" +
            "buckets=" + Arrays.toString(buckets) +
            '}';
    }

    @Override
    public HashTable clone() {
        try {
            HashTable hashTable = (HashTable) super.clone();
            hashTable.buckets = new Entry[buckets.length];
            for (int i =0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    hashTable.buckets[i] = buckets[i].deepCopy();
                }
            }
            return hashTable;
        } catch (CloneNotSupportedException exp) {
            throw new RuntimeException(exp);
        }
    }
}