package cgg.a07;

import static tools.Color.*;
import static tools.Functions.*;
import cgg.a02.*;
//import cgg.a04.StratifiedSampling;
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
    Mat44 transformationKamera = multiply(move(vec3(0,-50,230)),rotate(vec3(1,0,0),22), rotate(vec3(0,1,0),-7));
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
    Group dachLinks = new Group(multiply(move(vec3(-1.06, -1.93, -11.5)), rotate(vec3(0,0,1), -45)), new Ebene("quadratisch", 3, rot));
    Group dachRechts = new Group(multiply(move(vec3(1.06, -1.93, -11.5)), rotate(vec3(0,0,1), 45)), new Ebene("quadratisch", 3, rot));
    Group fensterUnten1 = new Group(multiply(move(vec3(-0.75, -0.62, -9.99)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 0.8, gelb));
    Group fensterUnten2 = new Group(multiply(move(vec3(0.75, 0.62, -9.99)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 0.8, gelb));
    Group fensterUnten3 = new Group(multiply(move(vec3(-0.75, 0.62, -9.99)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 0.8, spiegelMat));
    Group fensterUnten4 = new Group(multiply(move(vec3(0.75, -0.62, -9.99)), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 0.8, spiegelMat));
    
    Group hausUnten = new Group(wandVorne, wandSeiteLinks, wandSeiteRechts, wandHinten, fensterUnten1, fensterUnten2, fensterUnten3, fensterUnten4);
    Group hausOben = new Group(dachLinks, dachRechts);
    Group haus = new Group(hausUnten, hausOben);
    Group haus1 = new Group(move(+5, 0,0),haus);
    Group haus2 = new Group(move(0, 0,-5),haus);
    Group haus3 = new Group(move(+5, 0,-5),haus);
    Group vierHaueser = new Group(haus, haus1, haus2, haus3);

    Group boden = new Group(move(0,2,0),new Ebene("unbegrenzt", 0, gruen));

    Group alleHaeuser = new Group(fillPlane(vierHaueser, 5));
    Group welt = new Group(boden, alleHaeuser);
    
    Image image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht, white);
    image.sampleStream(rayTracer); //setzt Pixelfarben, ohne StratifiedSampling
    //image.sampleStream(new StratifiedSampling(rayTracer)); //setzt Pixelfarben, mit StratifiedSampling
    image.writePng("a07-image"); //erstellt Bild

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