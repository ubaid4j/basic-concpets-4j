package dev.ubaid.labs.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.NavigableMap;
import java.util.TreeMap;

@Slf4j
public class NavigableMapTest {
    
    @Test
    void testNewMethods() {
        NavigableMap<String, String> map = new TreeMap<>();
        map.put("A", "10");
        map.put("D", "10");
        map.put("C", "10");
        map.put("B", "10");
        
        log.debug("map: {}", map);
        
        //navigable map
        log.debug("descendingMap: {}", map.descendingMap());
        log.debug("higher entry than B: {}", map.higherEntry("B"));
        log.debug("lower entry than B: {}", map.lowerEntry("B"));
        
        
        //sorted map method
        log.debug("head map till B: {}", map.headMap("B", true));
        log.debug("tail map till C: {}", map.tailMap("C", true));
    }
}
