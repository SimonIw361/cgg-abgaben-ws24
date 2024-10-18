package cgg.a02;

import static tools.Functions.*;

public class Main2 {
    public static void main(String[] args) {
        testAufgabe2_3();
    }

    private static void testAufgabe2_2() {
        Lochkamera cam = new Lochkamera(Math.PI/2,10 , 10);
        System.out.println(cam.gibStrahl(0, 0));
        System.out.println(cam.gibStrahl(5, 5));
        System.out.println(cam.gibStrahl(10, 10));
    }

    private static void testAufgabe2_3() {
        var s1 = new Kugel(vec3(0, 0, -2), 1);
        var s2 = new Kugel(vec3(0, -1, -2), 1);
        var s3 = new Kugel(vec3(0, 0, 0), 1);
        var r1 = new Ray(vec3(0, 0, 0), vec3(0, 0, -1), 0, 100);
        var r2 = new Ray(vec3(0, 0, 0), vec3(0, 1, -1), 0, 100);
        System.out.println(s1.intersect(r1));
        System.out.println(s1.intersect(r2));
        System.out.println(s2.intersect(r1));
        System.out.println(s3.intersect(r1));
    }

}
