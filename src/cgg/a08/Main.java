package cgg.a08;

import static tools.Color.*;
import static tools.Functions.*;
import cgg.a02.*;
import cgg.a04.StratifiedSampling;
import cgg.a04.TexturedPhongMaterial;
import cgg.a05.Group;
import cgg.a05.Shape;
import cgg.a06.DiffusStreuung;
import cgg.a06.MaterialSpiegel;
import tools.ImageTexture;
import tools.Mat44;
import tools.Vertex;
import tools.Wavefront;
import tools.Wavefront.MeshData;
import tools.Wavefront.TriangleData;

import java.util.ArrayList;
import java.util.List;

import cgg.Image;
import cgg.a01.ConstantColor;

public class Main {

  public static void main(String[] args) {
    long startZeit = System.currentTimeMillis();
    int width = 900;
    int height = 900;

    //Licht und Kamera erstellen
    ArrayList<Lichtquelle> licht = new ArrayList<>();
    licht.add(new Richtungslichtquelle(vec3(10,-10,10), white));
    //licht.add(new Richtungslichtquelle(vec3(-10,-10,10), white));
    Mat44 transformationKamera = multiply(move(vec3(0,-20,15)),rotate(vec3(1,0,0),22), rotate(vec3(0,1,0),-7));
    Mat44 transformationKamera2 = move(vec3(5, 0, 20));
    Lochkamera kamera = new Lochkamera(Math.PI/4, width, height, transformationKamera2);

    //Farben fuer Materialien erstellen
    DiffusStreuung blau = new DiffusStreuung(new ConstantColor(blue), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung rot = new DiffusStreuung(new ConstantColor(red), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung gelb = new DiffusStreuung(new ConstantColor(yellow), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung gruen = new DiffusStreuung(new ConstantColor(color(0,0.52,0)), new ConstantColor(white), new ConstantColor(color(1000.0)));
    MaterialSpiegel spiegelMat = new MaterialSpiegel(new ConstantColor(white), new ConstantColor(white), new ConstantColor(color(1000.0)));

    List<MeshData> liste = Wavefront.loadMeshData("data/wilson-football/fb_low.obj");
    List<TriangleData> trData = new ArrayList<>();
    for(int i=0; i < liste.size(); i++) {
        trData.addAll(liste.get(i).triangles());
    }
    List<Triangle> tr = new ArrayList<>();
    for(int i=0; i < trData.size(); i++) {
        tr.add(new Triangle(trData.get(i).v0(), trData.get(i).v1(), trData.get(i).v2(), blau));
    }

    TriangleMesh trMesh = new TriangleMesh(tr, blau);

    //ein einzelnes Dreieck, zum ausprobieren
    Vertex v1 = new Vertex(vec3(1,-3,-10), vec3(0,0,1), vec2(0,0));
    Vertex v2 = new Vertex(vec3(6,-6,-10), vec3(0,0,1), vec2(1,1));
    Vertex v3 = new Vertex(vec3(10,-3,-10), vec3(0,0,1), vec2(0,1 ));
    TexturedPhongMaterial sterne = new TexturedPhongMaterial(new ImageTexture("data/sterne2.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    Triangle dreieck = new Triangle(v1, v2, v3, sterne);

    Group ball = new Group(move(9, 0,-1), trMesh);
    System.out.println(tr.get(0));
    //Group hurdle = new Group(scale(vec3(5)), dreieck); //Huerde nochmal ausprobieren
    Group welt = new Group(ball);
    
    Image image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht, white);
    //image.sampleStream(rayTracer); //setzt Pixelfarben, ohne StratifiedSampling
    image.sampleStream(new StratifiedSampling(rayTracer)); //setzt Pixelfarben, mit StratifiedSampling
    image.writePng("a08-image"); //erstellt Bild

    //Berechnung der gebrauchten Zeit
    long endZeit = System.currentTimeMillis();
    long dauer = (endZeit - startZeit)/1000;
    System.out.println("Zeit fuer die Berechnung des Bildes: " + dauer + " s");
  }

  private static Shape fillPlane(Shape s, int n) {
    double gap = 2.7;
    if(n == 0) {
      return s;
    }

    Shape p1 = fillPlane(s, n -1);
    Shape p2 = fillPlane(s, n -1);
    Shape p3 = fillPlane(s, n -1);
    Shape p4 = fillPlane(s, n -1);
    double size = p1.getBoundingBox().size().x() + gap;

    Group g1 = new Group(move(-size/2, 0, -size/2), p1);
    Group g2 = new Group(move(size/2, 0, -size/2), p2);
    Group g3 = new Group(move(-size/2, 0, size/2), p3);
    Group g4 = new Group(move(size/2, 0, size/2), p4);

    return new Group(g1, g2, g3, g4);
  }
}
