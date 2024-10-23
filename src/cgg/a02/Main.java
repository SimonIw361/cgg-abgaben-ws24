package cgg.a02;

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
    licht.add(new Punktlichtquelle(new Vec3(1,6,1)));
    Kugelgruppe kugeln = new Kugelgruppe(1, licht);

    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x,y,kugeln.getColor(new Vec2(x, y)));

    // Write the image to disk.
    image.writePng("a03-spheres");
  }
}
