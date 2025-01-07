package ru.vsu.cs.yesikov.math;

public class Vector3f implements Vector{

    private float[] values;

    public Vector3f(float[] values) {
        if (values.length != 3) {
            throw new IllegalArgumentException("Vector size must be 3.");
        }
        this.values = new float[values.length];
        this.values[0] = values[0];
        this.values[1] = values[1];
        this.values[2] = values[2];
    }

    public Vector3f(float x, float y, float z) {
        values = new float[3];
        this.values[0] = x;
        this.values[1] = y;
        this.values[2] = z;
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

    public float getZ() {
        return values[2];
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

    public void setZ(float z) {
        this.values[2] = z;
    }

    public void vector3fto4f(Vector4f v) {
        values[0] = v.getValues()[0];
        values[1] = v.getValues()[1];
        values[2] = v.getValues()[2];
    }

    public void vector3fto4f(float[] v) {
        values[0] = v[0];
        values[1] = v[1];
        values[2] = v[2];
    }

    @Override
    public boolean equals(Object o) {
        final float eps = 1e-7f;
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return Math.abs(values[0] - ((Vector3f) o).values[0]) < eps
                && Math.abs(values[1] - ((Vector3f) o).values[1]) < eps
                && Math.abs(values[2] - ((Vector3f) o).values[2]) < eps;
    }

    public float getLength() {
        return MathPart.lengthOfVector(this.values);
    }

    public static Vector3f getNewVecMul(Vector3f vector1, Vector3f vector2) {
        return new Vector3f(MathPart.getVecMul(vector1.getValues(), vector2.getValues()));
    }

    public void add(float[] vector) {
        MathPart.add(this.values, vector);
    }

    public void add(Vector vector) {
        MathPart.add(this.values, vector.getValues());
    }

    public static Vector3f getNewAdd(float[] vector1, float[] vector2) {
        return new Vector3f(MathPart.getNewAdd(vector1, vector2));
    }

    public static Vector3f getNewAdd(Vector vector1, Vector vector2) {
        return new Vector3f(MathPart.getNewAdd(vector1.getValues(), vector2.getValues()));
    }

    public void sub(float[] vector) {
        MathPart.sub(this.values, vector);
    }

    public void sub(Vector vector) {
        MathPart.sub(this.values, vector.getValues());
    }

    public static Vector3f getNewSub(float[] vector1, float[] vector2) {
        return new Vector3f(MathPart.getNewSub(vector1, vector2));
    }

    public static Vector3f getNewSub(Vector vector1, Vector vector2) {
        return new Vector3f(MathPart.getNewSub(vector1.getValues(), vector2.getValues()));
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

    public static Vector3f getNewMul(float[] vector1, float[] vector2) {
        return new Vector3f(MathPart.getNewMultiply(vector1, vector2));
    }

    public static Vector3f getNewMul(Vector vector1, Vector vector2) {
        return new Vector3f(MathPart.getNewMultiply(vector1.getValues(), vector2.getValues()));
    }

    public void normalize() {
        MathPart.normalize(this.values);
    }

    public static Vector3f normalize(Vector vector) {
        MathPart.normalize(vector.getValues());
        return new Vector3f(vector.getValues());
    }

    public static float dot(Vector vector1, Vector vector2) {
        return MathPart.dot(vector1.getValues(), vector2.getValues());
    }

    public Vector3f clone() {
        return new Vector3f(values);
    }

}
