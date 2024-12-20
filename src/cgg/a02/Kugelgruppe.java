package cgg.a02;

import java.util.ArrayList;

import cgg.a05.Shape;
import tools.BoundingBox;

public class Kugelgruppe implements Shape{
    private ArrayList<Kugel> kugeln;

    public Kugelgruppe(ArrayList<Kugel> kugeln) {
        this.kugeln = kugeln;
    }

    /**
     * gibt die Farbe fuer ein bestimmtes Pixel zurueck
     * 
     * @param point Punkt zu dem die Farbe herausgefunden werden soll
     * @return Farbe des Pixels
     */
    public Hit intersect(Ray r) {
        Hit treffer = null; //nur fuer Initialisierung

        for(int j = 0; j < kugeln.size(); j++) {
            Hit h = kugeln.get(j).intersect(r);
            if(h == null)
                continue;
            if(treffer == null || h.getT() < treffer.getT()) { //Hit nur aendern wenn es Treffer gibt und dieser naeher an der Kamera ist (t moeglichst klein)
                treffer = h;
            }
        }
        return treffer;
    }

    @Override
    public BoundingBox getBoundingBox() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoundingBox'");
    }
}
