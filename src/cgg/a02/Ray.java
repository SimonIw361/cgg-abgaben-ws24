package cgg.a02;

import static tools.Functions.*;

import tools.*;

public class Ray {
    private Vec3 x0;
    private Vec3 richtung;
    private int tMin;
    private int tMax;

    public Ray(Vec3 x0, Vec3 richtung, int tMin, int tMax) {
        this.x0 = x0;
        this.richtung = richtung;
        this.tMin = tMin;
        this.tMax = tMax;
    }

    public Vec3 gibStrahlPunkt(int t) {
        if(!tGueltig(t)) {
            return Vec3.zero;
        }
        Vec3 punkt = add(x0, multiply(t, richtung));
        return punkt;
    }

    /**
     * ueberprueft ob der Wert t innerhalb der Grenzen von
     * tMin und tMax liegt
     * 
     * @param t Parameter t der ueberprueft werden soll
     * @return boolescher Wert ob t gueltig ist
     */
    private boolean tGueltig(int t) {
        if(t >= tMin && t < tMax) {
            return true;
        }
        else{
            return false;
        }
    }

}
