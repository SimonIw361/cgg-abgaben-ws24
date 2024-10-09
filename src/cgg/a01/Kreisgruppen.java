package cgg.a01;

import java.util.ArrayList;
//import static tools.Functions.*;
import tools.*;

public class Kreisgruppen {
    private ArrayList<Kreis> kreise;

    public Kreisgruppen(int anzahlKreise) {
        kreise = new ArrayList<Kreis>();
        for(int i = 0; i < anzahlKreise; i++) {
            kreise.add(new Kreis(new Vec2(1,1), 3, new Color(1,0,1)));
        }
    }

    public Color getColor(Vec2 point) {
        //Color color = new Color(1,0,1);
        for(int j = 0; j < kreise.size(); j++) {
            if(kreise.get(j).coversPoint(point)){
                return kreise.get(j).getColor();
            }
        }
        return new Color(0, 0, 0);
    }

}
