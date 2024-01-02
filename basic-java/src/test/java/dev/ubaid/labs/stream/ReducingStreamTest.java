package dev.ubaid.labs.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReducingStreamTest {
    
    @Test
    void classicSum() {
        List<Integer> list = List.of(3, 2, 6, 1);
        int sum = list.getFirst();
        
        for (int index = 1; index < list.size(); index++) {
            sum += list.get(index);
        }

        Assertions.assertEquals(12, sum);
    }
    
    @Test
    void sumWithBinaryOperator() {
        List<Integer> ints = List.of(3, 2, 6, 1);

        IntBinaryOperator sum = Integer::sum;
        
        int result = ints.getFirst();
        
        for (int index = 1; index < ints.size(); index++) {
            result = sum.applyAsInt(result, ints.get(index));
        }
        
        Assertions.assertEquals(12, result);
    }
    
    @Test
    void maxWithBinaryOperator() {
        List<Integer> ints = List.of(3, 2, 6, 1);
        IntBinaryOperator max = Integer::max;
        
        int result = ints.getFirst();
        
        for (int index = 1; index < ints.size(); index++) {
            result = max.applyAsInt(result, ints.get(index));
        }
        
        Assertions.assertEquals(6, result);
    }
    
    @Test
    void mimicParallelStream() {
        List<Integer> ints = List.of(3, 6, 2, 1);
        IntBinaryOperator sum = Integer::sum;
        
        int result1 = reduce(ints.subList(0, 2), sum);
        int result2 = reduce(ints.subList(2, 4), sum);
        int result = sum.applyAsInt(result1, result2);
        
        Assertions.assertEquals(12, result);
    }
    
    private int reduce(List<Integer> ints, IntBinaryOperator sum) {
        int result = ints.getFirst();
        for (int index = 1; index < ints.size(); index++) {
            result = sum.applyAsInt(result, ints.get(index));
        }
        return result;
    }
    
    @Test
    void reducingWithIdentityElement() {
        List<Integer> ints = List.of(3, 6, 2, 1);
        IntBinaryOperator sum = Integer::sum;
        
        int identity = 0;
        System.out.println(STR."Identity: \{identity}");
        int result = identity;
        for (int i : ints) {
            result = sum.applyAsInt(result, i);
        }
        
        Assertions.assertEquals(12, result);
        
        int result1 = ints
                .stream()
                .reduce(0, Integer::sum);
        
        Assertions.assertEquals(12, result1);

        Supplier<Stream<Integer>> zeros = () -> Stream.of(0, 0, 0, 0);
        int corruptedResult = zeros.get().reduce(10, Integer::sum);
        Assertions.assertNotEquals(0, corruptedResult);
        
        int correctResult = zeros.get().reduce(0, Integer::sum);
        Assertions.assertEquals(0, correctResult);
    }
    
    @Test
    void reducingWithoutIdentityElement() {
        Stream<Integer> ints = Stream.of(2, 8, 1, 5, 3);
        Optional<Integer> max = ints.reduce(Integer::max);
        
        Assertions.assertEquals(8, max.orElseThrow());
    }
}
