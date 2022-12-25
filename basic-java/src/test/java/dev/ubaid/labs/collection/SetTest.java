package dev.ubaid.labs.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
