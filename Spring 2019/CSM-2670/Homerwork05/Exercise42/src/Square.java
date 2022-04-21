import java.awt.*;
public class Square extends Rectangle{
    Color color;

    public Square(double x,double y, double lenght,Color color){
        super(x,y,lenght,lenght);
        this.color = color;
    }

    public void setLength(double length)
    {
        super.SetHeight(length);
        super.SetWidth(length);
    }

    @Override
    public void SetWidth(double width)
    {
        setLength(width);
    }

    @Override
    public void SetHeight(double height)
    {
        setLength(height);
    }

    public void SetColor(Color color){this.color = color;}
    public Color GetColor(){return color;}


}
