package cgg.a02;

import tools.*;

public record Richtungslichtquelle(Vec3 richtungS, Color farbe) implements Lichtquelle {

    /**
     * @return Richtung zur Lichtquelle als Vec3
     */
    public Vec3 richtungLichtquelle(Vec3 x) {
        return richtungS;
    }

    public Color intensitaet(Vec3 x) {
        return farbe;
    }

}
