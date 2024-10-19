package cgg.a02;

import cgg.Image;
import tools.*;

//gleicher Code wie die Main-Methode vom Package a01
public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    // This class instance defines the contents of the image.
    Kugelgruppe kugeln = new Kugelgruppe(30);

    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        image.setPixel(x,y,kugeln.getColor(new Vec2(x, y)));

    // Write the image to disk.
    image.writePng("a02-spheres1");
  }
}
