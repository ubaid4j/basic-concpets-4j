package com.ubaid.forj.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringBeanFactoryUsingAnnotationTest {
    
    @Test
    void testSpringBeanFactory() {
        try (ConfigurableApplicationContext beanFactory = new AnnotationConfigApplicationContext("com.ubaid.forj.annotation")) {
            Car car = beanFactory.getBean(Car.class);
            car.start();
        }
    }
}
