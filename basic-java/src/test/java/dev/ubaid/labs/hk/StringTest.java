package dev.ubaid.labs.hk;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class StringTest {
    
    @Test
    void test() {
        String s = "07:05:45PM";

        LocalTime time = LocalTime.parse(s, DateTimeFormatter.ofPattern("hh:mm:ssa"));

        System.out.println(STR."old time: \{DateTimeFormatter.ofPattern("hh:mm:ssa").format(time)}");
        
        String newTime = DateTimeFormatter.ofPattern("HH:mm:ss").format(time);

        System.out.println(STR."new time: \{newTime}");
        
    }
}
