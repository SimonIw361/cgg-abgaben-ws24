package cgg.a02;

import tools.*;
import static tools.Functions.*;

public class Hit {
    private double t;
    private Vec3 trefferPunkt;
    private Vec3 normalenVektor;
    private Color farbeOberflaeche;
    private Kugel kugel; //Kugel auf welcher der Trefferpunkt liegt, fuer Schattenberechnung benoetigt

    public Hit(double t, Vec3 trefferPunkt, Vec3 normalenVektor, Color farbeOberflaeche, Kugel kugel) {
        this.t = t;
        this.trefferPunkt = trefferPunkt;
        if(normalenVektor != null) {
            this.normalenVektor = normalize(normalenVektor);
        }
        this.farbeOberflaeche = farbeOberflaeche;
        this.kugel = kugel;
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
     * @return Trefferpunkt als Vec3
     */
    public Vec3 getTrefferPunkt(){
        return trefferPunkt;
    }

    /**
     * @return Farbe von dem Trefferpunkt
     */
    public Color getfarbeOberflaeche(){
        return farbeOberflaeche;
    }

    /**
     * 
     * @return gibt das Objekt (Kugel) zurueck auf welcher der Treffer liegt
     */
    public Kugel getKugel()  {
        return kugel;
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
