import java.awt.*;
import java.util.Random;

public class Rectangle {

    private double height;
    private double width;
    private double x;
    private double y;
    Color color;
    Random ran = new Random();

    //constructor
    public Rectangle(double x,double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = new Color(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256));
    }
    public Rectangle(){this(30,30,20,15);}

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

    /** sets the Rectangle´s width to a desired value*/
    public void SetWidth(double w){this.width = w;}

    /** sets the Rectangle´s height to a desired value*/
    public void SetHeight(double h){this.height = h;}
}
