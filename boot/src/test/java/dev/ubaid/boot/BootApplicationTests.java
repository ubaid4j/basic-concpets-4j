package dev.ubaid.boot;

import dev.ubaid.boot.beans.Movable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootApplicationTests {

    @Autowired
    Movable car;
    
    @Test
    void contextLoads() {
        Assertions.assertNotNull(car);
        car.move();
    }

}
