package cgg.a02;

import static tools.Color.blue;
import static tools.Color.gray;
import static tools.Color.red;
import static tools.Functions.*;

import java.util.ArrayList;

import cgg.Image;
import tools.*;

//gleicher Code wie die Main-Methode vom Package a01
public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    //Licht und Kamera erstellen
    ArrayList<Lichtquelle> licht = new ArrayList<>();
    licht.add(new Richtungslichtquelle(new Vec3(10,0,0), blue));
    Lochkamera kamera = new Lochkamera(Math.PI/2, 400, 400);

    //Szene mit Kugeln erstellen
    ArrayList<Kugel> k = new ArrayList<>();
    k.add(new Kugel(vec3(0,1001,-15), 1000, gray));
    k.add(new Kugel(vec3(0,0,-5), 1, red));

    Kugelgruppe kugelScene = new Kugelgruppe(k); //erstellt Szene mit Kugeln

    var image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, kugelScene, licht);
    image.sample(rayTracer); //setzte Pixelfarben
    image.writePng("a03-spheres"); //erstellt Bild
  }
}
