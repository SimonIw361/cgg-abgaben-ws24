package cgg.a09;

public class Main {

  public static void main(String[] args) throws Exception{
    long startZeit = System.currentTimeMillis();

    Video video = new Video();

    int ssmpl = 2;
    Animation.render(0.0, 17, 8, video, ssmpl, "video09");

    long endZeit = System.currentTimeMillis();
    long dauer = (endZeit - startZeit) / 1000;
    System.out.println("Zeit fuer die Berechnung des Bildes: " + dauer + " s");
  }
}

