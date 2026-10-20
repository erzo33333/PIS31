package parser;

import model.Circle;
import model.Line;
import model.Point;
import model.Shape;


public final class ShapeParser {

    private final String src;
    private int pos;

    private ShapeParser(String src) {
        this.src = src;
        this.pos = 0;
    }

    public static Shape parse(String line) {
        if (line == null) {
            throw new IllegalArgumentException("null line");
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("empty line");
        }
        ShapeParser p = new ShapeParser(trimmed);
        Shape shape = p.parseShape();
        p.skipWs();
        if (p.pos != p.src.length()) {
            throw p.error("unexpected trailing characters");
        }
        return shape;
    }

    private Shape parseShape() {
        String name = parseIdentifier();
        skipWs();
        expect('(');
        skipWs();

        return switch (name) {
            case "Point" -> {
                double x = parseNumber();
                skipWs();
                expect(',');
                skipWs();
                double y = parseNumber();
                skipWs();
                expect(')');
                yield new Point(x, y);
            }
            case "Line" -> {
                Point start = parsePoint();
                skipWs();
                expect(',');
                skipWs();
                Point end = parsePoint();
                skipWs();
                expect(')');
                yield new Line(start, end);
            }
            case "Circle" -> {
                Point center = parsePoint();
                skipWs();
                expect(',');
                skipWs();
                double r = parseNumber();
                if (r < 0) {
                    throw error("radius must be non-negative");
                }
                skipWs();
                expect(')');
                yield new Circle(center, r);
            }
            default -> throw error("unknown shape: " + name);
        };
    }

    private Point parsePoint() {
        String name = parseIdentifier();
        if (!"Point".equals(name)) {
            throw error("expected Point, got " + name);
        }
        skipWs();
        expect('(');
        skipWs();
        double x = parseNumber();
        skipWs();
        expect(',');
        skipWs();
        double y = parseNumber();
        skipWs();
        expect(')');
        return new Point(x, y);
    }

    private String parseIdentifier() {
        int start = pos;
        while (pos < src.length() && Character.isLetter(src.charAt(pos))) {
            pos++;
        }
        if (start == pos) {
            throw error("expected identifier");
        }
        return src.substring(start, pos);
    }

    private double parseNumber() {
        skipWs();
        int start = pos;
        if (pos < src.length() && (src.charAt(pos) == '+' || src.charAt(pos) == '-')) {
            pos++;
        }
        boolean hasDigits = false;
        while (pos < src.length() && Character.isDigit(src.charAt(pos))) {
            pos++;
            hasDigits = true;
        }
        if (pos < src.length() && src.charAt(pos) == '.') {
            pos++;
            while (pos < src.length() && Character.isDigit(src.charAt(pos))) {
                pos++;
                hasDigits = true;
            }
        }

        if (!hasDigits) {
            throw error("expected number");
        }
        String num = src.substring(start, pos);
        try {
            return Double.parseDouble(num);
        } catch (NumberFormatException e) {
            throw error("invalid number: " + num);
        }
    }

    private void skipWs() {
        while (pos < src.length() && Character.isWhitespace(src.charAt(pos))) {
            pos++;
        }
    }

    private void expect(char c) {
        if (pos >= src.length() || src.charAt(pos) != c) {
            throw error("expected '" + c + "'");
        }
        pos++;
    }

    private IllegalArgumentException error(String msg) {
        return new IllegalArgumentException(msg + " at position " + pos + " in '" + src + "'");
    }
}