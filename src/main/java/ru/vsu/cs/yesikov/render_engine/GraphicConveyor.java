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
    public static Matrix4x4 getRotationMatrix(Vector3f angle) throws Exception {
        if (angle.getX() > 360 || angle.getY() > 360 || angle.getZ() > 360) {
            throw new Exception("The absolute value angle should be less than 360!");
        }
        Matrix4x4 matrixRotation = new Matrix4x4(new float[4][4]);
        matrixRotation.setIdentity();

        Matrix4x4 matrixRotationX = getRotationMatrixX(angle.getX());
        Matrix4x4 matrixRotationY = getRotationMatrixY(angle.getY());
        Matrix4x4 matrixRotationZ = getRotationMatrixZ(angle.getZ());

        matrixRotation.multiply(matrixRotationX);
        matrixRotation.multiply(matrixRotationY);
        matrixRotation.multiply(matrixRotationZ);

        return matrixRotation;
    }
    private static Matrix4x4 getRotationMatrixX(double xAngle) {
        xAngle = Math.toRadians(xAngle);
        Matrix4x4 matrixRotationX = new Matrix4x4(new float[4][4]);
        matrixRotationX.setIdentity();

        matrixRotationX.getValues()[1][1] = (float) Math.cos(xAngle);
        matrixRotationX.getValues()[2][2] = (float) Math.cos(xAngle);
        matrixRotationX.getValues()[2][1] = (float) Math.sin(xAngle);
        matrixRotationX.getValues()[1][2] = (float) (-Math.sin(xAngle));


        return matrixRotationX;
    }

    private static Matrix4x4 getRotationMatrixY(double yAngle) {
        yAngle = Math.toRadians(yAngle);
        Matrix4x4 matrixRotationY = new Matrix4x4(new float[4][4]);
        matrixRotationY.setIdentity();

        matrixRotationY.getValues()[0][0] = (float) Math.cos(yAngle);
        matrixRotationY.getValues()[2][2] = (float) Math.cos(yAngle);
        matrixRotationY.getValues()[2][0] = (float) (-Math.sin(yAngle));
        matrixRotationY.getValues()[0][2] = (float) Math.sin(yAngle);

        return matrixRotationY;
    }

    private static Matrix4x4 getRotationMatrixZ(double zAngle) {
        zAngle = Math.toRadians(zAngle);
        Matrix4x4 matrixRotationZ = new Matrix4x4(new float[4][4]);
        matrixRotationZ.setIdentity();

        matrixRotationZ.getValues()[0][0] = (float) Math.cos(zAngle);
        matrixRotationZ.getValues()[1][1] = (float) Math.cos(zAngle);
        matrixRotationZ.getValues()[0][1] = (float) (-Math.sin(zAngle));
        matrixRotationZ.getValues()[1][0] = (float) Math.sin(zAngle);

        return matrixRotationZ;
    }
    public static Matrix4x4 getModelMatrix(Vector3f translate, Vector3f anglesOfRotate, Vector3f scale) throws Exception {
        Matrix4x4 modelMatrix = new Matrix4x4(new float[4][4]);
        modelMatrix.setIdentity();

        Matrix4x4 translationMatrix = getTranslationMatrix(translate);
        Matrix4x4 rotationMatrix = getRotationMatrix(anglesOfRotate);
        Matrix4x4 scaleMatrix = getScaleMatrix(scale);

        modelMatrix.multiply(translationMatrix);
        modelMatrix.multiply(rotationMatrix);
        modelMatrix.multiply(scaleMatrix);


        return modelMatrix;
    }
    private static Matrix4x4 getTranslationMatrix(Vector3f translate) {
        Matrix4x4 matrixTranslation = new Matrix4x4(new float[4][4]);
        matrixTranslation.setIdentity();
        matrixTranslation.getValues()[3][0] = translate.getX();//Уточнить
        matrixTranslation.getValues()[3][1] = translate.getY();
        matrixTranslation.getValues()[3][2] = translate.getZ();

        return matrixTranslation;
    }
    private static Matrix4x4 getScaleMatrix(Vector3f scale) {
        Matrix4x4 matrixScale = new Matrix4x4(new float[4][4]);
        matrixScale.setIdentity();
        matrixScale.getValues()[0][0] = scale.getX();
        matrixScale.getValues()[1][1] = scale.getY();
        matrixScale.getValues()[2][2] = scale.getZ();
        return matrixScale;
    }
}
