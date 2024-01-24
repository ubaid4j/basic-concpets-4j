package dev.ubaid.labs.general;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class General5Test {
    
    @Test
    void test() {
//        log.info("Hello World");

        log.info("xwxx: {}, vztz: {}", hashFunction("xwxx"), hashFunction("vztz"));
        log.info("uwvy: {}, gvzz: {}", hashFunction("uwvy"), hashFunction("gvzz"));
        log.info("tttt: {}, zszt: {}", hashFunction("tttt"), hashFunction("zszt"));
        log.info("bvvv: {}, xxxw: {}", hashFunction("bvvv"), hashFunction("xxxw"));
        
        
        
        Assertions.assertEquals(hashFunction("uwvy"), hashFunction("gvzz"));
    }
    
    
    int hashFunction(String s) {
        int hash = 0;
        for (int i =0; i< s.length(); i++) {
            hash += (i+1)*(s.charAt(i)-'a'+1);
        }
        return hash;
    }
}
