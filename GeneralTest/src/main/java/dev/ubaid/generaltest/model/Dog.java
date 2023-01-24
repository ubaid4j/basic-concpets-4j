package dev.ubaid.generaltest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dog implements Movable {

    private static final Logger log = LoggerFactory.getLogger(Dog.class.getName());
    
    @Override
    public void move() {
        log.debug("Dog moves");
    }
}
