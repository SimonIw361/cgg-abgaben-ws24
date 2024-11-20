package cgg.a06;

import static tools.Functions.multiply;

import cgg.a02.Hit;
import cgg.a02.Ray;
import cgg.a04.Material;
import tools.Color;
import tools.Sampler;

public record DiffusStreuung(Sampler kd, Sampler ks, Sampler ke) implements Material{

    /**
     * @param h Trefferpunkt von Kugel
     * @return Farbe fuer Wert kd basierend nach Texturen u und v
     */
    public Color baseColor(Hit h) {
        return kd.getColor(h.getuv());
    }

    /**
     * @param h Trefferpunkt von Kugel
     * @return Farbe fuer Wert ks basierend nach Texturen u und v
     */
    public Color specular(Hit h) {
        return ks.getColor(h.getuv());
    }

    /**
     * @param h Trefferpunkt von Kugel
     * @return Intensitaet der Spiegelung
     */
    public double shininess(Hit h) {
        return ke.getColor(h.getuv()).b();
    }

    public Ray berechneSekundaerstrahl(Ray r, Hit h) {
        return null;
    }

    public Color albedo(Hit h) {
        return multiply(0, kd.getColor(h.getuv()));
    }

}
