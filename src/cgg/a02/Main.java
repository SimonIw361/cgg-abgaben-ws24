package cgg.a02;

import static tools.Color.blue;
import static tools.Color.gray;
import static tools.Color.green;
import static tools.Color.red;
import static tools.Color.white;
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
    licht.add(new Richtungslichtquelle(vec3(10,-10,5), white));//new Vec3(0.8,0.75,0.7), white));
    Lochkamera kamera = new Lochkamera(Math.PI/2, 400, 400);

    //Szene mit Kugeln erstellen
    ArrayList<Kugel> k = new ArrayList<>();
    k.add(new Kugel(vec3(0,1001,-15), 1000, blue));
    k.add(new Kugel(vec3(0,0,-5), 1, green));

    Kugelgruppe kugelScene = new Kugelgruppe(k); //erstellt Szene mit Kugeln

    var image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, kugelScene, licht);
    //image.setPixel(207,159,rayTracer.getColor(new Vec2(207, 159))); //auskommentieren wieder
    image.sample(rayTracer); //setzt Pixelfarben
    image.writePng("a03-spheres"); //erstellt Bild
  }
}
