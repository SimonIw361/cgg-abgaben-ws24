package cgg.a02;

import tools.*;
import static tools.Functions.*;

public class Lochkamera {
    private double winkelOeffnung;
    private int width;
    private int height;
    private Mat44 transformation;

    public Lochkamera(double winkelOeffnung, int width, int height, Mat44 transformation) {
        this.winkelOeffnung = winkelOeffnung;
        this.width = width;
        this.height = height;
        this.transformation = transformation;
    }

    /**
     * berechnet einen Strahl von dieser Kamera aus
     * 
     * @param x x Position des Abtastpunkt
     * @param y y Position des Abtastpunkt
     * @return Strahl von Kamera (bei 0) zum Abtastpunkt
     */
    public Ray gibStrahl(Vec2 point) {
        double x = point.u();
        double y = point.v();
        double xVec = x - (width/2);
        double yVec = y - (height/2);
        double zVec = -((width/2)/(Math.tan(winkelOeffnung/2)));

        Vec3 punktTransformiert = multiplyPoint(transformation, Vec3.zero); //Transformation des Punktes, von Nullpunkt ausgehend
        Vec3 vec3= 	new Vec3(xVec, yVec, zVec);
        Vec3 vec3transformiert = multiplyDirection(transformation, vec3); //Transformation der Richtung
        Ray ray = new Ray(punktTransformiert, normalize(vec3transformiert), 0, 100);
        return ray;
    }

}
