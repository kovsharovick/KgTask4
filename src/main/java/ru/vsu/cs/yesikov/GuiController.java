//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ru.vsu.cs.yesikov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javax.vecmath.Vector3f;
import ru.vsu.cs.yesikov.model.Model;
import ru.vsu.cs.yesikov.objreader.ObjReader;
import ru.vsu.cs.yesikov.render_engine.Camera;
import ru.vsu.cs.yesikov.render_engine.RenderEngine;
import ru.vsu.cs.yesikov.math.*;

public class GuiController {
    private final float TRANSLATION = 0.5F;
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    private Model mesh = null;
    private Camera camera = new Camera(new Vector3f(0.0F, 0.0F, 100.0F), new Vector3f(0.0F, 0.0F, 0.0F), 1.0F, 1.0F, 0.01F, 100.0F);
    private Timeline timeline;

    public GuiController() {
    }

    @FXML
    private void initialize() {
        this.anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> {
            this.canvas.setWidth(newValue.doubleValue());
        });
        this.anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> {
            this.canvas.setHeight(newValue.doubleValue());
        });
        this.timeline = new Timeline();
        this.timeline.setCycleCount(-1);
        KeyFrame frame = new KeyFrame(Duration.millis(15.0), (event) -> {
            double width = this.canvas.getWidth();
            double height = this.canvas.getHeight();
            this.canvas.getGraphicsContext2D().clearRect(0.0, 0.0, width, height);
            this.camera.setAspectRatio((float)(width / height));
            if (this.mesh != null) {
                RenderEngine.render(this.canvas.getGraphicsContext2D(), this.camera, this.mesh, (int)width, (int)height);
            }

        }, new KeyValue[0]);
        this.timeline.getKeyFrames().add(frame);
        this.timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", new String[]{"*.obj"}));
        fileChooser.setTitle("Load Model");
        File file = fileChooser.showOpenDialog((Stage)this.canvas.getScene().getWindow());
        if (file != null) {
            Path fileName = Path.of(file.getAbsolutePath());

            try {
                String fileContent = Files.readString(fileName);
                this.mesh = ObjReader.read(fileContent);
            } catch (IOException var5) {
            }

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        this.camera.movePosition(new Vector3f(0.0F, 0.0F, -0.5F));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        this.camera.movePosition(new Vector3f(0.0F, 0.0F, 0.5F));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        this.camera.movePosition(new Vector3f(0.5F, 0.0F, 0.0F));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        this.camera.movePosition(new Vector3f(-0.5F, 0.0F, 0.0F));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        this.camera.movePosition(new Vector3f(0.0F, 0.5F, 0.0F));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        this.camera.movePosition(new Vector3f(0.0F, -0.5F, 0.0F));
    }
}
