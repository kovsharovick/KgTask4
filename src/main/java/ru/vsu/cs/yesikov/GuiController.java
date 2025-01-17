package ru.vsu.cs.yesikov;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
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
import java.util.ArrayList;

import ru.vsu.cs.yesikov.math.*;
import ru.vsu.cs.yesikov.model.Model;
import ru.vsu.cs.yesikov.objreader.ObjReader;
import ru.vsu.cs.yesikov.render_engine.Camera;

public class GuiController {

    private float TRANSLATION = 0.2F;
    public CheckBox turnOnGrid;
    public CheckBox turnOnTexture;
    public CheckBox turnOnLight;
    public Pane sidebar;
    public ProgressBar progressBar;
    public TextField translateX;
    public TextField translateY;
    public TextField translateZ;
    public TextField rotateX;
    public TextField rotateY;
    public TextField rotateZ;
    public TextField scaleX;
    public TextField scaleY;
    public TextField scaleZ;
    public ArrayList<float[]> reverseAffineTransform = new ArrayList<>();

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;
    private float startXForMouse;
    private float startYForMouse;

    private Vector3f dragStartTarget;

    private Model mesh = null;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    @FXML
    private void initialize() {
        camera.movePosition(new Vector3f(0, 0, -75));
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Timeline timeline = new Timeline();
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


        Platform.runLater(() -> {
            anchorPane.getScene().addEventFilter(ScrollEvent.SCROLL, event -> {
                if (event.getDeltaY() > 0) {
                    handleCameraForward(null);
                } else {
                    handleCameraBackward(null);
                }
            });
        });

        canvas.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                this.startXForMouse = (float) mouseEvent.getX();
                this.startYForMouse = (float) mouseEvent.getY();
                Vector3f startTarget = camera.getTarget();
                dragStartTarget = new Vector3f(startTarget.getX(), startTarget.getY(), startTarget.getZ());
            }
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                Vector4f newTarget = Vector4f.getNewSub(dragStartTarget.toVector4f(0), (camera.getPosition().toVector4f(1)));
                newTarget = Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(
                        new Vector3f((float) ((mouseEvent.getY() - this.startYForMouse)) / 3,
                                (float) (-(mouseEvent.getX() - this.startXForMouse)) / 3, 0)), newTarget);
                Vector3f coordinate = Vector3f.getNewAdd(newTarget.toVector3f(), camera.getPosition());
                camera.setTarget(coordinate);
            }
        });
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            mesh.recalculateNormals();
            Model.triangulate(mesh);
            // todo: обработка ошибок
        } catch (IOException exception) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
        camera.moveTarget(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
        camera.moveTarget(new Vector3f(TRANSLATION, 0, 0));
    }


    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
        camera.moveTarget(new Vector3f(0, -TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
        camera.moveTarget(new Vector3f(0, TRANSLATION, 0));

    }

    @FXML
    public void handleCameraRotateLeft(ActionEvent actionEvent) {
        camera.setPosition(
                Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(new Vector3f(0, TRANSLATION, 0)),
                        camera.getPosition().toVector4f(1)).toVector3f());
    }

    @FXML
    public void handleCameraRotateRight(ActionEvent actionEvent) {
        camera.setPosition(
                Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(new Vector3f(0, -TRANSLATION, 0)),
                        camera.getPosition().toVector4f(1)).toVector3f());
    }

    @FXML
    public void handleCameraRotateUp(ActionEvent actionEvent) {
        camera.setPosition(
                Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(new Vector3f(TRANSLATION, 0, 0)),
                        camera.getPosition().toVector4f(1)).toVector3f());
    }

    @FXML
    public void handleCameraRotateDown(ActionEvent actionEvent) {
        camera.setPosition(
                Matrix4x4.multiplyByVector(AffineTransform.getRotateMatrix(new Vector3f(-TRANSLATION, 0, 0)),
                        camera.getPosition().toVector4f(1)).toVector3f());
    }

    @FXML
    public void onSaveModelMenuItemClick(ActionEvent actionEvent) {
        saveModel(actionEvent);
    }

    @FXML
    public void transformation(ActionEvent actionEvent) {
        float[] values = new float[]{
                Float.parseFloat(scaleX.getText()), Float.parseFloat(scaleY.getText()), Float.parseFloat(scaleZ.getText()),
                Float.parseFloat(rotateX.getText()), Float.parseFloat(rotateY.getText()), Float.parseFloat(rotateZ.getText()),
                Float.parseFloat(translateX.getText()), Float.parseFloat(translateY.getText()), Float.parseFloat(translateZ.getText())
        };
        if (mesh != null) {
            AffineTransform.affineTransform(mesh,
                    values[0], values[1], values[2],
                    values[3], values[4], values[5],
                    values[6], values[7], values[8]
            );
            float[] reverse = new float[]{
                    1 / values[0], 1 / values[1], 1 / values[2],
                    -values[3], -values[4], -values[5],
                    -values[6], -values[7], -values[8]
            };
            reverseAffineTransform.add(reverse);
        }
        ((Node) actionEvent.getSource()).getScene().getRoot().requestFocus();
    }

    @FXML
    public void saveModel(ActionEvent actionEvent) {
        //todo: сохранение через райтер
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Сохранить модель");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());
        if (file != null) {
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".obj")) {
                filePath += ".obj";
            }

            progressBar.setVisible(true);

            String finalFilePath = filePath;
        }
        ((Node) actionEvent.getSource()).getScene().getRoot().requestFocus();
    }

    @FXML
    public void selectTexture(ActionEvent actionEvent) {
        //todo: Выбор текстуры, похож на выбор модели
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        /*try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            mesh.recalculateNormals(mesh);
            Model.triangulate(mesh);
            // todo: обработка ошибок
        } catch (IOException exception) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
    }

    public void returnBackCamera(ActionEvent actionEvent) {
        camera = new Camera(
                new Vector3f(0, 0, 100),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100);
        camera.movePosition(new Vector3f(0, 0, -75));
    }

    public void returnBackModel(ActionEvent actionEvent) {
        if (mesh != null) {
            for (int i = reverseAffineTransform.size() - 1; i >= 0; i--) {
                AffineTransform.reverseAffineTransform(mesh,
                        reverseAffineTransform.get(i)[0], reverseAffineTransform.get(i)[1], reverseAffineTransform.get(i)[2],
                        reverseAffineTransform.get(i)[3], reverseAffineTransform.get(i)[4], reverseAffineTransform.get(i)[5],
                        reverseAffineTransform.get(i)[6], reverseAffineTransform.get(i)[7], reverseAffineTransform.get(i)[8]);
            }
        }
        reverseAffineTransform = new ArrayList<>();
    }
}