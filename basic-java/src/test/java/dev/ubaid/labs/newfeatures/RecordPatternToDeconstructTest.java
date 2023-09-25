package dev.ubaid.labs.newfeatures;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Optional;


@Slf4j
public class RecordPatternToDeconstructTest {

    record ColoredPoint(Point point, Color color) {

    }

    record Point(int x, int y) {

    }

    @Test
    void testDeConstructComplexStructure() {
        //need to get point y
        
        Point p = new Point(10, 20);
        ColoredPoint cp = new ColoredPoint(p, Color.BLUE);
        
        Optional<Integer> pointY = getPointY(cp);

        Assertions.assertFalse(pointY.isEmpty());
        Assertions.assertEquals(20, pointY.get());
        
        pointY = getPointY(p);
        Assertions.assertTrue(pointY.isEmpty());
    }
    

    Optional<Integer> getPointY(Object obj) {
        if (obj instanceof ColoredPoint(Point(int x, int y), Color c)) {
            return Optional.of(y);
        }
        return Optional.empty();
    }
}
