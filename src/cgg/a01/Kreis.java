package cgg.a01;

import static tools.Functions.*;
import tools.*;

public class Kreis {
    Vec2 mitteVec2;
    private int r;
    private Color color;

    public Kreis(Vec2 mittVec2, int r, Color color) {
        this.mitteVec2 = mittVec2;
        this.r = r;
        this.color = color;
    }

    /**
     * ueberprueft ob ein Punkt in dem Kreis liegt oder nicht
     * 
     * @param punktVec2 zu ueberpruefender Punkt
     * @return bollean Wert ob Punkt im Kreis liegt oder nicht
     */
    public boolean coversPoint(Vec2 punktVec2) {
        Vec2 abstandMitte = subtract(mitteVec2, punktVec2);
        double abstandMitteBetrag = length(abstandMitte);
        if (abstandMitteBetrag <= r) {
            return true; 
        }
        else {
            return false;
        }   
    }

    public Color getColor(){
        return color;
    }
}
