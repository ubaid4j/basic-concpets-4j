package dev.ubaid.labs.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class SetTest {

    @Test
    void setHasDuplicateElementsWhenDontOverrideEqualsAndHash() {
        Person person1 = new Person("ubaid", 26);
        log.debug("hash of {} is {}", person1, person1.hashCode());
        Person person2 = new Person("ubaid", 26);
        log.debug("hash of {} is {}", person2, person2.hashCode());
        Person person3 = new Person("ubaid", 26);
        log.debug("hash of {} is {}", person3, person3.hashCode());
        Person person4 = new Person("ubaid", 26);
        log.debug("hash of {} is {}", person4, person4.hashCode());


        Set<Person> set = new HashSet<>();
        set.add(person1);
        set.add(person2);
        set.add(person3);
        set.add(person4);

        log.debug("set: {}", set);
        Assertions.assertEquals(4, set.size());
    }
    
    @Test
    void setHasUniqueElementsWhenWeUseProperOverrideEqualsAndHash() {
        PersonR person1 = new PersonR("ubaid", 26);
        log.debug("hash of {} is {}", person1, person1.hashCode());
        PersonR person2 = new PersonR("ubaid", 26);
        log.debug("hash of {} is {}", person2, person2.hashCode());
        PersonR person3 = new PersonR("ubaid", 26);
        log.debug("hash of {} is {}", person3, person3.hashCode());
        PersonR person4 = new PersonR("ubaid", 26);
        log.debug("hash of {} is {}", person4, person4.hashCode());


        Set<PersonR> set = new HashSet<>();
        set.add(person1);
        set.add(person2);
        set.add(person3);
        set.add(person4);

        log.debug("set: {}", set);
        Assertions.assertEquals(1, set.size());
    }
    
    @Test
    void sortByBookPriceAsc() {

        Book b1 = new Book("111", 101);
        Book b2 = new Book("112", 102);
        Book b3 = new Book("113", 103);

        List<Book> books = new ArrayList<>();

        books.add(b3);
        books.add(b1);
        books.add(b2);

        log.debug("Books unsorted: {}", books);
        
        Collections.sort(books);
        
        log.debug("Sorted Books by Ascending Order: {}", books);
        
        Assertions.assertEquals(b1, books.get(0));
    }
    
    @Test
    void sortByBookPriceDesc() {
        Book b1 = new Book("111", 101);
        Book b2 = new Book("112", 102);
        Book b3 = new Book("113", 103);

        List<Book> books = new ArrayList<>();

        books.add(b3);
        books.add(b1);
        books.add(b2);

        log.debug("Books unsorted: {}", books);


        books.sort((i1, i2) -> i2.price() - i1.price());
        
        log.debug("Sorted Books by Ascending Order: {}", books);

        Assertions.assertEquals(b3, books.get(0));
    }
    
    @Test
    void noOrderMaintainBySet() {
        List<String> strings = List.of("one", "two", "three", "four", "five");
        Set<String> set = new HashSet<>(strings);
        
        log.info("{}", set);
        
        Assertions.assertNotEquals(strings, List.copyOf(set));
    }
    
    @Test
    void sortedSet() {
        SortedSet<String> strings = new TreeSet<>(Set.of("b", "d", "c", "a"));
        
        log.info("set: {}", strings);
        
        Assertions.assertEquals(Set.of("a", "b", "c", "d"), Set.copyOf(strings));
        
        SortedSet<String> c = strings.subSet("bc", "d");
        Assertions.assertEquals(Set.of("c"), c);
        
        SortedSet<String> a = strings.headSet("b");
        Assertions.assertEquals(Set.of("a"), a);
        
        SortedSet<String> d = strings.tailSet("d");
        Assertions.assertEquals(Set.of("d"), d);
    }
    
    @Test
    void navigableSet() {
        NavigableSet<Double> numbers = new TreeSet<>(Set.of(5.0, 1.0, 3.0 ,2.0, 4.0, 3.8, 4.1));
        Assertions.assertEquals(List.of(1.0, 2.0, 3.0, 3.8, 4.0, 4.1 ,5.0), List.copyOf(numbers));
        
        Double three = numbers.floor(3.7);
        Assertions.assertEquals(three, 3.0);
        
        Double four = numbers.ceiling(3.9);
        Assertions.assertEquals(four, 4.0);
        
        Double fourPointOne = numbers.lower(5.0);
        Assertions.assertEquals(fourPointOne, 4.1);
        
        Double threePointEight = numbers.higher(3.0);
        Assertions.assertEquals(threePointEight, 3.8);
    }
}

@Getter
@Setter
@RequiredArgsConstructor
@ToString
class Person {
    private final String name;
    private final Integer age;
}

record PersonR(String name, Integer age) {};

record Book(String isbn, Integer price) implements Comparable<Book> {
    @Override
    public int compareTo(Book o) {
        return this.price - o.price;
    }
}
