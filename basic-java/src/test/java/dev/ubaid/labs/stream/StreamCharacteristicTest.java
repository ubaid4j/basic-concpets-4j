package dev.ubaid.labs.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    void distinctStream() {
        Stream<Integer> stream = Stream.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2);
        List<Integer> result = stream
                .distinct()
                .toList();

        Assertions.assertEquals(List.of(1, 2), result);
        ;
    }

    @Test
    void nonNullStream() {
        Stream<Integer> stream = Stream.of(null, 1, null, null, null, null, null, null, 2);
        Set<Integer> result = stream
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Assertions.assertEquals(Set.of(1, 2), result);
    }

    @Test
    void sizedStream() {
        Stream<Integer> stream = Stream.of(1, 2);
        int size = stream.toList().size();
        Assertions.assertEquals(2, size);
    }
}
