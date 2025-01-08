package cgg.a08;

import static tools.Color.*;
import static tools.Functions.*;
import cgg.a02.*;
import cgg.a04.*;
import cgg.a05.Group;
import cgg.a05.Shape;
import tools.Mat44;
import tools.Vertex;
import tools.Wavefront;
import tools.Wavefront.MeshData;
import tools.Wavefront.TriangleData;

import java.util.ArrayList;
import java.util.List;

import cgg.Image;

public class Main {

  public static void main(String[] args) {
    long startZeit = System.currentTimeMillis();
    int width = 900;
    int height = 900;

    // Licht und Kamera erstellen
    ArrayList<Lichtquelle> licht = new ArrayList<>();
    licht.add(new Richtungslichtquelle(vec3(10, -10, 10), white));
    // licht.add(new Richtungslichtquelle(vec3(-10,-10,10), white));
    //Mat44 transformationKamera = multiply(move(vec3(0, -20, 15)), rotate(vec3(1, 0, 0), 22), rotate(vec3(0, 1, 0), -7));
    Mat44 transformationKameraNormal = move(vec3(0));
    Mat44 transformationKameraHuerden = multiply(move(0,-7000,0), rotate(vec3(1,0,0), 21));
    Lochkamera kamera = new Lochkamera(Math.PI / 4, width, height, transformationKameraHuerden);

    Group huerde = huerdenErstellen();
    Group welt = new Group(huerde);
    
    Image image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht, white);
    //image.sampleStream(rayTracer); // setzt Pixelfarben, ohne StratifiedSampling
     image.sampleStream(new StratifiedSampling(rayTracer)); // setzt Pixelfarben, mit StratifiedSampling
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
    Group huerde = new Group(multiply(scale(vec3(0.01)), move(0,100000,0),rotate(vec3(0, -1, 0),-15), rotate(vec3(0, 0, 1), 180), move(1007622, 31365, -2378453)),
        trMesh);
    
    ArrayList<Shape> g = new ArrayList<>();
    
    for(int i=0; i < 8; i++){
      for(int j=0; j < 10; j++){
        Group gHu = new Group(move(vec3(-3500*j + 7000 * i, 0, -15000 *j - 2000 * i)), huerde);
        g.add(gHu);
      }
    }
    Group alleHuerden = new Group(g);

    return alleHuerden;
  }

  /*private static Group haiErstellen() {
    List<MeshData> liste = Wavefront.loadMeshData("data/Origami_Shark/Origami_Shark.obj"); // Huerde

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
    Group huerde = new Group(multiply(move(vec3(0,0,-15)), rotate(vec3(0,0,1), 180)),trMesh);
    return huerde;
  }*/

}
