package dev.ubaid.labs.telephonic;

import java.io.FileInputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class GeneralTests {

    @Test
    void whatHappendWhenReturningFromFinallyBlockWhileHavingExceptionThrownInCatchBlock() {
        Assertions.assertDoesNotThrow(() -> {
            String result = check();
            log.debug("checking: {}", result);
            Assertions.assertEquals("returned from finally block", result);
        });
    }
    
    
    static String check() {
        try {
            new FileInputStream("file.txt");
        } catch (IOException exp) {
            log.error("io exception");
            throw new RuntimeException("io exception");
        } finally {
            return "returned from finally block";
        }
    }
    
    @Test
    void reverseNumber() {
        int number = 6789;
        int expectedNumber = 9876;
        Assertions.assertEquals(expectedNumber, reverseNumber(number));
    }
    
    static int reverseNumber(int number) {
        String strVal = String.valueOf(number);
        char[] arr = strVal.toCharArray();
        
        StringBuilder sb = new StringBuilder();
        for (int i = arr.length - 1; i >= 0; i--) {
            sb.append(arr[i]);
        }
        
        String reversedNumStrVal = sb.toString();

        return Integer.parseInt(reversedNumStrVal);
    }
    
    
    @Test
    void factorial() {
        Assertions.assertEquals(120, factorial(5));
    }
    
    static long factorial(int number) {
        //5! = 5*4*3*2*1
        long result = 1;
        for (int i = 1; i <= number; i++) {
            result = result * i;
        }
        return result;
    }
}
