package com.ubaid.forj;

import com.ubaid.forj.misc.ForwardingSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//ITEM 18: FAVOR COMPOSITION OVER INHERITANCE
public class Item18Test {

    @Test
    void testInstrumentedHashSet() {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
        s.addAll(List.of("Snap", "Crackle", "pop"));
        Assertions.assertNotEquals(3, s.getAddCount(), "count is not equal to 3 as inheritance breaking functionality");
    }
    
    @Test
    void testInstrumentedHasSetV2() {
        InstrumentedSet<String> s = new InstrumentedSet<>(new HashSet<>());
        s.addAll(List.of("Snap", "Crackle", "pop"));
        Assertions.assertEquals(3, s.getAddCount(), "count should be 3");
        
    }
    
}

//breaking functionality
class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }
    
    public int getAddCount() {
        return addCount;
    }
}

class InstrumentedSet<E> extends ForwardingSet<E> {
    
    private int addCount;

    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}

