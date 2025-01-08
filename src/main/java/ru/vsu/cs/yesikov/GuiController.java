package ru.vsu.cs.yesikov;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import ru.vsu.cs.yesikov.model.ModifiedModel;
import ru.vsu.cs.yesikov.render_engine.CameraController;
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
import ru.vsu.cs.yesikov.render_engine.Scene;

public class GuiController {

    final private float TRANSLATION = 0.2F;
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
    private Scene scene;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;
    @FXML
    public ColorPicker modelColor;
    @FXML
    public RadioButton radioButtonMesh;
    @FXML
    public RadioButton radioButtonTexture;
    @FXML
    public RadioButton radioButtonShades;
    @FXML
    public RadioButton radioButtonSolidColor;
    private boolean isRotationActive;
    private final Vector2f currentMouseCoordinates = new Vector2f(0, 0);
    private final Vector2f centerCoordinates = new Vector2f(0, 0);

    private Model mesh = null;

    private final Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    @FXML
    private void initialize() {
        camera.movePosition(new Vector3f(0, 0, -50));
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        scene = new Scene();

        scene.getCameraControllers().add(new CameraController(new Camera(
                new Vector3f(0, 0, 100),
                new Vector3f(0, 0, 0),
                1.0F, 1, 0.01F, 100), TRANSLATION));
        scene.setCurrentCameraController(scene.getCameraControllers().get(0));

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            scene.getCurrentCameraController().getCamera().setAspectRatio((float) (width / height));


            if (isRotationActive) {
                rotateCamera();
            }

            if (!scene.getActiveModels().isEmpty()) {
                try {
                    for (ModifiedModel model : scene.getActiveModels()) {
                        RenderEngine.render(canvas.getGraphicsContext2D(), scene.getCurrentCameraController().getCamera(),
                                model.getTransformedModel(), (int) width, (int) height,
                                modelColor.getValue(), model.getTexture(), getRenderWayData());
                    }
                } catch (Exception e) {
//                    showExceptionNotification(e);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }
    public void rotateCamera() {
        centerCoordinates.setX((float) (canvas.getWidth() / 2));
        centerCoordinates.setY((float) (canvas.getHeight() / 2));

        float diffX = currentMouseCoordinates.getX() - centerCoordinates.getX();
        float diffY = currentMouseCoordinates.getY() - centerCoordinates.getY();

        float xAngle = (float) ((diffX / canvas.getWidth()) * 1);
        float yAngle = (float) ((diffY / canvas.getHeight()) * -1);

        try {
            scene.getCurrentCameraController().rotateCamera(new Vector2f(xAngle, yAngle));
        } catch (Exception e) {
//            showExceptionNotification(e);
        }
    }
    public boolean[] getRenderWayData() {
        return new boolean[] {radioButtonMesh.isSelected(), radioButtonShades.isSelected(),
                radioButtonTexture.isSelected(), radioButtonSolidColor.isSelected()};
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
        System.out.println("Camera moved up");
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
        camera.moveTarget(new Vector3f(0, -TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
        camera.moveTarget(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void onSaveModelMenuItemClick(ActionEvent actionEvent) {
        saveModel(actionEvent);
    }

    @FXML
    public void transformation(ActionEvent actionEvent) {
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

}