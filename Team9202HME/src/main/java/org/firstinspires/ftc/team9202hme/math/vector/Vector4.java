package org.firstinspires.ftc.team9202hme.math.vector;


import static java.lang.Math.*;

public class Vector4 extends AbstractVector<Vector4> {
    public static final Vector4 ZERO = new Vector4();

    public double x, y, z, w;

    public Vector4() {
        this(0, 0, 0, 0);
    }

    public Vector4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public double dot(Vector4 multiplier) {
        return (x * multiplier.x) + (y * multiplier.y) + (z * multiplier.z) + (w * multiplier.w);
    }

    @Override
    public Vector4 plus(Vector4 addition) {
        return new Vector4(x + addition.x, y + addition.y, z + addition.z, w + addition.w);
    }

    @Override
    public Vector4 minus(Vector4 subtraction) {
        return new Vector4(x - subtraction.x, y - subtraction.y, z - subtraction.z, w - subtraction.w);
    }

    @Override
    public Vector4 times(double scalar) {
        return new Vector4(x * scalar, y * scalar, z * scalar, w * scalar);
    }

    @Override
    public double magnitude() {
        return sqrt((x * x) + (y * y) + (z * z) + (w * w));
    }

    @Override
    public Vector4 normalize() {
        double length = magnitude();
        return new Vector4(x / length, y / length, z / length, w / length);
    }

    @Override
    protected boolean isEqual(Object vector) {
        Vector4 comparator = (Vector4) vector;
        return (x == comparator.x) && (y == comparator.y) && (z == comparator.z) && (w == comparator.w);
    }

    @Override
    protected String toText() {
        return "(\n" +
                "\tX: " + x + ",\n" +
                "\tY: " + y + ",\n" +
                "\tZ: " + z + ",\n" +
                "\tW: " + w +
                "\n)";
    }
}
