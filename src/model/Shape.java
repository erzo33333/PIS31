package model;

public sealed interface Shape permits Point, Line, Circle {
    String describe();
}