package ru.vsu.cs.yesikov.affineTransform;

import ru.vsu.cs.yesikov.math.*;
import ru.vsu.cs.yesikov.model.Model;
import static ru.vsu.cs.yesikov.math.Quaternion.multiplyQuaternions;

public class AffineTransform {

    public static void affineTransform(Model model, int sX, int sY, int sZ,
                                       int rX, int rY, int rZ, String order,
                                       int tX, int tY, int tZ) {
        Matrix4x4 T = translation(tX, tY, tZ);
        Matrix4x4 R = rotate(rX, rY, rZ, order);
        Matrix4x4 affineTransform = scale(sX, sY, sZ);
        affineTransform.multiply(R.getValues());
        affineTransform.multiply(T.getValues());
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 1);
            float[] res = affineTransform.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 0);
            float[] res = affineTransform.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
    }

    public static void affineTransform(Model model, int sX, int sY, int sZ,
                                       int rX, int rY, int rZ,
                                       int tX, int tY, int tZ) {
        Matrix4x4 T = translation(tX, tY, tZ);
        Matrix4x4 R = rotate(rX, rY, rZ, "xyz");
        Matrix4x4 affineTransform = scale(sX, sY, sZ);
        affineTransform.multiply(R.getValues());
        affineTransform.multiply(T.getValues());
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 1);
            float[] res = affineTransform.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 0);
            float[] res = affineTransform.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
    }


    public static void scale(Model model, float sX, float sY, float sZ) {
        Matrix4x4 scale = new Matrix4x4(new float[][]{
                {sX, 0, 0, 0},
                {0, sY, 0, 0},
                {0, 0, sZ, 0},
                {0, 0, 0, 1}
        });
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 1);
            float[] res = scale.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 0);
            float[] res = scale.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
    }

    public static void rotate(Model model, int rX, int rY, int rZ, String order) {
        float radX = (float) Math.toRadians(rX);
        float radY = (float) Math.toRadians(rY);
        float radZ = (float) Math.toRadians(rZ);
        Quaternion qX = Quaternion.fromEuler(radX, 0, 0);
        Quaternion qY = Quaternion.fromEuler(0, radY, 0);
        Quaternion qZ = Quaternion.fromEuler(0, 0, radZ);
        Quaternion q = new Quaternion(1, 0, 0, 0);

        for (char ch : order.toCharArray()) {
            q = switch (ch) {
                case 'x' -> multiplyQuaternions(q, qX);
                case 'y' -> multiplyQuaternions(q, qY);
                case 'z' -> multiplyQuaternions(q, qZ);
                default -> q;
            };
        }
        Matrix4x4 rotate = q.toRotationMatrix();
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 1);
            float[] res = rotate.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 0);
            float[] res = rotate.multiplyByVector(v4);;
            v.vector3fto4f(res);
        }
    }

    public static void rotateEuler(Model model, int rX, int rY, int rZ, String order) {
        double radX = Math.toRadians(rX);
        double radY = Math.toRadians(rY);
        double radZ = Math.toRadians(rZ);
        Matrix4x4 rotateX = new Matrix4x4(new float[][]{
                {1, 0, 0, 0},
                {0, (float) Math.cos(radX), (float) Math.sin(radX), 0},
                {0, (float) -Math.sin(radX), (float) Math.cos(radX), 0},
                {0, 0, 0, 1}
        });
        Matrix4x4 rotateY = new Matrix4x4(new float[][]{
                {(float) Math.cos(radY), 0, (float) -Math.sin(radY), 0},
                {0, 1, 0, 0},
                {(float) Math.sin(radY), 0, (float) Math.cos(radY), 0},
                {0, 0, 0, 1}
        });
        Matrix4x4 rotateZ = new Matrix4x4(new float[][]{
                {(float) Math.cos(radZ), (float) Math.sin(radZ), 0, 0},
                {(float) -Math.sin(radZ), (float) Math.cos(radZ), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        Matrix4x4 rotate;
        switch (order) {
            case "zyx":
                rotate = rotateX;
                rotate.multiply(rotateY.getValues());
                rotate.multiply(rotateZ.getValues());
                break;
            case "yzx":
                rotate = rotateX;
                rotate.multiply(rotateZ.getValues());
                rotate.multiply(rotateY.getValues());
                break;
            case "zxy":
                rotate = rotateY;
                rotate.multiply(rotateX.getValues());
                rotate.multiply(rotateZ.getValues());
                break;
            case "xzy":
                rotate = rotateY;
                rotate.multiply(rotateZ.getValues());
                rotate.multiply(rotateX.getValues());
                break;
            case "yxz":
                rotate = rotateZ;
                rotate.multiply(rotateX.getValues());
                rotate.multiply(rotateY.getValues());
                break;
            case "xyz":
                rotate = rotateZ;
                rotate.multiply(rotateY.getValues());
                rotate.multiply(rotateX.getValues());
                break;
            default:
                throw new IllegalStateException("You should input order such as 'xyz', 'yzx' or 'zxy'...");
        }
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 1);
            float[] res = rotate.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 0);
            float[] res = rotate.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
    }

    public static void translation(Model model, int tX, int tY, int tZ) {
        Matrix4x4 translation = new Matrix4x4(new float[][]{
                {1, 0, 0, tX},
                {0, 1, 0, tY},
                {0, 0, 1, tZ},
                {0, 0, 0, 1}
        });
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 1);
            float[] res = translation.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x(), v.y(), v.z(), 0);
            float[] res = translation.multiplyByVector(v4);
            v.vector3fto4f(res);
        }
    }

    private static Matrix4x4 translation(int tX, int tY, int tZ) {
        return new Matrix4x4(new float[][]{
                {1, 0, 0, tX},
                {0, 1, 0, tY},
                {0, 0, 1, tZ},
                {0, 0, 0, 1}
        });
    }

    private static Matrix4x4 rotate(int rX, int rY, int rZ, String order) {
        float radX = (float) Math.toRadians(rX);
        float radY = (float) Math.toRadians(rY);
        float radZ = (float) Math.toRadians(rZ);
        Quaternion qX = Quaternion.fromEuler(radX, 0, 0);
        Quaternion qY = Quaternion.fromEuler(0, radY, 0);
        Quaternion qZ = Quaternion.fromEuler(0, 0, radZ);
        Quaternion q = new Quaternion(1, 0, 0, 0);

        for (char ch : order.toCharArray()) {
            q = switch (ch) {
                case 'x' -> multiplyQuaternions(q, qX);
                case 'y' -> multiplyQuaternions(q, qY);
                case 'z' -> multiplyQuaternions(q, qZ);
                default -> q;
            };
        }
        return q.toRotationMatrix();
    }

    private static Matrix4x4 scale(int sX, int sY, int sZ) {
        return new Matrix4x4(new float[][]{
                {sX, 0, 0, 0},
                {0, sY, 0, 0},
                {0, 0, sZ, 0},
                {0, 0, 0, 1}
        });
    }
}
