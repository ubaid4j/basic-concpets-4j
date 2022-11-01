package com.ubaid.forj;

import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 50: MAKE DEFENSIVE COPIES WHEN NEEDED
public class Item50Test {
    
    @Test
    void test() {
        Period period = new Period(new Date(), new Date());
        System.out.println("Period created: " + period);
        String p1 = period.toString();
        
        Date date = period.start();
        date.setYear(97);
        String p2 = period.toString();
        System.out.println("Period: " + period);
        
        
        Assertions.assertEquals(p1, p2);
    }
}

final class Period {
    private final Date start;
    private final Date end;


    /**
     * 
     * @param start: start the beginning of the period
     * @param end: end the end of the period; must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException if start or end is null
     */
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.after(this.end)) {
            throw new IllegalArgumentException(start + "after" + end);
        }
    }

    public Date start() {
//        return start;
        return new Date(start.getTime());
    }

    public Date end() {
//        return end;
        return new Date(end.getTime());
    }

    @Override
    public String toString() {
        return "Period{" +
            "start=" + start +
            ", end=" + end +
            '}';
    }
}
