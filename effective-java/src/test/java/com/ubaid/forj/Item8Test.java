package com.ubaid.forj;

import java.lang.ref.Cleaner;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Item8Test {

    @Test
    void testWithTryWith() throws Exception {
        
        try (Obj1 o1 = new Obj1()) {
            
        }
    }
    
    @Test
    void testWithFinalizer() throws Throwable {
        Obj2 o1 = new Obj2();
        o1.close();
    }
    
    @Test
    void testWithCleaner() throws Throwable {
        try (Room room = new Room(20)) {
            System.err.println("Good Bye");
        }
    }
    
    @Test
    void testWithCleanerWithoutClosingResource() throws Throwable {
        new Room(20);
        System.gc();
        System.err.println("Peace out");
    }
    
}

class Obj1 implements AutoCloseable {
    
    Logger logger = LoggerFactory.getLogger(Obj1.class);

    public Obj1() {
        logger.debug("constructing");
    }

    @Override
    public void close() throws Exception {
        logger.debug("Closing");
    }
}

class Obj2 {
    Logger logger = LoggerFactory.getLogger(Obj2.class);

    public Obj2() {
        logger.debug("constructing");
    }
    
    public void close() throws Throwable {
        this.finalize();
        logger.debug("Closing");
    }
    
}

class Room implements AutoCloseable {
    private static final Cleaner cleaner = Cleaner.create();
    private static final Logger logger = LoggerFactory.getLogger(Room.class);
    
    private static class State implements Runnable {

        int numJunkPiles; //number of junk piles in this room
        
        State(int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }
        
        // invoked by close method or cleaner
        @Override
        public void run() {
            logger.debug("Cleaning Room");
            numJunkPiles = 0;
        }
    }
    
    // the state of this room, shared with our cleanable
    private final State state;
    
    // our cleanable, cleans the room when its eligible for gc
    private final Cleaner.Cleanable cleanable;
    
    public Room(int numJunkPiles) {
        state = new State(numJunkPiles);
        cleanable = cleaner.register(this, state);
    }
    
    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}