package cgg.a02;

import tools.*;

import static tools.Functions.*;

public class Kugel {
    private Vec3 mittelpunkt;
    private double radius;
    private Color farbe;

    public Kugel(Vec3 mittelpunkt, double radius, Color farbe){
        this.mittelpunkt = mittelpunkt;
        this.radius = radius;
        this.farbe = farbe;
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

        Hit hit = new Hit(t1, r.gibStrahlPunkt(t1), divide(subtract(r.gibStrahlPunkt(t1), mittelpunkt),radius) , farbe, this); //Trefferpunkt fuer t1 berechnen
        Hit hit2 = new Hit(t2, r.gibStrahlPunkt(t2), divide(subtract(r.gibStrahlPunkt(t2), mittelpunkt),radius) , farbe, this);
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
    public Color getFarbe() {
        return farbe;
    }

    /**
     * 
     */
    @Override
    public boolean equals(Object obj) {
        Kugel kug = (Kugel) obj;
        if(this.mittelpunkt.u() == kug.mittelpunkt.u() && this.mittelpunkt.v() == kug.mittelpunkt.v() && this.mittelpunkt.w() == kug.mittelpunkt.w()) {
            if(this.radius == kug.radius){
                if(this.farbe.r() == kug.farbe.r() && this.farbe.g() == kug.farbe.g() && this.farbe.b() == kug.farbe.b()) {
                    return true;
                }
            }
        }
        return false;
    }
}
