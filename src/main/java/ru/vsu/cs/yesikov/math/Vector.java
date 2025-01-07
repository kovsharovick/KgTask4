package ru.vsu.cs.yesikov.math;

public interface Vector {

    float getX();

    float getY();

    float getZ();

    float getW();

    public float getLength();

    public void add(float[] vector);

    public void add(Vector vector);

    public static Vector getNewAdd(float[] vector1, float[] vector2) {
        return null;
    }

    public static Vector getNewAdd(Vector vector1, Vector vector2) {
        return null;
    }

    public void sub(float[] vector);

    public void sub(Vector vector);

    public static Vector getNewSub(float[] vector1, float[] vector2) {
        return null;
    }

    public static Vector getNewSub(Vector vector1, Vector vector2) {
        return null;
    }

    public void divByScalar(float scalar);

    public void multiply(float[] vector);

    public void multiply(Vector vector);

    public static Vector getNewMul(float[] vector1, float[] vector2) {
        return null;
    }

    public static Vector getNewMul(Vector vector1, Vector vector2) {
        return null;
    }

    public void normalize();

    public static float dot(Vector vector1, Vector vector2) {
        return 0;
    }

    float[] getValues();

    void setValues(float[] v);

    Vector clone();

}