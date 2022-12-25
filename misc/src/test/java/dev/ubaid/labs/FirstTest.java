package dev.ubaid.labs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FirstTest {
    
    private static final Logger logger = LoggerFactory.getLogger(FirstTest.class);
    
    @Test
    public void test() {
        List<Integer> list = List.of(12, 4, 6, 13, 5, 10);

        List<Integer> skills = new ArrayList<>(list);
        
        List<Integer> sortedSkills = skills.stream().sorted().collect(Collectors.toList());

        int minLevel = 5;
        int maxLevel = 7;
        
        int minLevelIndex = sortedSkills.indexOf(minLevel);
        minLevelIndex = minLevelIndex == -1 ? 0 : minLevelIndex;
        int maxLevelIndex = sortedSkills.indexOf(maxLevel);
        maxLevelIndex = maxLevelIndex == -1 ? sortedSkills.size() - 1 : maxLevelIndex;        
        List<Integer> subList = sortedSkills.subList(minLevelIndex, maxLevelIndex + 1);
        
        logger.debug(subList.toString());
        
        
    }

    
    @Test
    public void test1() {
        String s = "01:01:00PM";
        String pmOrAm = s.substring(s.length() - 2);
        boolean isPM = pmOrAm.equals("PM");
        System.out.println("AM or PM: " + pmOrAm);
        String hourS = s.substring(0, 2);
        int hour = Integer.parseInt(hourS);
        System.out.println("Hour: " + hour);
        if (isPM && hour < 12) {
            hour += 12;
            s = s.replaceFirst(hourS + ":", hour + ":");
        }
        System.out.println(s);
    }
    
    @Test
    public void test3() {
        String s1 = "yes";

        String s2 = "yes";

        String s3 = new String(s1);
        
        logger.debug("s1 == s2: {}",  s1 == s2);
        logger.debug("s3 == s1: {}",  s3 == s1);
        logger.debug("s1.equals(s2): {}",  s1.equals(s2));
        logger.debug("s3.equals(s1): {}",  s3.equals(s1));
    }
    
    private static final String privateFileContent = "-----BEGIN PRIVATE KEY-----"
        + "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"
        + "r7LrouIqm7iLno+t5sOEHk835H2ZgU8="
        + "-----END PRIVATE KEY-----";
    
    private static final String publicFileContent = "-----BEGIN PUBLIC KEY-----"
        + "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA1wlcjpoBjfPlWOWzl01J"
        + "YivSIiwFyc6gLACzytvDK7Df5M8/EUycy9CCNVfFWbeE9QWClgd/1SlcS/56oPwO"
        + "GTG/fU5zviyIJgLMDmHhl/kRFbXoSjUv5L9GLlEBlQWFSRHRHeZ4Z8DtV+eKedxg"
        + "8ACR7nGTDmx5qUA+M0zeIfTszc8nRRWRHEVKIzPvZhyNkz2nW5vuEUk9hTTSHijK"
        + "6RWR79Mms1wp2TnWZN6D0JVoAYGUFBuhuWtKGxZNBHyte1UcaQUbMHeIIpt1fk1M"
        + "v7kwFEqb4bF0iOi4bm+2eCm/RU+yv5M59r9iP1clvIdhdvXfNxdXFg1c+0Vy7cTW"
        + "81jKdPDtjRDH08LvAahwNj34VomvxBEygb23lB/dTaCdQP9YkllAMTyVvVvK0F12"
        + "PPAdoFQkn943pi4+MwUMEDbeh43oA3NTXogXXek11IwnY1EQtBSkHHRsJghNMB6l"
        + "bgM7MJyNbCb7vwpyJgxVjOUa+QsNdw46I9d2WP6RwU8Qsh42yNJ2qDFjEIZtAdng"
        + "PnVM9vi1XRWsSqGiDukHuIFF2u0oc1z1d0Q2RhAAWvZTzS1LO0AF3KHkyjjOKjuV"
        + "mfYcu0SAg0e0kjzUNhblSZV6v38X/KMYDtyE874++s88pXPs5dK40W4P0P+CkTXG"
        + "s45j+LcyAEu94JnVGmJmsHMCAwEAAQ=="
        + "-----END PUBLIC KEY-----";
    
    
    @Test
    public void test4() throws Exception {
        String privateKeyContent = privateFileContent;
        String publicKeyContent = publicFileContent;

        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        System.out.println(privKey);
        System.out.println(pubKey);
    }


    private static final String PRIVATE_KEY="/home/ubaid/Downloads/test-20-dec/private_key.pem";
    private static final String PUBLIC_KEY="/home/ubaid/Downloads/test-20-dec/public_key.pem";
    private static final String issuer = "a4a82037-f45c-4889-a042-0d6d5309a0ac";
    private static final String subject = "a4a82037-f45c-4889-a042-0d6d5309a0ac";
    private static final String jwtId = "f9eaafba-2e49-11ea-8880-5ce0c5aee612";
    private static final String audience = "https://np-apigateway.epiccloud.io/v1/gateway/urn:epic:apporchard.curprod/oauth2/token";
    private static final String expiryDateString = "20/12/2022 22:10:00";
    private static final String issueDateString = "20/12/2022 21:10:00";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    @Test
    public void test5() throws Exception {
        File file = new File(PRIVATE_KEY);
        RSAPrivateKey rsaPrivateKey = readPrivateKey(file);
        file = new File(PUBLIC_KEY);
        RSAPublicKey rsaPublicKey = readPublicKey(file);
        Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
        System.out.println("Algorithm name: " + algorithm.getName());
        String token = JWT.create()
            .withIssuer(issuer)
            .withSubject(subject)
            .withAudience(audience)
            .withJWTId(jwtId)
            .withExpiresAt(simpleDateFormat.parse(expiryDateString))
            .withNotBefore(simpleDateFormat.parse(issueDateString))
            .withIssuedAt(simpleDateFormat.parse(issueDateString))
            .sign(algorithm);
        System.out.println(token);
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .withSubject(subject)
            .withAudience(audience)
            .withJWTId(jwtId)
            .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        System.out.println("verify");
    }

    public static RSAPublicKey readPublicKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()));
        String publicKeyContent = key
            .replaceAll("\\n", "")
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        return (RSAPublicKey) kf.generatePublic(keySpecX509);
    }
    public static RSAPrivateKey readPrivateKey(File file) throws Exception {
        String key = new String(Files.readAllBytes(file.toPath()));
        String privateKeyContent = key
            .replaceAll("\\n", "")
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "");
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        return (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
    }


}
