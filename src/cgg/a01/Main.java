
package cgg.a01;

import cgg.Image;
import static tools.Color.*;
import tools.*;

public class Main {

  public static void main(String[] args) {
    int width = 400;
    int height = 400;

    // This class instance defines the contents of the image.
    var kreis = new Kreis(200,200, 100, blue);

    // Creates an image and iterates over all pixel positions inside the image.
    var image = new Image(width, height);
    for (int x = 0; x != width; x++)
      for (int y = 0; y != height; y++)
        // Sets the color for one particular pixel.
        if(kreis.coversPoint(new Vec2(x, y))) {
          image.setPixel(x, y, kreis.getColor());
        }
        else {
          image.setPixel(x, y, black);
        }

    // Write the image to disk.
    image.writePng("Kreis");
  }
}
