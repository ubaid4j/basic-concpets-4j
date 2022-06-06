package com.ubaid.forj.annotation;

import com.ubaid.forj.SoundSystem;
import org.springframework.stereotype.Component;

@Component
public class Car {
    private final Engine engine;
    private final SoundSystem soundSystem;

    public Car(Engine engine, SoundSystem soundSystem) {
        this.engine = engine;
        this.soundSystem = soundSystem;
    }
    
    public void start() {
        engine.start();
        soundSystem.play();
    }
}
