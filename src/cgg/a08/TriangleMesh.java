package cgg.a08;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cgg.a02.Hit;
import cgg.a02.Ray;
import cgg.a04.Material;
import cgg.a05.Shape;
import tools.BoundingBox;

public class TriangleMesh implements Shape {
    KdTree triangleTree;
    Material material;
    BoundingBox box;
    int anzahl;

    public TriangleMesh(List<Triangle> tris, Material m) {
        box = new BoundingBox();
        this.material = m;
        triangleTree = construct(tris);

        // Ueberpruefung der Dreiecke im Baum (zum debuggen)
        anzahl = 0;
        gibAnzahlDreieckeKdTree(triangleTree);
        System.out.println("Anzahl Dreiecke in KdTree von TriangleMesh: " + anzahl);
    }

    private KdTree construct(List<Triangle> liste) {
        if (liste.size() < 3) {
            return new KdTree(null, null, liste);
        }

        for (int i = 0; i < liste.size(); i++) {
            box = box.extend(liste.get(i).getBoundingBox());
        }

        int trennen = split(liste);
        KdTree links = construct(liste.subList(0, trennen));
        KdTree rechts = construct(liste.subList(trennen, liste.size()));

        KdTree tree = new KdTree(links, rechts, null);
        return tree;
    }

    /**
     * 
     * @return erstes Element der Liste welches schon zum rechten Baum gehört
     */
    private int split(List<Triangle> liste) {
        // laengste Seite finden
        double x = box.size().x();
        double y = box.size().y();
        double z = box.size().z();

        Collections.sort(liste, new Comparator<Triangle>() {
            @Override
            public int compare(Triangle o1, Triangle o2) {
                char axis = ' ';
                if (x >= y && x >= z) {
                    axis = 'x';
                }
                if (y >= x && y >= z) {
                    axis = 'y';
                }
                if (z >= x && z >= y) {
                    axis = 'z';
                }
                switch (axis) {
                    case 'x':
                        return Double.compare(o1.getBoundingBox().min().x(), o2.getBoundingBox().min().x());
                    case 'y':
                        return Double.compare(o1.getBoundingBox().min().y(), o2.getBoundingBox().min().y());
                    case 'z':
                        return Double.compare(o1.getBoundingBox().min().z(), o2.getBoundingBox().min().z());
                }
                return 0; // tritt nie auf da bei switch immer was erfuellt wird
            }
        });

        return liste.size() / 2;
    }

    @Override
    public Hit intersect(Ray ray) {
        if (!triangleTree.getBoundingBox().intersect(ray)) {
            return null;
        }
        Hit h1 = triangleTree.getLeftKdTree().intersect(ray);
        Hit h1m = null;
        if (h1 != null && h1.getMaterial() == null) { //wenn Farbe uebergeben wurde wird diese genommen, sonst die Textur von TriangleMesh
            h1m = new Hit(h1.getT(), h1.getTrefferPunkt(), h1.getNormalenVektor(), material, h1.getShape(), h1.getuv()); //Textur nehmen
        }
        else{
            h1m = h1; //Farbe von Dreieck nehmen
        }

        Hit h2 = triangleTree.getRightKdTree().intersect(ray);
        Hit h2m = null;
        if (h2 != null && h2.getMaterial() == null) {
            h2m = new Hit(h2.getT(), h2.getTrefferPunkt(), h2.getNormalenVektor(), material, h2.getShape(), h2.getuv());
        }
        else {
            h2m = h2;
        }

        if (h1m == null) {
            return h2m;
        } else if (h2m == null) {
            return h1m;
        }
        if (h1m.getT() < h2m.getT()) {
            return h1m;
        } else {
            return h2m;
        }

    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    private void gibAnzahlDreieckeKdTree(KdTree baum) {
        // int anzahl = 0;
        if (baum.getRightKdTree() != null) {
            gibAnzahlDreieckeKdTree(baum.getRightKdTree());
        }
        if (baum.getLeftKdTree() != null) {
            gibAnzahlDreieckeKdTree(baum.getLeftKdTree());
        }

        if (baum.getDreiecke() != null) {
            // System.out.println(baum.getDreiecke().size());
            anzahl = anzahl + baum.getDreiecke().size();
            return;
        }
    }

}
