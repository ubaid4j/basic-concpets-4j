package com.ubaid.forj;

// ITEM 23: PREFER CLASS HIERARCHIES TO TAGGED CLASSES
public class Item23Test {

}

// tagged class
class Figure {
    enum Shape {RECTANGLE, CIRCLE};

    // tag field
    final Shape shape;
    
    // for rectangle
    double length;
    double width;
    
    // for circle
    double radius;
    
    // circle constructor
    Figure(double radius) {
        this.shape = Shape.CIRCLE;
        this.radius = radius;
    }
    
    // rectangle constructor
    Figure(double length, double width) {
        this.shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }
    
    double area() {
        return switch (shape) {
            case CIRCLE -> Math.PI * (radius * radius);
            case RECTANGLE -> length * width;
        };
    }
}

// Class hierarchy replacement for a tagged class
abstract class Figure1 {
    abstract double area();
}

class Circle extends Figure1 {
    final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}

class Rectangle extends Figure1 {
    final double length;
    final double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}

class Square extends Rectangle {

    public Square(double side) {
        super(side, side);
    }
}