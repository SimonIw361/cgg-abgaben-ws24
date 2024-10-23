package cgg.a02;

import static tools.Functions.normalize;
import static tools.Functions.subtract;

import tools.Color;
import tools.Vec3;

public record Punktlichtquelle(Vec3 punktLichtquelle) implements Lichtquelle {

    /**
     * @return Richtung zur Lichtquelle als Vec3
     */
    public Vec3 richtungLichtquelle(Vec3 x) {
        Vec3 s = normalize(subtract(punktLichtquelle, x));
        return s;
    }

    public Color intensitaet(Vec3 x) {
        return null;    //???
    }

}
