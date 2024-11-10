package cgg.a02;

import tools.*;
import static tools.Functions.*;

import cgg.a04.Material;

public class Hit {
    private double t;
    private Vec3 trefferPunkt;
    private Vec3 normalenVektor;
    private Material material;
    private Kugel kugel; //Kugel auf welcher der Trefferpunkt liegt, fuer Schattenberechnung benoetigt
    private Vec2 uv;

    public Hit(double t, Vec3 trefferPunkt, Vec3 normalenVektor, Material material, Kugel kugel, Vec2 uv) {
        this.t = t;
        this.trefferPunkt = trefferPunkt;
        if(normalenVektor != null) {
            this.normalenVektor = normalize(normalenVektor);
        }
        this.material = material;
        this.kugel = kugel;
        this.uv = uv;
    }

    /**
     * @return Vektor der die Werte u und v (Texturkoordinaten) darstellt
     */
    public Vec2 getuv() {
        return uv;
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
    public Material getMaterial(){
        return material;
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
