package ru.vsu.cs.yesikov.render_engine;
//import javax.vecmath.*;

import ru.vsu.cs.yesikov.math.*;
import ru.vsu.cs.yesikov.model.Model;

//import javax.vecmath.Matrix4f;

import static ru.vsu.cs.yesikov.math.Vector3f.*;

public class GraphicConveyor {

    public GraphicConveyor() {
    }

    public static Matrix4x4 rotateScaleTranslate() {
        float[][] matrix = new float[][]{
                {1.0F, 0.0F, 0.0F, 0.0F},
                {0.0F, 1.0F, 0.0F, 0.0F},
                {0.0F, 0.0F, 1.0F, 0.0F},
                {0.0F, 0.0F, 0.0F, 1.0F}
        };
        return new Matrix4x4(matrix);
    }


    public static Matrix4x4 rotateScaleTranslate(Model mesh) {
        return Matrix4x4.createSingleMatrix();
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultZ = getNewSub(target, eye);
        Vector3f resultX = getNewMul(up, resultZ);
        Vector3f resultY = getNewMul(resultZ, resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        float[][] matrix = new float[][]{
                {resultX.getX(), resultY.getY(), resultZ.getZ(), -dot(resultX, eye)},
                {resultX.getX(), resultY.getY(), resultZ.getZ(), -dot(resultY, eye)},
                {resultX.getX(), resultY.getY(), resultZ.getZ(), -dot(resultZ, eye)},
                {0, 0, 0, 1}};
        return new Matrix4x4(matrix);
    }

    public static Matrix4x4 perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        return new Matrix4x4( new float[][] {
                {tangentMinusOnDegree / aspectRatio, 0, 0, 0},
                {0, tangentMinusOnDegree, 0, 0},
                {0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), 2 * (nearPlane * farPlane) / (nearPlane - farPlane)},
                {0, 0, 1.0F, 0}
        });
    }

    /*public static Vector3f multiplyMatrix4ByVector3(final Matrix4x4 matrix, final Vector3f vertex) {
        final float x = (vertex.getX() * matrix.getValues()[0][0]) + (vertex.getY() * matrix.getValues()[1][0]) + (vertex.getZ() * matrix.getValues()[2][0]) + matrix.getValues()[3][0];
        final float y = (vertex.getX() * matrix.getValues()[0][1]) + (vertex.getY() * matrix.getValues()[1][1]) + (vertex.getZ() * matrix.getValues()[2][1]) + matrix.getValues()[3][1];
        final float z = (vertex.getX() * matrix.getValues()[0][2]) + (vertex.getY() * matrix.getValues()[1][2]) + (vertex.getZ() * matrix.getValues()[2][2]) + matrix.getValues()[3][2];
        final float w = (vertex.getX() * matrix.getValues()[0][3]) + (vertex.getY() * matrix.getValues()[1][3]) + (vertex.getZ() * matrix.getValues()[2][3]) + matrix.getValues()[3][3];
        return new Vector3f(x / w, y / w, z / w);
    }*/

    public static Point2f vertexToPoint2f(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }

    public static Point3f vertexToPoint3f(final Vector3f vertex, final int width, final int height) {
        return new Point3f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F, vertex.getZ() * width + width / 2.0F);
    }

    public static javax.vecmath.Vector3f multiplyMatrix4ByVector3(final Matrix4x4 matrix, final javax.vecmath.Vector3f vertex) {
        Vector4f v = new Vector4f(vertex.x, vertex.y, vertex.z, 1);
        v = Matrix4x4.multiplyByVector(matrix, v);
        return new javax.vecmath.Vector3f(v.getX(), v.getY(), v.getZ());
    }

    public static javax.vecmath.Point2f vertexToPoint(javax.vecmath.Vector3f vertex, int width, int height) {
        return new javax.vecmath.Point2f(vertex.x * (float)width + (float)width / 2.0F, -vertex.y * (float)height + (float)height / 2.0F);
    }
}
