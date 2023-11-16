package dev.ubaid.labs.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class SortTest {
    
    @Test
    void sortACollection() {
        List<Person> persons = new java.util.ArrayList<>(List.of(
                new Person("ubaid", 26, 500),
                new Person("ubaid", 24, 499),
                new Person("ubaid", 24, 479),
                new Person("ubaid", 23, 498),
                new Person("attiq", 23, 400)
        ));
        
        log.debug("Persons: {}", persons);


        Comparator<Person> ascOrderComparisonByAge = (o1, o2) -> {
            if (Objects.equals(o1.age, o2.age)) return 0;
            if (o1.age > o2.age) return 1;
            return -1;
        };
        
        persons.sort(ascOrderComparisonByAge);
        
        log.debug("Persons sort ASC by age: {}", persons);
        
        persons.sort(Comparator.comparingInt(Person::age));
        persons = persons.reversed();

        log.debug("Persons sort DSC by age: {}", persons);
    }
    
    @Test
    void sortThenTest() {
        List<Person> persons = new java.util.ArrayList<>(List.of(
                new Person("ubaid", 26, 500),
                new Person("ubaid", 24, 499),
                new Person("ubaid", 24, 479),
                new Person("ubaid", 23, 498),
                new Person("attiq", 23, 400)
        ));

        persons.sort(Comparator.comparing(Person::name).thenComparingInt(Person::age).thenComparing(Person::number));
        
        log.debug("Persons: {}", persons);
        
    }
    
    record Person(String name, Integer age, Integer number) {}
}
