package cgg.a04;

import cgg.a02.Hit;
import tools.Color;
import tools.Sampler;

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

}
