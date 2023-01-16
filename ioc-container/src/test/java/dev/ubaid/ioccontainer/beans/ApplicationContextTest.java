package dev.ubaid.ioccontainer.beans;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class ApplicationContextTest {
    
    @Test
    void XmlClassPathApplication() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        
        Car car = context.getBean("car", Car.class);

        log.debug("{}", car);
        
        Assertions.assertEquals(1, car.id());
    }
}
