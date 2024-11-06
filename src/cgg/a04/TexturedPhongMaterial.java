package cgg.a04;

import static tools.Color.white;

import cgg.a02.Hit;
import tools.Color;
import tools.Sampler;

public record TexturedPhongMaterial(Sampler kd, Sampler ks, Sampler ke) implements Material {

    public Color baseColor(Hit h) {
        return kd.getColor(h.getuv());
    }

    public Color specular(Hit h) {
        return ks.getColor(h.getuv());
    }

    public double shininess(Hit h) {
        return ke.getColor(h.getuv()).b();
    }

}
