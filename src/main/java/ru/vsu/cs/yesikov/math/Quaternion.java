package ru.vsu.cs.yesikov.math;

public class Quaternion {

    public float w, x, y, z;

    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Quaternion fromEuler(float radX, float radY, float radZ) {
        float cy = (float) Math.cos(radZ * 0.5);
        float sy = (float) Math.sin(radZ * 0.5);
        float cr = (float) Math.cos(radX * 0.5);
        float sr = (float) Math.sin(radX * 0.5);
        float cp = (float) Math.cos(radY * 0.5);
        float sp = (float) Math.sin(radY * 0.5);

        Quaternion q = new Quaternion(0, 0, 0, 0);
        q.w = cr * cp * cy + sr * sp * sy;
        q.x = sr * cp * cy - cr * sp * sy;
        q.y = cr * sp * cy + sr * cp * sy;
        q.z = cr * cp * sy - sr * sp * cy;

        return q;
    }

    public Matrix4x4 toRotationMatrix() {
        return new Matrix4x4(new float[][]{
                {(1 - 2 * (y * y + z * z)), (2 * (x * y - z * w)), (2 * (x * z + y * w)), 0},
                {(2 * (x * y + z * w)), (1 - 2 * (x * x + z * z)), (2 * (y * z - x * w)), 0},
                {(2 * (x * z - y * w)), (2 * (y * z + x * w)), (1 - 2 * (x * x + y * y)), 0},
                {0, 0, 0, 1}
        });
    }

    public static Quaternion multiplyQuaternions(Quaternion q1, Quaternion q2) {
        float w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
        float x = q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y;
        float y = q1.w * q2.y - q1.x * q2.z + q1.y * q2.w + q1.z * q2.x;
        float z = q1.w * q2.z + q1.x * q2.y - q1.y * q2.x + q1.z * q2.w;
        return new Quaternion(w, x, y, z);
    }
}

