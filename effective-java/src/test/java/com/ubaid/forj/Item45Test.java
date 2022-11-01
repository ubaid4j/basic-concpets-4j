package com.ubaid.forj;

import static java.util.stream.Collectors.groupingBy;

import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

// ITEM 45: USE STREAMS JUDICIOUSLY
public class Item45Test {

    @Test
    void testAnagramWithTraditionalProgramming() throws Exception {
        int minGroupSize = 3;
        Map<String, Set<String>> groups = new HashMap<>();
        try (InputStream is = Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("anagrams.txt"));
             Scanner s = new Scanner(is)) {
            while (s.hasNext()) {
                String word = s.next();
                groups
                    .computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>())
                    .add(word);
            }
        }
        for (Set<String> group: groups.values()) {
            if (group.size() >= minGroupSize) {
                System.out.println(group.size() + ": " + group);
            }
        }
    }
    
    @Test
    void testAnagramWithBadCodeOfStreams() throws Exception {
        URL url = getClass().getClassLoader().getResource("anagrams.txt");
        Objects.requireNonNull(url);
        int minGroupSize = 3;
        Path p = Paths.get(url.toURI());
        try (Stream<String> words = Files.lines(p)) {
            words.collect(
                groupingBy(word -> 
                    word
                        .chars()
                        .sorted()
                        .collect(StringBuilder::new, (sb, c) -> sb.append((char) c), StringBuilder::append)
                        .toString())
            )
            .values().stream()
            .filter(group -> group.size() >= minGroupSize)
            .map(group -> group.size() + ": " + group)
            .forEach(System.out::println);
        }
    }
    
    @Test
    void testAnagramWithEffectiveStreams() throws Exception {
        URL url = getClass().getClassLoader().getResource("anagrams.txt");
        Objects.requireNonNull(url);
        int minGroupSize = 3;
        Path p = Paths.get(url.toURI());

        try (Stream<String> words = Files.lines(p)) {
            words
                .collect(groupingBy(Item45Test::alphabetize))
                .values()
                .stream()
                .filter(group -> group.size() >= minGroupSize)
                .forEach(g -> System.out.println(g.size() + ": " + g));
        }

    }
    
    @Test
    void testCharStream() {
        "Hello World"
        .chars()
        .forEach(System.out::print);
        System.out.println();
        "Hello World"
            .chars()
            .forEach(x -> System.out.print((char) x));

    }
    
    
    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
    
    
    @Test
    void printFirst20MersennePrime() {
        //mersenne number is a number of the form 2^p - 1 if p is prime
        primes()
            .map(p -> BigInteger.TWO.pow(p.intValueExact()).subtract(BigInteger.ONE))
            .filter(mersenne -> mersenne.isProbablePrime(50))
            .limit(20)
            .forEach(mp -> System.out.println(mp.bitLength() + ": " + mp));
    }
    
    static Stream<BigInteger> primes() {
        return Stream.iterate(BigInteger.TWO, BigInteger::nextProbablePrime);
    }
    
}
