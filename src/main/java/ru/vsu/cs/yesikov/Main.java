package ru.vsu.cs.yesikov;

import ru.vsu.cs.yesikov.math.Vector4f;

public class Main {
    public static void main(String[] args) {
        Vector4f v4 = new Vector4f(new float[]{4, 3, 2, 1});
        v4.normalize();
        for (float i : v4.getValues()) {
            System.out.println(i);
        }
        //Simple3DViewer.main(args);
    }
}
