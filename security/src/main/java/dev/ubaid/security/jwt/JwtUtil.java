package dev.ubaid.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class JwtUtil {
    
    private static final Random random = ThreadLocalRandom.current();
    
    public static String generateToken(String subject, String secretKey) {
        
        return Jwts
            .builder()
            .setId(String.valueOf(random.nextInt()))
            .setSubject(subject)
            .setIssuer("ubaid.dev")
            .setAudience("profile")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3)))
            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(secretKey.getBytes()))
            .compact();
    }
    
    public static Claims getClaims(String token, String secretKey) {
        return Jwts.parser()
            .setSigningKey(Base64.getEncoder().encode(secretKey.getBytes()))
            .parseClaimsJws(token)
            .getBody();
    }
}
