package dev.ubaid.labs.hk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
}
