package cgg.a02;

import tools.*;
import static tools.Functions.*;

public class Hit {
    private double t;
    private Vec3 trefferPunkt;
    private Vec3 normalenVektor;
    private Color farbeOberflaeche;

    public Hit(double t, Vec3 trefferPunkt, Vec3 normalenVektor, Color farbeOberflaeche) {
        this.t = t;
        this.trefferPunkt = trefferPunkt;
        if(normalenVektor != null) {
            this.normalenVektor = normalize(normalenVektor);
        }
        this.farbeOberflaeche = farbeOberflaeche;
    }

    /**
     * @return Wert fuer t
     */
    public double getT() {
        return t;
    }

    /**
     * @return Normalenvektor als Vec3
     */
    public Vec3 getNormalenVektor(){
        return normalenVektor;
    }

    /**
     * @return Farbe von dem Trefferpunkt
     */
    public Color getfarbeOberflaeche(){
        return farbeOberflaeche;
    }

    /**
     * @return Hit-Objekt als String
     */
    @Override
    public String toString(){
        String info = "Hit[t=" + t + ", point=" + trefferPunkt.toString() + ", normal=" + normalenVektor.toString() + "]";
        return info;
    }

}
