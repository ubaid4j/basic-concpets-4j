package dev.ubaid.labs.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StreamCharacteristicTest {

    @Test
    void orderedStream() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        
        List<Integer> result = list
                .stream()
                .unordered()
                .toList();

        Assertions.assertEquals(list, result);
    }
}
