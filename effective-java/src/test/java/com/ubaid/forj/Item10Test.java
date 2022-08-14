package com.ubaid.forj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

// OBEY THE GENERAL CONTRACT WHEN OVERRIDING EQUALS
public class Item10Test {
    
    @Test
    void testSymmetryViolation() {
        CaseInsensitiveStringViolationSymmetry cis = new CaseInsensitiveStringViolationSymmetry("Polish");
        String s = "polish";

        assertEquals(cis, s, cis + " should equal to " + s);

        //violating symmetry
        assertNotEquals(s, cis, s + " should equal to " + cis);
        
    }

    @Test
    void testSymmetry() {
        CaseInsensitiveStringFollowingSecondContract cis = new CaseInsensitiveStringFollowingSecondContract("Polish");
        String s = "polish";

        assertNotEquals(cis, s, cis + " should equal to " + s);

        //violating symmetry
        assertNotEquals(s, cis, s + " should equal to " + cis);
    }
    
    @Test
    void testColorPointSymmetryViolation() {
        Point p1 =new Point(1, 2);
        ColorPointViolateSymmetry cp1 = new ColorPointViolateSymmetry(1, 2, 1);
        
        // this assertions demonstrate symmetry violation
        assertEquals(p1, cp1);
        assertNotEquals(cp1, p1);
        
    }
    
    @Test
    void testColorPointTransitivityViolation() {
        Point p1 = new ColorPointViolateTrasitivity(1, 2, 1);
        Point p2 = new Point(1, 2);
        Point p3 = new ColorPointViolateTrasitivity(1, 2, 2);
        
        //symmetry passed but
        assertEquals(p1, p2, p1 + " should equal to " + p2);
        assertEquals(p2, p1, p1 + " should equal to " + p2);
        assertEquals(p2, p3, p2 + " should equal to " + p3);
        assertEquals(p2, p2, p2 + " should equal to " + p3);
        
        //if above are true then following p1 and p3 should be equal
        //transitivity failed
        assertNotEquals(p1, p3, p1 +  " should equal to " + p3);
    }
    
    @Test
    void testSymmetryAndTransitivity() {
        Point point = new Point(1, 2);
        ColorPoint p1 = new ColorPoint(point, 1);
        ColorPoint p2 = new ColorPoint(point, 1);
        ColorPoint p3 = new ColorPoint(point, 1);

        assertEquals(p1, p2, p1 + " should equal to " + p2);
        assertEquals(p2, p1, p1 + " should equal to " + p2);
        assertEquals(p2, p3, p2 + " should equal to " + p3);
        assertEquals(p2, p2, p2 + " should equal to " + p3);
        assertEquals(p1, p3, p1 +  " should equal to " + p3);

        assertNotEquals(point, p1);
        assertNotEquals(p1, point);
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
        if (o == this) return true;
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

class ColorPointViolateTrasitivity extends Point {
    private final int color;

    public ColorPointViolateTrasitivity(int x, int y, int color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        
        // on normal point, do a color-blind comparison
        if (!(o instanceof ColorPointViolateTrasitivity)) return o.equals(this);
        
        // on color point, do a full comparison
        return super.equals(o) && ((ColorPointViolateTrasitivity) o).color == color;
    }

    @Override
    public String toString() {
        return "ColorPointViolateTrasitivity{" +
            super.toString() +
            "color=" + color +
            '}';
    }
}

//This class follow symmetry and transitivity 
class ColorPoint {
    private final Point point;
    private final int color;

    public ColorPoint(Point point, int color) {
        this.point = point;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint)) return false;
        ColorPoint cp = (ColorPoint) o;
        return cp.point.equals(point) && cp.color == color;
    }
    
    @Override
    public String toString() {
        return "ColorPoint{" +
            "point=" + point +
            ", color=" + color +
            '}';
    }
}


