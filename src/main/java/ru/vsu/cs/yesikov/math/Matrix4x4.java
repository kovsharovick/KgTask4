package ru.vsu.cs.yesikov.math;

public class Matrix4x4 implements Matrix{

    private float[][] values;

    public Matrix4x4(float[][] values) {
        if (values.length != 4 || values[0].length != 4) {
            throw new IllegalArgumentException("Matrix size must be 4x4.");
        }
        this.values = values;
    }

    public float[][] getValues() {
        return values;
    }

    public void setValues(float[][] m) {
        values = m;
    }

    public float getValOfIndex(int colum, int row) {
        if (values.length < row || values[0].length < colum) {
            throw new IllegalArgumentException("Matrix size 4x4.");
        }
        return values[row][colum];
    }

    public void setValOfIndex(int colum, int row, float value) {
        if (values.length < row || values[0].length < colum) {
            throw new IllegalArgumentException("Matrix size 4x4.");
        }
        values[row][colum] = value;
    }

    public static Matrix4x4 createSingleMatrix() {
        return new Matrix4x4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public static Matrix4x4 createNullMatrix() {
        return new Matrix4x4(new float[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }

    @Override
    public boolean equals(Object o) {
        final float eps = 1e-7f;
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j] != ((Matrix4x4) o).values[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void add(float[][] matrix) {
        MathPart.add(values, matrix);
    }

    public void add(Matrix matrix) {
        MathPart.add(values, matrix.getValues());
    }

    public static Matrix4x4 getNewAdd(float[][] matrix1, float[][] matrix2) {
        return new Matrix4x4(MathPart.getNewAdd(matrix1, matrix2));
    }

    public static Matrix4x4 getNewAdd(Matrix matrix1, Matrix matrix2) {
        return new Matrix4x4(MathPart.getNewAdd(matrix1.getValues(), matrix2.getValues()));
    }

    public void sub(float[][] matrix) {
        MathPart.sub(values, matrix);
    }

    public void sub(Matrix matrix) {
        MathPart.sub(values, matrix.getValues());
    }

    public static Matrix4x4 getNewSub(float[][] matrix1, float[][] matrix2) {
        return new Matrix4x4(MathPart.getNewSub(matrix1, matrix2));
    }

    public static Matrix4x4 getNewSub(Matrix matrix1, Matrix matrix2) {
        return new Matrix4x4(MathPart.getNewSub(matrix1.getValues(), matrix2.getValues()));
    }

    public void multiply(float[][] matrix) {
        MathPart.multiply(values, matrix);
    }

    public void multiply(Matrix matrix) {
        MathPart.multiply(values, matrix.getValues());
    }

    public static Matrix4x4 getNewMul(float[][] matrix1, float[][] matrix2) {
        return new Matrix4x4(MathPart.getNewMultiply(matrix1, matrix2));
    }

    public static Matrix4x4 getNewMul(Matrix matrix1, Matrix matrix2) {
        return new Matrix4x4(MathPart.getNewMultiply(matrix1.getValues(), matrix2.getValues()));
    }

    public static Vector4f multiplyByVector(float[][] matrix, float[] vector) {
        return new Vector4f(MathPart.getNewMultiply(matrix, vector));
    }

    public static Vector4f multiplyByVector(Matrix matrix, Vector vector) {
        return new Vector4f(MathPart.getNewMultiply(matrix.getValues(), vector.getValues()));
    }

    public void transposition() {
        MathPart.transposition(values);
    }

    public Matrix4x4 clone() {
        return new Matrix4x4(values);
    }

}
