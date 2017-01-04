package org.firstinspires.ftc.team9202hme.math.vector;


import static java.lang.Math.*;

public class Vector2 extends AbstractVector<Vector2> {
    public static final Vector2 ZERO = new Vector2();

    public double x, y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double dot(Vector2 multiplier) {
        return (x * multiplier.x) + (y * multiplier.y);
    }

    @Override
    public Vector2 plus(Vector2 addition) {
        return new Vector2(x + addition.x, y + addition.y);
    }

    @Override
    public Vector2 minus(Vector2 subtraction) {
        return new Vector2(x - subtraction.x, y - subtraction.y);
    }

    @Override
    public Vector2 times(double scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    @Override
    public double magnitude() {
        return sqrt((x * x) + (y * y));
    }

    @Override
    public Vector2 normalize() {
        double length = magnitude();
        return new Vector2(x / length, y / length);
    }

    @Override
    protected boolean isEqual(Object vector) {
        Vector2 comparator = (Vector2) vector;
        return (x == comparator.x) && (y == comparator.y);
    }

    @Override
    protected String toText() {
        return "(\n" +
                    "\tX: " + x + ",\n" +
                    "\tY: " + y +
                "\n)";
    }
}
