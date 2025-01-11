package cgg.a09;

import static tools.Color.white;
import static tools.Functions.move;
import static tools.Functions.multiply;
import static tools.Functions.rotate;
import static tools.Functions.vec3;

import java.util.ArrayList;

import cgg.Image;
import cgg.a02.Lichtquelle;
import cgg.a02.Lochkamera;
import cgg.a02.Richtungslichtquelle;
import cgg.a05.Group;
import cgg.a09.Animation.Fixture;
import tools.Mat44;

public class Hai implements Animation.SpaceTime {
    int width = 500;
    int height = 500;

    Image image = new Image(width, height);



    public Fixture generateSnapshot(double t) {
        Group hai = Main.haiErstellen();

        Mat44 transformationKameraNormal = move(vec3(0));
        Mat44 transformationKameraHuerden = multiply(move(0,-7000,0), rotate(vec3(1,0,0), 21));
        Lochkamera kamera = new Lochkamera(Math.PI / 4, width, height, transformationKameraNormal);

        ArrayList<Lichtquelle> licht = new ArrayList<>();
        licht.add(new Richtungslichtquelle(vec3(10, -10, 10), white));

        return new Animation.Fixture(image, hai, kamera, licht);
    }


}
