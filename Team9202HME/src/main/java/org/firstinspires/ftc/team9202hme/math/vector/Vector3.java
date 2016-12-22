package org.firstinspires.ftc.team9202hme.math.vector;


import static java.lang.Math.*;

public class Vector3 extends AbstractVector<Vector3> {
    public double x, y, z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double dot(Vector3 multiplier) {
        return (x * multiplier.x) + (y * multiplier.y) + (z * multiplier.z);
    }

    public Vector3 cross(Vector3 multiplier) {
        return new Vector3(
                (y * multiplier.z) - (z * multiplier.y),
                (z * multiplier.x) - (x * multiplier.z),
                (x * multiplier.y) - (y * multiplier.x)
        );
    }

    @Override
    public Vector3 plus(Vector3 addition) {
        return new Vector3(x + addition.x, y + addition.y, z + addition.z);
    }

    @Override
    public Vector3 minus(Vector3 subtraction) {
        return new Vector3(x - subtraction.x, y - subtraction.y, z - subtraction.z);
    }

    @Override
    public Vector3 times(double scalar) {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    @Override
    public double magnitude() {
        return sqrt((x * x) + (y * y) + (z * z));
    }

    @Override
    public Vector3 normalize() {
        double length = magnitude();
        return new Vector3(x / length, y / length, z / length);
    }

    @Override
    protected boolean isEqual(Object vector) {
        Vector3 comparator = (Vector3) vector;
        return (x == comparator.x) && (y == comparator.y) && (z == comparator.z);
    }

    @Override
    protected String toText() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
