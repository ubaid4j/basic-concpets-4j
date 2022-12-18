package dev.ubaid.labs;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BreakTheRecord {

    Logger logger = LoggerFactory.getLogger(BreakTheRecord.class);
    
    @Test
    void test() {

        List<Integer> scores = List.of(12, 24, 10, 24);
        
        for (Integer score : scores) {
            Record.add(score);
        }

        int maxCount = Record.getMaxCount();
        int minCount = Record.getMinCount();
        
        logger.info("max: {}, min: {}", maxCount, minCount);
    }
    
    static class Record {

        private static int min = Integer.MAX_VALUE;
        private static int max = Integer.MIN_VALUE;
        private static int minCount = -1;
        private static int maxCount = -1;

        public static void add(int score) {
            if (score < Record.min) {
                minCount++;
                Record.min = score;
            }
            if (score > Record.max) {
                maxCount++;
                Record.max = score;
            }
        }

        public static int getMinCount() {
            return Record.minCount;
        }

        public static int getMaxCount() {
            return Record.maxCount;
        }
    }

}
