package ru.vsu.cs.yesikov.render_engine;
//import javax.vecmath.*;

import ru.vsu.cs.yesikov.math.*;

import static ru.vsu.cs.yesikov.math.Vector3f.dot;

public class GraphicConveyor {

    public static Matrix4x4 rotateScaleTranslate() {
        return Matrix4x4.createSingleMatrix();
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX;
        Vector3f resultY;
        Vector3f resultZ;

        resultZ = target;
        resultZ.sub(eye);
        resultX = up;
        resultX.multiply(resultZ);
        resultY = resultZ;
        resultY.multiply(resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        float[][] matrix = new float[][]{
                {resultX.x(), resultY.x(), resultZ.x(), 0},
                {resultX.y(), resultY.y(), resultZ.y(), 0},
                {resultX.z(), resultY.z(), resultZ.z(), 0},
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
        final float x = (vertex.x() * matrix.getValues()[0][0]) + (vertex.y() * matrix.getValues()[1][0]) + (vertex.z() * matrix.getValues()[2][0]) + matrix.getValues()[3][0];
        final float y = (vertex.x() * matrix.getValues()[0][1]) + (vertex.y() * matrix.getValues()[1][1]) + (vertex.z() * matrix.getValues()[2][1]) + matrix.getValues()[3][1];
        final float z = (vertex.x() * matrix.getValues()[0][2]) + (vertex.y() * matrix.getValues()[1][2]) + (vertex.z() * matrix.getValues()[2][2]) + matrix.getValues()[3][2];
        final float w = (vertex.x() * matrix.getValues()[0][3]) + (vertex.y() * matrix.getValues()[1][3]) + (vertex.z() * matrix.getValues()[2][3]) + matrix.getValues()[3][3];
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.x() * width + width / 2.0F, -vertex.y() * height + height / 2.0F);
    }
}
