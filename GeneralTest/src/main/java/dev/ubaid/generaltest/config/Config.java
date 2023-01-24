package dev.ubaid.generaltest.config;

import dev.ubaid.generaltest.model.Dog;
import dev.ubaid.generaltest.model.Human;
import dev.ubaid.generaltest.model.Movable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    
    @Bean
    Movable human() {
        return new Human();
    }
    
    @Bean
    Movable dog() {
        return new Dog();
    }
}
