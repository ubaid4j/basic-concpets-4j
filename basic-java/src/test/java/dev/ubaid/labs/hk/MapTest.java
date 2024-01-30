package dev.ubaid.labs.hk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class MapTest {
    
    @Test
    void sortedMap() {
        List<Integer> arr = List.of(1, 2, 3, 4, 3, 2, 1);
        
        Map<Long, List<Integer>> map = arr
                .stream()
                .collect(Collectors.groupingBy((Function.identity()), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
        
        
        log.info("map: {}", map);  
    }
    
    @Test
    void mapTest() {
        Map<Integer, Integer> myMap = IntStream.range(1, 11)
                .collect(HashMap::new, (map, val) -> map.put(val, 12), HashMap::putAll);
        
        log.info("{}", myMap);
        
    }
}
