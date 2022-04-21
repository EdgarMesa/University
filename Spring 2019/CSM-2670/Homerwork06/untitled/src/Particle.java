
import java.awt.geom.Rectangle2D;
import java.awt.*;

/** Particle abstract class that represents the pacman, ghost and foodbits*/
public abstract class Particle {
    public double x,y,dx,dy;
    public double radius;
    public double LimitHeight;


    public Particle(double x, double y, double radius)
    {
        this.x = x-radius;
        this.y = y-radius;
        this.radius = radius;

    }

    abstract void draw(Graphics2D g2d);


    abstract void move(int dir);

    public Rectangle2D.Double SurroundingRectangle()
    {
        return new Rectangle2D.Double(this.GetX(),this.GetY(),this.GetRadius(),this.GetRadius());
    }


    /**
     * @param cell =  Rectangle
     * @return true if the particle is inside, false if the rectangle or not
     */
    public Boolean InCell(Rectangle cell)
    {

        return x > cell.GetX() && y > cell.GetY() && x < cell.GetX()+cell.GetWidth() && y < cell.GetY()+cell.GetHeight();
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
    public double GetRadius(){return radius;}

    /**
     * @return the color of the particle
     */
    abstract Color GetColor();


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


}
