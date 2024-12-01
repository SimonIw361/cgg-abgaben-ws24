package cgg.a07;

import static tools.Functions.*;

import cgg.a02.Hit;
import cgg.a02.Ray;
import cgg.a04.Material;
import cgg.a05.Shape;
import tools.BoundingBox;
import tools.Vec3;

public class Ebene implements Shape{
    private final String art;
    private final double radius;
    private final double kantenlaenge;
    private Material material;

    /**
     * Konstruktor fuer quadratische Ebene
     */
    public Ebene(String arten, double s, Material m){
        this.material = m;
        switch (arten) {
            case "unbegrenzt":
                art = "unbegrenzt";
                radius = 0;
                kantenlaenge = 0;
                break;
            case "kreisrund":
                art = "kreisrund";
                radius = s;
                kantenlaenge = 0;
                break;
            case "quadratisch":
                art = "quadratisch";
                radius = 0;
                kantenlaenge = s;
                break;
            default:
                throw new IllegalArgumentException("Diese Art der Ebene gibt es nicht. Bitte unbegrenzt, kreisrund oder quadratisch angeben");
        }
    }

    public Hit intersect(Ray ray) {
        double t = (- ray.getX0().y())/(ray.getRichtung().y());
        
        Vec3 tPunkt = ray.gibStrahlPunkt(t);
        if(tPunkt == Vec3.zero){
            return null;
        }
        switch (art) {
            case "unbegrenzt":
                break;
            case "kreisrund":
                if(length(tPunkt) > radius){
                    return null;
                }
                break;
            case "quadratisch":
                if(tPunkt.x() > kantenlaenge/2 || tPunkt.x() < -(kantenlaenge/2) || tPunkt.z() > kantenlaenge/2 || tPunkt.z() < -(kantenlaenge/2)){
                    return null;
                }
                break;
        }
        
        

        //Normalenvektor aufstellen
        Vec3 n;
        if(ray.getX0().y()  > 0){
            n = vec3(0,1,0);
        }
        else {
            n = vec3(0,-1,0);
        }

        Hit treffer = new Hit(t, tPunkt, n, material, this, null);
        return treffer;
    }

    @Override
    public BoundingBox getBoundingBox() {
        BoundingBox box = null;
        switch (art) {
            case "unbegrenzt":
                box = BoundingBox.everything;
                break;
            case "kreisrund":
                box = new BoundingBox(vec3(0,0,0), vec3(radius, 0, 0));
                break;
            case "quadratisch":
                box = new BoundingBox(vec3(0,0,0), vec3(kantenlaenge, 0, 0));
                break;
        }
        return box;
    }
}
