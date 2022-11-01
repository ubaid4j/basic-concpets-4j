package com.ubaid.forj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ITEM 58: PREFER FOR-EACH LOOPS TO TRADITIONAL FOR LOOPS
public class Item58Test {
    
    Logger logger = LoggerFactory.getLogger(Item58Test.class);
    
    @Test
    void traditionalForLoop() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        
        for(Iterator<Integer> i = list.iterator(); i.hasNext();) {
            Integer integer = i.next();
            logger.debug("Int: {}", integer);
        }
    }
    
    @Test
    void enhancedForLoop() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        
        for (Integer integer : list) {
            logger.debug("Int: {}", integer);
        }
    }

    enum Suit {CLUB, DIAMOND, HEART, SPADE}
    enum Rank {ACE, DEUCE, THREE, FOUR, FIVE, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}
    record Card(Suit suit, Rank rank) {

    }
    static final Collection<Suit> suites = Arrays.asList(Suit.values());
    static final Collection<Rank> ranks = Arrays.asList(Rank.values());


    @Test
    void spotABug() {
        List<Card> deck = new ArrayList<>();
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            for (Iterator<Suit> s = suites.iterator(); s.hasNext();) {
                for (Iterator<Rank> r = ranks.iterator(); r.hasNext();) {
                    // here is bug
                    // as in nested loop, we are invoking next of suit
                    // and eventually, it throws No Such Element Exception
                    deck.add(new Card(s.next(), r.next()));
                }
            }
            logger.debug("Deck: {}", deck);
        });
    }
    
    @Test
    void fix() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit: suites) {
            for (Rank rank: ranks) {
                deck.add(new Card(suit, rank));
            }
        }
        logger.debug("deck: {}", deck);
    }
    
    enum Face {ONE, TWO, THREE, FOUR, FIVE, SIX};
    static final Collection<Face> faces = EnumSet.allOf(Face.class);
    
    @Test
    void spotABug2() {
        for (Iterator<Face> i = faces.iterator(); i.hasNext();) {
            for (Iterator<Face> j = faces.iterator(); j.hasNext();) {
                // the out iterator `i` next method
                // is invoked from inner loop
                // and it all elements are iterated in that inner loop
                // so, when inner loop terminates, the out loop as well 
                // as outer iterator will have no elements to iterate
                logger.debug(i.next() + " " + j.next());
            }
        }
    }
}
