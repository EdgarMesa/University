import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;


/**
 * Class to displaye the word to guess. by default are the letter are going to be mapped to false.
 * if the users make a right guess it will turn true and the letter will be displayed
 */
public class GuessedWord {



    public String guessedw;
    public char[] letters;
    public double WIDTH;
    public double HEIGHT;
    Map<Character, Boolean> mapletters = new HashMap<>(); //map between the letters and the boolean values


    public GuessedWord(String guess ,double w, double h)
    {
        this.guessedw = guess;
        this.WIDTH = w;
        this.HEIGHT = h;
        letters = guessedw.toCharArray();
        Createmap();

    }

    /**
     * Creates the map
     */
    public void Createmap()
    {
        for(char c : letters)
        {
            mapletters.put(c,false);
        }
    }


    /**
     * Displays the letters if their value is true
     * @param g2d
     */
    public void draw(Graphics2D g2d)
    {
        int sum = 0;
        for(char c :letters) //calculates the total length of the letters
        {
            sum +=23;
        }

        g2d.setColor(Color.BLACK);
        int startingx = (int)WIDTH/2-sum/2;
        int startingy = (int)HEIGHT-170;
        for(char c :letters)
        {
            g2d.setFont(new Font("Monospaced", Font.BOLD, 22));

            if(mapletters.get(c)){g2d.drawString(Character.toString(c),startingx,startingy);} //if the value is true, draw the letters
            Line2D.Double line = new Line2D.Double(startingx,startingy+7,startingx+12,startingy+7); // draws the underline to know how many letters the world has
            g2d.draw(line);

            startingx += 23; //gap between each letter
        }
    }




}
