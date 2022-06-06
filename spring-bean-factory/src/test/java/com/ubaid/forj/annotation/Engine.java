package com.ubaid.forj.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Engine {
    
    static Logger logger = LoggerFactory.getLogger(Engine.class);
    
    public void start() {
        logger.debug("starting engine");
    }
}
