package dev.ubaid.generaltest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Movable {
    
    Logger log = LoggerFactory.getLogger(Movable.class.getName());
    default void move() {
        log.debug("move");
    }
}
