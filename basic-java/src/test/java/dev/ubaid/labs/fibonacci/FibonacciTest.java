package dev.ubaid.labs.fibonacci;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class FibonacciTest {

    @Test
    public void test() {
        log.debug("Fib: 5 -> {}", fib(5));
        log.debug("Fib: 10 -> {}", fib(10));
        log.debug("Fib: 15 -> {}", fib(15));
    }

    static long[] fib(int count) {

        long[] fibonacci = new long[count];
        fibonacci[0] = 1;
        fibonacci[1] = 2;
        for (int i = 2; i < count; i++) {
            fibonacci[i] = fibonacci[i - 2] + fibonacci[i - 1];
        }
        return fibonacci;
    }


    @Test
    void test2() {
        log.debug("Fib: 5 -> {}", fib(1, 2, 5, new long[5]));
        log.debug("Fib: 10 -> {}", fib(1, 2, 10, new long[10]));
        log.debug("Fib: 15 -> {}", fib(1, 2, 15, new long[15]));
    }

    static long[] fib(long n1, long n2, int count, long[] fib) {
        if (count > 2) {
            long n3 = n1 + n2;
            fib[fib.length - count + 2] = n3;
            fib(n2, n3, --count, fib);
        } else {
            fib[0] = 1;
            fib[1] = 2;
        }
        return fib;
    }
}   
 
