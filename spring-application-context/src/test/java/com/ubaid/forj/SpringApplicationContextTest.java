package com.ubaid.forj;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContextTest {
    
    @Test
    void testSpringApplicationContext() {
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");) {
            HelloWorld helloWorld  = context.getBean("helloWorld", HelloWorld.class);
            Assertions.assertEquals("Hello World from Application Context", helloWorld.toString());
        }

    }

}
