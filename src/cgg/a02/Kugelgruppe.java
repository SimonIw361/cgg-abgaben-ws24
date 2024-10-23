package cgg.a02;

import static tools.Color.gray;
import static tools.Functions.add;
import static tools.Functions.dot;
import static tools.Functions.multiply;
import static tools.Functions.normalize;
import static tools.Functions.vec3;

import java.util.ArrayList;
import java.util.Random;

import tools.*;

public class Kugelgruppe implements Sampler{
    private ArrayList<Kugel> kugeln;

    public Kugelgruppe(int anzahlKugel, ArrayList<Lichtquelle> licht) {
        kugeln = new ArrayList<Kugel>();
        for(int i = 0; i < anzahlKugel; i++) {
            kugeln.add(new Kugel(new Vec3(getRandomZahl(80)-40,getRandomZahl(80)-40,getRandomZahl(60)-100), getRandomZahl(10) +4));
        }
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
        if(treffer.getT() != 300) {
            //Vec3 n0 = treffer.getNormalenVektor();  //Farbe mit Normalenvektor von Hit
            //return new Color(n0.u(), n0.v(), n0.w());
            return shade(treffer); //Farbe mit angezeigter Methode
            //return treffer.getfarbeOberflaeche();
        }
        
        return gray; //Standardhintergrundfarbe bei keinem Treffer
    }

    
    /**
     * berechnet Schattierung fuer gegebenen Treffer
     * 
     * @param hit der Trefferpunkt
     * @return Farbe fuer diesen Punkt
     */
    public static Color shade(Hit hit) {
        Vec3 lightDir = normalize(vec3(1, 1, 0.7));
        Color ambient = multiply(0.1, hit.getfarbeOberflaeche());
        Color diffuse = multiply(0.9 * Math.max(0, dot(lightDir, hit.getNormalenVektor())), hit.getfarbeOberflaeche());
        //Color spiegeln = null;
        return add(ambient, diffuse);
        //bei diffuse Lichtquelle aendern, aus der Liste nehmen
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

}
