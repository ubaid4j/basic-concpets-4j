package com.ubaid.forj.annotation;

import com.ubaid.forj.SoundSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    
    @Bean
    public SoundSystem soundSystem() {
        return new SoundSystem();
    }
}
