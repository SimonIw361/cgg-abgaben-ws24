package cgg.a05;

import static tools.Color.gray;
import static tools.Color.white;
import static tools.Functions.*;
import cgg.a02.*;
import cgg.a04.PhongMaterial;
import cgg.a04.StratifiedSampling;
import cgg.a04.TexturedPhongMaterial;
import tools.ImageTexture;
import tools.Mat44;

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
    licht.add(new Richtungslichtquelle(vec3(10,-10,10), white));
    //Mat44 transformationKamera = multiply(move(vec3(0,550,-40)),rotate(vec3(1,0,0),-20));
    Mat44 transformationKamera = multiply(move(vec3(0,-6,0.6)),rotate(vec3(1,0,0),30));
    Lochkamera kamera = new Lochkamera(Math.PI/2, 400, 400, transformationKamera);
    //System.out.println(move(vec3(50,0,0)));

    //Szene mit Kugeln erstellen
    ArrayList<Shape> k = new ArrayList<>();
    k.add(new Kugel(vec3(0,1001,-15), 1000, new PhongMaterial(gray, white, 1000.0)));
    TexturedPhongMaterial musterKugel = new TexturedPhongMaterial(new ImageTexture("data/sterne2.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    //k.add(new Kugel(vec3(0,-1.3,-4), 1.7, musterKugel));
    //k.add(new Kugel(vec3(8,-1.3,-4), 1.7, musterKugel));

    //Schneemann erstellen
    ArrayList<Shape> schneemannList = new ArrayList<>();
    schneemannList.add(new Kugel(vec3(0,-1.1,-4), 1.2, musterKugel));
    schneemannList.add(new Kugel(vec3(0,-3.1,-4), 0.8, musterKugel));
    Group schneemannNormal = new Group(schneemannList, move(vec3(0,0,0)));
    Mat44 transformationSchneemann1 = move(vec3(2,0,0));
    Mat44 transformationSchneemann2 = move(vec3(-2,0,0));
    Group schneemann1 = new Group(schneemannList, transformationSchneemann1);
    Group schneemann2 = new Group(schneemannList, transformationSchneemann2);

    //Mat44 transformationKugel = move(vec3(0,0,0));
    Group hintergrund = new Group(k, move(vec3(-0,0,0)));
    ArrayList<Shape> weltList = new ArrayList<>();
    weltList.add(schneemann1);
    weltList.add(schneemann2);
    weltList.add(hintergrund);
    Group welt = new Group(weltList, move(vec3(0,0,0)));
    
    

    var image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht);
    //image.sample(rayTracer); //setzt Pixelfarben, ohne StratifiedSampling
    image.sample(new StratifiedSampling(rayTracer)); //setzt Pixelfarben, mit StratifiedSampling
    image.writePng("a05-image"); //erstellt Bild
  }
}

