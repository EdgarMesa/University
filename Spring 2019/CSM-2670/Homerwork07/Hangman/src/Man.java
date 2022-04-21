import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

public class Man {



    String [] hang = {"HorizontalPole","VerticalPole","Head","Body","Leftarm","Rightarm","Leftleg","Rightleg"};

    public double WIDTH;
    public double HEIGHT;

    Map<String, Boolean> mapparts = new HashMap<>(); //map between the parts and the boolean values

    /**
     * Class to draw the man step by step. If the users guesses the wrong letter, one piece will be added.
     * @param w = width of the screen
     * @param h = height of the screen
     */
    public Man(double w, double h)
    {
        this.WIDTH = w;
        this.HEIGHT = h;
        Createmap();
    }

    public void Createmap()
    {
        for(String c : hang)
        {
            mapparts.put(c,false);
        }
    }

    public void draw(Graphics2D g2d)
    {


        double orix = WIDTH/2+65;
        double oriy = HEIGHT/2;


        //Creating the pols
        Line2D.Double stick1 = new Line2D.Double(orix ,oriy,orix -30,oriy+30);
        Line2D.Double stick2 = new Line2D.Double(orix ,oriy,orix +30,oriy+30);
        Line2D.Double mainstick = new Line2D.Double(orix ,oriy,orix ,oriy-200);
        Line2D.Double horizontslPol = new Line2D.Double(orix ,oriy-200,orix-85 ,oriy-200);
        Line2D.Double verticalPole= new Line2D.Double(orix-85 ,oriy-200,orix-85 ,oriy-170);

        //creating the body
        Ellipse2D.Double head = new Ellipse2D.Double(orix-85-45/2 ,oriy-170,45,40);
        Line2D.Double body= new Line2D.Double(orix-85 ,oriy-130,orix-85 ,oriy-50);

        //creating the arms
        Line2D.Double leftarm= new Line2D.Double(orix-85 ,oriy-110,orix-115 ,oriy-100);
        Line2D.Double rightarm= new Line2D.Double(orix-85 ,oriy-110,orix-55 ,oriy-100);

        //creating the legs
        Line2D.Double leftleg= new Line2D.Double(orix-85 ,oriy-50,orix-55 ,oriy-10);
        Line2D.Double rightleg= new Line2D.Double(orix-85 ,oriy-50,orix-115 ,oriy-10);

        g2d.draw(stick1);
        g2d.draw(stick2);
        g2d.draw(mainstick);

        //if set to false, draw it


        if(mapparts.get("HorizontalPole")){ g2d.draw(horizontslPol);}
        if(mapparts.get("VerticalPole")){ g2d.draw(verticalPole);}
        if(mapparts.get("Head")){ g2d.draw(head);}
        if(mapparts.get("Body")){ g2d.draw(body);}
        if(mapparts.get("Leftarm")){ g2d.draw(leftarm);}
        if(mapparts.get("Rightarm")){ g2d.draw(rightarm);}
        if(mapparts.get("Leftleg")){ g2d.draw(leftleg);}
        if(mapparts.get("Rightleg")){ g2d.draw(rightleg);}


    }



}
