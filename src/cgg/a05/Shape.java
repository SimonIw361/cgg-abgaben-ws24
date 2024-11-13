package cgg.a05;

import cgg.a02.Hit;
import cgg.a02.Ray;

public interface Shape {
    public Hit intersect(Ray ray);

}
