package dev.ubaid.labs.prime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class PrimeTest {

    @Test
    void  primeNumberCheckTest() {
        List<Integer> primeNumbers = generatePrimeNumbers(5000);
        log.debug("prime numbers til 5000: {}", primeNumbers);
    }

    static boolean isPrime(long num) {
        for (long i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    static boolean isPrime(int num) {
        return IntStream
            .rangeClosed(2, (int) Math.sqrt(num))
            .allMatch(n -> num % n != 0);
    }
    
    static List<Integer> generatePrimeNumbers(int range) {
        return IntStream
            .rangeClosed(2, range)
            .filter(PrimeTest::isPrime)
            .boxed()
            .collect(Collectors.toList());
    }
}
