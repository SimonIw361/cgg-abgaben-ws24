package cgg.a09;

import static tools.Color.white;
import static tools.Functions.color;
import static tools.Functions.move;
import static tools.Functions.multiply;
import static tools.Functions.rotate;
import static tools.Functions.vec3;

import java.util.ArrayList;
import java.util.List;

import cgg.Image;
import cgg.a01.ConstantColor;
import cgg.a02.Lichtquelle;
import cgg.a02.Lochkamera;
import cgg.a02.Richtungslichtquelle;
import cgg.a04.PhongMaterial;
import cgg.a05.Group;
import cgg.a06.DiffusStreuung;
import cgg.a07.Ebene;
import cgg.a08.Triangle;
import cgg.a08.TriangleMesh;
import cgg.a09.Animation.Fixture;
import tools.Mat44;
import tools.Vertex;
import tools.Wavefront;
import tools.Wavefront.MeshData;
import tools.Wavefront.TriangleData;

public class Video implements Animation.SpaceTime {
    int width = 600;
    int height = 600;

    Image image = new Image(width, height);



    public Fixture generateSnapshot(double t) {
        //Group hai = new Group(multiply(launch(t, 1), move(vec3(0,0,-40)), rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Shark/Origami_Shark.obj"));
        Group pig1 = new Group(multiply(launchPig1(t, 1.5), move(vec3(-7,5,-34)), rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Pig/Origami_Pig.obj"));
        Group pig2 = new Group(multiply(launchPig3(t, 1), move(vec3(-1,5,-21)), rotate(vec3(0,1,0), -40),rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Pig/Origami_Pig.obj"));
        Group pig3 = new Group(multiply(launchPig2(t, 1), move(vec3(-12,5,-22)), rotate(vec3(0,1,0), 40),rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Pig/Origami_Pig.obj"));
        Group schweine = new Group(pig1, pig2, pig3);

        Group panda1 = new Group(multiply(launchPanda1(t, 1), move(vec3(7,5,-24)), rotate(vec3(0,1,0), -90), rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Panda/Origami_Panda.obj"));
        Group panda2 = new Group(multiply(move(vec3(10,4,-14)), rotate(vec3(0,1,0), 90),rotate(vec3(1,0,0), 90), rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Panda/Origami_Panda.obj"));
        Group pandas = new Group(panda1, panda2);

        Group vogel1 = new Group(multiply(launchVogel1(t, 4), move(vec3(6,-5,-80)), rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Bird/Origami_Bird.obj"));
        Group vogel2 = new Group(multiply(launchVogel2(t, 5), move(vec3(-7.2,-2.1,-8)), rotate(vec3(0,1,0), 90), rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Bird/Origami_Bird.obj"));
        Group vogel3 = new Group(multiply(launchVogel3(t, 2), move(vec3(7,-3,-8)), rotate(vec3(0,1,0), -90), rotate(vec3(0,0,1), 180)), tierErstellen("data/Tiere/Origami_Bird/Origami_Bird.obj"));
        Group voegel = new Group(vogel1, vogel2, vogel3);

        PhongMaterial gruen = new PhongMaterial(color(0,0.52,0), white, 1000.0);
        PhongMaterial blau = new PhongMaterial(color(0.15,0.56,0.98), white, 1000.0);
        Group gras = new Group(move(0,5,0), new Ebene("unbegrenzt", 0, gruen));
        Group himmel = new Group(multiply(move(0,0,-300), rotate(vec3(1,0,0), 90)), new Ebene("quadratisch", 370, blau));
        Group hintergrund = new Group(gras, himmel, schweine, pandas, voegel);

        Group welt = new Group(hintergrund);
        Mat44 transformationKameraNormal = multiply(launchKamera(t,1),move(vec3(0,0,7)));
        Lochkamera kamera = new Lochkamera(Math.PI / 4, width, height, transformationKameraNormal);

        ArrayList<Lichtquelle> licht = new ArrayList<>();
        licht.add(new Richtungslichtquelle(vec3(10, -10, 10), white));

        return new Animation.Fixture(image, welt, kamera, licht);
    }

    public static Mat44 launchVogel1(double t, double thrust) {
        t = Math.min(t, 7);
        return move(0,0, 0.5 * t* t*thrust);
    }

    public static Mat44 launchVogel2(double t, double thrust) {
        if(t < 5) {
            t = 0;
        }
        else {
            t = t -5;
            t = Math.min(t, 8);
        }
        return move(0.5 * t* t*thrust,0, 0);
    }

    public static Mat44 launchVogel3(double t, double thrust) {
        t = Math.min(t, 8);
        return move(-0.5 * t* t*thrust,0, 0);
    }

    public static Mat44 launchPanda1(double t, double thrust) {
        t = Math.min(t, 5);
        return move(-0.5 * t* t*thrust,0, 0);
    }

    public static Mat44 launchPig1(double t, double thrust) {
        t = Math.min(t, 4);
        return move(0,0, 0.5 * t* t*thrust);
    }

    public static Mat44 launchPig2(double t, double thrust) {
        t = Math.min(t, 1.7);
        return move(0.3 * t* t*thrust,0, 0.3 * t* t*thrust);
    }

    public static Mat44 launchPig3(double t, double thrust) {
        t = Math.min(t, 4);
        return move(-0.5 * t* t*thrust,0, 0.5 * t* t*thrust);
    }

    public static Mat44 launchKamera(double t, double thrust) {
        //t = Math.min(t, 4);
        t = Math.max(t,0.6);
        return move(0.3 * t* t*thrust,0, 0);
    }


    private static Group tierErstellen(String src) {
        List<MeshData> liste = Wavefront.loadMeshData(src); // Huerde

        List<Triangle> tr = new ArrayList<>();
        for (int i = 0; i < liste.size(); i++) {
            List<TriangleData> data = liste.get(i).triangles();
            // Dreiecke neu erstellen und jeweilige Farbe auch hinzufuegen
            for (int j = 0; j < data.size(); j++) {
                Vertex v0 = new Vertex(data.get(j).v0().position(), data.get(j).v0().normal(), data.get(j).v0().uv(),
                    liste.get(i).material().kd());
                Vertex v1 = new Vertex(data.get(j).v1().position(), data.get(j).v1().normal(), data.get(j).v1().uv(),
                    liste.get(i).material().kd());
                Vertex v2 = new Vertex(data.get(j).v2().position(), data.get(j).v2().normal(), data.get(j).v2().uv(),
                    liste.get(i).material().kd());
                Triangle d = new Triangle(v0, v1, v2);
                tr.add(d);
            }
        }

        // als Material kann nur null uebergeben werden, weil jedes Dreieck ueber Vertex eigene Farbe hat
        TriangleMesh trMesh = new TriangleMesh(tr, null);
        Group tier = new Group(trMesh);
        return tier;
    }

}
