package com.ubaid.forj;

import java.io.Serial;
import java.io.Serializable;

public class Item89Test {

}

class Singleton implements Serializable {
    private static final Singleton INSTANCE = new Singleton();
    
    public Singleton getInstance() {
        return INSTANCE;
    }
    
    @Serial
    public Object readResolve() {
        return INSTANCE;
    }
}
