package cgg.a04;

import static tools.Functions.add;
import static tools.Functions.color;
import static tools.Functions.length;
import static tools.Functions.multiply;
import static tools.Functions.normalize;
import static tools.Functions.random;
import static tools.Functions.vec3;

import cgg.a02.Hit;
import cgg.a02.Ray;
import tools.Color;
import tools.Vec3;

public record PhongMaterial(Color kd, Color ks, double ke) implements Material {

    /**
     * @param h Trefferpunkt von Kugel
     * @return Farbe fuer Wert kd
     */
    public Color baseColor(Hit h) {
        return kd;
    }

    /**
     * @param h Trefferpunkt von Kugel
     * @return Farbe fuer Wert ks
     */
    public Color specular(Hit h) {
        return ks;
    }

    /**
     * @param h Trefferpunkt von Kugel
     * @return Intensitaet der Spiegelung
     */
    public double shininess(Hit h) {
        return ke;
    }

    public Ray berechneSekundaerstrahl(Ray r, Hit h) {
        Vec3 zufaellig = vec3(2 * random() -1, 2 * random() -1, 2 * random() -1);
        while(length(zufaellig) > 1){
            zufaellig = vec3(2 * random() -1, 2 * random() -1, 2 * random() -1);
        }

        Vec3 richtung = normalize(add(zufaellig, h.getNormalenVektor()));
        Vec3 ursprung = h.getTrefferPunkt();
        
        return new Ray(ursprung, richtung, 0.0001, 99999);
    }

    public Color albedo(Hit h) {
        return ks;
    }

}
