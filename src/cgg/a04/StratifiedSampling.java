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
        //anzahlRaster ist 25
        Color farbePixel = black;
        double punktPlus = 1/25; // 1/anzahlRaster wenn Raster Quadratzahl ist
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                Vec2 punkt = vec2(p.u() + random()/5 + i * punktPlus, p.v() + random()/5 + j * punktPlus);
                farbePixel = add(farbePixel, sampler.getColor(punkt));
            }
        }
        farbePixel = divide(farbePixel, 25);
        
        return farbePixel;
    }

}
