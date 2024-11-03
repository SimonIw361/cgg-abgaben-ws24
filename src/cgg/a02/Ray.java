package cgg.a02;

import tools.*;
import static tools.Functions.*;

public class Ray {
    private Vec3 x0;
    private Vec3 richtung;
    private double tMin;
    private double tMax;

    public Ray(Vec3 x0, Vec3 richtung, double tMin, double tMax) {
        this.x0 = x0;
        this.richtung = richtung;
        this.tMin = tMin;
        this.tMax = tMax;
    }

    /**
     * berechnet Punkt der um t vom Startpunkt entfernt ist
     * 
     * @param t Entfernung des gesuchten Punkts vom Startpunkt 
     * @return Punkt auf dem Strahl
     */
    public Vec3 gibStrahlPunkt(double t) {
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
    private boolean tGueltig(double t) {
        if(t >= tMin && t <= tMax) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return Startpunkt des Strahls
     */
    public Vec3 getX0() {
        return x0;
    }

    /**
     * @return die Richtung als Vec3
     */
    public Vec3 getRichtung() {
        return richtung;
    }

    /**
     * @return untere Grenze fuer t
     */
    public double gettMin(){
        return tMin;
    }

    /**
     * @return obere Grenze fuer t
     */
    public double gettMax(){
        return tMax;
    }

    /**
     * gibt Ray als String zurueck
     * 
     * @return Ray-Objekt als String
     */
    @Override
    public String toString() {
        String info = "Ray[orign=" + x0.toString() + ", direction=" + richtung.toString() + "]";
        return info;
    }

}
