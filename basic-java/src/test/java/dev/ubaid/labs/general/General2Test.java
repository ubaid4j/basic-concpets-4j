package dev.ubaid.labs.general;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class General2Test {
    
    
    /*Given a string, find the character with 2nd most occurrences.
    Input: "abbbcdabcda"
    Output: "a"*/
    
    @Test
    void getSecondMostOccurredCharacterInGivenString() {
        final String input = "abbbcdabcda";

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
        class OccurrenceList extends AbstractList<OccurrenceEntry> {

            private final Map<Long, List<String>> internalOccuranceMap = new HashMap<>();
            private final List<OccurrenceEntry> list = new ArrayList<>(); 

            @Override
            public boolean add(OccurrenceEntry occurrenceEntry) {
                if (internalOccuranceMap.containsKey(occurrenceEntry.occurrence())) {
                    List<String> newCharacter = new ArrayList<>(occurrenceEntry.characters());
                    List<String> existingCharacters = new ArrayList<>(internalOccuranceMap.get(occurrenceEntry.occurrence()));
                    List<String> allCharactersWithSameOccurrence = Stream.concat(existingCharacters.stream(), newCharacter.stream()).toList();
                    internalOccuranceMap.put(occurrenceEntry.occurrence(), allCharactersWithSameOccurrence);
                    list.remove(new OccurrenceEntry(existingCharacters, occurrenceEntry.occurrence));
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

        List<OccurrenceEntry> occurenceList = input.chars()
                .mapToObj(Character::toString) //get list of character
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) //get a map character -> occurrence of char
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //sort that map in desc order by occurrence of characters (value)
                .map(OccurrenceEntry::from) //map to custom object
                .collect(Collectors.toCollection(OccurrenceList::new)); // collect in custom list that will have logic to merge same occurrence elements 

        log.info("{}", occurenceList);

        Assertions.assertEquals(List.of("a"), occurenceList.get(1).characters(), "second most occurred element should be a");

        
        //other tests to verify all scenarios
        Assertions.assertEquals(List.of("b"), occurenceList.getFirst().characters(), "first most occurred element should be b");
        Assertions.assertEquals(List.of("c", "d"), occurenceList.get(2).characters(), "third most occurred element should be c and d");

    }
}
