package cgg.a07;

import static tools.Color.*;
import static tools.Functions.*;
import cgg.a02.*;
import cgg.a04.StratifiedSampling;
import cgg.a05.Group;
import cgg.a05.Shape;
import cgg.a06.DiffusStreuung;
import cgg.a06.MaterialSpiegel;
import tools.Mat44;

import java.util.ArrayList;

import cgg.Image;
import cgg.a01.ConstantColor;

public class Main {

  public static void main(String[] args) {
    long startZeit = System.currentTimeMillis();
    int width = 900;
    int height = 900;

    //Licht und Kamera erstellen
    ArrayList<Lichtquelle> licht = new ArrayList<>();
    //licht.add(new Richtungslichtquelle(vec3(10,-10,10), white));
    //licht.add(new Richtungslichtquelle(vec3(-10,-10,10), white));
    Mat44 transformationKamera = multiply(move(vec3(0,-18,20.5)),rotate(vec3(1,0,0),22), rotate(vec3(0,1,0),-7));
    Lochkamera kamera = new Lochkamera(Math.PI/4, width, height, transformationKamera);

    //Farben fuer Materialien erstellen
    DiffusStreuung blau = new DiffusStreuung(new ConstantColor(blue), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung rot = new DiffusStreuung(new ConstantColor(red), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung gelb = new DiffusStreuung(new ConstantColor(yellow), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung gruen = new DiffusStreuung(new ConstantColor(color(0,0.52,0)), new ConstantColor(white), new ConstantColor(color(1000.0)));
    MaterialSpiegel spiegelMat = new MaterialSpiegel(new ConstantColor(white), new ConstantColor(white), new ConstantColor(color(1000.0)));

    

    Group wandVorne = new Group(multiply(move(vec3(0, 0, -10)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 3, blau));
    Group wandSeiteLinks = new Group(multiply(move(vec3(-1.5, 0, -11.5)), rotate(vec3(0,0,1), 90)), new Ebene("quadratisch", 3, blau));
    Group wandSeiteRechts = new Group(multiply(move(vec3(1.5, 0, -11.5)), rotate(vec3(0,0,1), -90)), new Ebene("quadratisch", 3, blau));
    Group wandHinten = new Group(multiply(move(vec3(0, 0, -13)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 3, blau));
    Group dachLinks = new Group(multiply(move(vec3(-1.07, -1.93, -11.5)), rotate(vec3(0,0,1), -45)), new Ebene("quadratisch", 3, rot));
    Group dachRechts = new Group(multiply(move(vec3(1.07, -1.93, -11.5)), rotate(vec3(0,0,1), 45)), new Ebene("quadratisch", 3, rot));
    Group fensterUnten1 = new Group(multiply(move(vec3(-0.75, -0.62, -9.99)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 0.8, gelb));
    Group fensterUnten2 = new Group(multiply(move(vec3(0.75, 0.62, -9.99)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 0.8, gelb));
    Group fensterUnten3 = new Group(multiply(move(vec3(-0.75, 0.62, -9.99)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 0.8, spiegelMat));
    Group fensterUnten4 = new Group(multiply(move(vec3(0.75, -0.62, -9.99)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 0.8, spiegelMat));
    
    Group hausUnten = new Group(wandVorne, wandSeiteLinks, wandSeiteRechts, wandHinten, fensterUnten1, fensterUnten2, fensterUnten3, fensterUnten4);
    Group hausOben = new Group(dachLinks, dachRechts);
    Group haus = new Group(hausUnten, hausOben);

    //Array Liste mit allen Gruppen von Kugeln die in der Schleife erstellt werden
    ArrayList<Shape> haeuser = new ArrayList<>();
    for(int i=0; i < 9; i++){
      for(int j=0; j < 10; j++){
        Group g = new Group(move(vec3(-9 + 5*i, 0, -7.6 *j)), haus);
        haeuser.add(g);
      }
    }
  
    //Group spiegel = new Group(multiply(move(vec3(-11, -2, -30)), rotate(vec3(0,1,0), -10), rotate(vec3(0,0,1), 90)), new Ebene("kreisrund", 15, spiegelMat));
    Group boden = new Group(move(vec3(0, 1.5, 0)), new Ebene("unbegrenzt", 0, gruen));

    Group alleHaeuser = new Group(haeuser);
    Group szene = new Group(alleHaeuser);
    Group welt = new Group(szene, boden);
    
    Image image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht, white);
    //image.sampleStream(rayTracer); //setzt Pixelfarben, ohne StratifiedSampling
    image.sampleStream(new StratifiedSampling(rayTracer)); //setzt Pixelfarben, mit StratifiedSampling
    image.writePng("a07-image"); //erstellt Bild

    //Berechnung der gebrauchten Zeit
    long endZeit = System.currentTimeMillis();
    long dauer = endZeit - startZeit;
    long dauerMin = (dauer/1000)/60;
    System.out.println("Zeit fuer die Berechnung des Bildes: " + dauerMin + " min");
  }
}