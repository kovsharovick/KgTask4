package ru.vsu.cs.yesikov.math;

public class Matrix3x3 {

    private final float[][] values;

    public Matrix3x3(float[][] values) {
        if (values.length != 3 || values[0].length != 3) {
            throw new IllegalArgumentException("Matrix size must be 3x3.");
        }
        this.values = values;
    }

    public float[][] getValues() {
        return values;
    }

    public float getValOfIndex(int colum, int row) {
        if (values.length < row || values[0].length < colum) {
            throw new IllegalArgumentException("Matrix size 3x3.");
        }
        return values[row][colum];
    }

    public void setValOfIndex(int colum, int row, float value) {
        if (values.length < row || values[0].length < colum) {
            throw new IllegalArgumentException("Matrix size 3x3.");
        }
        values[row][colum] = value;
    }

    public static Matrix3x3 createSingleMatrix() {
        return new Matrix3x3(new float[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
    }

    public static Matrix3x3 createNullMatrix() {
        return new Matrix3x3(new float[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        });
    }

    @Override
    public boolean equals(Object o) {
        final float eps = 1e-7f;
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j] != ((Matrix3x3) o).values[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void add(float[][] matrix) {
        MathPart.add(values, matrix);
    }

    public void add(Matrix3x3 matrix) {
        MathPart.add(values, matrix.getValues());
    }

    public static Matrix3x3 getNewAdd(float[][] matrix1, float[][] matrix2) {
        return new Matrix3x3(MathPart.getNewAdd(matrix1, matrix2));
    }

    public static Matrix3x3 getNewAdd(Matrix3x3 matrix1, Matrix3x3 matrix2) {
        return new Matrix3x3(MathPart.getNewAdd(matrix1.getValues(), matrix2.getValues()));
    }

    public void sub(float[][] matrix) {
        MathPart.sub(values, matrix);
    }

    public void sub(Matrix3x3 matrix) {
        MathPart.sub(values, matrix.getValues());
    }

    public static Matrix3x3 getNewSub(float[][] matrix1, float[][] matrix2) {
        return new Matrix3x3(MathPart.getNewSub(matrix1, matrix2));
    }

    public static Matrix3x3 getNewSub(Matrix3x3 matrix1, Matrix3x3 matrix2) {
        return new Matrix3x3(MathPart.getNewSub(matrix1.getValues(), matrix2.getValues()));
    }

    public void multiply(float[][] matrix) {
        MathPart.multiply(values, matrix);
    }

    public void multiply(Matrix3x3 matrix) {
        MathPart.multiply(values, matrix.getValues());
    }

    public static Matrix3x3 getNewMul(float[][] matrix1, float[][] matrix2) {
        return new Matrix3x3(MathPart.getNewMultiply(matrix1, matrix2));
    }

    public static Matrix3x3 getNewMul(Matrix3x3 matrix1, Matrix3x3 matrix2) {
        return new Matrix3x3(MathPart.getNewMultiply(matrix1.getValues(), matrix2.getValues()));
    }

    public static Vector3f multiplyByVector(float[][] matrix, float[] vector) {
        return new Vector3f(MathPart.getNewMultiply(matrix, vector));
    }

    public static Vector3f multiplyByVector(Matrix3x3 matrix, Vector3f vector) {
        return new Vector3f(MathPart.getNewMultiply(matrix.getValues(), vector.getValues()));
    }

    public void transposition() {
        MathPart.transposition(values);
    }
}

