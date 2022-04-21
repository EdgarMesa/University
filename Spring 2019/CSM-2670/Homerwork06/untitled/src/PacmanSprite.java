import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;


/** class that represents the pacmans in the score representing the lives left*/
public class PacmanSprite extends Particle {

    BufferedImage i;

    public PacmanSprite(double x, double y, double radius){super(x,y,radius);}


    @Override
    Color GetColor() {
        return Color.YELLOW;
    }

    @Override
    void draw(Graphics2D g2d)
    {
        // get the default position of pacman from the img dictionary
        File directory = new File("src\\img");
        File[] arr = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains("pacman.derone");
            }
        });

        Arrays.sort(arr);

        try
        {
            i = ImageIO.read(new File(arr[0].toString()));
        } catch (Exception ex) {System.err.println("No image loaded");System.exit(0);}



        // draws the pacman
        g2d.drawImage(i,(int)x,(int)y,null);


    }

    @Override
    void move(int dir) {

    }
}
