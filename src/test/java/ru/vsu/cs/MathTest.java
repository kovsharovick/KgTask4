package ru.vsu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.vsu.cs.yesikov.math.*;

class MathTest {

    @Test
    public void testAddVector2f() {
        Vector2f actual = new Vector2f(3, 4);
        Vector2f addPart = new Vector2f(1, 0);
        Vector2f expected = new Vector2f(4, 4);
        actual.add(addPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewAddVector2f() {
        Vector2f vector1 = new Vector2f(3, 4);
        Vector2f vector2 = new Vector2f(2, 1);
        Vector2f expected = new Vector2f(5, 5);
        Vector2f actual = Vector2f.getNewAdd(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testAddVector3f() {
        Vector3f actual = new Vector3f(3, 4, 5);
        Vector3f addPart = new Vector3f(1, 0, -2);
        Vector3f expected = new Vector3f(4, 4, 3);
        actual.add(addPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewAddVector3f() {
        Vector3f vector1 = new Vector3f(3, 4, 5);
        Vector3f vector2 = new Vector3f(2, 1, 0);
        Vector3f expected = new Vector3f(5, 5, 5);
        Vector3f actual = Vector3f.getNewAdd(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testAddVector4f() {
        Vector4f actual = new Vector4f(2, 1, 0, 6);
        Vector4f addPart = new Vector4f(4, 2, 0, -6);
        Vector4f expected = new Vector4f(6, 3, 0, 0);
        actual.add(addPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewAddVector4f() {
        Vector4f vector1 = new Vector4f(-5, -3, 5, 5);
        Vector4f vector2 = new Vector4f(1, 1, 1, 1);
        Vector4f expected = new Vector4f(-4, -2, 6, 6);
        Vector4f actual = Vector4f.getNewAdd(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testSubVector2f() {
        Vector2f actual = new Vector2f(3, 4);
        Vector2f subPart = new Vector2f(1, 0);
        Vector2f expected = new Vector2f(2, 4);
        actual.sub(subPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewSubVector2f() {
        Vector2f vector1 = new Vector2f(3, 4);
        Vector2f vector2 = new Vector2f(2, 1);
        Vector2f expected = new Vector2f(1, 3);
        Vector2f actual = Vector2f.getNewSub(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testSubVector3f() {
        Vector3f actual = new Vector3f(3, 4, 5);
        Vector3f subPart = new Vector3f(1, 0, -2);
        Vector3f expected = new Vector3f(2, 4, 7);
        actual.sub(subPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewSubVector3f() {
        Vector3f vector1 = new Vector3f(3, 4, 5);
        Vector3f vector2 = new Vector3f(2, 1, 0);
        Vector3f expected = new Vector3f(1, 3, 5);
        Vector3f actual = Vector3f.getNewSub(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testSubVector4f() {
        Vector4f actual = new Vector4f(2, 1, 0, 6);
        Vector4f subPart = new Vector4f(4, 2, 0, -6);
        Vector4f expected = new Vector4f(-2, -1, 0, 12);
        actual.sub(subPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewSubVector4f() {
        Vector4f vector1 = new Vector4f(-5, -3, 5, 5);
        Vector4f vector2 = new Vector4f(1, 1, 1, 1);
        Vector4f expected = new Vector4f(-6, -4, 4, 4);
        Vector4f actual = Vector4f.getNewSub(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testMulVector4f() {
        Vector4f actual = new Vector4f(2, 1, 0, 6);
        Vector4f mulPart = new Vector4f(4, 2, 0, -6);
        Vector4f expected = new Vector4f(8, 2, 0, -36);
        actual.multiply(mulPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewMulVector4f() {
        Vector4f vector1 = new Vector4f(-5, -3, 5, 5);
        Vector4f vector2 = new Vector4f(1, 1, 1, 1);
        Vector4f expected = new Vector4f(-5, -3, 5, 5);
        Vector4f actual = Vector4f.getNewMul(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testMulVector3f() {
        Vector3f actual = new Vector3f(2, 1, 0);
        Vector3f mulPart = new Vector3f(4, 2, 0);
        Vector3f expected = new Vector3f(8, 2, 0);
        actual.multiply(mulPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewMulVector3f() {
        Vector3f vector1 = new Vector3f(-5, -3, 5);
        Vector3f vector2 = new Vector3f(1, 1, 1);
        Vector3f expected = new Vector3f(-5, -3, 5);
        Vector3f actual = Vector3f.getNewMul(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testMulVector2f() {
        Vector2f actual = new Vector2f(2, 1);
        Vector2f mulPart = new Vector2f(4, 2);
        Vector2f expected = new Vector2f(8, 2);
        actual.multiply(mulPart);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewMulVector2f() {
        Vector2f vector1 = new Vector2f(-5, -3);
        Vector2f vector2 = new Vector2f(1, 1);
        Vector2f expected = new Vector2f(-5, -3);
        Vector2f actual = Vector2f.getNewMul(vector1, vector2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetLengthVector2f() {
        Vector2f vector = new Vector2f(2, 3);
        float actual = vector.getLength();
        float expected = (float) Math.sqrt(2 * 2 + 3 * 3);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetLengthVector3f() {
        Vector3f vector = new Vector3f(-4, 3, 6);
        float actual = vector.getLength();
        float expected = (float) Math.sqrt(-4 * -4 + 3 * 3 + 6 * 6);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetLengthVector4f() {
        Vector4f vector = new Vector4f(1, 3, 6, 10);
        float actual = vector.getLength();
        float expected = (float) Math.sqrt(1 + 3 * 3 + 6 * 6 + 10 * 10);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testDivByScalarVector2f() {
        Vector2f actual = new Vector2f(6, 3);
        actual.divByScalar(3);
        Vector2f expected = new Vector2f(2, 1);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testDivByScalarVector3f() {
        Vector3f actual = new Vector3f(8, 5, 4);
        actual.divByScalar(2);
        Vector3f expected = new Vector3f(4, 2.5F, 2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testDivByScalarVector4f() {
        Vector4f actual = new Vector4f(11, 100, 25, 40);
        actual.divByScalar(10);
        Vector4f expected = new Vector4f(1.1F, 10, 2.5F, 4);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testNormalizeVector4f() {
        Vector4f actual = new Vector4f(4, 3, 8, 100);
        actual.normalize();
        Vector4f expected = new Vector4f((float) (4 / Math.sqrt(10089)), (float) (3 / Math.sqrt(10089)),
                (float) (8 / Math.sqrt(10089)), (float) (100 / Math.sqrt(10089)));
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testNormalizeVector3f() {
        Vector3f actual = new Vector3f(2, 1, 0);
        actual.normalize();
        Vector3f expected = new Vector3f((float) (2 / Math.sqrt(5)), (float) (1 / Math.sqrt(5)), 0);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testNormalizeVector2f() {
        Vector2f actual = new Vector2f(45, 50);
        actual.normalize();
        Vector2f expected = new Vector2f((float) (45 / Math.sqrt(4525)), (float) (50 / Math.sqrt(4525)));
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testDotVector4f() {
        Vector4f vector1 = new Vector4f(2, 4, 5, 1);
        Vector4f vector2 = new Vector4f(4, 3, 0, 9);
        float actual = Vector4f.dot(vector1, vector2);
        float expected = 4 * 2 + 4 * 3 + 9;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testDotVector3f() {
        Vector3f vector1 = new Vector3f(-2, 4, 5);
        Vector3f vector2 = new Vector3f(-6, -5, 1);
        float actual = Vector3f.dot(vector1, vector2);
        float expected = 2 * 6 - 4 * 5 + 5;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testAddMatrix4x4() {
        Matrix4x4 actual = new Matrix4x4( new float[][] {
            {1, 5, 3, 4},
            {5, 4, 3, 2},
            {0, 0, 0, 0},
            {-3, 3, 1, 1}
        });
        Matrix4x4 addPart = new Matrix4x4( new float[][] {
                {2, 2, 2, 2},
                {0, 2, 2, 2},
                {2, 0, 2, 0},
                {2, 0, 2, 2}
        });
        actual.add(addPart);
        Matrix4x4 expected = new Matrix4x4(new float[][] {
                {3, 7, 5, 6},
                {5, 6, 5, 4},
                {2, 0, 2, 0},
                {-1, 3, 3, 3}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewAddMatrix4x4() {
        Matrix4x4 matrix1 = new Matrix4x4( new float[][] {
                {1, 5, 3, 4},
                {5, 4, 3, 2},
                {0, 0, 0, 0},
                {-3, 3, 1, 1}
        });
        Matrix4x4 matrix2 = new Matrix4x4( new float[][] {
                {2, 2, 2, 2},
                {0, 2, 2, 2},
                {2, 0, 2, 0},
                {2, 0, 2, 2}
        });
        Matrix4x4 actual = Matrix4x4.getNewAdd(matrix1, matrix2);
        Matrix4x4 expected = new Matrix4x4(new float[][] {
                {3, 7, 5, 6},
                {5, 6, 5, 4},
                {2, 0, 2, 0},
                {-1, 3, 3, 3}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testAddMatrix3x3() {
        Matrix3x3 actual = new Matrix3x3( new float[][] {
                {1, 5, 3},
                {5, 4, 3},
                {0, 0, 0}
        });
        Matrix3x3 addPart = new Matrix3x3( new float[][] {
                {2, 2, 2},
                {0, 2, 2},
                {2, 0, 2}
        });
        actual.add(addPart);
        Matrix3x3 expected = new Matrix3x3(new float[][] {
                {3, 7, 5},
                {5, 6, 5},
                {2, 0, 2}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewAddMatrix3x3() {
        Matrix3x3 matrix1 = new Matrix3x3( new float[][] {
                {1, 5, 3},
                {5, 4, 3},
                {0, 0, 0}
        });
        Matrix3x3 matrix2 = new Matrix3x3( new float[][] {
                {2, 2, 2},
                {0, 2, 2},
                {2, 0, 2}
        });
        Matrix3x3 actual = Matrix3x3.getNewAdd(matrix1, matrix2);
        Matrix3x3 expected = new Matrix3x3(new float[][] {
                {3, 7, 5},
                {5, 6, 5},
                {2, 0, 2},
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testSubMatrix4x4() {
        Matrix4x4 actual = new Matrix4x4( new float[][] {
                {1, 5, 3, 4},
                {5, 4, 3, 2},
                {0, 0, 0, 0},
                {-3, 3, 1, 1}
        });
        Matrix4x4 subPart = new Matrix4x4( new float[][] {
                {2, 2, 2, 2},
                {0, 2, 2, 2},
                {2, 0, 2, 0},
                {2, 0, 2, 2}
        });
        actual.sub(subPart);
        Matrix4x4 expected = new Matrix4x4(new float[][] {
                {-1, 3, 1, 2},
                {5, 2, 1, 0},
                {-2, 0, -2, 0},
                {-5, 3, -1, -1}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewSubMatrix4x4() {
        Matrix4x4 vector1 = new Matrix4x4( new float[][] {
                {1, 5, 3, 4},
                {5, 4, 3, 2},
                {0, 0, 0, 0},
                {-3, 3, 1, 1}
        });
        Matrix4x4 vector2 = new Matrix4x4( new float[][] {
                {2, 2, 2, 2},
                {0, 2, 2, 2},
                {2, 0, 2, 0},
                {2, 0, 2, 2}
        });
        Matrix4x4 actual = Matrix4x4.getNewSub(vector1, vector2);
        Matrix4x4 expected = new Matrix4x4(new float[][] {
                {-1, 3, 1, 2},
                {5, 2, 1, 0},
                {-2, 0, -2, 0},
                {-5, 3, -1, -1}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testSubMatrix3x3() {
        Matrix3x3 actual = new Matrix3x3( new float[][] {
                {1, 5, 3},
                {5, 4, 3},
                {0, 0, 0}
        });
        Matrix3x3 subPart = new Matrix3x3( new float[][] {
                {2, 2, 2},
                {0, 2, 2},
                {2, 0, 2}
        });
        actual.sub(subPart);
        Matrix3x3 expected = new Matrix3x3(new float[][] {
                {-1, 3, 1},
                {5, 2, 1},
                {-2, 0, -2}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewSubMatrix3x3() {
        Matrix3x3 vector1 = new Matrix3x3( new float[][] {
                {1, 5, 3},
                {5, 4, 3},
                {0, 0, 0}
        });
        Matrix3x3 vector2 = new Matrix3x3( new float[][] {
                {2, 2, 2},
                {0, 2, 2},
                {2, 0, 2}
        });
        Matrix3x3 actual = Matrix3x3.getNewSub(vector1, vector2);
        Matrix3x3 expected = new Matrix3x3(new float[][] {
                {-1, 3, 1},
                {5, 2, 1},
                {-2, 0, -2}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testMulMatrix4x4() {
        Matrix4x4 actual = new Matrix4x4( new float[][] {
                {3, 3, 4, 5},
                {6, 2, 4, 6},
                {6, 3, 6, 0},
                {4, 7, 2, 0}
        });
        Matrix4x4 mulPart = new Matrix4x4( new float[][] {
                {2, 3, 2, 1},
                {5, 3, 2, 3},
                {0, 0, 0, 0},
                {3, 2, 1, 3}
        });
        actual.multiply(mulPart);
        Matrix4x4 expected = new Matrix4x4(new float[][] {
                {36, 28, 17, 27},
                {40, 36, 22, 30},
                {27, 27, 18, 15},
                {43, 33, 22, 25}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewMulMatrix4x4() {
        Matrix4x4 vector1 = new Matrix4x4( new float[][] {
                {3, 3, 4, 5},
                {6, 2, 4, 6},
                {6, 3, 6, 0},
                {4, 7, 2, 0}
        });
        Matrix4x4 vector2 = new Matrix4x4( new float[][] {
                {2, 3, 2, 1},
                {5, 3, 2, 3},
                {0, 0, 0, 0},
                {3, 2, 1, 3}
        });
        Matrix4x4 actual = Matrix4x4.getNewMul(vector1, vector2);
        Matrix4x4 expected = new Matrix4x4(new float[][] {
                {36, 28, 17, 27},
                {40, 36, 22, 30},
                {27, 27, 18, 15},
                {43, 33, 22, 25}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testMulMatrix3x3() {
        Matrix3x3 actual = new Matrix3x3( new float[][] {
                {9, 3, 5},
                {2, 0, 3},
                {0, 1, -6}
        });
        Matrix3x3 mulPart = new Matrix3x3( new float[][] {
                {1, -1, -1},
                {-1, 4, 7},
                {8, 1, -1}
        });
        actual.multiply(mulPart);
        Matrix3x3 expected = new Matrix3x3(new float[][] {
                {46, 8, 7},
                {26, 1, -5},
                {-49, -2, 13}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetNewMulMatrix3x3() {
        Matrix3x3 vector1 = new Matrix3x3( new float[][] {
                {9, 3, 5},
                {2, 0, 3},
                {0, 1, -6}
        });
        Matrix3x3 vector2 = new Matrix3x3( new float[][] {
                {1, -1, -1},
                {-1, 4, 7},
                {8, 1, -1}
        });
        Matrix3x3 actual = Matrix3x3.getNewMul(vector1, vector2);
        Matrix3x3 expected = new Matrix3x3(new float[][] {
                {46, 8, 7},
                {26, 1, -5},
                {-49, -2, 13}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testMulMatrix4x4OnVector4f() {
        Matrix4x4 matrix = new Matrix4x4( new float[][] {
                {3, 3, 4, 5},
                {6, 2, 4, 6},
                {6, 3, 6, 0},
                {4, 7, 2, 0}
        });
        Vector4f vector = new Vector4f(3, 4, 5, 1);
        Vector4f actual = Matrix4x4.multiplyByVector(matrix, vector);
        Vector4f expected = new Vector4f(46, 52, 60, 50);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testMulMatrix3x3OnVector3f() {
        Matrix3x3 matrix = new Matrix3x3( new float[][] {
                {3, 3, 4},
                {6, 2, 4},
                {6, 3, 6}
        });
        Vector3f vector = new Vector3f(3, 4, 5);
        Vector3f actual = Matrix3x3.multiplyByVector(matrix, vector);
        Vector3f expected = new Vector3f(41, 46, 60);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testTranspositionMatrix4x4() {
        Matrix4x4 actual = new Matrix4x4( new float[][] {
                {3, 3, 4, 5},
                {6, 2, 4, 6},
                {6, 3, 6, 0},
                {4, 7, 2, 0}
        });
        actual.transposition();
        Matrix4x4 expected = new Matrix4x4(new float[][] {
                {3, 6, 6, 4},
                {3, 2, 3, 7},
                {4, 4, 6, 2},
                {5, 6, 0, 0}
        });
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testTranspositionMatrix3x3() {
        Matrix3x3 actual = new Matrix3x3( new float[][] {
                {3, 3, 4},
                {6, 2, 4},
                {6, 3, 6}
        });
        actual.transposition();
        Matrix3x3 expected = new Matrix3x3(new float[][] {
                {3, 6, 6},
                {3, 2, 3},
                {4, 4, 6}
        });
        Assertions.assertEquals(actual, expected);
    }



}