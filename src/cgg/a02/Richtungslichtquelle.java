package cgg.a02;

import tools.*;

public record Richtungslichtquelle(Vec3 richtungS, Color farbe) implements Lichtquelle {

    /**
     * gibt Richtung der Lichtquelle zurueck
     * 
     * @param x zu untersuchender Punkt
     * @return Richtung zur Lichtquelle als Vec3
     */
    public Vec3 richtungLichtquelle(Vec3 x) {
        return richtungS;
    }

    /**
     * gibt Intensitaet der Lichtquelle zurueck
     * 
     * @param x zu untersuchender Punkt
     * @return Farbe des aktuellen Punktes
     */
    public Color intensitaet(Vec3 x) {
        return farbe;
    }

}
