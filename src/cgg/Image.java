
package cgg;

import static tools.Functions.vec2;

import java.util.stream.Stream;

import tools.*;

public class Image implements tools.Image {
    int width;
    int height;
    double[] pixelFarben;

    /**
     * @param width Breite des Bildes
     * @param height Hoehe des Bildes
     */
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixelFarben = new double[3*width*height];
    }

    /**
     * setzt die Pixelfarben fuer dieses Bild
     * 
     * @param sampler Szene die beschrieben wird, muss Sampler implementieren
     */
    public void sample(Sampler sampler) {
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++){
                // Sets the color for one particular pixel.
                this.setPixel(x,y,sampler.getColor(new Vec2(x, y)));
                //System.out.println(x + " " + y); //zum Debuggen
            }
        }
    }

    /**
     * setzt die Pixelfarben fuer dieses Bild mit Multi Threading
     * 
     * @param sampler Szene die beschrieben wird, muss Sampler implementieren
     */
    public void sampleStream(Sampler sampler) {
        Stream.iterate(0, y -> y != height, y -> y + 1)
            .unordered().parallel()
            .forEach(y -> Stream.iterate(0, x -> x != width, x -> x + 1)
            .forEach(x -> setPixel(x, y, sampler.getColor(vec2(x,y)))));
    }

    /**
     * setzt fuer einen Pixel die gegebene Farbe
     * 
     * @param x Koordinate x des Pixels
     * @param y Koordinate y des Pixels
     * @param color Farbe die gesetzt werden soll
     */
    public void setPixel(int x, int y, Color color) {
        pixelFarben[3*(y*width + x) +0] = color.r();
        pixelFarben[3*(y*width + x) +1] = color.g();
        pixelFarben[3*(y*width + x) +2] = color.b();
    }

    /**
     * gibt den Farbwert vom gegebenen Pixel zurueck
     * 
     * @param x Koordinate x des Pixels
     * @param y Koordinate y des Pixels
     * @return Farbwert des gegebenen Pixels als Color
     */
    public Color getPixel(int x, int y) {
        double r = pixelFarben[3*(y*width + x) +0];
        double g = pixelFarben[3*(y*width + x) +1];
        double b = pixelFarben[3*(y*width + x) +2];
        Color color = new Color(r, g, b);
        return color;
    }

    /**
     * @param name name des gespeicherten Bilds
     */
    public void writePng(String name) {
        ImageWriter.writePng(name, pixelFarben, width, height);
    }

    /**
     * @param name name des gespeicherten Bilds
     */
    public void writeHdr(String name) {
        ImageWriter.writePng(name, pixelFarben, width, height);
    }

    /**
     * @return Breite des Bildes
     */
    public int width() {
        return width;
    }

    /**
     * @return Hoehe des Bildes
     */
    public int height() {
        return height;
    }
}
