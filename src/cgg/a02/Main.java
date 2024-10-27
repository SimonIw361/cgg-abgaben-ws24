package cgg.a02;

import static tools.Color.blue;
import static tools.Color.red;
import static tools.Functions.add;

import java.util.ArrayList;

import cgg.Image;
import tools.*;

//gleicher Code wie die Main-Methode vom Package a01
public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    // This class instance defines the contents of the image.
    ArrayList<Lichtquelle> licht = new ArrayList<>();
    licht.add(new Richtungslichtquelle(new Vec3(10,0,0), blue));
    Kugelgruppe kugeln = new Kugelgruppe(licht);

    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    Color blau = new Color(0,0,1);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x,y,kugeln.getColor(new Vec2(x, y)));
        //image.setPixel(x, y, blau);
        //System.out.println(blau);
        //blau = add(blau, new Color(0, 0, 0.1));}
    // Write the image to disk.
    image.writePng("a03-spheres");
  }
}
