package cgg.a04;

import static tools.Color.black;
import static tools.Functions.add;
import static tools.Functions.divide;
import static tools.Functions.random;
import static tools.Functions.vec2;

import tools.Color;
import tools.Sampler;
import tools.Vec2;

public record StratifiedSampling(Sampler sampler) implements Sampler {

    public Color getColor(Vec2 p) {
        //anzahlRaster ist 16
        Color farbePixel = black;
        double punktPlus = 1/4; // 1/sqrt(anzahlRaster) wenn Raster Quadratzahl ist, sqrt(anzahlRaster) auch jeweils in die Schleifen schreiben fuer i und j
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                Vec2 punkt = vec2(p.u() + random()/4 + i * punktPlus, p.v() + random()/4 + j * punktPlus); //random durch 4 wegen anzahlRaster = 16
                farbePixel = add(farbePixel, sampler.getColor(punkt));
            }
        }
        farbePixel = divide(farbePixel, 16); //durch anzahlRaster teilen, weil davor alle addiert wurden
        
        return farbePixel;
    }
}
