package cgg.a01;


import java.util.ArrayList;
import java.util.Random;

import static tools.Color.black;
import tools.Color;
import tools.Vec2;

public class Kreisgruppe {
    private ArrayList<Kreis> kreise;

    public Kreisgruppe(int anzahlKreise) {
        kreise = new ArrayList<Kreis>();
        for(int i = 0; i < anzahlKreise; i++) {
            kreise.add(new Kreis(getRandomZahl(400), getRandomZahl(400), getRandomZahl(40) +10, getRandomColor()));
        }
    }

    /**
     * gibt die Farbe fuer ein bestimmtes Pixel zurueck
     * 
     * @param point Punkt zu dem die Farbe herausgefunden werden soll
     * @return Farbe des Pixels
     */
    public Color getColor(Vec2 point) {
        Color c = black; //Standardfarbe mit keinem Kreis darauf
        int kreisradius = 5000; //groeßtmoeglichste Zahl fuer Radius

        for(int j = 0; j < kreise.size(); j++) {
            if(kreise.get(j).coversPoint(point)){ //Ueberpruefung ob der Pixel im Kreis liegt
                if(kreise.get(j).getRadius() < kreisradius) { //Kreisfarbe wird nur geaendert, wenn Kreis kleiner ist
                    c = kreise.get(j).getColor();
                    kreisradius = kreise.get(j).getRadius();
                }
            }
        }
        return c;
    }

    /**
     * erzeugt mit zufaelligen Zahlen eine Farbe
     * 
     * @return zufaellige Farbe
     */
    public Color getRandomColor(){
        Random random = new Random();
        double r = random.nextDouble();
        double g = random.nextDouble();
        double b = random.nextDouble();
        return new Color(r,g,b);
    }

    /**
     * erzeugt eine zufaellige Zahl
     * 
     * @param maximum obere Grenze fuer zufaellige Zahlen
     * @return zufaellige ZAhl bis zum maximum
     */
    public int getRandomZahl(int maximum) {
        Random random = new Random();
        int zahl = random.nextInt(maximum);
        return zahl;
    }
}
