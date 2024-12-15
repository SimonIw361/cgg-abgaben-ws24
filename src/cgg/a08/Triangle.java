package cgg.a08;

import static tools.Functions.*;

import cgg.a02.Hit;
import cgg.a02.Ray;
import cgg.a04.Material;
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
    private Material material;

    public Triangle(Vertex v0, Vertex v1, Vertex v2, Material m) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        box = BoundingBox.around(v0.position(), v1.position(), v2.position());
        normalenVektor = normalize(cross(subtract(v2.position(), v0.position()),subtract(v1.position(), v0.position())));
        this.material = m;
    }

    public Hit intersect(Ray ray) {
        if(!box.intersect(ray)) {
            return null;
        }

        //Schnittpunkt mit kompletter Ebene des Dreiecks berechnen
        double t = dot(subtract(v0.position(),ray.getX0()), normalenVektor)/dot(ray.getRichtung(), normalenVektor);
        Vec3 p = ray.gibStrahlPunkt(t);

        double flaeche012 = berechneFlaeche(v0.position(), v1.position(), v2.position());
        double flaeche12P = berechneFlaeche(v1.position(), v2.position(), p);
        double flaeche20P = berechneFlaeche(v2.position(), v0.position(), p);
        double flaeche01P = berechneFlaeche(v0.position(), v1.position(), p);

        double u = flaeche12P/flaeche012;
        double v = flaeche20P/flaeche012;
        double w = flaeche01P/flaeche012;
        Vec3 uvw = vec3(u,v,w);
        Vec3 interNormale = interplolate(v0.normal(), v1.normal(), v2.normal(), uvw);
        Vec2 interTextur = interplolate(v0.uv(), v1.uv(), v2.uv(), uvw);

        if(almostEqual(u+v+w, 1) && u <= 1 && u >= 0 && v <= 1 && v >= 0 && w <= 1 && w >= 0){
            return new Hit(t, ray.gibStrahlPunkt(t), interNormale, material, this, interTextur);
        }
        else {
            return null; //kein Treffer
        }
    }

    public BoundingBox getBoundingBox() {
       return box; 
    }

    private double berechneFlaeche(Vec3 a, Vec3 b, Vec3 c){
        Vec3 kreuzprodukt = cross(subtract(a, c),subtract(b, c));
        double flaeche = length(kreuzprodukt)/2;
        return flaeche;
    }

    public String toString(){
        String info = "Triangle[v0=" + v0.toString() + " v1=" + v1.toString() + " v2=" + v2.toString() + /* " Material: " + material + */"]";
        return info;
    }

}
