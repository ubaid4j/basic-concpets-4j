package com.ubaid.forj;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//ITEM 11: ALWAYS OVERRIDE HASHCODE WHEN YOU OVERRIDE EQUALS
public class Item11Test {
    
    static final Logger logger = LoggerFactory.getLogger(Item11Test.class);
    
    
    @Test
    void testCoordinateWithOutHashCode() {
        CoordinateWithOutHashCode c1 = new CoordinateWithOutHashCode(0, 0);
        Map<CoordinateWithOutHashCode, String> map = new HashMap<>();
        
        map.put(c1, "jand");
        
        CoordinateWithOutHashCode jandC = new CoordinateWithOutHashCode(0, 0);
        
        logger.debug("c1.equals(jandc) = {}", c1.equals(jandC));
        logger.debug("c1.hashCode() = {} & jandC.hashCode() = {}", c1.hashCode(), jandC.hashCode());
        
        String output = map.get(jandC);

        Assertions.assertNull(output);
    }
    
    @Test
    void testCoordinateWithHasCode() {
        Coordinate c1 = new Coordinate(1, 1);
        Map<Coordinate, String> map = new HashMap<>();
        map.put(c1, "jand");
        
        Coordinate jandC = new Coordinate(1, 1);

        logger.debug("c1.equals(jandc) = {}", c1.equals(jandC));
        logger.debug("c1.hashCode() = {} & jandC.hashCode() = {}", c1.hashCode(), jandC.hashCode());
        String output = map.get(jandC);

        Assertions.assertNotNull(output);

    }
    
    @Test
    void testPrime31() {
        int result = 3000;
        int outputByMultiplication = result * 31;
        int outputByShifting = (result << 5) - result;
        Assertions.assertEquals(outputByMultiplication, outputByShifting);
    }

}


class CoordinateWithOutHashCode {
    final int x;
    final int y;

    public CoordinateWithOutHashCode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CoordinateWithOutHashCode)) return false;
        CoordinateWithOutHashCode c1 = (CoordinateWithOutHashCode) obj;
        return c1.x == x && c1.y == y;
    }

    @Override
    public String toString() {
        return "CoordinateWithOutHashCode{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}

class Coordinate {
    final int x;
    final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate c1 = (Coordinate) obj;
        return c1.x == x && c1.y == y;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}