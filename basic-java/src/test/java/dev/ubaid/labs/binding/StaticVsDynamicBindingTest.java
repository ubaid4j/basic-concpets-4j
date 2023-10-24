package dev.ubaid.labs.binding;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class StaticVsDynamicBindingTest {
    
    class Base {
        static String print() {
            log.debug("print at base class");
            return "base";
        }
        
        String printI() {
            log.debug("print at base class");
            return "base";
        }
    }
    
    class Derived extends Base {
        static String print() {
            log.debug("print at derived class");
            return "derived";
        }
        
        String printI() {
            log.debug("print at derived class");    
            return "derived";
        }
    }
    
    @Test
    void differenceBetweenStaticVsDynamicBinding() {
        Base baseclass = new Base();
        Base derivedClass = new Derived();
        
        Assertions.assertEquals("base", baseclass.print());
        Assertions.assertNotEquals("derived", derivedClass.print());

        Assertions.assertEquals("base", baseclass.printI());
        Assertions.assertEquals("derived", derivedClass.printI());
    }
    
    
}
