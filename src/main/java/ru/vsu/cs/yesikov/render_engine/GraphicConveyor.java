package ru.vsu.cs.yesikov.render_engine;

import ru.vsu.cs.yesikov.math.Matrix4x4;
import ru.vsu.cs.yesikov.math.Vector3f;
import ru.vsu.cs.yesikov.math.Point2f;
import static ru.vsu.cs.yesikov.math.Vector3f.*;

public class GraphicConveyor {

    public GraphicConveyor() {
    }

    public static Matrix4x4 rotateScaleTranslate() {
        return Matrix4x4.createSingleMatrix();
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4x4 lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultZ = getNewSub(target, eye);
        Vector3f resultX = getNewVecMul(up, resultZ);
        Vector3f resultY = getNewVecMul(resultZ, resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        float[][] matrix = new float[][]{
                {resultX.getX(), resultX.getY(), resultX.getZ(), -dot(resultX, eye)},
                {resultY.getX(), resultY.getY(), resultY.getZ(), -dot(resultY, eye)},
                {resultZ.getX(), resultZ.getY(), resultZ.getZ(), -dot(resultZ, eye)},
                {0, 0, 0, 1}};
        return new Matrix4x4(matrix);
    }

    public static Matrix4x4 perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        return new Matrix4x4(new float[][]{
                {tangentMinusOnDegree / aspectRatio, 0, 0, 0},
                {0, tangentMinusOnDegree, 0, 0},
                {0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), 2 * (nearPlane * farPlane) / (nearPlane - farPlane)},
                {0, 0, 1.0F, 0}
        });
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4x4 matrix, final Vector3f vertex) {
        final float x = (vertex.getX() * matrix.getValOfIndex(0,0)) + (vertex.getY() * matrix.getValOfIndex(1,0))
                + (vertex.getZ() * matrix.getValOfIndex(2,0)) + matrix.getValOfIndex(3,0);
        final float y = (vertex.getX() * matrix.getValOfIndex(0,1)) + (vertex.getY() * matrix.getValOfIndex(1,1))
                + (vertex.getZ() * matrix.getValOfIndex(2,1)) + matrix.getValOfIndex(3,1);
        final float z = (vertex.getX() * matrix.getValOfIndex(0,2)) + (vertex.getY() * matrix.getValOfIndex(1,2))
                + (vertex.getZ() * matrix.getValOfIndex(2,2)) + matrix.getValOfIndex(3,2);
        final float w = (vertex.getX() * matrix.getValOfIndex(0,3)) + (vertex.getY() * matrix.getValOfIndex(1,3))
                + (vertex.getZ() * matrix.getValOfIndex(2,3)) + matrix.getValOfIndex(3,3);
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}
