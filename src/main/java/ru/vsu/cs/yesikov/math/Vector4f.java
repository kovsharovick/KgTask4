package ru.vsu.cs.yesikov.math;

public class Vector4f {

    private float[] values;

    public Vector4f(float[] values) {
        if (values.length != 4) {
            throw new IllegalArgumentException("Vector size must be 4.");
        }
        this.values = new float[values.length];
        this.values[0] = values[0];
        this.values[1] = values[1];
        this.values[2] = values[2];
        this.values[3] = values[3];
    }

    public Vector4f(float x, float y, float z, float w) {
        values = new float[4];
        this.values[0] = x;
        this.values[1] = y;
        this.values[2] = z;
        this.values[3] = w;
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

    public float z() {
        return values[2];
    }

    public float w() {
        return values[3];
    }

    @Override
    public boolean equals(Object o) {
        final float eps = 1e-7f;
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return Math.abs(values[0] - ((Vector4f) o).values[0]) < eps
                && Math.abs(values[1] - ((Vector4f) o).values[1]) < eps
                && Math.abs(values[2] - ((Vector4f) o).values[2]) < eps
                && Math.abs(values[3] - ((Vector4f) o).values[3]) < eps;
    }

    public float getLength() {
        return MathPart.lengthOfVector(this.values);
    }

    public void add(float[] vector) {
        MathPart.add(this.values, vector);
    }

    public void add(Vector4f vector) {
        MathPart.add(this.values, vector.getValues());
    }

    public static Vector4f getNewAdd(float[] vector1, float[] vector2) {
        return new Vector4f(MathPart.getNewAdd(vector1, vector2));
    }

    public static Vector4f getNewAdd(Vector4f vector1, Vector4f vector2) {
        return new Vector4f(MathPart.getNewAdd(vector1.getValues(), vector2.getValues()));
    }

    public void sub(float[] vector) {
        MathPart.add(this.values, vector);
    }

    public void sub(Vector4f vector) {
        MathPart.add(this.values, vector.getValues());
    }

    public static Vector4f getNewSub(float[] vector1, float[] vector2) {
        return new Vector4f(MathPart.getNewSub(vector1, vector2));
    }

    public static Vector4f getNewSub(Vector4f vector1, Vector4f vector2) {
        return new Vector4f(MathPart.getNewSub(vector1.getValues(), vector2.getValues()));
    }

    public void divByScalar(float scalar) {
        MathPart.divisionByScalar(this.values, scalar);
    }

    public void multiply(float[] vector) {
        MathPart.multiply(this.values, vector);
    }

    public void multiply(Vector4f vector) {
        MathPart.multiply(this.values, vector.getValues());
    }

    public static Vector4f getNewMul(float[] vector1, float[] vector2) {
        return new Vector4f(MathPart.getNewMultiply(vector1, vector2));
    }

    public static Vector4f getNewMul(Vector4f vector1, Vector4f vector2) {
        return new Vector4f(MathPart.getNewMultiply(vector1.getValues(), vector2.getValues()));
    }

    public void normalize() {
        MathPart.normalize(this.values);
    }
}
