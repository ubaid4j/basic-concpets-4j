package dev.ubaid.labs.general;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.concurrent.Callable;

public class GeneralTest {
    
    static class A {
        private int getA() {
            return 0;
        }
        
        int getAA() {
            return 0;
        }
    }
    
    static class B extends A {
        
        private int getA() {
            return 0;
        }
        
        @Override
        int getAA() {
            System.out.println("What is your name");
            int initialVal = super.getAA();
            return initialVal + 1;
        }
    }
    
    interface A1 {
        int getA();
        
        default int getB() {
            int val = doStuff();
            return 1 + val;
        }
        
        private int doStuff() {
            return 2 + doGooStuff();
        }
        
        static int doGooStuff() {
            return 1;
        }
        
    }
    
    abstract static class Action {
        int state = 0;
        Action(int state) {
            this.state = state;
        }
        
        abstract void act();
    }
    
    
    static class ActionImp extends Action {


        public ActionImp(int state) {
            super(state);
            System.out.println("Hello World");
        }

//        abstract void act();
        void act() {}
    }
    
    
    interface ActionV1 {
        void act();
    }
    
    interface ActionV2 {
        void act() throws IOException;
    }
    
    class ActionV2Impl implements ActionV2 {
        @Override
        public void act() throws RuntimeException {
            
        }
    }
    
    static class Executor {
        public void execute(Action action) {
            action.act();
        }
        
        public void execute(ActionV1 actionV1) {
            actionV1.act();
        }
    }
    
    static class C1 implements Callable<String> {
        @Override
        public String call() {
            return null;
        }
    }
    
    class BBB {
        private BBB() {
            
        }
    }
    
    class CCC extends BBB {
        
    }
    
    @Test
    void test1() {
        Executor executor = new Executor();
        executor.execute(new Action(1) {
            @Override
            void act() {
                System.out.println("Hello World");
            }
        });
        
        executor.execute(() -> System.out.println("With V1 Hello World"));
    }
}
