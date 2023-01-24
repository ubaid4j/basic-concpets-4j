package dev.ubaid.ioccontainer.beans.resolution;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ResolutionTest {

    @Test
    void testBeans() {
        
    }
    
}

@Configuration
class Config {
    
    @Bean
    Movable human() {
        return new Human();
    }
    
    @Bean
    Movable dog() {
        return new Dog();
    }
    
}

interface Movable  {
    
    Logger log = LoggerFactory.getLogger(Movable.class.getName());
    
    default void move() {
        log.debug("move");
    }
}

class Human implements Movable {}

class Dog implements Movable {}
