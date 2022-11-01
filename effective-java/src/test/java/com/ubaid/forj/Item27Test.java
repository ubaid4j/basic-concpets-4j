package com.ubaid.forj;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class Item27Test {
    
    @Test
    void testWarnings() {
        App1 app = new App1();
        
    }
}

class App1 {
    {
        // compiler warning unchecked assignment
        @SuppressWarnings("unchecked")
        Set<String> strings_legacy = new HashSet(); 

        // add diamond operator to eliminate the warning
        Set<String> strings = new HashSet<>();
        
    }
    
}
