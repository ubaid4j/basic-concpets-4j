package com.ubaid.forj.xml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class SpringBeanFactoryUsingXMLTest {
    
    @Test
    public void springBeanFactoryTest() {
        BeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
        HelloWorld helloWorld = (HelloWorld) factory.getBean("helloWorld");
        Assertions.assertEquals("Hello World from Spring", helloWorld.toString());
    }
    
    @Test
    public void defaultListableBeanFactoryTest() {
        DefaultListableBeanFactory beanDefinitionRegistry = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));
        HelloWorld helloWorld = (HelloWorld) beanDefinitionRegistry.getBean("helloWorld");
        Assertions.assertEquals("Hello World from Spring", helloWorld.toString());
    }

    @Test
    void testSpringApplicationContext() {
        try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");) {
            HelloWorld helloWorld  = context.getBean("helloWorld", HelloWorld.class);
            Assertions.assertEquals("Hello World from Spring", helloWorld.toString());
        }

    }


}
