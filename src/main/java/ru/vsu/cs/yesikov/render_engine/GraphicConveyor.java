package ru.vsu.cs.yesikov.render_engine;
//import javax.vecmath.*;

import ru.vsu.cs.yesikov.math.*;

import static ru.vsu.cs.yesikov.math.Vector3f.*;

public class GraphicConveyor {

    public static Matrix4x4 rotateScaleTranslate() {
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
                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
                {-dot(resultX, eye), -dot(resultY, eye), -dot(resultZ, eye), 1}};
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
                {0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), 1.0F},
                {0, 0, 2 * (nearPlane * farPlane) / (nearPlane - farPlane), 0}
        });
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4x4 matrix, final Vector3f vertex) {
        final float x = (vertex.getX() * matrix.getValues()[0][0]) + (vertex.getY() * matrix.getValues()[1][0]) + (vertex.getZ() * matrix.getValues()[2][0]) + matrix.getValues()[3][0];
        final float y = (vertex.getX() * matrix.getValues()[0][1]) + (vertex.getY() * matrix.getValues()[1][1]) + (vertex.getZ() * matrix.getValues()[2][1]) + matrix.getValues()[3][1];
        final float z = (vertex.getX() * matrix.getValues()[0][2]) + (vertex.getY() * matrix.getValues()[1][2]) + (vertex.getZ() * matrix.getValues()[2][2]) + matrix.getValues()[3][2];
        final float w = (vertex.getX() * matrix.getValues()[0][3]) + (vertex.getY() * matrix.getValues()[1][3]) + (vertex.getZ() * matrix.getValues()[2][3]) + matrix.getValues()[3][3];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint2f(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }

    public static Point3f vertexToPoint3f(final Vector3f vertex, final int width, final int height) {
        return new Point3f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F, vertex.getZ() * width + width / 2.0F);
    }
}
