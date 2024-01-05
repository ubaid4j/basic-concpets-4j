package dev.ubaid.labs.general;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

@Slf4j
public class General2Test {


    //custom entry that holds a list of characters and their occurrence
    record OccurrenceEntry(List<String> characters, Long occurrence) {
        private static OccurrenceEntry from(Map.Entry<String, Long> entry) {
            return new OccurrenceEntry(List.of(entry.getKey()), entry.getValue());
        }
    }

    // A custom list that will merge the same occurrence elements in same entry
    // like let suppose if we have 
    // two entries c -> 2 and d -> 2 that needs to add in this list.  
    //this list will merge above entries into one  [c, d] -> 2 entry and add this entry into the list 
    static class OccurrenceList extends AbstractList<OccurrenceEntry> {

        private final Map<Long, List<String>> internalOccuranceMap = new HashMap<>();
        private final List<OccurrenceEntry> list = new LinkedList<>();

        //If entry occurrence value is same with already existed entry in the list
        //then merge the characters of this entry with already existed ones and put entry in the list.
        @Override
        public boolean add(OccurrenceEntry occurrenceEntry) {
            if (internalOccuranceMap.containsKey(occurrenceEntry.occurrence())) {
                List<String> newCharacter = new ArrayList<>(occurrenceEntry.characters());
                List<String> existingCharacters = new ArrayList<>(internalOccuranceMap.get(occurrenceEntry.occurrence()));
                List<String> allCharactersWithSameOccurrence = Stream.concat(existingCharacters.stream(), newCharacter.stream()).toList();
                internalOccuranceMap.put(occurrenceEntry.occurrence(), allCharactersWithSameOccurrence);
                list.remove(new OccurrenceEntry(existingCharacters, occurrenceEntry.occurrence)); //time complexity O(n)
                OccurrenceEntry newOccurrenceEntry = new OccurrenceEntry(allCharactersWithSameOccurrence, occurrenceEntry.occurrence);
                return list.add(newOccurrenceEntry);
            }
            internalOccuranceMap.put(occurrenceEntry.occurrence(), occurrenceEntry.characters());
            return list.add(occurrenceEntry);
        }

        @Override
        public OccurrenceEntry get(int index) {
            return list.get(index);
        }

        @Override
        public int size() {
            return list.size();
        }
    }

    
    /*Given a string, find the character with 2nd most occurrences.
    Input: "abbbcdabcda"
    Output: "a"*/

    //total time complexity: O(n log n) for worst case scenario
    @Test
    void getSecondMostOccurredCharacterInGivenString() {
        final String input = "abbbcdabcda";

        List<OccurrenceEntry> occurenceList = input.chars()
                .mapToObj(Character::toString) //get list of character
                .collect(groupingBy(identity(), counting())) //get a map character -> occurrence of char
                .entrySet()
                .stream()
                .sorted(comparingByValue(reverseOrder())) //sort that map in desc order by occurrence of characters (value) time complexity for worst case: O(n log n)
                .map(OccurrenceEntry::from) //map to custom object
                .collect(toCollection(OccurrenceList::new)); // collect in custom list that will have logic to merge same occurrence elements 

        log.info("{}", occurenceList);

        Assertions.assertEquals(List.of("a"), occurenceList.get(1).characters(), "second most occurred element should be a");


        //other tests to verify all scenarios
        Assertions.assertEquals(List.of("b"), occurenceList.getFirst().characters(), "first most occurred element should be b");
        Assertions.assertEquals(List.of("c", "d"), occurenceList.get(2).characters(), "third most occurred element should be c and d");
    }
}
