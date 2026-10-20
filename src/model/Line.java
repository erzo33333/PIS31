package model;

public record Line(Point start, Point end) implements Shape {
    @Override
    public String describe() {
        return String.format("Line(%s, %s)", start.describe(), end.describe());
    }
}