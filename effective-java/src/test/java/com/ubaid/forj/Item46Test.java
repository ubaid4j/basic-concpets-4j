package com.ubaid.forj;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

// ITEM 46: PREFER SIDE-EFFECT-FREE FUNCTIONS IN STREAMS
public class Item46Test {

    
    @Test
    void streamBadPractice() throws Exception {

        Map<String, Long> freq = new HashMap<>();
        try (InputStream is = Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("frequency.txt"));
            Stream<String> words = new Scanner(is).tokens()) {
            words.forEach(word -> freq.merge(word.toLowerCase(), 1L, Long::sum));
        }
        System.out.println("freq: " + freq);
    }
    
    @Test
    void effectiveStream() throws Exception {
        Map<String, Long> freq;
        try (InputStream is = Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("frequency.txt"));
            Stream<String> words = new Scanner(is).tokens();
        ) {
            freq = words
                .collect(groupingBy(String::toLowerCase, counting()));
        }
        System.out.println("freq: " + freq);

        List<String> top5 = freq
            .keySet()
            .stream()
            .sorted(Comparator.comparing(key -> freq.get((String) key)).reversed())
            .limit(5)
            .toList();

        System.out.println("Top 5: " + top5);
    }
}
