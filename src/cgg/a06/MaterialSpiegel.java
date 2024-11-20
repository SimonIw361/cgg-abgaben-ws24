package cgg.a06;

import static tools.Functions.*;

import cgg.a02.Hit;
import cgg.a02.Ray;
import cgg.a04.Material;
import tools.Color;
import tools.Sampler;
import tools.Vec3;

public record MaterialSpiegel(Sampler kd, Sampler ks, Sampler ke) implements Material {

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

    public Ray berechneSekundaerstrahl(Ray ray, Hit h) {
        Vec3 v = normalize(negate(ray.getRichtung()));
        Vec3 n = normalize(h.getNormalenVektor());
        //r = v - 2 * (v * n) * n
        Vec3 r = normalize(subtract(v, multiply(2 * dot(v, n), n) )); //Spiegelung von s an n

        Ray raySekundaer = new Ray(h.getTrefferPunkt(), r, 0.0001, 99999);
        return raySekundaer;
    }
}
