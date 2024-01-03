package dev.ubaid.labs.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TerminalOperationTest {
    
    @Test
    void collectionCollector() {
        Supplier<Stream<String>> strings = () -> Stream.of("one", "two", "three", "four");

        List<String> result1 = strings.get()
                .filter(s -> s.length() == 3)
                .collect(Collectors.toCollection(ArrayList::new));

        Assertions.assertDoesNotThrow(() -> result1.add("five"));
        
        List<String> result2 = strings.get()
                .toList();
        
        Assertions.assertThrows(UnsupportedOperationException.class, () -> result2.add("five"));
        
        List<String> result3 = strings.get()
                .collect(Collectors.toList());
        
        Assertions.assertDoesNotThrow(() -> result3.add("five"));
        
        List<String> linkedList = strings.get()
                .collect(Collectors.toCollection(LinkedList::new));
        
        Assertions.assertTrue(linkedList instanceof LinkedList<String>);

        Set<String> set = strings.get()
                .collect(Collectors.toUnmodifiableSet());
        
        Assertions.assertThrows(UnsupportedOperationException.class, () -> set.add("five"));
        
        IntFunction<String[]> generator = String[]::new;
        String[] arr = strings.get()
                .toArray(generator);
        System.out.println(Arrays.toString(arr));
    }
    
    @Test
    void minMaxOfStream() {
        Supplier<Stream<String>> strings = () -> Stream.of("one", "two", "three", "four");
        
        //we need to get the longest word that is three
        class LongestWordComparator implements Comparator<String> {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() > o2.length()) {
                    return 1;
                }
                
                if (o1.length() == o2.length()) {
                    return 0;
                }
                
                return -1;
            }
        }
        
        String three = strings.get()
                .max(new LongestWordComparator())
                .orElseThrow();
        
        Assertions.assertEquals("three", three);
        
        three = strings.get()
                .max(Comparator.comparing((string) -> string.length()))
                .orElseThrow();
        Assertions.assertEquals("three", three);
        
        three = strings.get()
                .max(Comparator.comparing(String::length))
                .orElseThrow();
        Assertions.assertEquals("three", three);
    }
    
    @Test
    void findingElementInStream() {
        Supplier<Stream<String>> strings = () -> Stream.of("one", "two", "three", "four");
        
        String first = strings.get()
                .findFirst()
                .orElseThrow();
        
        Assertions.assertEquals("one", first);
    }
    
    @Test
    void ifElementExists() {
        List<String> strings = List.of("one", "two", "three", "four");
        
        //check no blank
        boolean isAnyBlank = strings.stream()
                .anyMatch(String::isBlank);
        
        Assertions.assertFalse(isAnyBlank);
        
        //no one is blank
        boolean allFilled = strings.stream()
                .noneMatch(String::isBlank);
        Assertions.assertTrue(allFilled);
        
        //no blank
        boolean allFilled2 = strings.stream()
                .allMatch(Predicate.not(String::isBlank));
        Assertions.assertTrue(allFilled2);
        
        //is four exists
        boolean is4Exists = strings.stream()
                .anyMatch(string -> Objects.equals(string, "four"));
        Assertions.assertTrue(is4Exists);
    }
}
