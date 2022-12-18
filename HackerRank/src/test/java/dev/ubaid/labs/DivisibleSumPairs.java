package dev.ubaid.labs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DivisibleSumPairs {
    
    @Test
    void test1() {
        
    }

    
    @Test
    void  divisibleSumPairs() {
        // Write your code here
        
        List<Integer> ar = new ArrayList<>(Arrays.asList(13,91,5,100,5,12,5,79,99,87,59,65,62,73,93,73,63,65,59,46,67,35,22,55,50,53,38,79,75,44,95,53,5,73,44,94,95,21,60,2,32,48,72,13,91,74,79,99,17,31,53,20,88,17,54,47,56,79,23,49,95,81,9,50,12,20,45,82,44,82,93,15,73,51,65,96,4,77,37,41,30,11,65,100,62,51,64,48,12,11,68,81,46,37,10,46,75,82,21,23));
        
        List<Integer> sorted = ar.stream().sorted().collect(Collectors.toList());
        
        Set<Pair> allPairs = getAllPossiblePairs(ar, 40);

        Assertions.assertEquals(100, allPairs.size());
    }

    private static Set<Pair> getAllPossiblePairs(List<Integer> ar, int k) {
        Set<Pair> listOfPairs = new HashSet<>();
        for (Integer i : ar) {
            for (Integer j : ar) {
                Pair pair = new Pair(i, j);
                if (pair.isISmallerThanJ() && pair.isSumOfPairIsDivisible(k) && !pair.isPresentIn(listOfPairs)) {
                    listOfPairs.add(pair);
                }
            }
        }
        return listOfPairs;
    }

    static class Pair {
        private final int i;
        private final int j;
        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public boolean isISmallerThanJ() {
            return i < j;
        }

        public boolean isSumOfPairIsDivisible(int k) {
            return ((i + j) % k) == 0;
        }

        @Override
        public String toString() {
            return "i=" + i + ",j=" + j;
        }

        @Override
        public boolean equals(Object o) {
            Pair pair = (Pair) o;
            return i == pair.i && j == pair.j;
        }
        
        public boolean isPresentIn(Set<Pair> pairs) {
            for (Pair pair: pairs) {
                if (equals(pair)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    @Test
    void test2() {
        Pair pair = new Pair(10, 20);
        Assertions.assertTrue(pair.isSumOfPairIsDivisible(3));
    }

}
