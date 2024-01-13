package dev.ubaid.labs.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorInterfaceTest {
    
    @Test
    void typeOfCollectorInterface() {
        Collection<String> str = List.of("a", "b", "b", "b", "c", "d", "a", "b", "c", "d", "a");

        Collector<String, ?, List<String>> listCollector = Collectors.toList();
        List<String> resultList = str.stream().collect(listCollector);
        Assertions.assertEquals(str, resultList);
        
        Collector<String, ?, Set<String>> setCollector = Collectors.toSet();
        Set<String> resultSet = str.stream().collect(setCollector);
        Assertions.assertEquals(Set.copyOf(str), resultSet);
        
        Collector<String, ?, Map<String, Long>> groupByOccurrence = Collectors.groupingBy(Function.identity(), Collectors.counting());
        Map<String, Long> occurrenceMap = str.stream().collect(groupByOccurrence);
        Assertions.assertEquals(Map.of(
                "b", 4L, 
                "a", 3L, 
                "c", 2L, 
                "d", 2L), occurrenceMap);
    }
    
    
    final static class ToList<T> implements Collector<T, List<T>, List<T>> {
        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return Collection::add;
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            };
        }

        @Override
        public Function<List<T>, List<T>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.IDENTITY_FINISH);
        }
    }
    
    @Test
    void customToListCollector() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        List<Integer> result = list.stream().collect(new ToList<>());
        Assertions.assertEquals(list, result);
    }
    
    final static class StringJoiner implements Collector<String, StringBuilder, String> {
        @Override
        public Supplier<StringBuilder> supplier() {
            return StringBuilder::new;
        }

        @Override
        public BiConsumer<StringBuilder, String> accumulator() {
            return StringBuilder::append;
        }

        @Override
        public BinaryOperator<StringBuilder> combiner() {
            return (stringBuilder, stringBuilder2) -> {
                stringBuilder.append(stringBuilder2);
                return stringBuilder;
            };
        }

        @Override
        public Function<StringBuilder, String> finisher() {
            return StringBuilder::toString;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of();
        }
    }
    
    @Test
    void testStringJoiner() {
        List<String> arr = List.of("H", "e", "l", "l", "o");
        String result = arr.stream().collect(new StringJoiner());
        Assertions.assertEquals("Hello", result);
    }
}
