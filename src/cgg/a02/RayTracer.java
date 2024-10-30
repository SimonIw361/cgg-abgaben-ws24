package cgg.a02;

import static tools.Color.black;

import java.util.ArrayList;

import tools.Color;
import tools.Sampler;
import tools.Vec2;

public record RayTracer(Lochkamera camera, Kugelgruppe kugeln, ArrayList<Lichtquelle> lichtquelle) implements Sampler {

    /**
     * gibt die Farbe fuer ein bestimmtes Pixel zurueck
     * 
     * @param point Punkt zu dem die Farbe herausgefunden werden soll
     * @return Farbe des Pixels
     */
    public Color getColor(Vec2 point) {
        Ray r = camera.gibStrahl(point);
        Hit treffer = kugeln.intersect(r);

        if(treffer == null) {
            return black; //Standardhintergrundfarbe bei keinem Treffer
        }
        else {
            return shade(treffer, r);
        }
    }


    /**
     * berechnet Schattierung fuer gegebenen Treffer mit Phong Methode
     * 
     * @param hit der Trefferpunkt
     * @return Farbe fuer diesen Punkt
     */
    public static Color shade(Hit hit, Ray r) {
        
        return hit.getfarbeOberflaeche();
    }

}
