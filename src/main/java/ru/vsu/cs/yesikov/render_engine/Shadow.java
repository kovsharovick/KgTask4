package ru.vsu.cs.yesikov.render_engine;

import ru.vsu.cs.yesikov.math.Vector3f;
import ru.vsu.cs.yesikov.math.BarycentricCoordinates;
public class Shadow {
    float c1;
    float c2;
    float c3;

    public Shadow(Vector3f[] normals, Vector3f target, Vector3f position){
        this.c1 = calculateShade(normals[0], target, position);
        this.c2 = calculateShade(normals[1], target, position);
        this.c3 = calculateShade(normals[2], target, position);
    }

    private float calculateShade(
            Vector3f normal,
            Vector3f target,
            Vector3f position) {
        Vector3f v = Vector3f.getNewSub(target, position);
        float cosine = Vector3f.dot(Vector3f.getNewNormalize(normal), Vector3f.getNewNormalize(v));
        return Float.max(0.8f, Math.abs(cosine));
    }

    public float calculateShadeCoefficients(BarycentricCoordinates bCoordinates){
        return bCoordinates.getU() * c1 + bCoordinates.getV() * c2 + bCoordinates.getW() * c3;
    }
}
