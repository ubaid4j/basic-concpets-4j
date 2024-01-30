package dev.ubaid.labs.hk;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
    
    @Test
    void test() {
        Stream<Long> str = Stream.of(5L, 6L, 7L);

        List<Long> longs = str.collect(Collectors.toList());;
        
        longs.remove(Long.valueOf(7));

        System.out.println(longs);
    }
    
    @Test
    void intStream() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        
        for (int i = 0; i < list.size()/2; i++) {
            int val = list.get(i);
            list.set(i, list.get(list.size() - 1 -i));
            list.set(list.size() - 1 -i, val);
        }

        System.out.println(list);
    }
    
}
