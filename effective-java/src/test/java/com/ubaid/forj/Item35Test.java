package com.ubaid.forj;

import org.junit.jupiter.api.Test;

// ITEM 35: USE INSTANCE FIELDS INSTEAD OF ORDINALS
public class Item35Test {
    
    enum EnsembleDoNotDoThis {
        SOLO,
        DUET,
        TRIO,
        QUARTET,
        QUINTET,
        SEXTET,
        SEPTET,
        OCTET,
        NONET,
        DECTET;


        // do not do this
        public int numberOfMusicians() {
            return ordinal() + 1;
        }
    }
    
    @Test
    void testEnsembleDoNoDoThis() {
        System.out.println(EnsembleDoNotDoThis.DECTET.numberOfMusicians());
    }
    
    enum Ensemble {
        SOLO(1),
        DUET(2),
        TRIO(3),
        QUARTET(4),
        QUINTEST(5),
        SEXTET(6),
        SEPTEST(7),
        OCTET(8),
        DOUBLE_QUARTET(8),
        NONTET(9),
        DECTET(10),
        TRIPLE_QUARTET(12);
        


        private final int numberOfMusicians;
        
        Ensemble(int size) {
            this.numberOfMusicians = size;
        }

        public int numberOfMusicians() {
            return numberOfMusicians;
        }
    }
}
