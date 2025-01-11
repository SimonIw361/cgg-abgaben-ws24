package cgg.a09;

import static tools.Color.white;

import java.io.File;
import java.util.ArrayList;

import cgg.Image;
import cgg.a02.Lichtquelle;
import cgg.a02.Lochkamera;
import cgg.a02.RayTracer;
import cgg.a04.StratifiedSampling;
import cgg.a05.Shape;

public class Animation {

    record Fixture(Image image, Shape scene, Lochkamera kamera, ArrayList<Lichtquelle> lights) {}

    interface SpaceTime {
        Fixture generateSnapshot(double t);
    }

    //was ist spp
    public static void render(double start, double stop, int fps, SpaceTime scene, int spp, String dir) throws Exception {
        new ProcessBuilder("rm", "-r", "images/" + dir).start().waitFor();
        new ProcessBuilder("mkdir", "images/" + dir).start().waitFor();

        double frameTime = 1.0/fps;
        int frameNumber = 0;

        for(double t = start; t <= stop; t += frameTime){
            Fixture fixture = scene.generateSnapshot(t);
            RayTracer raytracer = new RayTracer(fixture.kamera(), fixture.scene(), fixture.lights(), white);
            //fixture.image().sampleStream(new StratifiedSampling(raytracer)); //mit RayTracing
            fixture.image().sampleStream(raytracer); //ohne RayTracing
            String filename = String.format("%sframe-%04d", dir, frameNumber++);
            fixture.image().writeHdr(filename);
        }

        videoErstellen(fps, dir);
    }

    private static void videoErstellen(int fps, String dir) {
    String[] ffmpeg = {"ffmpeg", "-y", "-loglevel", "panic", "-r", Integer.valueOf(fps).toString(), "-start_number", "0", "-i", "video09frame-%04d.png",
                        "-pix_fmt", "yuv420p", "-vcodec", "libx264", "-crf", "16", "-preset", "veryslow", "video.mp4"};
    try{
        new ProcessBuilder(ffmpeg)
            .directory(new File("images/" +dir))
            .start()
            .waitFor();
        /*new ProcessBuilder("open", "-a", "quicktime player", "video.mp4")
            .directory(new File("images/" +dir))
            .start()
            .waitFor();*/

    } catch(Exception e){
        System.out.println(e);
    }
  }

}
