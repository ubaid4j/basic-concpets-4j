package com.ubaid.forj;

import java.math.BigInteger;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

// ITEM 48: USE CAUTION WHEN MAKING STREAMS PARALLEL
public class Item48Test {
    
    @Test
    void test() {
        primes()
            .map(p -> BigInteger.TWO.pow(p.intValueExact()).subtract(BigInteger.ONE))
            .filter(mersenne -> mersenne.isProbablePrime(50))
            .limit(20)
            .forEach(System.out::println);
    }
    
    @Test
    void test2() {
        long numerOfprimes = pi(2322323);
    }
    
    static Stream<BigInteger> primes() {
        return Stream
            .iterate(BigInteger.TWO, BigInteger::nextProbablePrime);
    }
    
    static long pi(long n) {
        return LongStream
            .rangeClosed(2, n)
            .mapToObj(BigInteger::valueOf)
            .filter(i -> i.isProbablePrime(50))
            .count();
    }
}
