package com.ubaid.forj;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// OBEY THE GENERAL CONTRACT WHEN OVERRIDING EQUALS
public class Item10Test {
    
    @Test
    void testSymmetryViolation() {
        CaseInsensitiveStringViolationSymmetry cis = new CaseInsensitiveStringViolationSymmetry("Polish");
        String s = "polish";

        Assertions.assertEquals(cis, s, cis + " should equal to " + s);

        //violating symmetry
        Assertions.assertNotEquals(s, cis, s + " should equal to " + cis);
        
    }

    @Test
    void testSymmetry() {
        CaseInsensitiveStringFollowingSecondContract cis = new CaseInsensitiveStringFollowingSecondContract("Polish");
        String s = "polish";

        Assertions.assertNotEquals(cis, s, cis + " should equal to " + s);

        //violating symmetry
        Assertions.assertNotEquals(s, cis, s + " should equal to " + cis);
    }
    
    @Test
    void testColorPointSymmetryViolation() {
        Point p1 =new Point(1, 2);
        ColorPointViolateSymmetry cp1 = new ColorPointViolateSymmetry(1, 2, 1);
        
        // this assertions demonstrate symmetry violation
        Assertions.assertEquals(p1, cp1);
        Assertions.assertNotEquals(cp1, p1);
        
    }


}

final class CaseInsensitiveStringViolationSymmetry {
    private final String s;

    public CaseInsensitiveStringViolationSymmetry(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveStringViolationSymmetry)
            return s.equalsIgnoreCase(((CaseInsensitiveStringViolationSymmetry) o).s);
        if (o instanceof String)
            return s.equalsIgnoreCase((String) o);
        return false;
    }

    @Override
    public String toString() {
        return "CaseInsensitiveStringViolationSymmetry{" +
            "s='" + s + '\'' +
            '}';
    }
}

final class CaseInsensitiveStringFollowingSecondContract {
    private final String s;

    public CaseInsensitiveStringFollowingSecondContract(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CaseInsensitiveStringFollowingSecondContract
            && ((CaseInsensitiveStringFollowingSecondContract) o).s.equalsIgnoreCase(s);
    }

    @Override
    public String toString() {
        return "CaseInsensitiveStringFollowingSecondContract{" +
            "s='" + s + '\'' +
            '}';
    }
}

class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        
        Point p = (Point) o;
        return p.x == x && p.y == y;
    }

    @Override
    public String toString() {
        return "Point{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}

class ColorPointViolateSymmetry extends Point {
    private final int color;

    public ColorPointViolateSymmetry(int x, int y, int color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPointViolateSymmetry)) return false;
        return super.equals(o) && ((ColorPointViolateSymmetry) o).color == color;
    }

    @Override
    public String toString() {
        return "ColorPointViolateSymmetry{" +
            super.toString() + 
            "color=" + color +
            '}';
    }
}

