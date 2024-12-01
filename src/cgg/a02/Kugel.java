package cgg.a02;

import tools.*;

import static tools.Functions.*;

import cgg.a04.Material;
import cgg.a05.Shape;

public class Kugel implements Shape {
    private Vec3 mittelpunkt;
    private double radius;
    private Material material;

    public Kugel(Vec3 mittelpunkt, double radius, Material material){
        this.mittelpunkt = mittelpunkt;
        this.radius = radius;
        this.material = material;
    }

    /**
     * berechnet naechsten Schnittpunkt von einem Strahl mit einer Kugel
     * 
     * @param r Strahl der Kugel evtl trifft
     * @return Trefferpunkt, der am naechsten ist
     */
    public Hit intersect(Ray r) {
        // nach Formel: d * t2 + 2 * (x0 -c) * d * t + (x0 -c)2 -r2 = 0
        Vec3 x0c = subtract(r.getX0(), mittelpunkt); //x0 - c (beides Vektoren)
        double a = squaredLength(r.getRichtung()); //Vektor d, alles einzeln multiplizieren und dann addieren
        double b = 2 * coordSum(multiply(x0c, r.getRichtung())); //x0c und d einzeln multiplizieren, aus diesem Vektor dann noch mit coordSum eine Zahl machen
        double c = squaredLength(x0c) - radius * radius; //Vektor x0c einzeln multiplizieren und dann addieren, davon r*r abziehen
        double t1 = (-b + Math.sqrt(b* b - 4 * a * c))/(2 * a); //ABC-Formel mit +
        double t2 = (-b - Math.sqrt(b* b - 4 * a * c))/(2 * a); //ABC-Formel mit -

        Vec3 punkt1 = r.gibStrahlPunkt(t1);
        Vec3 punkt2 = r.gibStrahlPunkt(t2);
        //Punkte x,y und z auf der Kugel berechnen, fuer Berechnung von u und v benoetigt
        Vec3 kugelPunkt1 = subtract(punkt1, mittelpunkt);
        Vec3 kugelPunkt2 = subtract(punkt2, mittelpunkt);

        //uv berechnen (Kugelkoordinaten zu Texturkoordinaten),
        //jeweils fuer beide moeglichen Trefferpunkte, Auswahl von Trefferpunkt erst spaeter
        //nach Formel: u = (arctan2((x/z) + pi))/(2pi)
        double u1 = (Math.atan2(kugelPunkt1.w(),kugelPunkt1.u()) + Math.PI)/(2 * Math.PI);
        double u2 = (Math.atan2(kugelPunkt2.w(),kugelPunkt2.u()) + Math.PI)/(2 * Math.PI);
        //nach Formel: v = (pi - arccos(y/radius))/(pi)
        double v1 = (Math.PI - Math.acos(kugelPunkt1.v()/radius))/(Math.PI);
        double v2 = (Math.PI - Math.acos(kugelPunkt2.v()/radius))/(Math.PI);
        Vec2 uv1 = vec2(u1,v1);
        Vec2 uv2 = vec2(u2,v2);

        Hit hit = new Hit(t1, punkt1, divide(subtract(r.gibStrahlPunkt(t1), mittelpunkt),radius) , material, this, uv1); //Trefferpunkt fuer t1 berechnen
        Hit hit2 = new Hit(t2, punkt2, divide(subtract(r.gibStrahlPunkt(t2), mittelpunkt),radius) , material, this, uv2);
        if(b* b - 4 * a * c < 0) { //Wurzel ist negativ, keine Loesung
            return null;
        }

        if(!in(r.gettMin(), r.gettMax(), t1) && !in(r.gettMin(), r.gettMax(), t2)) { //t1 und t2 sind außerhab des Bereiches
            return null;
        }
        else if(!in(r.gettMin(), r.gettMax(), t1) && in(r.gettMin(), r.gettMax(), t2)) { //nur t1 ist außerhalb, t2 zurueckgeben
            return hit2; 
        }
        else if(in(r.gettMin(), r.gettMax(), t1) && !in(r.gettMin(), r.gettMax(), t2)) { //nur t2 ist außerhalb, t1 zurueckgeben
            return hit; 
        }
        else if(t1 < t2) { //beide sind im Bereich, kleineren zurueckgeben
            return hit;
        }
        else {
            return hit2;
        }
    }

    /**
     * @return gibt die Farbe zurück
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param obj zu vergleichende Kugel
     * @return true wenn Kugeln gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        Kugel kug = (Kugel) obj;
        if(this.mittelpunkt.u() == kug.mittelpunkt.u() && this.mittelpunkt.v() == kug.mittelpunkt.v() && this.mittelpunkt.w() == kug.mittelpunkt.w()) {
            if(this.radius == kug.radius){
                //if(this.farbe.r() == kug.farbe.r() && this.farbe.g() == kug.farbe.g() && this.farbe.b() == kug.farbe.b()) {
                    return true;
                //}
            }
        }
        return false;
    }

    @Override
    public BoundingBox getBoundingBox() {
        BoundingBox box = new BoundingBox(mittelpunkt, vec3(mittelpunkt.x() -radius, mittelpunkt.y(), mittelpunkt.z()));
        return box;
    }
}
