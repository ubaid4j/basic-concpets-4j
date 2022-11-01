package dev.ubaid.labs.stack;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoublyLinkedListStackImplTest {
    private static final Logger logger = LoggerFactory.getLogger(DoublyLinkedListStackImplTest.class);
    
    @Test
    public void test01() {
        Stack<Integer> stack = new DoublyLinkedListStackImpl<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        logger.debug("{}", stack);
        stack.pop();
        stack.pop();
        logger.debug("After two pop(): {}", stack);
    }
}
