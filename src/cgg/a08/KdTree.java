package cgg.a08;

import java.util.List;

import cgg.a02.Hit;
import cgg.a02.Ray;
import cgg.a05.Shape;
import tools.BoundingBox;

public class KdTree implements Shape{
    private List<Triangle> triangles;
    private KdTree left;
    private KdTree right;
    private BoundingBox box;

    public KdTree(KdTree left, KdTree right, List<Triangle> t) {
        this.triangles = t;
        this.left = left;
        this.right = right;
        if(left == null && right == null) {
            box = BoundingBox.empty;
        }
        else if(left == null) {
            box = BoundingBox.around(right.box);
        }
        else if(right == null) {
            box = BoundingBox.around(left.box);
        } 
        else {
            box = BoundingBox.around(left.box, right.box);
        }
        
    }

    @Override
    public Hit intersect(Ray ray) {
        if(triangles == null) {
            Hit h1 = left.intersect(ray);
            Hit h2 = right.intersect(ray);

            if(h1 == null) {
                return h2;
            }
            else if(h2 == null) {
                return h1;
            }
            if(h1.getT() < h2.getT()) {
                return h1;
            }
            else {
                return h2;
            }
        }

        Hit h = null;
        for(int i=0; i< triangles.size(); i++) {
            Hit hNeu = triangles.get(i).intersect(ray);
            if(h == null) {
                h = hNeu;
            }
            else if(hNeu != null && h != null && hNeu.getT() < h.getT()) {
                h = hNeu;
            }
        }
        return h;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    public KdTree getLeftKdTree() {
        return left;
    }

    public KdTree getRightKdTree() {
        return right;
    }


}
