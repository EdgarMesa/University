
import java.util.Random;
import java.awt.*;

public class Particle {
    private double x,y,dx,dy;
    private static final Random ran = new Random();
    private Color color;
    private int radius;

    public Particle(int x, int y, int radius, int maxspeed)
    {
        color = new Color(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256));
        this.x = x-radius;
        this.y = y-radius;
//        double angle = ran.nextDouble()*2*Math.PI;
        this.dx = ran.nextInt(maxspeed+1);
        this.dy = ran.nextInt(maxspeed+1);
        this.radius = radius;
    }

    /**
     * Changes the X coordinate by DX
     */
    public void moveX(){this.x += dx;}

    /**
     * Changes the Y coordinate by DY
     */
    public void moveY(){this.y += dy;}


    /**
     * @return the X value of the particle
     */
    public double GetX(){return x;}

    /**
     * @return the Y value of the particle
     */
    public double GetY(){return y;}

    /**
     * @return the Dx value of the particle
     */
    public double GetDX(){return dx;}

    /**
     * @return the Dy value of the particle
     */
    public double GetDY(){return dy;}

    /**
     * @return the radius value of the particle
     */
    public int GetRadius(){return radius;}

    /**
     * @return the color of the particle
     */
    public Color GetColor(){return color;}




    /** sets the x value to a desired value
     * @param x coordinate
     */
    public void SetX(double x){this.x = x;}

    /** sets the y value to a desired value
     * @param y coordinate
     */
    public void SetY(double y){this.y = y;}

    /** sets the dx value to a desired value
     * @param dx speed
     */
    public void SetDX(double dx){this.dx = dx;}

    /** sets the dy value to a desired value
     * @param dy speed
     */
    public void SetDY(double dy){this.dy = dy;}

    /** sets the radius of the particle value to a desired value
     * @param dia radius
     */
    public void SetRadius(int dia){this.radius = dia;}


    /** sets the color of the particle to a desired color
     * @param color
     */
    public void SetAppearence(Color color){this.color = color;}





}
