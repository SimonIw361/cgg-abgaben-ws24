package cgg.a08;

import static tools.Color.*;
import static tools.Functions.*;
import cgg.a02.*;
import cgg.a04.StratifiedSampling;
import cgg.a04.TexturedPhongMaterial;
import cgg.a05.Group;
import cgg.a05.Shape;
import tools.Color;
import tools.ImageTexture;
import tools.Mat44;
import tools.Vec2;
import tools.Vertex;
import tools.Wavefront;
import tools.Wavefront.MaterialData;
import tools.Wavefront.MeshData;
import tools.Wavefront.TriangleData;

import java.util.ArrayList;
import java.util.List;

import cgg.Image;
import cgg.a01.ConstantColor;

public class Main {

  // TODO Textur fuer Dreiecksnetze geht noch nicht richtig
  // tauschen von u und v bei Texturkoordintane aendet hier nichts
  // das ist glaube ich falsch, direkte Angabe von u und v auch nicht moeglich,
  // wie ist referenz zu bild!?
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

    // List<MeshData> liste = Wavefront.loadMeshData("data/Gondel/model.obj"); //
    // Gondel
    // List<MeshData> liste =
    // Wavefront.loadMeshData("data/Haus/source/low-poly-home.obj"); //Haus
    // List<MeshData> liste = Wavefront.loadMeshData("data/Baum/Canstar Christmas
    // Tree.obj"); //Weihnachtsbaum
    List<MeshData> liste = Wavefront.loadMeshData("data/Flughafen/airport-track-curve/Airport_trackCurve1.obj");
    TexturedPhongMaterial bild = new TexturedPhongMaterial(
        new ImageTexture("data/Flughafen/airport-track-curve/Texture/Texture_airport256b.png"),
        new ConstantColor(white), new ConstantColor(color(1000.0)));
    TexturedPhongMaterial sterne = new TexturedPhongMaterial(new ImageTexture("data/sterne2.png"),
        new ConstantColor(white), new ConstantColor(color(1000.0)));

    // ab hier Ball erstellen
    List<TriangleData> trData = new ArrayList<>();
    List<MaterialData> ma = new ArrayList<>();
    for (int i = 0; i < liste.size(); i++) {
      MaterialData m = liste.get(i).material();
      //if(liste.get(i).materialName().equals("AIRPORT")){
        trData.addAll(liste.get(i).triangles());
      //}
      
      System.out.println(liste.get(i).materialName());
      ma.add(liste.get(i).material());
    }
    List<Triangle> tr = new ArrayList<>();
    for (int i = 0; i < trData.size(); i++) {
      tr.add(new Triangle(trData.get(i).v0(), trData.get(i).v1(),
       trData.get(i).v2()));
      // Vertex v0 = new Vertex(trData.get(i).v0().position(), trData.get(i).v0().normal(), vec2(0.5, 0.5));
      // Vertex v1 = new Vertex(trData.get(i).v1().position(), trData.get(i).v1().normal(), vec2(0.5, 0.5));
      // Vertex v2 = new Vertex(trData.get(i).v2().position(), trData.get(i).v2().normal(), vec2(0.5, 0.5));
      // tr.add(new Triangle(v0, v1, v2));
      // System.out.println(
      //     "v0: " + trData.get(i).v0().uv() + " v1: " + trData.get(i).v1().uv() + "v2: " + trData.get(i).v2().uv());
    }

    TriangleMesh trMesh = new TriangleMesh(tr, bild);
    //Group objekt = new Group(multiply(move(3, 80, -420), rotate(vec3(1, 0, 0), -180)), trMesh);
    // bis hier
    
    Group flugzeug = flugzeugeErstellen();
    Group huerde = huerdenErstellen();
    
    Group ball = ballErstellen();
    Group welt = new Group(huerde, flugzeug, ball);

