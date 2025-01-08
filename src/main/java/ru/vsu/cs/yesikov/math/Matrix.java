package ru.vsu.cs.yesikov.math;

public interface Matrix {

    public void add(float[][] matrix);

    public void add(Matrix matrix);

    public static Matrix getNewAdd(float[][] matrix1, float[][] matrix2) {
        return null;
    }

    public static Matrix getNewAdd(Matrix matrix1, Matrix matrix2) {
        return null;
    }

    public void sub(float[][] matrix);

    public void sub(Matrix matrix);

    public static Matrix getNewSub(float[][] matrix1, float[][] matrix2) {
        return null;
    }

    public static Matrix getNewSub(Matrix matrix1, Matrix matrix2) {
        return null;
    }

    public void multiply(float[][] matrix);

    public void multiply(Matrix matrix);

    public static Matrix getNewMul(float[][] matrix1, float[][] matrix2) {
        return null;
    }

    public static Matrix getNewMul(Matrix matrix1, Matrix matrix2) {
        return null;
    }

    public static Vector multiplyByVector(float[][] matrix, float[] vector) {
        return null;
    }

    public static Vector multiplyByVector(Matrix matrix, Vector vector) {
        return null;
    }

    public void transposition();

    public static Matrix4x4 createSingleMatrix() {
        return null;
    }

    public static Matrix4x4 createNullMatrix() {
        return null;
    }

    float[][] getValues();

    void setValues(float [][] m);

    Matrix clone();

}