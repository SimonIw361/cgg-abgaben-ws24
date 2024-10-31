package cgg.a02;

import static tools.Functions.divide;
import static tools.Functions.length;
import static tools.Functions.normalize;
import static tools.Functions.subtract;

import tools.Color;
import tools.Vec3;

public record Punktlichtquelle(Vec3 punktLichtquelle, Color farbe) implements Lichtquelle {

    /**
    * berechnet Richtung der Lichtquelle zurueck
    * 
    * @param x zu untersuchender Punkt
    * @return Richtung zur Lichtquelle als Vec3
    */
    public Vec3 richtungLichtquelle(Vec3 x) {
        Vec3 s = normalize(subtract(punktLichtquelle, x));
        return s;
    }

    /**
     * berechnet Intensitaet der Lichtquelle zurueck
     * 
     * @param x zu untersuchender Punkt
     * @return Farbe des aktuellen Punktes
     */
    public Color intensitaet(Vec3 x) {
        double zahl = length(subtract(punktLichtquelle, x));
        return divide(farbe,zahl*zahl);
    }

}
