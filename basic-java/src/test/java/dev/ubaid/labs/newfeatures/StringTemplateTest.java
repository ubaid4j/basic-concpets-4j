package dev.ubaid.labs.newfeatures;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringTemplateTest {
    
    @Test
    void testStringInterpolation() {
        String name = "ubaid";
        var info = STR."My name is \{name}";
        Assertions.assertEquals("My name is ubaid", info);
    }
}
