package dev.ubaid.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class JwtUtilTest {
    
    @Test
    void testTokenGeneration() {
        String token = JwtUtil.generateToken("ubaid", "secret_here_some_more_words");
        log.debug("token: {}", token);  
    }
}
