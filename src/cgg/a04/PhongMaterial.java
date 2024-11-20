package cgg.a04;

import static tools.Functions.multiply;

import cgg.a02.Hit;
import cgg.a02.Ray;
import tools.Color;

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
        return null;
    }

    public Color emission(Hit h) {
        return multiply(0, kd);
    }

}
