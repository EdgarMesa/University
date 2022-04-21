import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Letters class to draw all the letters. If a letter has been already guessed, will be colored gray. Otherwise, black
 */
public class Letters {


    public double WIDTH;
    public double HEIGHT;
    public Character [] alpahabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n'
            ,'o','p','q','r','s','t','u','v','w','x','y','z'};
    Map<Character, Boolean> mapletters = new HashMap<>();


    public Letters(double w, double h)
    {
        this.WIDTH = w;
        this.HEIGHT = h;
        CreateMap();
    }

    /**
     * Create a map between each letter and their own state. true = no guessed jet. false = guessed
     */
    public void CreateMap()
    {
        for(Character letter : alpahabet)
        {
            mapletters.put(letter,true);
        }
    }



    public void draw(Graphics2D g2d)
    {
        int startingx = (int)WIDTH-625;
        int startingy = (int)HEIGHT-100;

        //for each letter
        for(Character letter : mapletters.keySet())
        {
            //if true black
            if(mapletters.get(letter)){g2d.setColor(Color.BLACK);}
            else{g2d.setColor(Color.RED);}
            g2d.setFont(new Font("Monospaced", Font.BOLD, 19));

            g2d.drawString(letter.toString(),startingx,startingy);
            startingx += 23; //gap between each letter
        }
    }

}
