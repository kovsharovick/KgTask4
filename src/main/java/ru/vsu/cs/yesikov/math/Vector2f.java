package ru.vsu.cs.yesikov.math;

public class Vector2f {

    private final float[] values;

    public Vector2f(float[] values) {
        if (values.length != 2) {
            throw new IllegalArgumentException("Vector size must be 2.");
        }
        this.values = new float[values.length];
        this.values[0] = values[0];
        this.values[1] = values[1];
    }

    public Vector2f(float x, float y) {
        values = new float[2];
        this.values[0] = x;
        this.values[1] = y;
    }

    public float[] getValues() {
        return values;
    }

    public float x() {
        return values[0];
    }

    public float y() {
        return values[1];
    }

    @Override
    public boolean equals(Object o) {
        final float eps = 1e-7f;
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return Math.abs(values[0] - ((Vector2f) o).values[0]) < eps
                && Math.abs(values[1] - ((Vector2f) o).values[1]) < eps;
    }

    public float getLength() {
        return MathPart.lengthOfVector(this.values);
    }

    public void add(float[] vector) {
        MathPart.add(this.values, vector);
    }

    public void add(Vector2f vector) {
        MathPart.add(this.values, vector.getValues());
    }

    public void sub(float[] vector) {
        MathPart.add(this.values, vector);
    }

    public void sub(Vector2f vector) {
        MathPart.add(this.values, vector.getValues());
    }

    public void divByScalar(float scalar) {
        MathPart.divisionByScalar(this.values, scalar);
    }

    public void multiply(float[] vector) {
        MathPart.multiply(this.values, vector);
    }

    public void multiply(Vector2f vector) {
        MathPart.multiply(this.values, vector.getValues());
    }

    public void normalize() {
        MathPart.normalize(this.values);
    }

}
