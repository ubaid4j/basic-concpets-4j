package dev.ubaid.labs.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
public class CollectionTest {
    
    @Test
    void containsTest() {
        Collection<String> strings = new ArrayList<>();
        strings.add("one");
        strings.add("two");
        log.info("strings: {}", strings);
        
        strings.remove("one");
        log.info("strings: {}", strings);

        Assertions.assertTrue(strings.contains("two"));
        Assertions.assertFalse(strings.contains("one"));
    }
    
    @Test
    void containsAllTest() {
        Collection<Integer> s1 = new ArrayList<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);
        
        Collection<Integer> s2 = new ArrayList<>();
        s2.add(3);
        s2.add(4);
        
        Collection<Integer> s3 = new ArrayList<>();
        s3.add(1);
        s3.add(3);
        
        Assertions.assertFalse(s1.containsAll(s2));
        Assertions.assertTrue(s1.containsAll(s3));
    }
    
    @Test
    void addAllTest() {
        Collection<Integer> s1 = new ArrayList<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);

        Collection<Integer> s2 = new ArrayList<>();
        s2.add(3);
        s2.add(4);

        Assertions.assertTrue(s1.addAll(s2));
        
        Collection<Integer> expectedArr = new ArrayList<>();
        expectedArr.add(1);
        expectedArr.add(2);
        expectedArr.add(3);
        expectedArr.add(3);
        expectedArr.add(4);

        Assertions.assertEquals(expectedArr, s1);
    }
    
    @Test
    void removeAllTest() {
        Collection<Integer> s1 = new ArrayList<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);

        Collection<Integer> s2 = new ArrayList<>();
        s2.add(3);
        s2.add(1);

        Assertions.assertTrue(s1.removeAll(s2));

        Collection<Integer> expectedArr = new ArrayList<>();
        expectedArr.add(2);

        Assertions.assertEquals(expectedArr, s1);
    }

    @Test
    void retainsAllTest() {
        Collection<Integer> s1 = new ArrayList<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);
        s1.add(4);
        s1.add(5);

        Collection<Integer> s2 = new ArrayList<>();
        s2.add(5);
        s2.add(6);
        s2.add(7);
        s2.add(8);
        s2.add(9);

        Assertions.assertTrue(s1.retainAll(s2));

        Collection<Integer> expectedArr = new ArrayList<>();
        expectedArr.add(5);

        Assertions.assertEquals(expectedArr, s1);
    }
    
    @Test
    void sizeAndIsEmptyTest() {
        Collection<Integer> s1 = new ArrayList<>();
        s1.add(1);
        
        Assertions.assertEquals(1, s1.size());
        Assertions.assertFalse(s1.isEmpty());
        
        s1.clear();
        
        Assertions.assertTrue(s1.isEmpty());
    }
    
    
    @Test
    void toArray() {
        Collection<Integer> ints = List.of(1, 2, 3, 4, 5);
        
        Object[] intsArr =  ints.toArray();
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, intsArr);
        
        Integer[] intsArr2 = ints.toArray(new Integer[0]);
        Assertions.assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, intsArr2);
        
        Integer[] intsArr3 = ints.toArray(Integer[]::new);
        Assertions.assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, intsArr3);
    }
    
    @Test
    void removePredicate() {

        Predicate<String> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNullOrIsEmpty = isNull.or(isEmpty);
        
        Collection<String> strings = new ArrayList<>();
        strings.add("one");
        strings.add("two");
        strings.add(null);
        strings.add("");
        strings.add("");
        strings.add("three");
        strings.add(null);
        strings.add("four");
        
        strings.removeIf(isNullOrIsEmpty);
        
        Assertions.assertEquals(List.of("one", "two", "three", "four"), List.copyOf(strings));
    }
    
    @Test
    void iterator() {
        Collection<String> strings = new ArrayList<>(List.of("one", "two", "three"));
        
        for (String str : strings) {
            Assertions.assertTrue(strings.contains(str));
        }
        
        
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext();) {
            String name = iterator.next();
            Assertions.assertTrue(strings.contains(name));
        }
        
        Assertions.assertThrowsExactly(ConcurrentModificationException.class, () -> {
            for (Iterator<String> iterator = strings.iterator(); iterator.hasNext();) {
                String name = iterator.next();
                Assertions.assertTrue(strings.contains(name));
                strings.remove(name);
            }
        });
    }
    
    record Range(int start, int end) implements Iterable<Integer> {
        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                
                private  int index = start;
                
                @Override
                public boolean hasNext() {
                    return index < end;
                }

                @Override
                public Integer next() {
                    if (index > end) {
                        throw new NoSuchElementException(STR."\{index}");
                    }
                    int currentIndex = index;
                    index++;
                    return currentIndex;
                }
            };
        }
    }
    
    @Test
    void iterable() {
        for (int i : new Range(10, 20)) {
            log.info("i: {}", i);
            Assertions.assertTrue(i > 9);
            Assertions.assertTrue(i < 21);
        }
    }
}
