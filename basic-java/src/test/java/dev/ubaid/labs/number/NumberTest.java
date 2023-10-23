package dev.ubaid.labs.number;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class NumberTest {
    
    @Test
    void reverseNumberTest() {
        Assertions.assertEquals(67, reverse(76), "number should be reversed");
        Assertions.assertEquals(37, reverse(73), "number should be reversed");
        Assertions.assertEquals(12, reverse(21), "number should be reversed");      
        Assertions.assertEquals(89, reverse(98), "number should be reversed");      
        Assertions.assertEquals(112, reverse(211), "number should be reversed");      
    }
    
    int reverse(int number) {
        log.debug("number: {}", number);
        int hundred = number/100;
        log.debug("hundred: {}", hundred);
        int tenth = hundred==0 ? number/10 : number - ((number/10)*10);
        log.debug("tenth: {}", tenth);
        int unit = number - (tenth*10) - (hundred * 100);
        log.debug("unit: {}", unit);
        int reversedNumber;
        if (hundred == 0) {
            reversedNumber = (unit * 10) + tenth;
        } else {
            reversedNumber = (unit * 100) + (tenth * 10) + hundred;
        }
        return reversedNumber;
    }

    @Test
    void reverseNumberTestByString() {
        Assertions.assertEquals(67, reverseByString(76), "number should be reversed");
        Assertions.assertEquals(37, reverseByString(73), "number should be reversed");
        Assertions.assertEquals(12, reverseByString(21), "number should be reversed");
        Assertions.assertEquals(89, reverseByString(98), "number should be reversed");
        Assertions.assertEquals(112, reverseByString(211), "number should be reversed");
        Assertions.assertEquals(1122, reverseByString(2211), "number should be reversed");
        Assertions.assertEquals(3092, reverseByString(2903), "number should be reversed");
        Assertions.assertEquals(152122, reverseByString(221251), "number should be reversed");
    }


    int reverseByString(int number) {
        String val = String.valueOf(number);
        char[] numbers = val.toCharArray();
        log.debug("char array: {}", numbers);
        StringBuilder builder = new StringBuilder();
        //reverse
        for (int i = numbers.length; i > 0; i--) {
            builder.append(numbers[i - 1]);           
        }
        log.debug("reversed: {}", builder.toString());
        return Integer.parseInt(builder.toString());
    }
}