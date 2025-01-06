package ru.vsu.cs.yesikov.math;

public class Matrix4x4 {

    private final float[][] values;

    public Matrix4x4(float[][] values) {
        if (values.length != 4 || values[0].length != 4) {
            throw new IllegalArgumentException("Matrix size must be 4x4.");
        }
        this.values = values;
    }

    public float[][] getValues() {
        return values;
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

    public void add(Matrix4x4 matrix) {
        MathPart.add(values, matrix.getValues());
    }

    public void sub(float[][] matrix) {
        MathPart.sub(values, matrix);
    }

    public void sub(Matrix4x4 matrix) {
        MathPart.sub(values, matrix.getValues());
    }

    public void multiply(float[][] matrix) {
        MathPart.multiply(values, matrix);
    }

    public void multiply(Matrix4x4 matrix) {
        MathPart.multiply(values, matrix.getValues());
    }

    public float[] multiplyByVector(float[] vector) {
        return MathPart.getNewMultiply(values, vector);
    }

    public float[] multiplyByVector(Vector4f vector) {
        return MathPart.getNewMultiply(values, vector.getValues());
    }

    public void transposition() {
        MathPart.transposition(values);
    }
}
