package cgg.a02;

import static tools.Color.blue;
import static tools.Color.green;
import static tools.Color.red;
import static tools.Functions.*;

import java.util.ArrayList;

import cgg.Image;

//gleicher Code wie die Main-Methode vom Package a01
public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    //Licht und Kamera erstellen
    ArrayList<Lichtquelle> licht = new ArrayList<>();
    //licht.add(new Richtungslichtquelle(vec3(10,0,6), white));
    licht.add(new Richtungslichtquelle(vec3(10,-10,5), red));
    licht.add(new Richtungslichtquelle(vec3(-10,-10,5), blue));
    licht.add(new Richtungslichtquelle(vec3(-10,-10,10), green));
    //licht.add(new Punktlichtquelle(vec3(10,-10,5), red));
    //licht.add(new Punktlichtquelle(vec3(-10,-10,5), blue));
    //Lochkamera kamera = new Lochkamera(Math.PI/2, 400, 400); //auskommentiert wegen Aederung der Kamera

    //Szene mit Kugeln erstellen
    //ArrayList<Kugel> k = new ArrayList<>(); //auskommentiert, da Konstruktor veraendert
    //k.add(new Kugel(vec3(0,1001,-15), 1000, white));
    //k.add(new Kugel(vec3(2.3,0,-4), 1, red));
    //k.add(new Kugel(vec3(-3,0,-5), 1, blue));
    //k.add(new Kugel(vec3(0,-0.3,-2), 0.75, green));

    //Kugelgruppe kugelScene = new Kugelgruppe(k); //erstellt Szene mit Kugeln

    var image = new Image(width, height);
    //RayTracer rayTracer = new RayTracer(kamera, kugelScene, licht); //auskommentiert wegen Aenderung
    //image.sample(rayTracer); //setzt Pixelfarben //auskommentiert wegen Aenderung
    image.writePng("a03-spheres"); //erstellt Bild
  }
}
