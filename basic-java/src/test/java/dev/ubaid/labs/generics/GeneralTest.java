package dev.ubaid.labs.generics;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class GeneralTest {
    
    @Test
    void compileTimeIssueWithArrayOfGenericList() {
    }
    
    static class Good {
        {
            System.out.println("Good instance {}");
        }
        
        static {
            System.out.println("static Good {}");
        }
        
        Good() {
            System.out.println("Good constructor");
        }
    }
    
    static class Best extends Good {
        {
            System.out.println("Best instance {}");
        }

        static {
            System.out.println("static Best {}");
        }
        
        Best() {
            System.out.println("Best Constructor");
        }
    }
    
    
    
    
    @Test
    void test() {
        Best good = new Best();
    }
    
    
}
