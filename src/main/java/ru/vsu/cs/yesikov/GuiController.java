package ru.vsu.cs.yesikov;

//import javafx.scene.Scene;

import ru.vsu.cs.yesikov.affineTransform.AffineTransform;
import ru.vsu.cs.yesikov.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;

import ru.vsu.cs.yesikov.math.*;
import ru.vsu.cs.yesikov.model.Model;
import ru.vsu.cs.yesikov.objreader.ObjReader;
import ru.vsu.cs.yesikov.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;
    private boolean isRotationActive;
    private final Vector2f currentMouseCoordinates = new Vector2f(0, 0);
    private final Vector2f centerCoordinates = new Vector2f(0, 0);
    private boolean focusOnTarget = false;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    public void focusOnTarget(ActionEvent actionEvent) {
        focusOnTarget = !focusOnTarget;
    }

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            mesh.recalculateNormals(mesh);
            Model.triangulate(mesh);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
        if (!focusOnTarget) {
            camera.moveTarget(new Vector3f(0, 0, -TRANSLATION));
        }
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
        if (!focusOnTarget) {
            camera.moveTarget(new Vector3f(0, 0, TRANSLATION));
        }
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        if (!focusOnTarget) {
            camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
            camera.moveTarget(new Vector3f(TRANSLATION, 0, 0));
        } else {
            Vector4f v4 = Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(new Vector3f(0, TRANSLATION, 0)),
                    new Vector4f(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ(), 1));
            camera.setPosition(new Vector3f(v4.getX(), v4.getY(), v4.getZ()));
        }
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        if (!focusOnTarget) {
            camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
            camera.moveTarget(new Vector3f(-TRANSLATION, 0, 0));
        } else {
            Vector4f v4 = Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(new Vector3f(0, -TRANSLATION, 0)),
                    new Vector4f(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ(), 1));
            camera.setPosition(new Vector3f(v4.getX(), v4.getY(), v4.getZ()));
        }
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        if (!focusOnTarget) {
            camera.movePosition(new Vector3f(0, TRANSLATION, 0));
            camera.moveTarget(new Vector3f(0, TRANSLATION, 0));
        } else {
            Vector4f v4 = Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(new Vector3f(-TRANSLATION, 0, 0)),
                    new Vector4f(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ(), 1));
            camera.setPosition(new Vector3f(v4.getX(), v4.getY(), v4.getZ()));
        }
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        if (!focusOnTarget) {
            camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
            camera.moveTarget(new Vector3f(0, -TRANSLATION, 0));
        } else {
            Vector4f v4 = Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(new Vector3f(TRANSLATION, 0, 0)),
                    new Vector4f(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ(), 1));
            camera.setPosition(new Vector3f(v4.getX(), v4.getY(), v4.getZ()));
        }
    }
}