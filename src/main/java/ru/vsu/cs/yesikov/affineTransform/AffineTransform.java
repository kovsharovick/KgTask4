package ru.vsu.cs.yesikov.affineTransform;

import ru.vsu.cs.yesikov.math.Vector3f;
import ru.vsu.cs.yesikov.math.Matrix4x4;
import ru.vsu.cs.yesikov.math.Quaternion;
import ru.vsu.cs.yesikov.model.Model;

import static ru.vsu.cs.yesikov.math.Matrix4x4.multiplyByVector;
import static ru.vsu.cs.yesikov.math.Quaternion.multiplyQuaternions;

public class AffineTransform {

    public static Matrix4x4 getModelMatrix(float sX, float sY, float sZ,
                                          float rX, float rY, float rZ,
                                          float tX, float tY, float tZ) {
        Matrix4x4 T = translation(tX, tY, tZ);
        Matrix4x4 R = rotate(rX, rY, rZ, "xyz");
        Matrix4x4 S = scale(sX, sY, sZ);
        T.multiply(R);
        T.multiply(S);
        return T;
    }

    public static Matrix4x4 getRotateMatrix(Vector3f angle) {
        return rotate(angle.getX(), angle.getY(), angle.getZ(), "xyz");
    }

    public static void reverseAffineTransform(Model model, float sX, float sY, float sZ,
                                       float rX, float rY, float rZ,
                                       float tX, float tY, float tZ) {
        Matrix4x4 T = translation(tX, tY, tZ);
        Matrix4x4 R = rotate(rX, rY, rZ, "zyx");
        Matrix4x4 affineTransform = scale(sX, sY, sZ);
        affineTransform.multiply(R.getValues());
        affineTransform.multiply(T.getValues());
        for (Vector3f v : model.vertices) {
            v.change4fto3f(multiplyByVector(affineTransform, v.toVector4f(1)));
        }
        for (Vector3f v : model.normals) {
            v.change4fto3f(multiplyByVector(affineTransform, v.toVector4f(0)));
        }
    }


    public static void affineTransform(Model model, float sX, float sY, float sZ,
                                       float rX, float rY, float rZ, String order,
                                       float tX, float tY, float tZ) {
        Matrix4x4 affineTransform = translation(tX, tY, tZ);
        Matrix4x4 R = rotate(rX, rY, rZ, order);
        Matrix4x4 S = scale(sX, sY, sZ);
        affineTransform.multiply(R.getValues());
        affineTransform.multiply(S.getValues());
        for (Vector3f v : model.vertices) {
            v.change4fto3f(multiplyByVector(affineTransform, v.toVector4f(1)));
        }
        for (Vector3f v : model.normals) {
            v.change4fto3f(multiplyByVector(affineTransform, v.toVector4f(0)));
        }
    }

    public static void affineTransform(Model model, float sX, float sY, float sZ,
                                       float rX, float rY, float rZ,
                                       float tX, float tY, float tZ) {
        Matrix4x4 affineTransform = translation(tX, tY, tZ);
        Matrix4x4 R = rotate(rX, rY, rZ, "xyz");
        Matrix4x4 S = scale(sX, sY, sZ);
        affineTransform.multiply(R.getValues());
        affineTransform.multiply(S.getValues());
        for (Vector3f v : model.vertices) {
            v.change4fto3f(multiplyByVector(affineTransform, v.toVector4f(1)));
        }
        for (Vector3f v : model.normals) {
            v.change4fto3f(multiplyByVector(affineTransform, v.toVector4f(0)));
        }
    }


    public static void scale(Model model, float sX, float sY, float sZ) {
        Matrix4x4 scale = new Matrix4x4(new float[][]{
                {sX, 0, 0, 0},
                {0, sY, 0, 0},
                {0, 0, sZ, 0},
                {0, 0, 0, 1}
        });
        for (Vector3f v : model.vertices) {
            v.change4fto3f(multiplyByVector(scale, v.toVector4f(1)));
        }
        for (Vector3f v : model.normals) {
            v.change4fto3f(multiplyByVector(scale, v.toVector4f(0)));
        }
    }

    public static void rotate(Model model, float rX, float rY, float rZ, String order) {
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
        for (Vector3f v : model.vertices) {
            v.change4fto3f(multiplyByVector(rotate, v.toVector4f(1)));
        }
        for (Vector3f v : model.normals) {
            v.change4fto3f(multiplyByVector(rotate, v.toVector4f(0)));
        }
    }

    public static void rotateEuler(Model model, float rX, float rY, float rZ, String order) {
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
        for (Vector3f v : model.vertices) {
            v.change4fto3f(multiplyByVector(rotate, v.toVector4f(1)));
        }
        for (Vector3f v : model.normals) {
            v.change4fto3f(multiplyByVector(rotate, v.toVector4f(0)));
        }
    }

    public static void translation(Model model, float tX, float tY, float tZ) {
        Matrix4x4 translation = new Matrix4x4(new float[][]{
                {1, 0, 0, tX},
                {0, 1, 0, tY},
                {0, 0, 1, tZ},
                {0, 0, 0, 1}
        });
        for (Vector3f v : model.vertices) {
            v.change4fto3f(multiplyByVector(translation, v.toVector4f(1)));
        }
        for (Vector3f v : model.normals) {
            v.change4fto3f(multiplyByVector(translation, v.toVector4f(0)));
        }
    }

    private static Matrix4x4 translation(float tX, float tY, float tZ) {
        return new Matrix4x4(new float[][]{
                {1, 0, 0, tX},
                {0, 1, 0, tY},
                {0, 0, 1, tZ},
                {0, 0, 0, 1}
        });
    }

    private static Matrix4x4 rotate(float rX, float rY, float rZ, String order) {
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

    private static Matrix4x4 scale(float sX, float sY, float sZ) {
        return new Matrix4x4(new float[][]{
                {sX, 0, 0, 0},
                {0, sY, 0, 0},
                {0, 0, sZ, 0},
                {0, 0, 0, 1}
        });
    }
}
