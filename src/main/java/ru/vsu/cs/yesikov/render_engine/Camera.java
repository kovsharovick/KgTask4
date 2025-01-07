package ru.vsu.cs.yesikov.render_engine;

import ru.vsu.cs.yesikov.math.*;

public class Camera {

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(Vector3f translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target.add(translation);
    }

    Matrix4x4 getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    Matrix4x4 getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public Camera copy() {
        return new Camera(position.clone(), target.clone(), fov, aspectRatio, nearPlane, farPlane);
    }
    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;
}