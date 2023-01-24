package dev.ubaid.generaltest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Human implements Movable {
    private static final Logger log = LoggerFactory.getLogger(Human.class.getName());

    @Override
    public void move() {
        log.debug("Human moves");
    }
}
