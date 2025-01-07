package ru.vsu.cs.yesikov.render_engine;

import ru.vsu.cs.yesikov.affineTransform.AffineTransform;
import ru.vsu.cs.yesikov.math.Matrix4x4;
import ru.vsu.cs.yesikov.math.Vector2f;
import ru.vsu.cs.yesikov.math.Vector3f;

public class CameraController {

    private Vector3f forwardV;
    private Vector3f backwardV;
    private Vector3f leftV;
    private Vector3f rightV;
    private final Vector3f upV;
    private final Vector3f downV;

    private final Camera camera;


    public CameraController(Camera camera, float translation) {
        this.camera = camera;
        forwardV = new Vector3f(0, 0, -translation);
        backwardV = new Vector3f(0, 0, translation);
        leftV = new Vector3f(translation, 0, 0);
        rightV = new Vector3f(-translation, 0, 0);
        upV = new Vector3f(0, translation, 0);
        downV = new Vector3f(0, -translation, 0);
    }

    public void handleCameraForward() {
        camera.movePosition(forwardV);
    }

    public void handleCameraBackward() {
        camera.movePosition(backwardV);
    }

    public void handleCameraLeft() {
        camera.movePosition(leftV);
    }

    public void handleCameraRight() {
        camera.movePosition(rightV);
    }

    public void handleCameraUp() {
        camera.movePosition(upV);
    }

    public void handleCameraDown() {
        camera.movePosition(downV);
    }

    public void rotateCamera(final Vector2f angleOfRotate) throws Exception {
        if (angleOfRotate.getY() >= 90) {
            angleOfRotate.setY(89.9F);
        } else if (angleOfRotate.getY() <= -90) {
            angleOfRotate.setY(-89.9F);
        }
        if (angleOfRotate.getX() >= 90) {
            angleOfRotate.setX(89.9F);
        } else if (angleOfRotate.getX() <= -90) {
            angleOfRotate.setX(-89.9F);
        }

        Matrix4x4 mR = AffineTransform.getRotateMatrix(new Vector3f(angleOfRotate.getY(), 0, 0));

        forwardV = GraphicConveyor.multiplyMatrix4ByVector3(mR, forwardV);
        backwardV = GraphicConveyor.multiplyMatrix4ByVector3(mR, backwardV);
        leftV = GraphicConveyor.multiplyMatrix4ByVector3(mR, leftV);
        rightV = GraphicConveyor.multiplyMatrix4ByVector3(mR, rightV);
        mR = AffineTransform.getRotateMatrix(new Vector3f(angleOfRotate.getY(), angleOfRotate.getX(), 0));
        camera.rotateCamera(mR);
    }

    public Camera getCamera() {
        return camera;
    }
}
