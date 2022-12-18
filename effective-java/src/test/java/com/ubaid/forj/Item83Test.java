package com.ubaid.forj;

// ITEM 83: USE LAZY INITIALIZATION JUDICIOUSLY
public class Item83Test {

    public static void main(String[] args) {
        System.out.println("Initialized");
        C1 c1 = new C1();
//        Integer value = Holder1.value;
//        System.out.println("Value: " + value);
    }
    

    private static class Holder1 {
        static {
            System.out.println("Initialization Holder1");
        }
        static final Integer value = computeValue();
    }
    
    private static Integer computeValue() {
        System.out.println("computing value");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("value computed");
        return 200;
    }
}

class C1 {
    static {
        System.out.println("Initializing C1");
    }
}
