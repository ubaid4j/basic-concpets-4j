package com.ubaid.forj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// ITEM 28: PREFER LISTS TO ARRAYS
public class Item28Test {

    static final Logger logger = LoggerFactory.getLogger(Item28Test.class);
    
    
    @Test
    void testArrayCovariantError() {
        Object[] objArr = new Long[1];

        Assertions.assertThrows(ArrayStoreException.class, () -> {
            objArr[0] = "hello world";
        }, "It should throw exception as we are adding string in Long container");
    }
    
    @Test
    void testListInvariant() {
//       java: incompatible types: java.util.ArrayList<java.lang.Long> cannot be converted to java.util.List<java.lang.Object>
//        List<Object> list = new ArrayList<Long>();
    }
    
    @Test
    void illegalWayToCreateArr() {
        // generic type arr
        // java: cannot find symbol
//        List<E>[] genericTypeArr = new ArrayList<E>[1];
        
        //parameterized type arr
//        java: generic array creation
//        List<String>[] parameterizedArr = new ArrayList<String>[1];
        
        // type parameter array
//        java: cannot find symbol
//        E[] typeParameterArr = new E[1];
    }
    
    @Test
    void whyGenericArrayCreationIsIllegal() {
        List<String>[] stringsLists = new List[1];
        List<Integer> intList = List.of(42);
        Object[] objects = stringsLists;
        objects[0] = intList;
        
        Assertions.assertThrows(ClassCastException.class, () -> {
            String s = stringsLists[0].get(0);    
        }, "Class Cast exception as string list array  contains list of integers");
        
    }
    
    @Test
    void withOutGenericProgramRedundantCasts() {
        
        // this class needs generics
        class Chooser {
            private final Object[] choiceArray;

            public Chooser(Collection choices) {
                this.choiceArray = choices.toArray();
            }
            
            public Object choose() {
                Random rnd = ThreadLocalRandom.current();
                return choiceArray[rnd.nextInt(choiceArray.length)];
            }
        }
        
        Object[] arr = new Object[3];
        arr[0] = new Game1();
        arr[1] = new Game2();
        arr[2] = new Game3();
        Chooser chooser = new Chooser(List.of(Arrays.copyOf(arr, arr.length)));
        
        Object obj = chooser.choose();
        
        //redundant cast
        Game game = (Game) obj;
        game.play();
        
    }
    
    @Test
    void withGenericProgramNotCasts() {
        class Chooser<T> {
//            private final T[] choiceArr;

            private final List<T> choiseList;
            
            public Chooser(Collection<T> choices) {
                // java: incompatible types: java.lang.Object[] cannot be converted to T[]
                // this.choiceArr = choices.toArray();
                choiseList = new ArrayList<>(choices);
            }
            
            public T choose() {
                Random rnd = ThreadLocalRandom.current();
                return choiseList.get(rnd.nextInt(choiseList.size()));
            }
        }
        
        List<Game> games = new ArrayList<>();
        games.add(new Game1());
        games.add(new Game2());
        games.add(new Game3());

        Chooser<Game> chooser = new Chooser<>(games);

        Game game = chooser.choose();
        game.play();
    }
}

interface Game {
    Logger logger = LoggerFactory.getLogger(Game.class);
    void play();
}

abstract class AbstractGame implements Game {

    @Override
    public void play() {
        logger.info("Playing {}", this.getClass().getSimpleName());
    }
}

class Game1 extends AbstractGame {

    @Override
    public void play() {
        super.play();
    }
}

class Game2 extends AbstractGame {

    @Override
    public void play() {
        super.play();
    }
}

class Game3 extends AbstractGame {

    @Override
    public void play() {
        super.play();
    }
}
