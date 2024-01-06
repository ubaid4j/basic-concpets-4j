package dev.ubaid.labs.general;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class General3Test {


    record Entry(String ch, int count) implements Comparable<Entry> {
        @Override
        public int compareTo(Entry o) {
            String thisStr = ch + "" + count;
            String otherStr = o.ch + "" + o.count;
            return thisStr.compareTo(otherStr);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(ch, entry.ch);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ch);
        }

        @Override
        public String toString() {
            return STR."{ch=\{ch}, count=\{count}}";
        }
    };

        /*Given a string, find the character with 2nd most occurrences.
    Input: "abbbcdabcda"
    Output: "a"*/
    
    static class SortingList extends AbstractList<General3Test.Entry> {
        private final List<General3Test.Entry> list;
        
        public SortingList() {
            this.list = new LinkedList<>();
        }

        @Override
        public boolean add(Entry newEntry) {
            
            int insertionIndex = list.indexOf(newEntry);
            log.info("insertion index of {} in {}: {}", newEntry, list, insertionIndex);
            if (insertionIndex < 0) {
                log.info("Adding {} last in list", newEntry);
                list.addLast(newEntry);
            } else {

                Entry existingEntry = list.get(insertionIndex);
                log.info("removing {} from list", existingEntry);
                list.remove(existingEntry);
                
                
                int adjacentInsertionPoint = insertionIndex == 0 ? 0 : insertionIndex - 1;
                Entry adjacentEntry = list.get(adjacentInsertionPoint);
                log.info("adjacent index {} and entry {}", adjacentInsertionPoint, adjacentEntry);
                
                newEntry = new Entry(existingEntry.ch, existingEntry.count + 1);
                
                if (newEntry.count > adjacentEntry.count) {
                    log.info("adding {} in list at index {}", newEntry, adjacentInsertionPoint);
                    list.add(adjacentInsertionPoint, newEntry);
                } else {
                    log.info("adding {} in list at index {}", newEntry, insertionIndex);
                    list.add(insertionIndex , newEntry);
                }
            }
            log.info("result: {} \n\n\n", list);   
            return true;
        }

        @Override
        public Entry get(int index) {
            return list.get(index);
        }

        @Override
        public int size() {
            return list.size();
        }
    }


    @Test
    void getSecondMostOccurredCharacterInGivenString() {
        final String input = "abbbcdabcda";

        
        List<Entry> sortedList = new SortingList();
        
        char[] chars = input.toCharArray();

        Map<String, Integer> internalMap = new HashMap<>(10);
        
        for (char c : chars) {
            sortedList.add(new Entry(String.valueOf(c), 1));
        }

        Assertions.assertEquals("a", sortedList.get(1).ch, "second most occurred element should be a");

    }
    
    
    @Test
    void testB() {
//        insertion index of {ch=b, count=1} in [{ch=b, count=3}, {ch=a, count=2}, {ch=c, count=1}, {ch=d, count=1}]: -3
        
        List<Entry> list = new LinkedList<>();
        list.add(new Entry("b", 3));
        list.add(new Entry("a", 2));
        list.add(new Entry("c", 1));
        list.add(new Entry("d", 1));
        
        Entry entry = new Entry("b", 3);
        
        int index = list.indexOf(entry);
        
        Assertions.assertEquals(index, 0);
    }
    
}