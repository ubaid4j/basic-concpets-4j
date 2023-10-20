package dev.ubaid.labs.telephonic.access;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

@Slf4j
public class AccessTest {
    
    private static final String fieldA = "field1";
    private final String fieldB = "fieldB";
    
    static final String packagePrivateField = "packagePrivateField";
    protected static final String protectedField = "protectedField";
    
    public static final String publicField = "publicField";
    
    @Test
    void test() {
        Helper main1 = new AccessTest.Helper();
        Helper2 main2 = new AccessTest.Helper2();
        Supplier<String> result = () -> AccessTest.fieldA;
        log.debug(result.get());
    }
    
    static class Helper {
        
        static {
            log.debug("{}", AccessTest.fieldA);
            AccessTest accessTest = new AccessTest();
            log.debug("{}", accessTest.fieldB);
        }
    }
    
    class Helper2 {
        
        static {
            log.debug("{}", AccessTest.fieldA);
            
            AccessTest accessTest = new AccessTest();
            log.debug("{}", accessTest.fieldB);
        }
    }
}