package ru.vsu.cs.yesikov.model;

import ru.vsu.cs.yesikov.math.Matrix4x4;
import ru.vsu.cs.yesikov.math.Vector2f;
import ru.vsu.cs.yesikov.render_engine.GraphicConveyor;
import ru.vsu.cs.yesikov.math.Vector3f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ModifiedModel extends Model {
    private Vector3f rotateV;
    private Vector3f scaleV;
    private Vector3f translateV;
	private BufferedImage texture = null;

    public ModifiedModel(Model model) throws Exception {
        super(model);
        translateV = new Vector3f(0, 0, 0);
        rotateV = new Vector3f(0, 0, 0);
        scaleV = new Vector3f(1, 1, 1);
    }

    public ModifiedModel(ModifiedModel model) throws Exception {
        super(model);
        translateV = model.getTranslateV();
        rotateV = model.getRotateV();
        scaleV = model.getScaleV();
    }

    public ModifiedModel(Model model, Vector3f translateV, Vector3f rotateV, Vector3f scaleV) throws Exception {
		super(model);
		this.translateV = translateV;
        this.rotateV =  rotateV;
        this.scaleV = scaleV;
    }

    public Matrix4x4 getModelMatrix() throws Exception {
        Matrix4x4 modelMatrix = GraphicConveyor.getModelMatrix(translateV, rotateV, scaleV);
        modelMatrix.transposition();
        return modelMatrix;
    }


    public ArrayList<Vector3f> getModifiedVertexes () throws Exception {
        ArrayList<Vector3f> newVertexes = new ArrayList<>();
        Matrix4x4 modelMatrix = getModelMatrix();

        for (int i = 0; i < super.getVertices().size(); i++) {
            Vector3f vertex = new Vector3f(super.getVertices().get(i).getX(),super.getVertices().get(i).getY(), super.getVertices().get(i).getZ());
            Vector3f multipliedVector = GraphicConveyor.multiplyMatrix4ByVector3(modelMatrix, vertex);
            newVertexes.add(multipliedVector);
        }
        return newVertexes;
    }

    public Model getTransformedModel() throws Exception {
        ArrayList<Vector2f> tV = new ArrayList<>(super.getTextureVertices());
        ArrayList<Polygon> p = new ArrayList<>(super.getPolygons());
        ArrayList<Vector3f> nV = new ArrayList<>(super.getNormals());
        ArrayList<Vector3f> vM = new ArrayList<>(getModifiedVertexes());
        Model transformedModel = new Model(vM, tV, nV, p);

        transformedModel.recalculateNormals();

        return transformedModel;
    }



    public Vector3f getRotateV() {
        return rotateV;
    }

    public void setRotateV(Vector3f rotateV) {
        this.rotateV = rotateV;
    }

    public Vector3f getScaleV() {
        return scaleV;
    }

    public void setScaleV(Vector3f scaleV) {
        this.scaleV = scaleV;
    }

    public Vector3f getTranslateV() {
        return translateV;
    }

    public void setTranslateV(Vector3f translateV) {
        this.translateV = translateV;
    }

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
}