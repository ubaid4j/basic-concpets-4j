package dev.ubaid.labs.stack;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleLinkedListStackImplTest {

    private static final Logger logger = LoggerFactory.getLogger(SingleLinkedListStackImpl.class);
    
    @Test
    void test1() {
        Stack<Integer> stack = new SingleLinkedListStackImpl<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        
        logger.debug("{}", stack);
        stack.pop();
        stack.pop();
        logger.debug("After two pop() -> {}", stack);
    }
}
