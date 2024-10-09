
package cgg;

import tools.*;

public class Image implements tools.Image {
    double[] pixelFarben;

    /**
     * @param width Breite des Bildes
     * @param height Hoehe des Bildes
     */
    public Image(int width, int height) {
        pixelFarben = new double[width*height*3];
    }

    /**
     * setzt fuer einen Pixel die gegebene Farbe
     * 
     * @param x Koordinate x des Pixels
     * @param y Koordinate y des Pixels
     * @param color Farbe die gesetzt werden soll
     */
    public void setPixel(int x, int y, Color color) {
        pixelFarben[x*y+0] = color.r();
        pixelFarben[x*y+1] = color.g();
        pixelFarben[x*y+2] = color.b();
    }

    /**
     * gibt den Farbwert vom gegebenen Pixel zurueck
     * 
     * @param x Koordinate x des Pixels
     * @param y Koordinate y des Pixels
     * @return Farbwert des gegebenen Pixels als Color
     */
    public Color getPixel(int x, int y) {
        double r = pixelFarben[x*y+0];
        double g = pixelFarben[x*y+1];
        double b = pixelFarben[x*y+2];
        Color color = new Color(r, g, b);
        return color;
    }

    public void writePng(String name) {
        // TODO This call also needs to be adjusted once Image() and setPixel()
        // are implemented. Use
        // ImageWriter.writePng(String name, double[] data, int width, int height) to
        // write the image data to disk in PNG format.
    }

    public void writeHdr(String name) {
        // TODO This call also needs to be adjusted once Image() and setPixel()
        // are implemented. Use
        // ImageWriter.writePng(String name, double[] data, int width, int height) to
        // write the image data to disk in OpenEXR format.
    }

    public int width() {
        // TODO This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual width of the Image is
        // returned.
        return 0;
    }

    public int height() {
        // TODO This is just a dummy value to make the compiler happy. This
        // needs to be adjusted such that the actual height of the Image is
        // returned.
        return 0;
    }
}
