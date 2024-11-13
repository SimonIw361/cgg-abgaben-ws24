package cgg.a02;

import static tools.Color.black;

import java.util.ArrayList;

import cgg.a05.Shape;
import tools.*;
import static tools.Functions.*;

public record RayTracer(Lochkamera camera, Shape kugeln, ArrayList<Lichtquelle> lichtquelle) implements Sampler {

    /**
     * gibt die Farbe fuer ein bestimmtes Pixel zurueck
     * 
     * @param point Punkt zu dem die Farbe herausgefunden werden soll
     * @return Farbe des Pixels
     */
    public Color getColor(Vec2 point) {
        Ray r = camera.gibStrahl(point);
        Hit treffer = kugeln.intersect(r);

        if(treffer == null) {
            return black; //Standardhintergrundfarbe bei keinem Treffer
        }
        else {
            //System.out.println(point);
            return shade(treffer, r);
        }
    }


    /**
     * berechnet Schattierung fuer gegebenen Treffer mit Phong Methode
     * 
     * @param hit der Trefferpunkt
     * @param ray Strahl durch diesen Punkt
     * @return Farbe fuer diesen Punkt
     */
    public Color shade(Hit hit, Ray ray) {
        Vec3 n = hit.getNormalenVektor(); //n Normalenvektor
        Vec3 s = null; //in Schleife initialisiert
        Vec3 r = null; //in Schleife initialisert
        Vec3 v = normalize(negate(ray.getRichtung())); //v Richtung zum Betrachter
        Color l = null; //l ankommende Intensitaet im Punkt, in Schleife initialisiert
        Color kd = hit.getMaterial().baseColor(hit); //kd Reflexionskoeffizient, also die Farbe
        Color ks = hit.getMaterial().specular(hit); //hit.getfarbeOberflaeche(); //ks spiegelnder Reflexionskoeffizient, nur eigene Farbe spiegelt
        double ke = hit.getMaterial().shininess(hit); //ke Exponent, wie stark die Spiegelung ist
        Color intensitaet = black; //addiert ueber alle Lichtquellen
        Color ads = black; //Summe aus ambient, diffuse und spiegelnd fuer jeweils eine Lichtquelle

        for(int i= 0; i < lichtquelle.size(); i++) {
            s = normalize(lichtquelle.get(i).richtungLichtquelle(hit.getTrefferPunkt())); //s Richtung zur Lichtquelle
            l = clamp(lichtquelle.get(i).intensitaet(hit.getTrefferPunkt())); //l ankommende Intensitaet im Punkt
            r = normalize(add(negate(s), multiply(2 * dot(s, n), n) )); //r Spiegelung von s an n

            Color ambient = multiply(multiply(kd, l), 0.1);
            Color diffuse = clamp(multiply(kd, multiply(l, dot(n, s))));
            Color spiegelnd = black;
            if(dot(n, s) > 0 && dot(vec3(kd), vec3(l)) != 0){//&& dot(vec3(kd), s) != 0) { //wenn negativ ist weg zur Lichtquelle verdeckt (Winkel ist groesser 90)
                spiegelnd = multiply(ks, multiply(l, Math.pow(dot(r, v), ke)));
            }

            //Schattenberechnung
            Ray raySchatten = null;
            if(lichtquelle.get(i) instanceof Richtungslichtquelle) {
                raySchatten = new Ray(hit.getTrefferPunkt(), s, 0.0001, 99999);
            }
            else if(lichtquelle.get(i) instanceof Punktlichtquelle) {
                raySchatten = new Ray(hit.getTrefferPunkt(), s, 0.0001, length(subtract(s, hit.getTrefferPunkt())));
            }

            if(kugeln.intersect(raySchatten) != null && !kugeln.intersect(raySchatten).getKugel().equals(hit.getKugel())) { 
                //nur wenn es Hit gibt und dieser nicht auf der gleichen Kugel ist continue
                intensitaet= add(intensitaet, ambient); //damit nicht ganz schwarz leichter Lichtanteil (nur ambient)
                continue;
            }

            ads = clamp(add(ambient, diffuse, spiegelnd)); //darf nicht groesser als 1 sein
            intensitaet = clamp(add(intensitaet, ads));
        }
        return intensitaet; //macht Farbe zwischen 0 und 1
    }
}
