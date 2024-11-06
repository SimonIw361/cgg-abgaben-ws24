package cgg.a04;

import cgg.a02.Hit;
import tools.Color;

public record PhongMaterial(Color kd, Color ks, double ke) implements Material {

    public Color baseColor(Hit h) {
        return kd;
    }

    public Color specular(Hit h) {
        return ks;
    }


    public double shininess(Hit h) {
        return ke;
    }


}
