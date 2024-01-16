package dev.ubaid.labs.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class ParallelStreamTest {
    
    @Test
    void parallelStream() {
        Set<String> threadNames = IntStream
                .range(0, 100)
                .parallel()
                .mapToObj(index -> Thread.currentThread().getName())
                .collect(Collectors.toSet());
        
        log.info("thread names: {}", threadNames);
    }
    
    @Test
    void inconsistentAdd() {
        List<Integer> ints = new ArrayList<>();
        
        try {
            IntStream.range(0, 1_000_000)
                    .parallel()
                    .forEach(ints::add);
        } catch (ArrayIndexOutOfBoundsException exp) {
            log.error("error", exp);
        }
        
        log.info("size: {}", ints.size());
    }
}
