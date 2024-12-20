package cgg.a08;

import static tools.Color.*;
import static tools.Functions.*;
import cgg.a02.*;
import cgg.a04.StratifiedSampling;
import cgg.a04.TexturedPhongMaterial;
import cgg.a05.Group;
import cgg.a05.Shape;
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

  //TODO Textur fuer Dreiecksnetze geht noch nicht richtig
  public static void main(String[] args) {
    long startZeit = System.currentTimeMillis();
    int width = 900;
    int height = 900;

    // Licht und Kamera erstellen
    ArrayList<Lichtquelle> licht = new ArrayList<>();
    licht.add(new Richtungslichtquelle(vec3(10, -10, 10), white));
    // licht.add(new Richtungslichtquelle(vec3(-10,-10,10), white));
    Mat44 transformationKamera = multiply(move(vec3(0, -20, 15)), rotate(vec3(1, 0, 0), 22), rotate(vec3(0, 1, 0), -7));
    Mat44 transformationKamera2 = move(vec3(0));
    Lochkamera kamera = new Lochkamera(Math.PI / 4, width, height, transformationKamera2);

    //List<MeshData> liste = Wavefront.loadMeshData("data/wilson-football/fb_low.obj"); //Football
    //TexturedPhongMaterial bild = new TexturedPhongMaterial(new ImageTexture("data/wilson-football/fb_low_lambert2SG_Roughness.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    //List<MeshData> liste = Wavefront.loadMeshData("data/Gondel/model.obj"); // Gondel
    // List<MeshData> liste = Wavefront.loadMeshData("data/Haus/source/low-poly-home.obj"); //Haus
    // List<MeshData> liste = Wavefront.loadMeshData("data/Baum/Canstar Christmas Tree.obj"); //Weihnachtsbaum
    List<MeshData> liste = Wavefront.loadMeshData("data/Flugzeug2/Airplane_2.obj"); //Flugzeug
    TexturedPhongMaterial bild = new TexturedPhongMaterial(new ImageTexture("data/Flugzeug2/None_albedo.jpeg"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    
    //ab hier Ball erstellen
    List<TriangleData> trData = new ArrayList<>();
    for(int i=0; i < liste.size(); i++) {
        trData.addAll(liste.get(i).triangles());
    }
    List<Triangle> tr = new ArrayList<>();
    for(int i=0; i < trData.size(); i++) {
        tr.add(new Triangle(trData.get(i).v0(), trData.get(i).v1(), trData.get(i).v2()));
        //System.out.println("v0: " + trData.get(i).v0().uv() + " v1: " + trData.get(i).v1().uv() + "v2: " + trData.get(i).v2().uv());
    }

    TriangleMesh trMesh = new TriangleMesh(tr, bild);
    Group ball = new Group(multiply(move(0, 0,-8), rotate(vec3(0,1,0), -40),rotate(vec3(0,0,1), 180)), trMesh);
    //bis hier

    Group huerde = huerdeErstellen();
    Group welt = new Group(huerde, ball);

    Image image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht, white);
    // image.sampleStream(rayTracer); //setzt Pixelfarben, ohne StratifiedSampling
    image.sampleStream(new StratifiedSampling(rayTracer)); // setzt Pixelfarben, mit StratifiedSampling
    image.writePng("a08-image"); // erstellt Bild

    // Berechnung der gebrauchten Zeit
    long endZeit = System.currentTimeMillis();
    long dauer = (endZeit - startZeit) / 1000;
    System.out.println("Zeit fuer die Berechnung des Bildes: " + dauer + " s");
  }

  private static Group huerdeErstellen(){
    List<MeshData> liste = Wavefront.loadMeshData("data/Huerde/Hurdle.obj"); //Huerde

    List<Triangle> tr = new ArrayList<>();
    //Dreiecke sind in 9 Gruppen sortiert die unterschiedliche Farbe haben
    for (int i = 0; i < liste.size(); i++) {
      List<TriangleData> data = liste.get(i).triangles();
      //Dreiecke neu erstellen und jeweilige Farbe auch hinzufuegen
      for(int j=0; j < data.size(); j++){
        Vertex v0 = new Vertex(data.get(j).v0().position(), data.get(j).v0().normal(), data.get(j).v0().uv(), liste.get(i).material().kd());
        Vertex v1 = new Vertex(data.get(j).v1().position(), data.get(j).v1().normal(), data.get(j).v1().uv(), liste.get(i).material().kd());
        Vertex v2 = new Vertex(data.get(j).v2().position(), data.get(j).v2().normal(), data.get(j).v2().uv(), liste.get(i).material().kd());
        Triangle d = new Triangle(v0, v1, v2);
        tr.add(d);
      }
    }

    //als Material kann nur null uebergeben werden, weil jedes Dreieck ueber Vertex eigene Farbe hat
    TriangleMesh trMesh = new TriangleMesh(tr, null);

    Group huerde = new Group(multiply(scale(vec3(0.1)),rotate(vec3(0,0,1), 180),move(1007622, 31365, -2378453)), trMesh);
    return huerde;
  }


  private static Shape fillPlane(Shape s, int n) {
    double gap = 2.7;
    if (n == 0) {
      return s;
    }

    Shape p1 = fillPlane(s, n - 1);
    Shape p2 = fillPlane(s, n - 1);
    Shape p3 = fillPlane(s, n - 1);
    Shape p4 = fillPlane(s, n - 1);
    double size = p1.getBoundingBox().size().x() + gap;

    Group g1 = new Group(move(-size / 2, 0, -size / 2), p1);
    Group g2 = new Group(move(size / 2, 0, -size / 2), p2);
    Group g3 = new Group(move(-size / 2, 0, size / 2), p3);
    Group g4 = new Group(move(size / 2, 0, size / 2), p4);

    return new Group(g1, g2, g3, g4);
  }
}
