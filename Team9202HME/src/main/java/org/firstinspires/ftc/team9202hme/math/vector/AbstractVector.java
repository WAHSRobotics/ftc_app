package org.firstinspires.ftc.team9202hme.math.vector;


abstract class AbstractVector<T extends AbstractVector<T>> {
    public abstract double dot(T multiplier);

    public abstract T plus(T addition);
    public abstract T minus(T subtraction);

    public abstract T times(double scalar);

    public abstract double magnitude();
    public abstract T normalize();

    protected abstract boolean isEqual(Object vector);
    protected abstract String toText();

    @Override
    public boolean equals(Object object) {
        return object instanceof AbstractVector && this.isEqual(object);
    }

    @Override
    public String toString() {
        return toText();
    }
}
