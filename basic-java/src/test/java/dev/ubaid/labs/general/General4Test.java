package dev.ubaid.labs.general;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
public class General4Test {

    /*Given a string, find the character with 2nd most occurrences.
        Input: "abbbcdabcda"
        Output: "a"
    */
    
    @Test
    void getSecondMostOccurredCharacterInGivenString() {
        final String input = "abbbcdabcda";

        Map<Character, Long> occurrenceMap = input.chars() //O(n)
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), counting()));
        
        log.info("occurrence map: {}", occurrenceMap);

        long maxOccurrence = Long.MIN_VALUE;
        Character mostOccurredElement = null;
        for (Map.Entry<Character, Long> entry : occurrenceMap.entrySet()) { //O(n)
            if (entry.getValue() > maxOccurrence) {
                maxOccurrence = entry.getValue();
                mostOccurredElement = entry.getKey();
            }
        }
        occurrenceMap.remove(mostOccurredElement);
        

        maxOccurrence = Long.MIN_VALUE;
        Character secondMostOccurredElement = null;
        for (Map.Entry<Character, Long> entry : occurrenceMap.entrySet()) { //O(n)
            if (entry.getValue() > maxOccurrence) {
                maxOccurrence = entry.getValue();
                secondMostOccurredElement = entry.getKey();
            }
        }
        
        Assertions.assertEquals('a', secondMostOccurredElement);
    }
}
