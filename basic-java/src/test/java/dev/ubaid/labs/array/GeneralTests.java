package dev.ubaid.labs.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneralTests {

    @Test
    void testMax() {
        int[] arr = {1, 2, 3, 4, 5, 100, 6};
        Assertions.assertEquals(100, max(arr));
    }
    
    @Test
    void testMin() {
        int[] arr = {1, 2, -100, 10, 12};
        Assertions.assertEquals(-100, min(arr));
    }
    
    
    static int max(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int j : arr) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }
    
    static int min(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int j : arr) {
            if (j < min) {
                min = j;
            }
        }
        return min;
    }
    
    @Test
    void testReverseArr1() {
        int[] arr1 = {1, 2, 3};
        int[] reversedArr = reverseArr(arr1);
        Assertions.assertArrayEquals(new int[]{3, 2, 1}, reversedArr);
    }
    @Test
    void testReverseArr2() {
        int[] arr2 = {1, 2};
        int[] reversedArr2 = reverseArr(arr2);
        Assertions.assertArrayEquals(new int[]{2, 1}, reversedArr2);
    }
    @Test
    void testReverseArr3() {
        int[] arr1 = {1, 2, 3, 5, 6, 7, 8, 9};
        int[] reversedArr = reverseArr(arr1);
        Assertions.assertArrayEquals(new int[]{9, 8, 7, 6, 5, 3, 2, 1}, reversedArr);
    }
    @Test
    void testReverseArr4() {
        int[] arr1 = {1, 10, 100, 1000};
        int[] reversedArr = reverseArr(arr1);
        Assertions.assertArrayEquals(new int[]{1000, 100, 10, 1}, reversedArr);
    }
    
    static int[] reverseArr(int[] arr) {
        int mid = arr.length / 2;
        for (int i = 0; i < mid; i++) {
            int tmpVal = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = tmpVal;
        }
        return arr;
    }
}
