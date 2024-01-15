package dev.ubaid.labs.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class FlatMapTest {

    @Test
    void test() {
        Stream<Stream<Integer>> s1 = Stream.of(Stream.of(1), Stream.of(2), Stream.of(3), Stream.of(4));

        var result = s1
                .flatMap(Function.identity())
                .toList();

        Assertions.assertEquals(List.of(1, 2, 3, 4), result);

    }
}
