package cgg.a05;

import static tools.Functions.invert;
import static tools.Functions.move;
import static tools.Functions.multiplyDirection;
import static tools.Functions.multiplyPoint;
import static tools.Functions.transpose;
import static tools.Functions.vec3;

import java.util.ArrayList;

import cgg.a02.Hit;
import cgg.a02.Ray;
import tools.BoundingBox;
import tools.Mat44;
import tools.Vec3;

public class Group implements Shape {
    private ArrayList<Shape> elemente;
    private BoundingBox box;
    private BoundingBox boxTransformiert;
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
            box = box.extend(elemente.get(i).getBoundingBox().transform(transformation));
        }
        boxTransformiert = box;//.transform(transformationInvert);
        System.out.println(box.toString());
    }

    public Group(Shape... shapes) {
        this(move(Vec3.zero), shapes);
        System.out.println(box.equals(boxTransformiert));
        System.out.println(box.toString() + " " + boxTransformiert + transformation);
    }

    public Group(ArrayList<Shape> shapes) {
        //es soll keine Transformation stattfinden
        this.transformation = move(Vec3.zero);
        this.transformationInvert = transformation;
        this.transformationInvertTransponse = transformation;

        elemente = shapes;
        box = BoundingBox.empty;
        for(int i= 0; i < elemente.size(); i++) {
            box = box.extend(elemente.get(i).getBoundingBox().transform(transformation));
        }
        boxTransformiert = box;//.transform(transformation);
        System.out.println(box.equals(boxTransformiert));
        System.out.println(box.toString() + " " + boxTransformiert + transformation);
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
        
        if(boxTransformiert.intersect(r) == false) {
            return null;
        }
        if(box.intersect(r) == false) {
            //return null;
        }
        for(int j = 0; j < elemente.size(); j++) {
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
        //box = box.transform(transformationInvert);
        //return boxTransformiert;
        return box;
    }
}
