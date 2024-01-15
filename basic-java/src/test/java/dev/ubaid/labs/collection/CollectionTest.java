package dev.ubaid.labs.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

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
}
