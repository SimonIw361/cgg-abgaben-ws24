package cgg.a05;

import static tools.Functions.invert;
import static tools.Functions.multiplyDirection;
import static tools.Functions.multiplyPoint;
import static tools.Functions.transpose;

import java.util.ArrayList;

import cgg.a02.Hit;
import cgg.a02.Kugel;
import cgg.a02.Ray;
import tools.Mat44;

public class Group implements Shape {
    private ArrayList<Kugel> elemente;
    private Mat44 transformation;

    public Group(ArrayList<Kugel> elemente, Mat44 transformation) {
        this.elemente = elemente;
        this.transformation = transformation;
    }

    /**
     * gibt die Farbe fuer ein bestimmtes Pixel zurueck
     * 
     * @param point Punkt zu dem die Farbe herausgefunden werden soll
     * @return Farbe des Pixels
     */
    public Hit intersect(Ray r) {
        Hit treffer = null; //nur fuer Initialisierung
        //Ray r2 = new Ray(multiplyPoint(transpose(invert(transformation)), r.getX0()), multiplyPoint(transpose(invert(transformation)), r.getRichtung()), r.gettMin(), r.gettMax());
        Ray r2 = new Ray(multiplyPoint(invert(transformation), r.getX0()), multiplyDirection(invert(transformation), r.getRichtung()), r.gettMin(), r.gettMax());
        for(int j = 0; j < elemente.size(); j++) {
            Hit h = elemente.get(j).intersect(r2);
            if(h == null)
                continue;
            if(treffer == null || h.getT() < treffer.getT()) { //Hit nur aendern wenn es Treffer gibt und dieser naeher an der Kamera ist (t moeglichst klein)
                treffer = h;
            }
        }
        Hit treffer2 = null;
        if(treffer != null){
            treffer2 = new Hit(treffer.getT(), multiplyPoint(transformation, treffer.getTrefferPunkt()), multiplyDirection(transpose(invert(transformation)), treffer.getNormalenVektor()), treffer.getMaterial(), treffer.getKugel(), treffer.getuv());
            //System.out.println(treffer2.getT());
        }
        return treffer2;
    }

}
