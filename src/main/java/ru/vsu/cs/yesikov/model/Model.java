package ru.vsu.cs.yesikov.model;

import ru.vsu.cs.yesikov.math.Vector2f;
import ru.vsu.cs.yesikov.math.Vector3f;

import java.util.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public void recalculateNormals(Model model) {
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

            Vector3f edge1 = Vector3f.getNewSub(v1,v0);
            Vector3f edge2 =Vector3f.getNewSub(v2,v1);

                    Vector3f normal = Vector3f.getNewMul(edge1,edge2);
                    normal.normalize();


            if (shouldInvertNormal(normal, vertexIndices)) {
                normal = new Vector3f(-normal.x(), -normal.y(), -normal.z());
            }

            for (int vertexIndex : vertexIndices) {
                vertexNormals.put(vertexIndex, vertexNormals.getOrDefault(vertexIndex,  Vector3f.getNewAdd(new Vector3f(0,0,0),normal)));
            }
        }

        for (Map.Entry<Integer, Vector3f> entry : vertexNormals.entrySet()) {
            Vector3f averageNormal = entry.getValue();
            averageNormal.normalize();
            normals.add(averageNormal);
        }
    }

    private boolean shouldInvertNormal(Vector3f normal, ArrayList<Integer> face) {
        // Логика для проверки необходимости инвертирования нормали
        return false;  // По умолчанию - не инвертировать
    }
    public static void triangulate (Model model){
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
}
