package cgg.a04;

import static tools.Functions.*;

import cgg.a02.Hit;
import cgg.a02.Ray;
import tools.Color;
import tools.Sampler;
import tools.Vec3;

public record TexturedPhongMaterial(Sampler kd, Sampler ks, Sampler ke) implements Material {

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
        Vec3 zufaellig = vec3(2 * random() -1, 2 * random() -1, 2 * random() -1);
        while(length(zufaellig) > 1){
            zufaellig = vec3(2 * random() -1, 2 * random() -1, 2 * random() -1);
        }

        Vec3 richtung = normalize(add(normalize(zufaellig), normalize(h.getNormalenVektor())));
        Vec3 ursprung = h.getTrefferPunkt();
        
        return new Ray(ursprung, richtung, 0.0001, 99999);
    }

    public Color albedo(Hit h) {
        return multiply(0.9, baseColor(h));
    }

    public Color emission() {
        return color(0,0,0);
    }
}
