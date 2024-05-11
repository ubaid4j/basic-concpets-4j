package dev.ubaid.labs.hk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FlipMatrixTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }



    @Test
    void flipMatrixTest() {
        /* 
         * 1, 1, 3, 4
         * 1, 1, 7, 8
         * 9, 9, 3, 4
         * 9, 9, 9, 9
         */

        //ans = 36

        
        /*
            112 42 83 119
            56 125 56 49
            15 78 101 43
            62 98 114 108
         */
        //ans 414


        List<List<Integer>> matrix = new ArrayList<>(List.of(
            new ArrayList<>(List.of(112, 42, 83, 119)),
            new ArrayList<>(List.of(56, 125, 56, 49)),
            new ArrayList<>(List.of(15, 78, 101, 43)),
            new ArrayList<>(List.of(62, 98, 114, 108))
        ));

        reverseRowsIFSecondHalfGreaterThanFirstHalf(matrix);
        matrix = flipMatrix(matrix);
        reverseRowsIFSecondHalfGreaterThanFirstHalf(matrix);
        matrix = flipMatrix(matrix);
        
        reverseRowsIFSecondHalfGreaterThanFirstHalf(matrix);
        matrix = flipMatrix(matrix);
        reverseRowsIFSecondHalfGreaterThanFirstHalf(matrix);
        matrix = flipMatrix(matrix);


        reverseRowsIFSecondHalfGreaterThanFirstHalf(matrix);
        matrix = flipMatrix(matrix);
        reverseRowsIFSecondHalfGreaterThanFirstHalf(matrix);
        matrix = flipMatrix(matrix);
        
        print("matrix:", matrix);


        int sum = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                sum += matrix.get(i).get(j);
            }
        }

        //TODO Sat May 11 2024 4:20:11 PM 
        //fix it
        Assertions.assertEquals(390, sum);
    }
    
    List<List<Integer>> flipMatrix(List<List<Integer>> matrix) {
        List<List<Integer>> newMatrix = new ArrayList<>();
        for (int rowI = 0; rowI < matrix.size(); rowI++) {
            List<Integer> row = matrix.get(rowI);
            List<Integer> column = new ArrayList<>();
            for (int i = 0; i < row.size(); i++) {
                int val = matrix.get(i).get(rowI);
                column.add(val);
            }
            newMatrix.add(column);
        }
        return newMatrix;
    }
    
    int sum(List<Integer> list) {
        return list.stream().mapToInt(i -> i).sum();
    }
    
    void reverseRow(List<Integer> list) {
        for (int i = 0; i < list.size()/2; i++) {
            int indexVal = list.get(i);
            list.set(i, list.get(list.size() - i - 1));
            list.set(list.size() - i - 1, indexVal);
        }
    }
    
    void reverseRowsIFSecondHalfGreaterThanFirstHalf(List<List<Integer>> matrix) {
        for (List<Integer> row : matrix) {
            int mid = row.size()/2;
            int firstHalfSum = sum(row.subList(0, mid));
            int secondHalfSum = sum(row.subList(mid, row.size()));
            if (secondHalfSum > firstHalfSum) {
                reverseRow(row);
            }
        }
    }

    void print(String message, Object obj) {
        try {
            log.debug("{}: {}", message, mapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
