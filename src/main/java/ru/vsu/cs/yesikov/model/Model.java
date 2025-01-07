package ru.vsu.cs.yesikov.model;

import ru.vsu.cs.yesikov.affineTransform.AffineTransform;
import ru.vsu.cs.yesikov.math.Matrix4x4;
import ru.vsu.cs.yesikov.math.Vector2f;
import ru.vsu.cs.yesikov.math.Vector3f;

import java.awt.image.BufferedImage;
import java.util.*;

import static ru.vsu.cs.yesikov.math.Vector3f.getNewAdd;
import static ru.vsu.cs.yesikov.math.Vector3f.normalize;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    private BufferedImage texture = null;

    private float sX = 1; private float sY = 1; private float sZ = 1;
    private float rX = 0; private float rY = 0; private float rZ = 0;
    private float tX = 0; private float tY = 0; private float tZ = 0;

    //Метод примитивной триангуляции
    public static void triangulate(Model model) {
        ArrayList<Polygon> triangulatedPolygons = new ArrayList<>();

        for (Polygon polygon : model.polygons) {
            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
            ArrayList<Integer> textureVertexIndices = polygon.getTextureVertexIndices();

            for (int i = 1; i < vertexIndices.size() - 1; i++) {
                Polygon triangle = new Polygon();

                ArrayList<Integer> vert = new ArrayList<>();
                vert.add(vertexIndices.get(0));
                vert.add(vertexIndices.get(i));
                vert.add(vertexIndices.get(i + 1));
                triangle.setVertexIndices(vert);

                ArrayList<Integer> texVert = new ArrayList<>();
                texVert.add(textureVertexIndices.get(0));
                texVert.add(textureVertexIndices.get(i));
                texVert.add(textureVertexIndices.get(i + 1));
                triangle.setTextureVertexIndices(texVert);

                triangulatedPolygons.add(triangle);
            }
        }
        model.polygons = triangulatedPolygons;
    }

    // Неподходящий кодик, слишком прост
//    public void calculateNormals() {
//        normals.clear();
//
//        for (Polygon polygon : polygons) {
//            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
//            if (vertexIndices.size() < 3) {
//                continue;
//            }
//            Vector3F v0 = vertices.get(vertexIndices.get(0));
//            Vector3F v1 = vertices.get(vertexIndices.get(1));
//            Vector3F v2 = vertices.get(vertexIndices.get(2));
//
//            Vector3F edge1 = v1.minus(v0);
//            Vector3F edge2 = v2.minus(v0);
//
//            Vector3F normal = edge1.vecMult(edge2);
//            normals.add(normal.getNormalized());
//        }
//    }

    public void calculateNormals() {
        normals.clear();
        Map<Integer, Vector3f> vertexNormals = new HashMap<>();

        for (Polygon polygon : polygons) {
            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
            if (vertexIndices.size() < 3) {
                continue;
            }

            Vector3f v0 = vertices.get(vertexIndices.get(0));
            Vector3f v1 = vertices.get(vertexIndices.get(1));
            Vector3f v2 = vertices.get(vertexIndices.get(2));

            Vector3f edge1 = Vector3f.getNewSub(v1, v0);
            Vector3f edge2 = Vector3f.getNewSub(v2, v0);


            Vector3f normal = Vector3f.getNewMul(edge1, edge2);
            normal.normalize();

            if (shouldInvertNormal(normal, vertexIndices)) {
                normal = new Vector3f(-normal.getX(), -normal.getY(), -normal.getZ());
            }

            for (int vertexIndex : vertexIndices) {
                vertexNormals.put(vertexIndex, vertexNormals.getOrDefault(vertexIndex, getNewAdd(new Vector3f(0, 0,0), normal)));
            }
        }

        for (Map.Entry<Integer, Vector3f> entry : vertexNormals.entrySet()) {
            Vector3f averageNormal = normalize(entry.getValue());
            normals.add(averageNormal);
        }
    }

    private boolean shouldInvertNormal(Vector3f normal, ArrayList<Integer> face) {
        // Логика для проверки необходимости инвертирования нормали
        return false;  // По умолчанию - не инвертировать
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public ArrayList<Vector3f> getVertices() {
        return vertices;
    }

    public ArrayList<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public ArrayList<Vector3f> getNormals() {
        return normals;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    // Метод клонирования вершин
    public ArrayList<Vector3f> cloneVertices() {
        ArrayList<Vector3f> clonedVertices = new ArrayList<>();
        for (Vector3f vertex : this.vertices) {
            clonedVertices.add(vertex.clone());
        }
        return clonedVertices;
    }

    // Метод клонирования текстурных вершин
    public ArrayList<Vector2f> cloneTextureVertices() {
        ArrayList<Vector2f> clonedTextureVertices = new ArrayList<>();
        for (Vector2f textureVertex : this.textureVertices) {
            clonedTextureVertices.add(textureVertex.clone());
        }
        return clonedTextureVertices;
    }

    // Метод клонирования нормалей
    public ArrayList<Vector3f> cloneNormals() {
        ArrayList<Vector3f> clonedNormals = new ArrayList<>();
        for (Vector3f normal : this.normals) {
            clonedNormals.add(normal.clone());
        }
        return clonedNormals;
    }

    // Метод клонирования полигонов
    public ArrayList<Polygon> clonePolygons() {
        ArrayList<Polygon> clonedPolygons = new ArrayList<>();
        for (Polygon polygon : this.polygons) {
            clonedPolygons.add(polygon.clone());
        }
        return clonedPolygons;
    }

    public void setTransformation(float sX, float sY, float sZ,
                                  float rX, float rY, float rZ,
                                  float tX, float tY, float tZ) {
        this.sX = sX; this.sY = sY; this.sZ = sZ;
        this.rX = rX; this.rY = rY; this.rZ = rZ;
        this.tX = tX; this.tY = tY; this.tZ = tZ;
    }

    public Matrix4x4 getModelMatrix() {
        return AffineTransform.getModelMatrix(sX, sY, sZ, rX, rY, rZ, tX, tY, tZ);
    }

    public Model getModelWithAppliedTransformation() {
        Model newModel = this.clone();
        AffineTransform.affineTransform(newModel, sX, sY, sZ, rX, rY, rZ, tX, tY, tZ);
        return newModel;
    }

    // Метод clone
    @Override
    public Model clone() {
        Model clonedModel = new Model();
        clonedModel.vertices = this.cloneVertices();
        clonedModel.textureVertices = this.cloneTextureVertices();
        clonedModel.normals = this.cloneNormals();
        clonedModel.polygons = this.clonePolygons();
        return clonedModel;
    }
}

