package com.ubaid.forj;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// ITEM 40: CONSISTENTLY USE THE OVERRIDE ANNOTATION
public class Item40Test {
    
    @Test
    void testBigramWhenOverrideIsNotPresent() {
        Set<BigramBroken> s = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                s.add(new BigramBroken(ch, ch));
            }
        }
        System.out.println(s.size());
    }
    
    @Test
    void testBigramWithProperOverrideAnnotation() {
        Set<Bigram> s = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                s.add(new Bigram(ch, ch));
            }
        }

        System.out.println(s.size());
        Assertions.assertEquals(26, s.size());
    }
}

class BigramBroken {
    private final char first;
    private final char second;
    
    public BigramBroken(char first, char second) {
        this.first = first;
        this.second = second;
    }
    
    public boolean equals(BigramBroken b) {
        return b.first == first && b.second == second;
    }
    
    public int hashCode() {
        return 31 * first + second;
    }
}

class Bigram {
    private final char first;
    private final char second;
    
    public Bigram(char first, char second) {
        this.first = first;
        this.second = second;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Bigram b))
            return false;

        return b.first == first && b.second == second;
    }
    
    @Override
    public int hashCode() {
        return 31 * first + second;
    }
}
