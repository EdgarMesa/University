import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Rectangle {

    private double height;
    private double width;
    private double x;
    private double y;
    private Color color;
    private Boolean OnorOff;
    private int state = 0;

    //constructor
    public Rectangle(double x,double y, double width, double height,Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        OnorOff = false;
        this.color = color;


    }
    public Rectangle(){this(30,30,20,15,Color.RED);}


    public void draw(Graphics2D g2d)
    {
        g2d.setColor(color);
        Rectangle2D.Double rec = new Rectangle2D.Double(x,y,width,height);
        g2d.fill(rec);

    }

    public void SetState(int s)
    {
        this.state = s;
    }


    public void drawdraw(Graphics2D g2d)
    {
        g2d.setColor(color);
        Rectangle2D.Double rec = new Rectangle2D.Double(x,y,width,height);
        g2d.draw(rec);

    }

    public int GetState(){return state;}


    /**
     *
     * @return the state of the rectangle
     */
    public Boolean GetOnorOf(){return OnorOff;}

    /**
     *
     * @return the color of the rectangle
     */
    public Color GetColor() {return color;}

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

    public Boolean Inside( Rectangle w)
    {
        double midx = x+width/2.0;
        double midy = y+height/2.0;


        return midx > w.x && midy > w.y && midx < w.x +w.width && midy < w.y+w.height;

    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "height=" + height +
                ", width=" + width +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
