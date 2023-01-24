package dev.ubaid.generaltest;

import dev.ubaid.generaltest.model.Movable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeneralTestApplicationTests {

    Logger log = LoggerFactory.getLogger(GeneralTestApplication.class);
    
    @Autowired
    Movable human;
    
    @Autowired
    Movable dog;
    
    @Test
    void contextLoads() {
        human.move();
        dog.move();
    }

}
