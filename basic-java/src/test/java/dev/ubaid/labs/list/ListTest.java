package dev.ubaid.labs.list;

import dev.ubaid.labs.list.LinkedList.Node;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@Slf4j
public class ListTest {

    
    @Test
    void linkedListHasNoLoop() {
        LinkedList<Integer> list = new LinkedList<>();
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(4);
        
        log.debug("List: {}", list);

        Assertions.assertFalse(list.hasLoop());
    }
    
    @Test
    void linkedListHasLoop() {
        LinkedList<Integer> list = new LinkedList<>();
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(4);
        
        list.head.next.next = list.head.next;
        Assertions.assertTrue(list.hasLoop());
    }
    
    @Test
    void findTheMiddleElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.push(1);
        list.push(2);
        list.push(3);
        list.push(4);
        list.push(5);

        Node<Integer> middle = list.findMiddle();
        
        Assertions.assertEquals(3, middle.data);

    }
    
    @Test
    void sortArrayListInDescendingOrder() {
        List<Integer> list = new java.util.LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(43);
        list.add(4);
        list.add(50);
        
        list.sort((i1, i2) -> i2 - i1);
        
        List<Integer> expectedArray = new java.util.LinkedList<>();
        expectedArray.add(50);
        expectedArray.add(43);
        expectedArray.add(4);
        expectedArray.add(3);
        expectedArray.add(2);
        expectedArray.add(1);
        
        Assertions.assertIterableEquals(expectedArray, list);
    }

}

class LinkedList<T> {
    
    Node<T> head;
    
    static class Node<T> {
        Node<T> next;
        T data;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public void push(T data) {
        Node<T> node = new Node<>(data);
        node.next = head;
        head = node;
    }
    
    public boolean hasLoop() {
        Node<T> slow = head;
        Node<T> fast = head;
        
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
    
    public  Node<T> findMiddle() {
        Node<T> fast = head;
        Node<T> slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> temp = head;
        sb.append("]");
        while (temp != null) {
            sb.append(temp.data);
            temp = temp.next;
            if (temp != null) {
                sb.append(",");
            }
        }
        sb.append("[");
        String list = sb.toString();
        char[] arr = list.toCharArray();
        StringBuilder newSb = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; i--) {
            newSb.append(arr[i]);
        }
        
        return newSb.toString();
    }
}
