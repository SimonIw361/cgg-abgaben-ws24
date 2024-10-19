package cgg.a02;

import tools.*;
import static tools.Functions.*;

public class Lochkamera {
    private double winkelOeffnung;
    private int width;
    private int height;

    public Lochkamera(double winkelOeffnung, int width, int height) {
        this.winkelOeffnung = winkelOeffnung;
        this.width = width;
        this.height = height;
    }

    /**
     * berechnet einen Strahl von dieser Kamera aus
     * 
     * @param x x Position des Abtastpunkt
     * @param y y Position des Abtastpunkt
     * @return Strahl von Kamera (bei 0) zum Abtastpunkt
     */
    public Ray gibStrahl(double x, double y) {
        double xVec = x - (width/2);
        double yVec = y - (height/2);
        double zVec = -((width/2)/(Math.tan(winkelOeffnung/2)));
        Vec3 vec3= 	new Vec3(xVec, yVec, zVec);
        Ray ray = new Ray(Vec3.zero, normalize(vec3), 0, 100);

        return ray;
    }

}
