package dev.ubaid.labs.newfeatures;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class PatternMatchingForSwitchTest {
    
    @Test
    void testPatternMatchingForSwitch() {
        Integer i1 = 20;
        Long l1 = 20L;
        String nullStr = null;
        String name = "ubaid";
        
        String detail = getDetail(i1);
        Assertions.assertEquals("a large integer 20", detail);
        
        detail = getDetail(l1);
        Assertions.assertEquals("a long 20", detail);
        
        detail = getDetail(nullStr);
        Assertions.assertEquals("please give me non null object", detail);
        
        detail = getDetail(name);
        Assertions.assertEquals("ubaid", detail);
    }
    
    
    private String getDetail(Object object) {
        return switch (object) {
            case null -> "please give me non null object";
            case Integer i when i > 10 -> String.format("a large integer %d", i);
            case Integer i -> String.format("a small integer %d", i);
            case Long l -> String.format("a long %d", l);
            default -> object.toString();
        };
    }
}
