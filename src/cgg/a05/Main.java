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
    Mat44 transformationKamera = multiply(move(vec3(0,-6,0.6)),rotate(vec3(1,0,0),30));
    Lochkamera kamera = new Lochkamera(Math.PI/2, 400, 400, transformationKamera);

    //Texturen fuer Kugeln erstellen
    TexturedPhongMaterial sterne = new TexturedPhongMaterial(new ImageTexture("data/sterne2.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));
    TexturedPhongMaterial streifen = new TexturedPhongMaterial(new ImageTexture("data/streifen.png"), new ConstantColor(white), new ConstantColor(color(1000.0)));

    //Gruppe mit zwei Schneemaennern mit unetrschiedlichen Texturen erstellen
    Kugel schneeMuster1 = new Kugel(vec3(1,-1,-4), 1.2, sterne);
    Kugel schneeMuster2 = new Kugel(vec3(1,-2.9,-4), 0.8, sterne);
    Group schneemannSterne = new Group(schneeMuster1, schneeMuster2);
    Kugel schneeStreifen1 = new Kugel(vec3(-2,-1,-4), 1.2, streifen);
    Kugel schneeStreifen2 = new Kugel(vec3(-2,-2.9,-4), 0.8, streifen);
    Group schneemannStreifen = new Group(schneeStreifen1, schneeStreifen2);
    Group zweiSchneemaenner = new Group(schneemannSterne, schneemannStreifen);

    //die Gruppen von Schneemaenner immer wieder verschieben
    Group schneemann1 = new Group(zweiSchneemaenner);
    Group schneemann2 = new Group(move(vec3(0,0,-10)), zweiSchneemaenner);
    Group schneemann3 = new Group(multiply(move(vec3(0,0,-9)),rotate(vec3(0,1,0),80)), zweiSchneemaenner);
    Group schneemann4 = new Group(multiply(move(vec3(2,0,-9)),rotate(vec3(0,1,0),-70)), zweiSchneemaenner);
    Group schneemann5 = new Group(move(vec3(-12,0,-20)), zweiSchneemaenner);
    Group schneemann6 = new Group(move(vec3(7,0,-27)), zweiSchneemaenner);
    Group schneemann7 = new Group(multiply(move(vec3(18,0,-17)),rotate(vec3(0,1,0),80)), zweiSchneemaenner);
    Group schneemann8 = new Group(multiply(move(vec3(3,0,-1.4)),scale(vec3(0.3))), zweiSchneemaenner);
    Group schneemann9 = new Group(multiply(move(vec3(-4,0,-30)),scale(vec3(4))), zweiSchneemaenner);

    //alle Kugeln in eine Gruppe machen
    Kugel hinterKugel = new Kugel(vec3(0,1001,-30), 1000, new PhongMaterial(gray, white, 1000.0));
    Group hintergrund = new Group(hinterKugel, schneemann1, schneemann2, schneemann3, schneemann4, schneemann5, schneemann6, schneemann7, schneemann8, schneemann9);
    Group welt = new Group(hintergrund);
    
    var image = new Image(width, height);
    RayTracer rayTracer = new RayTracer(kamera, welt, licht, white);
    //image.sample(rayTracer); //setzt Pixelfarben, ohne StratifiedSampling
    image.sample(new StratifiedSampling(rayTracer)); //setzt Pixelfarben, mit StratifiedSampling
    image.writePng("a05-image"); //erstellt Bild
  }
}

