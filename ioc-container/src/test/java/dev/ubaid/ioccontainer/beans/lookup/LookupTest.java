package dev.ubaid.ioccontainer.beans.lookup;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class LookupTest {

    @Test
    @RepeatedTest(value = 5)
    void testLookup() {
        
    }
    
}

@Component
abstract class App {
    void printPoint() {
        
    }
    
    abstract Point getPoint();
}

@Configuration
class Config {
    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    Point point() {
        return new Point();
    }
}

class Point {
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final int x;
    private final int y;

    public Point() {
        this.x = random.nextInt();
        this.y = random.nextInt();
    }

    @Override
    public String toString() {
        return "Point{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
