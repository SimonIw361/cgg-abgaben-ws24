package cgg.a02;

public class Main2 {
    public static void main(String[] args) {
        Lochkamera cam = new Lochkamera(Math.PI/2,10 , 10);
        System.out.println(cam.gibStrahl(0, 0));
        System.out.println(cam.gibStrahl(5, 5));
        System.out.println(cam.gibStrahl(10, 10));
    }

}
