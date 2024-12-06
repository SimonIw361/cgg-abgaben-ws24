package cgg.a05;

import static tools.Functions.invert;
import static tools.Functions.move;
import static tools.Functions.multiplyDirection;
import static tools.Functions.multiplyPoint;
import static tools.Functions.transpose;

import java.util.ArrayList;

import cgg.a02.Hit;
import cgg.a02.Ray;
import tools.BoundingBox;
import tools.Mat44;
import tools.Vec3;

public class Group implements Shape {
    private ArrayList<Shape> elemente;
    private BoundingBox box;
    private Mat44 transformation;
    private Mat44 transformationInvert;
    private Mat44 transformationInvertTransponse;

    public Group(Mat44 transformation, Shape... shapes) {
        this.transformation = transformation;
        //Matrizen berechnen, damit Berechnung nur einmal noetig ist
        this.transformationInvert = invert(this.transformation);
        this.transformationInvertTransponse = transpose(this.transformationInvert);

        elemente = new ArrayList<>();
        for(int i=0; i < shapes.length; i++) {
            elemente.add(shapes[i]);
        }
        box = BoundingBox.empty;
        for(int i= 0; i < elemente.size(); i++) {
            BoundingBox b = elemente.get(i).getBoundingBox().transform(transformation);
            box = box.extend(b);
        }
    }

    public Group(Shape... shapes) {
        this(move(Vec3.zero), shapes);
    }

    public Group(ArrayList<Shape> shapes) {
        //es soll keine Transformation stattfinden
        this.transformation = move(Vec3.zero);
        this.transformationInvert = transformation;
        this.transformationInvertTransponse = transformation;

        elemente = shapes;
        box = BoundingBox.empty;
        for(int i= 0; i < elemente.size(); i++) {
            BoundingBox b = elemente.get(i).getBoundingBox();
            box = box.extend(b);
        }
    }

    /**
     * gibt die Farbe fuer ein bestimmtes Pixel zurueck
     * 
     * @param point Punkt zu dem die Farbe herausgefunden werden soll
     * @return Farbe des Pixels
     */
    public Hit intersect(Ray r) {
        Hit treffer = null; //nur fuer Initialisierung
        Ray r2 = new Ray(multiplyPoint(transformationInvert, r.getX0()), multiplyDirection(transformationInvert, r.getRichtung()), r.gettMin(), r.gettMax());
        
        //BoundingBox ist schon in transformiert, deshalb hier mir r schneiden
        if(box.intersect(r) == false) {
            return null;
        }
        for(int j = 0; j < elemente.size(); j++) {
            //Elemente sind nicht transformiert, deshalb hier mit r2 schneiden
            Hit h = elemente.get(j).intersect(r2);
            if(h == null)
                continue;
            if(treffer == null || h.getT() < treffer.getT()) { //Hit nur aendern wenn es Treffer gibt und dieser naeher an der Kamera ist (t moeglichst klein)
                treffer = h;
            }
        }
        
        Hit trefferTransformiert = null;
        if(treffer != null){
            trefferTransformiert = new Hit(treffer.getT(), multiplyPoint(transformation, treffer.getTrefferPunkt()), multiplyDirection(transformationInvertTransponse, treffer.getNormalenVektor()), treffer.getMaterial(), treffer.getKugel(), treffer.getuv());
        }
        return trefferTransformiert;
    }

    public BoundingBox getBoundingBox() {
        return box;
    }
}
