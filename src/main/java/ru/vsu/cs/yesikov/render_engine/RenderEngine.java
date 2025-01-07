package ru.vsu.cs.yesikov.render_engine;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point2f;
import ru.vsu.cs.yesikov.math.*;
import ru.vsu.cs.yesikov.model.Model;
import ru.vsu.cs.yesikov.model.Polygon;

public class RenderEngine {
    public RenderEngine() {
    }

    public static void render(GraphicsContext graphicsContext, Camera camera, Model mesh, int width, int height) {
        Matrix4x4 modelMatrix = GraphicConveyor.rotateScaleTranslate();
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();
        Matrix4x4 modelViewProjectionMatrix = new Matrix4x4(modelMatrix.getValues());
        modelViewProjectionMatrix.multiply(viewMatrix);
        modelViewProjectionMatrix.multiply(projectionMatrix);
        int nPolygons = mesh.polygons.size();

        for(int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            int nVerticesInPolygon = ((Polygon)mesh.polygons.get(polygonInd)).getVertexIndices().size();
            ArrayList<Point2f> resultPoints = new ArrayList();

            int vertexInPolygonInd;
            for(vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = (Vector3f)mesh.vertices.get((Integer)((Polygon)mesh.polygons.get(polygonInd)).getVertexIndices().get(vertexInPolygonInd));
                javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.getX(), vertex.getY(), vertex.getZ());
                Point2f resultPoint = GraphicConveyor.vertexToPoint(GraphicConveyor.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            for(vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine((double)((Point2f)resultPoints.get(vertexInPolygonInd - 1)).x, (double)((Point2f)resultPoints.get(vertexInPolygonInd - 1)).y, (double)((Point2f)resultPoints.get(vertexInPolygonInd)).x, (double)((Point2f)resultPoints.get(vertexInPolygonInd)).y);
            }

            if (nVerticesInPolygon > 0) {
                graphicsContext.strokeLine((double)((Point2f)resultPoints.get(nVerticesInPolygon - 1)).x, (double)((Point2f)resultPoints.get(nVerticesInPolygon - 1)).y, (double)((Point2f)resultPoints.get(0)).x, (double)((Point2f)resultPoints.get(0)).y);
            }
        }

    }
}
