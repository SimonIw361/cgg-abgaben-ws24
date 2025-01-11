package cgg.a09;

import static tools.Color.*;
import static tools.Functions.*;
import cgg.a02.*;
import cgg.a04.*;
import cgg.a05.Group;
import cgg.a05.Shape;
import cgg.a08.Triangle;
import cgg.a08.TriangleMesh;
import tools.ImageTexture;
import tools.Mat44;
import tools.Vertex;
import tools.Wavefront;
import tools.Wavefront.MaterialData;
import tools.Wavefront.MeshData;
import tools.Wavefront.TriangleData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cgg.Image;
import cgg.a01.ConstantColor;

public class Main {

  public static void main(String[] args) throws Exception{
    long startZeit = System.currentTimeMillis();

    Hai hai = new Hai();

    int ssmpl = 2;
    Animation.render(0.0, 1.0, 10, hai, ssmpl, "video09");

    long endZeit = System.currentTimeMillis();
    long dauer = (endZeit - startZeit) / 1000;
    System.out.println("Zeit fuer die Berechnung des Bildes: " + dauer + " s");
  }

  protected static Group haiErstellen() {
    List<MeshData> liste = Wavefront.loadMeshData("data/Tiere/Origami_Shark/Origami_Shark.obj"); // Huerde

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

    // als Material kann nur null uebergeben werden, weil jedes Dreieck ueber Vertex eigene Farbe hat
    TriangleMesh trMesh = new TriangleMesh(tr, null);
    Group huerde = new Group(multiply(move(vec3(0,0,-15)), rotate(vec3(0,0,1), 180)),trMesh);
    return huerde;
  }

}

