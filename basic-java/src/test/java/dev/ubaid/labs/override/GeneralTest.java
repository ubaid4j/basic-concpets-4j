package dev.ubaid.labs.override;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralTest {
    
    @Test
    void test() throws Exception {
        Parent parent = new Parent();
        Child child = new Child();
        
        parent.M1();
        child.M1();
    }
}

@Slf4j
class Parent {

    public void M1() throws Exception {
        log.debug("M1");
    }
    
    public void M2() {
        log.debug("M2");
    }
}

@Slf4j
class Child extends Parent {
    
    @Override
    public void M1() throws RuntimeException {
        log.debug("M1");
    }
    
    public void M2() {
        log.debug("M2");
    }
}


interface Movable {
    
    Logger log = LoggerFactory.getLogger(Movable.class);
    
    default void move() {
        log.debug("move");
    }
    
    private void _move() {
        
    }
    
    private static void __move() {
        
    }
    
    static void _public_move() {
        
    }
}

interface Flyable {
    
}

interface Machine extends Movable, Flyable {
    
}