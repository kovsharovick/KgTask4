package ru.vsu.cs.yesikov.objwriter;

import ru.vsu.cs.yesikov.math.Vector2f;
import ru.vsu.cs.yesikov.math.Vector3f;
import ru.vsu.cs.yesikov.model.Model;
import ru.vsu.cs.yesikov.model.Polygon;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ObjWriter {

    public static void write(Model model, Writer writer) throws IOException {
        write(model, writer, " ");
    }

    public static void write(Model model, Writer writer, String separator) throws IOException {
        // Writing vertices, texture vertices, and normals
        writeVertices(writer, model.vertices, separator);
        writeTextureVertices(writer, model.textureVertices, separator);
        writeNormals(writer, model.normals, separator);

        // Writing faces
        writeFaces(writer, model.polygons, separator);
    }

    private static void writeVertices(Writer writer, ArrayList<Vector3f> vertices, String separator) throws IOException {
        for (Vector3f vertex : vertices) {
            writer.append("v").append(separator).append(String.valueOf(vertex.getX())).append(" ")
                    .append(String.valueOf(vertex.getY())).append(" ")
                    .append(String.valueOf(vertex.getZ())).append("\n");
            writer.flush();
        }
    }

    private static void writeTextureVertices(Writer writer, ArrayList<Vector2f> textureVertices, String separator) throws IOException {
        for (Vector2f textureVertex : textureVertices) {
            writer.append("vt").append(separator).append(String.valueOf(textureVertex.getX())).append(separator)
                    .append(String.valueOf(textureVertex.getY())).append("\n");
            writer.flush();
        }
    }

    private static void writeNormals(Writer writer, ArrayList<Vector3f> normals, String separator) throws IOException {
        for (Vector3f normal : normals) {
            writer.append("vn").append(separator).append(String.valueOf(normal.getX())).append(separator)
                    .append(String.valueOf(normal.getY())).append(" ")
                    .append(String.valueOf(normal.getZ())).append("\n");
            writer.flush();
        }
    }

    private static void writeFaces(Writer writer, ArrayList<Polygon> polygons, String separator) throws IOException {
        for (Polygon polygon : polygons) {
            StringBuilder sb = new StringBuilder("f");
            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
            ArrayList<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
            ArrayList<Integer> normalIndices = polygon.getNormalIndices();
            for (int i = 0; i < vertexIndices.size(); i++) {
                sb.append(separator).append(vertexIndices.get(i) + 1); // OBJ index starts at 1
                if (textureVertexIndices.size() > i || normalIndices.size() > i) {
                    sb.append("/");
                    if (textureVertexIndices.size() > i) {
                        sb.append(textureVertexIndices.get(i) + 1);
                    }
                    if (normalIndices.size() > i) {
                        sb.append("/").append(normalIndices.get(i) + 1);
                    }
                }
            }
            sb.append("\n");
            writer.append(sb.toString());
            writer.flush();
        }
    }
}