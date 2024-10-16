package cgg.a02;

import tools.*;

public class Hit {
    private int t;
    private Vec3 trefferPunkt;
    private Vec3 normalenVektor;
    private Color farbeOberflaeche;

    public Hit(int t, Vec3 trefferPunkt, Vec3 normalenVektor, Color farbeOberflaeche) {
        this.t = t;
        this.trefferPunkt = trefferPunkt;
        this.normalenVektor = normalenVektor;
        this.farbeOberflaeche = farbeOberflaeche;
    }

}
