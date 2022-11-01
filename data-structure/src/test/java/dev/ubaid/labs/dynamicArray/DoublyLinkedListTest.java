package dev.ubaid.labs.dynamicArray;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoublyLinkedListTest {
    
    private static Logger logger = LoggerFactory.getLogger(DoublyLinkedList.class);
    
    @Test
    public void test1() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        
        logger.debug("{}", list);
        
        list.removeLast();

        logger.debug("After removing last: {}", list);

    }

}
