package com.ubaid.forj.annotation.ac;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${os.name}")
    private String osName;

    @Value("${java.class.path}")
    private String javaClassPath;


    public String getOsName() {
        return osName;
    }

    public String getJavaClassPath() {
        return javaClassPath;
    }

    @Bean(name = "name")
    public String getName() {

        return "Ubaid";
    }
}
