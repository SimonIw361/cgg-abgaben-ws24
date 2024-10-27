package cgg.a02;

import static tools.Color.black;
import static tools.Color.blue;
import static tools.Color.gray;
import static tools.Functions.add;
import static tools.Functions.dot;
import static tools.Functions.multiply;
import static tools.Functions.normalize;
import static tools.Functions.subtract;
import static tools.Functions.vec3;
import static tools.Functions.color;

import java.util.ArrayList;
import java.util.Random;

import tools.*;

public class Kugelgruppe implements Sampler{
    private ArrayList<Kugel> kugeln;
    private ArrayList<Lichtquelle> licht;

    public Kugelgruppe(int anzahlKugel, ArrayList<Lichtquelle> licht) {
        kugeln = new ArrayList<Kugel>();
        for(int i = 0; i < anzahlKugel; i++) {
            Vec3 mitteKugel = new Vec3(getRandomZahl(80)-40,getRandomZahl(80)-40,getRandomZahl(60)-100);
            kugeln.add(new Kugel(mitteKugel, getRandomZahl(10) +4, getRandomColor()));
        }
    }

    public Kugelgruppe(ArrayList<Lichtquelle> licht) {
        this.licht = licht;
        kugeln = new ArrayList<Kugel>();
        Vec3 mitteBild = new Vec3(0, 0, -80);
        kugeln.add(new Kugel(mitteBild, 30, blue));
    }

    /**
     * gibt die Farbe fuer ein bestimmtes Pixel zurueck
     * 
     * @param point Punkt zu dem die Farbe herausgefunden werden soll
     * @return Farbe des Pixels
     */
    public Color getColor(Vec2 point) {
        Lochkamera kamera = new Lochkamera(Math.PI/2, 400, 400);
        Ray r = kamera.gibStrahl(point.u(), point.v());
        Hit treffer = new Hit(300, null, null, gray); //nur fuer Initialisierung

        for(int j = 0; j < kugeln.size(); j++) {
            Hit h = kugeln.get(j).intersect(r);
            if(h != null && h.getT() < treffer.getT()) { //Hit nur aendern wenn es Treffer gibt und dieser naeher an der Kamera ist (t moeglichst klein)
                    treffer = h;
            }
        }
        if(treffer.getT() != 300) { //300 ist bei Initialisierung
            //Vec3 n0 = treffer.getNormalenVektor();  //Farbe mit Normalenvektor von Hit
            //return new Color(n0.u(), n0.v(), n0.w());
            for(int j = 0; j < licht.size(); j++) {
                return shade(treffer, licht.get(j));
            }
            //return treffer.getfarbeOberflaeche();
        }
        
        return gray; //Standardhintergrundfarbe bei keinem Treffer
    }
    
    /**
     * berechnet Schattierung fuer gegebenen Treffer mit Phong Methode
     * 
     * @param hit der Trefferpunkt
     * @return Farbe fuer diesen Punkt
     */
    public static Color shade(Hit hit, Lichtquelle lichtquelle) {
        //ist Berechnung richtig??
        Vec3 lightDir = lichtquelle.richtungLichtquelle(hit.getTrefferPunkt());
        Color intensitaet = lichtquelle.intensitaet(hit.getTrefferPunkt());
        Color ambient = multiply(0.1, hit.getfarbeOberflaeche()); //kd ist 0.1, ambienter Rueckstrahlwert der Oberflaeche
        //Color ambient = multiply(0.1, intensitaet);
        //System.out.println(intensitaet + ", " + hit.getfarbeOberflaeche());
        Color diffuse = multiply(0.9 * Math.max(0, dot(lightDir, hit.getNormalenVektor())), hit.getfarbeOberflaeche());
        //Color diffuse = multiply(kd, dot(lightDir, hit.getNormalenVektor()));
        //Color spiegeln = 
        //System.out.println(ambient + ", " + diffuse + ", " + add(ambient, diffuse));
        return add(ambient, diffuse);
    }

    /**
     * erzeugt eine zufaellige Zahl
     * 
     * @param maximum obere Grenze fuer zufaellige Zahlen
     * @return zufaellige ZAhl bis zum maximum
     */
    public int getRandomZahl(int maximum) {
        Random random = new Random();
        int zahl = random.nextInt(maximum);
        return zahl;
    }

    /**
     * erzeugt mit zufaelligen Zahlen eine Farbe
     * 
     * @return zufaellige Farbe
     */
    public Color getRandomColor(){
        Random random = new Random();
        double r = random.nextDouble();
        double g = random.nextDouble();
        double b = random.nextDouble();
        return new Color(r,g,b);
    }

}
