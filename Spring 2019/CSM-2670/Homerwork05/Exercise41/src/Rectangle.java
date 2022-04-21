
public class Rectangle {

    private double height;
    private double width;
    private double x;
    private double y;

    //constructor
    public Rectangle(double x,double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Rectangle(){this(30,30,20,15);}

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

    /** sets the Rectangle´s coordinate to a desired values*/
    public void SetCoordintes(double x, double y){this.x = x;this.y = y;}

    /** sets the Rectangle´s width to a desired value*/
    public void SetWidth(double w){this.width = w;}

    /** sets the Rectangle´s height to a desired value*/
    public void SetHeight(double h){this.height = h;}
}
