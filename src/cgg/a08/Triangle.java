package cgg.a08;

import static tools.Color.*;
import static tools.Functions.*;

import cgg.a02.Hit;
import cgg.a02.Ray;
import cgg.a04.PhongMaterial;
import cgg.a05.Shape;
import tools.BoundingBox;
import tools.Vec2;
import tools.Vec3;
import tools.Vertex;

public class Triangle implements Shape {
    private Vertex v0;
    private Vertex v1;
    private Vertex v2;
    private BoundingBox box;
    private Vec3 normalenVektor;

    public Triangle(Vertex v0, Vertex v1, Vertex v2) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        box = BoundingBox.around(v0.position(), v1.position(), v2.position());
        normalenVektor = normalize(cross(subtract(v1.position(), v0.position()),subtract(v2.position(), v0.position())));
    }

    public Hit intersect(Ray ray) {
        if(!box.intersect(ray)) {
            return null;
        }

        //Schnittpunkt mit kompletter Ebene des Dreiecks berechnen
        double t = dot(subtract(v0.position(),ray.getX0()), normalenVektor)/dot(ray.getRichtung(), normalenVektor);
        //double t = dot(subtract(v1.position(),ray.getX0()), v1.normal())/dot(ray.getRichtung(), v1.normal());
        Vec3 p = ray.gibStrahlPunkt(t);

        double flaeche012 = berechneFlaeche(v0.position(), v1.position(), v2.position());
        double flaeche12P = berechneFlaeche(v1.position(), v2.position(), p);
        double flaeche20P = berechneFlaeche(v2.position(), v0.position(), p);
        double flaeche01P = berechneFlaeche(v0.position(), v1.position(), p);

        double u = flaeche12P/flaeche012;
        double v = flaeche20P/flaeche012;
        double w = flaeche01P/flaeche012;
        Vec3 uvw = vec3(u,v,w);
        Vec3 interNormale = normalize(interplolate(v0.normal(), v1.normal(), v2.normal(), uvw));
        Vec2 interTextur = interplolate(v0.uv(), v1.uv(), v2.uv(), uvw);
        //interTextur = add(interTextur, vec2(0.,-0.3));
        
        PhongMaterial farbe = null;
        if(v0.color() != magenta) { //wenn Farbe von dreieck geaendert wurde, diese Farbe als Material nehmen (magenta ist default Farbe bei Dreieck)
            farbe = new PhongMaterial(v0.color(), v0.color(), 1000);
        }

        if(almostEqual(u+v+w, 1.0) && u <= 1 && u >= 0 && v <= 1 && v >= 0 && w <= 1 && w >= 0){
            //System.out.println("1" + v0.uv() + " " + v1.uv() + " " + v2.uv() + " " + uvw);
            //System.out.println("2" + interTextur);
            //System.out.println("v0: " + v0.uv() + " v1: " + v1.uv() + "v2: " +v2.uv());
            //System.out.println(v0.color());
            //System.out.println("Baryzentrische Gewichte: u=" + u + ", v=" + v + ", w=" + w);
            Hit h =  new Hit(t, ray.gibStrahlPunkt(t), interNormale, farbe, this, interTextur); //Material fuer Dreieck wird in triangleMesh gesetzt
            
            return h;
        }
        else {
            return null; //kein Treffer
        }
    }

    public BoundingBox getBoundingBox() {
       return box; 
    }

    private double berechneFlaeche(Vec3 a, Vec3 b, Vec3 c){
        //Vec3 kreuzprodukt = cross(subtract(a, c),subtract(b, c));
        Vec3 kreuzprodukt = cross(subtract(b, a),subtract(c, a));
        double flaeche = length(kreuzprodukt)/2;
        return flaeche;
    }

    public String toString(){
        String info = "Triangle[v0=" + v0.toString() + " v1=" + v1.toString() + " v2=" + v2.toString() + /* " Material: " + material + */"]";
        return info;
    }

}