    Image image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht, white);
    image.sampleStream(rayTracer); // setzt Pixelfarben, ohne StratifiedSampling
    // image.sampleStream(new StratifiedSampling(rayTracer)); // setzt Pixelfarben,
    // mit StratifiedSampling
    image.writePng("a08-image"); // erstellt Bild

    // Berechnung der gebrauchten Zeit
    long endZeit = System.currentTimeMillis();
    long dauer = (endZeit - startZeit) / 1000;
    System.out.println("Zeit fuer die Berechnung des Bildes: " + dauer + " s");
  }

  private static Group huerdenErstellen() {
    List<MeshData> liste = Wavefront.loadMeshData("data/Huerde/Hurdle.obj"); // Huerde

    List<Triangle> tr = new ArrayList<>();
    // Dreiecke sind in 9 Gruppen sortiert die unterschiedliche Farbe haben
    for (int i = 0; i < liste.size(); i++) {
      List<TriangleData> data = liste.get(i).triangles();
      // Dreiecke neu erstellen und jeweilige Farbe auch hinzufuegen
      for (int j = 0; j < data.size(); j++) {
        Vertex v0 = new Vertex(data.get(j).v0().position(), data.get(j).v0().normal(), data.get(j).v0().uv(),
            liste.get(i).material().kd());
        Vertex v1 = new Vertex(data.get(j).v1().position(), data.get(j).v1().normal(), data.get(j).v1().uv(),
            liste.get(i).material().kd());
        Vertex v2 = new Vertex(data.get(j).v2().position(), data.get(j).v2().normal(), data.get(j).v2().uv(),
            liste.get(i).material().kd());
        Triangle d = new Triangle(v0, v1, v2);
        tr.add(d);
      }
    }

    // als Material kann nur null uebergeben werden, weil jedes Dreieck ueber Vertex
    // eigene Farbe hat
    TriangleMesh trMesh = new TriangleMesh(tr, null);

    Group huerde = new Group(multiply(scale(vec3(0.1)), rotate(vec3(0, 0, 1), 180), move(1007622, 31365, -2378453)),
        trMesh);
    return huerde;
  }

  public static Group flugzeugeErstellen() {
    List<MeshData> liste = Wavefront.loadMeshData("data/Flugzeug2/Airplane_2.obj"); // Flugzeug
    TexturedPhongMaterial bild = new TexturedPhongMaterial(new ImageTexture("data/Flugzeug2/None_albedo.jpeg"),
        new ConstantColor(white), new ConstantColor(color(1000.0)));
    TexturedPhongMaterial sterne = new TexturedPhongMaterial(new ImageTexture("data/sterne2.png"),
        new ConstantColor(white), new ConstantColor(color(1000.0)));

    List<TriangleData> trData = new ArrayList<>();
    for (int i = 0; i < liste.size(); i++) {
      trData.addAll(liste.get(i).triangles());
    }
    List<Triangle> tr = new ArrayList<>();
    for (int i = 0; i < trData.size(); i++) {
      //Vec2 interTextur = interplolate(trData.get(i).v0().uv(), trData.get(i).v1().uv(), trData.get(i).v2().uv(), uvw);
      Color f1 = bild.baseColor(new Hit(0, null, null, null, null, trData.get(i).v0().uv()));
      Color f2 = bild.baseColor(new Hit(0, null, null, null, null, trData.get(i).v1().uv()));
      Color f3 = bild.baseColor(new Hit(0, null, null, null, null, trData.get(i).v2().uv()));
      Vertex v0 = new Vertex(trData.get(i).v0().position(), trData.get(i).v0().normal(), trData.get(i).v0().uv(),
            f1);
        Vertex v1 = new Vertex(trData.get(i).v1().position(), trData.get(i).v1().normal(), trData.get(i).v1().uv(),
            f2);
        Vertex v2 = new Vertex(trData.get(i).v2().position(), trData.get(i).v2().normal(), trData.get(i).v2().uv(),
            f3);
      tr.add(new Triangle(v0, v1, v2));
      // System.out.println("v0: " + trData.get(i).v0().uv() + " v1: " +
      // trData.get(i).v1().uv() + "v2: " + trData.get(i).v2().uv());
    }

    TriangleMesh trMesh = new TriangleMesh(tr, bild);
    Group flugzeug = new Group(multiply(move(0, 0, -8), rotate(vec3(0, 1, 0), -40), rotate(vec3(0, 0, 1), 180)),
        trMesh);

    return flugzeug;
  }

  public static Group ballErstellen() {
    List<MeshData> liste = Wavefront.loadMeshData("data/wilson-football/fb_low.obj"); // Football
    TexturedPhongMaterial bild = new TexturedPhongMaterial(
        new ImageTexture("data/wilson-football/fb_low_lambert2SG_BaseColor.png"), new ConstantColor(white),
        new ConstantColor(color(1000.0)));
    TexturedPhongMaterial sterne = new TexturedPhongMaterial(new ImageTexture("data/sterne2.png"),
        new ConstantColor(white), new ConstantColor(color(1000.0)));

    List<TriangleData> trData = new ArrayList<>();
    for (int i = 0; i < liste.size(); i++) {
      trData.addAll(liste.get(i).triangles());
    }
    List<Triangle> tr = new ArrayList<>();
    for (int i = 0; i < trData.size(); i++) {
      tr.add(new Triangle(trData.get(i).v0(), trData.get(i).v1(), trData.get(i).v2()));
      // System.out.println("v0: " + trData.get(i).v0().uv() + " v1: " +
      // trData.get(i).v1().uv() + "v2: " + trData.get(i).v2().uv());
    }
    TriangleMesh trMesh = new TriangleMesh(tr, sterne);
    Group ball = new Group(move(3, 0, -27), trMesh);
    return ball;
  }
}
