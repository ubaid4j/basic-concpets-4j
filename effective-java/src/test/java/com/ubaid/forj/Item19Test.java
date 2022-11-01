package com.ubaid.forj;

import java.time.Instant;
import org.junit.jupiter.api.Test;

//ITEM 19: DESIGN AND DOCUMENT FOR INHERITANCE OR ELSE PROHIBIT IT
public class Item19Test {

    // final fields in two different states
    @Test
    void testErroneousInheritance() {
        Sub sub = new Sub();
        sub.overrideMe();
    }
    
}

class Super {
    public Super() {
        overrideMe();
    }
    
    public void overrideMe() {
        System.out.println(getClass().getSimpleName() + " : overrideMe");
    }
    
}

final class Sub extends Super {
    private final Instant instant;

    public Sub() {
        this.instant = Instant.now();
    }

    @Override
    public void overrideMe() {
        System.out.println(instant);
    }
}
