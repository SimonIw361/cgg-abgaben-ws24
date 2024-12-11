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

    public TriangleMesh(List<Triangle> tris, Material m) {
        this.material = m;
        triangleTree = construct(tris);
    }

    private KdTree construct(List<Triangle> liste) {
        if (liste.size() < 3) {
            return new KdTree(null, null, liste);
        }

        box = BoundingBox.empty;
        for (int i = 0; i < liste.size(); i++) {
            box = BoundingBox.around(box, liste.get(i).getBoundingBox());
        }

        int trennen = split(liste);

        KdTree links = construct(liste.subList(0, trennen - 1));
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
                switch(axis) {
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
        Hit h2 = triangleTree.getRightKdTree().intersect(ray);

        if (h1 == null) {
            return h2;
        } else if (h2 == null) {
            return h1;
        }
        if (h1.getT() < h2.getT()) {
            return h1;
        } else {
            return h2;
        }

    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

}
