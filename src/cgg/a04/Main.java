package cgg.a04;

import static tools.Color.blue;
import static tools.Color.green;
import static tools.Color.red;
import static tools.Color.white;
import static tools.Functions.*;
import cgg.a02.*;

import java.util.ArrayList;

import cgg.Image;
import cgg.a01.ConstantColor;

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
    Lochkamera kamera = new Lochkamera(Math.PI/2, 400, 400);

    //Szene mit Kugeln erstellen
    ArrayList<Kugel> k = new ArrayList<>(); //auskommentiert, da Konstruktor veraendert
    k.add(new Kugel(vec3(0,1001,-15), 1000, new TexturedPhongMaterial(new ConstantColor(white), new ConstantColor(white), new ConstantColor(color(1000.0)))));
    k.add(new Kugel(vec3(2.3,0,-4), 1, new TexturedPhongMaterial(new ConstantColor(red), new ConstantColor(white), new ConstantColor(color(1000.0)))));
    k.add(new Kugel(vec3(-3,0,-5), 1, new TexturedPhongMaterial(new ConstantColor(blue), new ConstantColor(white), new ConstantColor(color(1000.0)))));
    k.add(new Kugel(vec3(0,-0.3,-2), 0.75, new TexturedPhongMaterial(new ConstantColor(green), new ConstantColor(white), new ConstantColor(color(1000.0)))));

    Kugelgruppe kugelScene = new Kugelgruppe(k); //erstellt Szene mit Kugeln

    var image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, kugelScene, licht);
    image.sample(rayTracer); //setzt Pixelfarben
    image.writePng("a04-image"); //erstellt Bild
  }
}

