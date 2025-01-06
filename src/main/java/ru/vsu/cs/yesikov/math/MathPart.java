package ru.vsu.cs.yesikov.math;

public class MathPart {

    //add

    public static void add(float[] vector, float[] addPart) {
        if (vector.length != addPart.length) {
            throw new IllegalArgumentException("Vector lengths must be equal.");
        }
        for (int i = 0; i < vector.length; i++) {
            vector[i] += addPart[i];
        }
    }

    public static float[] getNewAdd(float[] vector1, float[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vector lengths must be equal.");
        }
        float[] res = new float[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            res[i] = vector1[i] + vector2[i];
        }
        return res;
    }

    public static void add(float[][] matrix, float[][] addPart) {
        if (matrix.length != addPart.length || matrix[0].length != addPart[0].length) {
            throw new IllegalArgumentException("Matrix lengths must be equal.");
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] += addPart[i][j];
            }
        }
    }

    public static float[][] getNewAdd(float[][] matrix1, float[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            throw new IllegalArgumentException("Matrix lengths must be equal.");
        }
        float[][] res = new float[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                res[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return res;
    }

    //sub

    public static void sub(float[] vector, float[] subPart) {
        if (vector.length != subPart.length) {
            throw new IllegalArgumentException("Vector lengths must be equal.");
        }
        for (int i = 0; i < vector.length; i++) {
            vector[i] -= subPart[i];
        }
    }

    public static float[] getNewSub(float[] vector1, float[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vector lengths must be equal.");
        }
        float[] res = new float[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            res[i] = vector1[i] - vector2[i];
        }
        return res;
    }

    public static void sub(float[][] matrix, float[][] subPart) {
        if (matrix.length != subPart.length || matrix[0].length != subPart[0].length) {
            throw new IllegalArgumentException("Matrix lengths must be equal.");
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] -= subPart[i][j];
            }
        }
    }

    public static float[][] getNewSub(float[][] matrix1, float[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            throw new IllegalArgumentException("Matrix lengths must be equal.");
        }
        float[][] res = new float[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                res[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return res;
    }

    //mul

    public static float[] getNewMultiply(float[][] matrix, float[] vector) {
        if (matrix[0].length != vector.length) {
            throw new IllegalArgumentException("Number columns in matrix must match vector length");
        }
        float[] res = new float[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                res[i] += matrix[i][j] * vector[j];
            }
        }
        return res;
    }

    public static void multiply(float[] vector, float[] mulPart) {
        if (vector.length != mulPart.length) {
            throw new IllegalArgumentException("Vector lengths must be equal.");
        }
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= mulPart[i];
        }
    }

    public static float[] getNewMultiply(float[] vector1, float[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vector lengths must be equal.");
        }
        float[] res = new float[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            res[i] = vector1[i] * vector2[i];
        }
        return res;
    }

    //todo
    public static void multiply(float[][] matrix, float[][] mulPart) {
        if (matrix[0].length != mulPart.length) {
            throw new IllegalArgumentException("Number columns first matrix must match number rows second matrix");
        }
        float[][] result = new float[matrix.length][mulPart[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < mulPart[0].length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    result[i][j] += matrix[i][k] * mulPart[k][j];
                }
            }
        }
        System.arraycopy(result, 0, matrix, 0, matrix.length);
    }

    public static float[][] getNewMultiply(float[][] matrix1, float[][] matrix2) {
        if (matrix1[0].length != matrix2.length) {
            throw new IllegalArgumentException("Number columns first matrix must match number rows second matrix");
        }
        float[][] res = new float[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                for (int k = 0; k < matrix1[0].length; k++) {
                    res[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return res;
    }

    //normalize

    public static void normalize(float[] vector) {
        float length = lengthOfVector(vector);
        for (int i = 0; i < vector.length; i++) {
            vector[i] /= length;
        }
    }

    public static float[] getNewNormalize(float[] vector) {
        float length = lengthOfVector(vector);
        float[] res = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            res[i] = vector[i] / length;
        }
        return res;
    }

    //

    public static void divisionByScalar(float[] vector, float scalar) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] /= scalar;
        }
    }

    public static float lengthOfVector(float[] vector) {
        float res = 0;
        for (float v : vector) {
            res += v * v;
        }
        return (float) java.lang.Math.sqrt(res);
    }

    public static void transposition(float[][] matrix) {
        float swap;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                swap = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = swap;
            }
        }
    }

}
