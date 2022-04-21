import java.awt.*;
import java.awt.geom.Line2D;

public class Line {
    public double x1,y1,x2,y2;
    public Color color;

    public Line(double x1,double y1,double x2,double y2,Color color)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    protected void draw(Graphics2D g2d)
    {
        Line2D.Double line = new Line2D.Double(x1,y1,x2,y2);
        g2d.draw(line);
    }

}
