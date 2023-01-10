package com.ubaid.forj;

import com.ubaid.forj.annotation.SpringBean;
import com.ubaid.forj.annotation.ac.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class SpringApplicationContextTest {
    
    @Test
    void testSpringApplicationContext() {
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");) {
            HelloWorld helloWorld  = context.getBean("helloWorld", HelloWorld.class);
            Assertions.assertEquals("Hello World from Application Context", helloWorld.toString());
        }
    }
    
    @Test
    void testSpringBeanLifeCycle() {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext("com.ubaid.forj");
        SpringBean bean = ctx.getBean(SpringBean.class);
        Assertions.assertEquals("Most Confidential: " + SpringBean.class.getSimpleName(),
            "Most Confidential: " + bean.getClass().getSimpleName());
        ctx.close();
    }
    

    @Test
    void testValue() {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext("com.ubaid.forj.annotation.ac");
        AppConfig appConfig = ctx.getBean(AppConfig.class);
        
        log.debug("os name: {}", appConfig.getOsName());
        log.debug("java class path: {}", appConfig.getJavaClassPath());
    }
    
}
