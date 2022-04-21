import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Rectangle {

    private double height;
    private double width;
    private double x;
    private double y;
    Color color;
    Boolean OnorOff;
    Random ran = new Random();

    //constructor
    public Rectangle(double x,double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        OnorOff = false;

    }
    public Rectangle(){this(30,30,20,15);}

    public void draw(Graphics2D g2d)
    {
        if(OnorOff)
        {
            g2d.setColor(Color.RED);

        }

        else
            {
                g2d.setColor(Color.BLACK);

            }

        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, width, height);
        g2d.fill(rect);
        g2d.setColor(Color.GRAY);
        g2d.draw(rect);
    }

    /**
     *
     * @return the state of the rectangle
     */
    public Boolean GetState(){return OnorOff;}

    /**
     *
     * @return the color of the rectangle
     */
    public Color GetColor(){return color;}

    /**
     * @return Rectangle´s width
     */
    public double GetWidth(){return width;}
    /**
     * @return Rectangle´s height
     */
    public double GetHeight(){return height;}
    /**
     * @return Rectangle´s x coordinate
     */
    public double GetX(){return x;}
    /**
     * @return Rectangle´s y coordinate
     */
    public double GetY(){return y;}

    /**
     * sets the rectangle´s color to the desired one
     * @param color desired color
     */
    public void SetColor(Color color){this.color = color;}

    /** sets the Rectangle´s coordinate to a desired values*/
    public void SetCoordintes(double x, double y){this.x = x;this.y = y;}

    public void SetX(double x){this.x = x;}
    public void SetY(double y){this.y = y;}



    /** sets the Rectangle´s width to a desired value*/
    public void SetWidth(double w){this.width = w;}

    /** sets the Rectangle´s height to a desired value*/
    public void SetHeight(double h){this.height = h;}

    /** sets the Rectangle´s state to true*/
    public void TurnOn(){this.OnorOff = true;}

    /** sets the Rectangle´s state to false*/
    public void TurnOff(){this.OnorOff = false;}
}
