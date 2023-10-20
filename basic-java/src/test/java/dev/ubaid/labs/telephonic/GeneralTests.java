package dev.ubaid.labs.telephonic;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
    
    @Test
    void arrOfBytesToArrayOfCharConversion() {
        byte[] bytes = new byte[]  { 72, 101, 108, 108, 111 }; // "Hello" in ASCII encoding
        log.debug("Bytes: {}", bytes);
        String str = new String(bytes, StandardCharsets.UTF_8);
        log.debug("string: {}", str);
        char[] charArr = str.toCharArray();
        log.debug("chars: {}", charArr);
        
        char[] newCharArr = new char[bytes.length];
        int index = 0;
        for (byte b : bytes) {
            newCharArr[index++]= (char) b;
        }
        log.debug("char array by casting: {}", newCharArr);
    }
    
    @Test
    void returnStatementInFinallyClause() {
        String result = getResult(true);
        Assertions.assertEquals("finally", result, "Will be returned from finally block");
        
        result = getResult(false);
        Assertions.assertEquals("finally", result, "Will be returned from finally block");
        
        result = getResult();
        Assertions.assertEquals("finally", result, "Will be returned from finally block");
    }
    
    static String getResult(boolean throwException) {
        try {
            if (throwException) throw new RuntimeException("I am not feeling good");
            return "normal";
        } catch (Exception exp) {
            log.error("Exp: ", exp);
            throw new RuntimeException(exp);
        } finally {
            log.debug("Returning from finally");
            return "finally";
        }
    }
    
    static String getResult() {
        try {
            throw new RuntimeException();
        } finally {
            log.debug("Returning from finally");
            return "finally";
        }
    }
}
