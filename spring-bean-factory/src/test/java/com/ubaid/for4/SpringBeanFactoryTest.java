package com.ubaid.for4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class SpringBeanFactoryTest {
    
    @Test
    public void springBeanFactoryTest() {
        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
        HelloWord helloWord = (HelloWord) factory.getBean("helloWorld");
        Assertions.assertEquals("Hello World from Spring", helloWord.toString());
    }
    
    @Test
    public void defaultListableBeanFactoryTest() {
        DefaultListableBeanFactory beanDefinitionRegistry = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanDefinitionRegistry);
        reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));
        HelloWord helloWord = (HelloWord) beanDefinitionRegistry.getBean("helloWorld");
        Assertions.assertEquals("Hello World from Spring", helloWord.toString());
    }
    
}
