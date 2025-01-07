package ru.vsu.cs.yesikov.math;

public class Vector2f implements Vector{

    private float[] values;

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

    public void setValues(float[] v) {
        values = v;
    }

    public float getX() {
        return values[0];
    }

    public float getY() {
        return values[1];
    }

    @Override
    public float getZ() {
        return 0;
    }

    @Override
    public float getW() {
        return 0;
    }

    public void setX(float x) {
        this.values[0] = x;
    }

    public void setY(float y) {
        this.values[1] = y;
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

    public void add(Vector vector) {
        MathPart.add(this.values, vector.getValues());
    }

    public static Vector2f getNewAdd(float[] vector1, float[] vector2) {
        return new Vector2f(MathPart.getNewAdd(vector1, vector2));
    }

    public static Vector2f getNewAdd(Vector vector1, Vector vector2) {
        return new Vector2f(MathPart.getNewAdd(vector1.getValues(), vector2.getValues()));
    }

    public void sub(float[] vector) {
        MathPart.sub(this.values, vector);
    }

    public void sub(Vector vector) {
        MathPart.sub(this.values, vector.getValues());
    }

    public static Vector2f getNewSub(float[] vector1, float[] vector2) {
        return new Vector2f(MathPart.getNewSub(vector1, vector2));
    }

    public static Vector2f getNewSub(Vector vector1, Vector vector2) {
        return new Vector2f(MathPart.getNewSub(vector1.getValues(), vector2.getValues()));
    }

    public void divByScalar(float scalar) {
        MathPart.divisionByScalar(this.values, scalar);
    }

    public void multiply(float[] vector) {
        MathPart.multiply(this.values, vector);
    }

    public void multiply(Vector vector) {
        MathPart.multiply(this.values, vector.getValues());
    }

    public static Vector2f getNewMul(float[] vector1, float[] vector2) {
        return new Vector2f(MathPart.getNewMultiply(vector1, vector2));
    }

    public static Vector2f getNewMul(Vector vector1, Vector vector2) {
        return new Vector2f(MathPart.getNewMultiply(vector1.getValues(), vector2.getValues()));
    }

    public void normalize() {
        MathPart.normalize(this.values);
    }

    public static float dot(Vector vector1, Vector vector2) {
        return MathPart.dot(vector1.getValues(), vector2.getValues());
    }

    public Vector2f clone() {
        return new Vector2f(values);
    }

}
