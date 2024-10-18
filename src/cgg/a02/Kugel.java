package cgg.a02;

import tools.*;
import static tools.Functions.*;

public class Kugel {
    private Vec3 mittelpunkt;
    private double radius;

    public Kugel(Vec3 mittelpunkt, double radius){
        this.mittelpunkt = mittelpunkt;
        this.radius = radius;
    }

    public Hit intersect(Ray r) {
        Vec3 x0c = subtract(r.getX0(), mittelpunkt);
        double a = r.getRichtung().u() * r.getRichtung().u() + r.getRichtung().v() * r.getRichtung().v() + r.getRichtung().w() * r.getRichtung().w(); //length(r.getRichtung()) * length(r.getRichtung());
        double b = 2 * (x0c.u() * r.getRichtung().u() + x0c.v() * r.getRichtung().v() + x0c.w() * r.getRichtung().w()); //length(multiply(2, multiply(x0c, r.getRichtung())));
        double c = (x0c.u() * x0c.u() + x0c.v() * x0c.v() + x0c.w() * x0c.w()) - radius * radius; //length(multiply(subtract(r.getX0(), mittelpunkt),x0c)) - (radius * radius);
        double t1 = (-b + Math.sqrt(b* b - 4 * a * c))/(2 * a);
        double t2 = (-b - Math.sqrt(b* b - 4 * a * c))/(2 * a);

        Hit hit = new Hit(t1, r.gibStrahlPunkt(t1), divide(subtract(r.gibStrahlPunkt(t1), mittelpunkt),radius) , Color.black);
        Hit hit2 = new Hit(t2, r.gibStrahlPunkt(t2), divide(subtract(r.gibStrahlPunkt(t2), mittelpunkt),radius) , Color.black);
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
}
