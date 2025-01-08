package ru.vsu.cs.yesikov.render_engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import ru.vsu.cs.yesikov.math.*;
import ru.vsu.cs.yesikov.model.Model;

import static ru.vsu.cs.yesikov.render_engine.Coloring.getPixelColor;
import static ru.vsu.cs.yesikov.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height,
            final javafx.scene.paint.Color modelColor,
            BufferedImage texture,
            final boolean[] renderingStatements) {
        Matrix4x4 modelMatrix = rotateScaleTranslate();
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();

        Matrix4x4 modelViewProjectionMatrix = new Matrix4x4(projectionMatrix.getValues());
        modelViewProjectionMatrix.multiply(viewMatrix);
        modelViewProjectionMatrix.multiply(modelMatrix);

        javafx.scene.paint.Color meshColor = javafx.scene.paint.Color.WHITE;

        float[] zBuffer = new float[width * height];

        Arrays.fill(zBuffer, Float.NEGATIVE_INFINITY);
        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                Vector3f vertexVecmath = new Vector3f(vertex.getX(), vertex.getY(), vertex.getZ());

                Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).getX(),
                        resultPoints.get(vertexInPolygonInd - 1).getY(),
                        resultPoints.get(vertexInPolygonInd).getX(),
                        resultPoints.get(vertexInPolygonInd).getY());
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).getX(),
                        resultPoints.get(nVerticesInPolygon - 1).getY(),
                        resultPoints.get(0).getX(),
                        resultPoints.get(0).getY());

            startRender(renderingStatements[0],
                    renderingStatements[1],
                    renderingStatements[2],
                    renderingStatements[3],
                    Coloring.convertColorToAWT(modelColor),
                    meshColor,
                    graphicsContext,
                    texture,
                    width,
                    height,
                    mesh,
                    camera,
                    polygonInd,
                    nVerticesInPolygon,
                    resultPoints,
                    zBuffer);
        }
    }

    private static void startRender(
            boolean haveMesh,
            boolean haveShade,
            boolean haveTexture,
            boolean haveSolidColor,
            Color modelColor,
            javafx.scene.paint.Color meshColor,
            GraphicsContext graphicsContext,
            BufferedImage texture,
            int width,
            int height,
            Model mesh,
            Camera camera,
            int polygonInd,
            int nVerticesInPolygon,
            ArrayList<Point2f> points,
            float[] zBuffer) {

        if (haveTexture && texture != null) {
            rasterizePolygon(
                    false,
                    true,
                    haveShade,
                    modelColor,
                    graphicsContext,
                    texture,
                    width,
                    height,
                    mesh,
                    camera.getTarget(),
                    camera.getPosition(),
                    polygonInd,
                    points,
                    zBuffer);
        }
        if (haveSolidColor) {
            rasterizePolygon(
                    true,
                    false,
                    haveShade,
                    modelColor,
                    graphicsContext,
                    null,
                    width,
                    height,
                    mesh,
                    camera.getTarget(),
                    camera.getPosition(),
                    polygonInd,
                    points,
                    zBuffer);
        }
        if (haveMesh) {
            drawMesh(graphicsContext, meshColor, nVerticesInPolygon, points);
        }
    }


    private static void rasterizePolygon(
            boolean haveSolidColor,
            boolean haveTexture,
            boolean haveShade,
            Color modelColor,
            GraphicsContext graphicsContext,
            BufferedImage texture,
            int width,
            int height,
            Model mesh,
            Vector3f target,
            Vector3f position,
            int polygonInd,
            ArrayList<Point2f> points,
            float[] zBuffer) {

        List<Integer> vertexIndices = mesh.getPolygons().get(polygonInd).getVertexIndices();
        Vector3f[] v = new Vector3f[]{mesh.getVertices().get(vertexIndices.get(0)), mesh.getVertices().get(vertexIndices.get(1)), mesh.getVertices().get(vertexIndices.get(2))};

        float x1 = points.get(0).getX();
        float y1 = points.get(0).getY();
        float x2 = points.get(1).getX();
        float y2 = points.get(1).getY();
        float x3 = points.get(2).getX();
        float y3 = points.get(2).getY();

        int minX = (int) Math.max(0, Math.ceil(Math.min(x1, Math.min(x2, x3))));
        int maxX = (int) Math.min(width - 1, Math.floor(Math.max(x1, Math.max(x2, x3))));

        int minY = (int) Math.max(0, Math.ceil(Math.min(y1, Math.min(y2, y3))));
        int maxY = (int) Math.min(height - 1, Math.floor(Math.max(y1, Math.max(y2, y3))));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                BarycentricCoordinates bCoordinates = new BarycentricCoordinates(x, y, x1, x2, x3, y1, y2, y3);
                if (bCoordinates.getU() >= 0 && bCoordinates.getU() <= 1 && bCoordinates.getV() >= 0 && bCoordinates.getV() <= 1 && bCoordinates.getW() >= 0 && bCoordinates.getW() <= 1) {
                    float depth = (float) (bCoordinates.getU() * v[0].getZ() + bCoordinates.getV() * v[1].getZ() + bCoordinates.getW() * v[2].getZ());
                    int zIndex = y * width + x;
                    if (zBuffer[zIndex] < depth) {
                        if (haveTexture) {
                            ArrayList<Integer> textureVertexIndices = mesh.getPolygons().get(polygonInd).getTextureVertexIndices();
                            Vector2f[] vt = new Vector2f[]{mesh.getTextureVertices().get(textureVertexIndices.get(0)), mesh.getTextureVertices().get(textureVertexIndices.get(1)), mesh.getTextureVertices().get(textureVertexIndices.get(2))};

                            float xt = (float) (bCoordinates.getU() * vt[0].getX() + bCoordinates.getV() * vt[1].getX() + bCoordinates.getW() * vt[2].getX());
                            float yt = (float) (1 - (bCoordinates.getU() * vt[0].getY() + bCoordinates.getV() * vt[1].getY() + bCoordinates.getW() * vt[2].getY()));

                            if (haveShade) {
                                Vector3f[] vn = new Vector3f[]{mesh.getNormals().get(vertexIndices.get(0)), mesh.getNormals().get(vertexIndices.get(1)), mesh.getNormals().get(vertexIndices.get(2))};
                                drawPixel(graphicsContext, x, y, xt, yt, bCoordinates, vn, target, position, texture);
                            } else {
                                drawPixel(graphicsContext, x, y, xt, yt, texture);
                            }
                        } else {
                            if (haveSolidColor) {
                                int color = modelColor.getRGB();
                                if (haveShade) {
                                    Vector3f[] vn = new Vector3f[]{mesh.getNormals().get(vertexIndices.get(0)), mesh.getNormals().get(vertexIndices.get(1)), mesh.getNormals().get(vertexIndices.get(2))};
                                    drawPixel(graphicsContext, x, y, bCoordinates, vn, target, position, color);
                                } else {
                                    drawPixel(graphicsContext, x, y, color);
                                }
                            }
                        }
                        zBuffer[zIndex] = depth;
                    }
                }
            }
        }
    }

    // Рисование пикселей на экране
    private static void drawPixel(GraphicsContext graphicsContext, int x, int y, int color) {
        graphicsContext.getPixelWriter().setArgb(x, y, color);
    }

    private static void drawPixel(GraphicsContext graphicsContext, int x, int y, BarycentricCoordinates bCoordinates, Vector3f[] polygonNormals, Vector3f target, Vector3f position, int color) {
        Shadow shadow = new Shadow(polygonNormals, target, position);
        color = getPixelColor(shadow.calculateShadeCoefficients(bCoordinates), color);
        graphicsContext.getPixelWriter().setArgb(x, y, color);
    }

    private static void drawPixel(GraphicsContext graphicsContext, int x, int y, float xt, float yt, BufferedImage texture) {
        int color = getPixelColor(xt, yt, texture);
        graphicsContext.getPixelWriter().setArgb(x, y, color);
    }

    private static void drawPixel(GraphicsContext graphicsContext, int x, int y, float xt, float yt, BarycentricCoordinates bCoordinates, Vector3f[] polygonNormals, Vector3f target, Vector3f position, BufferedImage texture) {
        Shadow shadow = new Shadow(polygonNormals, target, position);
        int color = getPixelColor(shadow.calculateShadeCoefficients(bCoordinates), xt, yt, texture);
        graphicsContext.getPixelWriter().setArgb(x, y, color);
    }

    private static void drawMesh(
            GraphicsContext graphicsContext,
            javafx.scene.paint.Color meshColor,
            int nVerticesInPolygon,
            ArrayList<Point2f> resultPoints) {
        graphicsContext.setStroke(meshColor);
        for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
            graphicsContext.strokeLine(
                    resultPoints.get(vertexInPolygonInd - 1).getX(),
                    resultPoints.get(vertexInPolygonInd - 1).getY(),
                    resultPoints.get(vertexInPolygonInd).getX(),
                    resultPoints.get(vertexInPolygonInd).getY());
        }

        if (nVerticesInPolygon > 0) {
            graphicsContext.strokeLine(
                    resultPoints.get(nVerticesInPolygon - 1).getX(),
                    resultPoints.get(nVerticesInPolygon - 1).getY(),
                    resultPoints.get(0).getX(),
                    resultPoints.get(0).getY());
        }
    }


}