package dev.ubaid.boot.beans;

public interface Movable {
    default void move() {
        System.out.println("Moving");
    }
}
