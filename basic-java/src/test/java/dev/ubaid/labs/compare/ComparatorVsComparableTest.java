package dev.ubaid.labs.compare;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ComparatorVsComparableTest {
    
    @Test
    void testComparableAndComparator() {
        
        Person p1 = new Person("A", (byte) 30);
        Person p2 = new Person("B", (byte) 29);
        Person p3 = new Person("C", (byte) 28);

        List<Person> persons = new ArrayList<>(List.of(p2, p1, p3));
        log.debug("persons: {}", persons);
        
        log.debug("\n Sorting on their default order");
        Collections.sort(persons);
        log.debug("persons: {}", persons);
        Assertions.assertArrayEquals(List.of(p1, p2, p3).toArray(), persons.toArray());
        
        log.debug("\n Sorting by age");
        persons.sort(Comparator.comparing(Person::age));
        log.debug("persons: {}", persons);
        Assertions.assertArrayEquals(List.of(p3, p2, p1).toArray(), persons.toArray());
    }
    
    @Test
    void defaultSortingBehavior() {
        List<Integer> numbers = new ArrayList<>(Stream.of(11, 10, 3, 100).toList());
        log.debug("Numbers: {}", numbers);
        Collections.sort(numbers);
        log.debug("Sorted Numbers (ASC): {}", numbers);
        numbers.sort(Comparator.reverseOrder());
        log.debug("Sorted Numbers (DSC): {}", numbers);
    }
    
}

record Person(String name, Byte age) implements Comparable<Person> {
    @Override
    public int compareTo(Person o) {
        return name.compareTo(o.name);
    }
}