package dev.ubaid.labs.hk;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    
    @Test
    void test() {
        Stream<Long> str = Stream.of(5L, 6L, 7L);

        List<Long> longs = str.collect(Collectors.toList());;
        
        longs.remove(Long.valueOf(7));

        System.out.println(longs);
    }
    
}
