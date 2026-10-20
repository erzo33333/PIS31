package model;

public record Point(double x, double y) implements Shape {
    @Override
    public String describe() {
        return String.format("Point(%.10g, %.10g)", x, y);
    }
}