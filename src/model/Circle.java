package model;

public record Circle(Point center, double radius) implements Shape {
    @Override
    public String describe() {
        return String.format("Circle(%s, %.10g)", center.describe(), radius);
    }
}