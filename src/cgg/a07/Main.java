package cgg.a07;

import static tools.Color.*;
import static tools.Functions.*;
import cgg.a02.*;
import cgg.a04.*;
import cgg.a05.Group;
import cgg.a05.Shape;
import cgg.a06.DiffusStreuung;
import cgg.a06.MaterialSpiegel;
import tools.ImageTexture;
import tools.Mat44;

import java.util.ArrayList;

import cgg.Image;
import cgg.a01.ConstantColor;

//gleicher Code wie die Main-Methode vom Package a01
public class Main {

  public static void main(String[] args) {
    long startZeit = System.currentTimeMillis();
    int width = 900;
    int height = 900;

    //Licht und Kamera erstellen
    ArrayList<Lichtquelle> licht = new ArrayList<>();
    licht.add(new Richtungslichtquelle(vec3(10,-10,10), white));
    Mat44 transformationKamera = multiply(move(vec3(0,-18,20.5)),rotate(vec3(1,0,0),22), rotate(vec3(0,1,0),-7));
    Lochkamera kamera = new Lochkamera(Math.PI/4, width, height, transformationKamera);

    //Texturen/Materialien fuer Kugeln erstellen
    DiffusStreuung blau = new DiffusStreuung(new ImageTexture("data/blau.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung rot = new DiffusStreuung(new ImageTexture("data/rot.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung gelb = new DiffusStreuung(new ImageTexture("data/gelb.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung orange = new DiffusStreuung(new ImageTexture("data/orange.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    DiffusStreuung schwarz = new DiffusStreuung(new ImageTexture("data/schwarz.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    MaterialSpiegel spiegel = new MaterialSpiegel(new ConstantColor(white), new ConstantColor(white), new ConstantColor(color(1000.0)));

    //Array Liste mit allen Gruppen von Kugeln die in der Schleife erstellt werden
    ArrayList<Shape> gesichter = new ArrayList<>();

    Kugel kopf = new Kugel(vec3(0,-0.2,-3), 1.4, rot);
    Kugel kopf2 = new Kugel(vec3(0,-0.2,-3), 1.4, blau);
    Kugel auge = new Kugel(vec3(0.42,-1.35,-2.29), 0.1, schwarz);
    Kugel auge2 = new Kugel(vec3(-0.42,-1.35,-2.29), 0.1, schwarz);
    Kugel nase = new Kugel(vec3(0,-1.04,-1.87), 0.1, orange);
    Kugel mund1 = new Kugel(vec3(-0.09,-0.5,-1.7), 0.12, gelb);
    Kugel mund2 = new Kugel(vec3(0.09,-0.5,-1.7), 0.12, gelb);
    Kugel mund3 = new Kugel(vec3(-0.26,-0.58,-1.72), 0.11, gelb);
    Kugel mund4 = new Kugel(vec3(0.26,-0.58,-1.72), 0.11, gelb);

    Group augen = new Group(auge, auge2);
    Group mundGesamt = new Group(mund1, mund2, mund3, mund4);
    Group gesicht = new Group(kopf, augen, nase, mundGesamt);
    Group gesicht2 = new Group(move(vec3(3,0,0)), kopf2, augen, nase, mundGesamt);
    Group gruppe = new Group(gesicht, gesicht2);

    for(int i=0; i < 1; i++){
      for(int j=0; j < 1; j++){
        Group g = new Group(move(vec3(8*i, 0, -4 *j)), gruppe);
        gesichter.add(g);
      }
    }
  
    Group groupSpiegel = new Group(new Kugel(vec3(-11,-4,-22), 6, spiegel));
    //Group ebene = new Group(multiply(move(0, 0, -10), rotate(vec3(1,0,0), 90)),new Ebene("kreisrund", 5.4, spiegel));
    Group ebene2 = new Group(move(2, -2, -5), new Ebene("dreieck", 2.5, new PhongMaterial(blue, white, 1000)));

    Group alleGesichter = new Group(gesichter);
    Kugel hinterKugel = new Kugel(vec3(0,1001,-30), 1000, new DiffusStreuung(new ConstantColor(color(0.35)), new ConstantColor(white), new ConstantColor(color(1000.0))));//new PhongMaterial(color(0.35), white, 1000.0));
    Group hintergrund = new Group(groupSpiegel, alleGesichter, ebene2);
    Group welt = new Group(hintergrund);
    
    Image image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht, white);
    image.sample(rayTracer); //setzt Pixelfarben, ohne StratifiedSampling
    //image.sample(new StratifiedSampling(rayTracer)); //setzt Pixelfarben, mit StratifiedSampling
    image.writePng("a07-image"); //erstellt Bild

    //Berechnung der gebrauchten Zeit
    long endZeit = System.currentTimeMillis();
    long dauer = endZeit - startZeit;
    long dauerMin = (dauer/1000)/60;
    System.out.println("Zeit fuer die Berechnung des Bildes: " + dauerMin + " min");
  }

}