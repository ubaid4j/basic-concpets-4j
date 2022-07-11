package com.ubaid.learn;

import java.util.Arrays;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@ComponentScan
public class App {
    
    @Autowired
    WebApplicationContext wac;
    
    @Bean
    public String info() {
        String appName = wac.getApplicationName();
        Date startupDate = new Date(wac.getStartupDate());
        int totalBeans = wac.getBeanDefinitionCount();
        String info = appName + " is started at " + startupDate + " with " + totalBeans + " beans";
        System.out.println(info);
        System.out.println(Arrays.toString(wac.getBeanDefinitionNames()));
        
        return info;
    }

}
