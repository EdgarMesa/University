package mat2670;

import java.util.Scanner;

/**
 * The is a really cool class that does not really do anything.
 *
 * @author aemertz
 */
public class HelloApplication
{
    public static final Scanner cin = new Scanner(System.in);

    public static void main(String[] args)
    {
        // Get the width
        int width = promptForInt2("Enter width: ");

        // Get the height
        int height = promptForInt2("Enter height: ");

        // Draw a box of that size
        drawBox(width, height);

    }

    private static void drawBox(int width, int height)
    {
        // print the top line of width stars.
        drawLine(width);

        // draw each of the middle lines (height - 2)
        for (int i = 0; i < height - 2; i++)
        {
            System.out.print("*");
            echoPrint(width - 2, " ");
            System.out.print("*");
            System.out.println();
        }

        // print the last line of width stars.
        drawLine(width);
    }

    private static void echoPrint(int numberOfTimes, String message)
    {
        for (int j = 0; j < numberOfTimes; j++)
        {
            System.out.print(message);
        }
    }

    private static void drawLine(int width)
    {
        echoPrint(width, "*");
        System.out.println();
    }

    private static int promptForInt(String message)
    {
        // ask for input
        System.out.print(message);
        
        while(!cin.hasNextInt())
        {
            // remove the old input
            String line = cin.nextLine();
            
            // keep asking
            System.err.println("Invaild input: " + line + " is not an int");
            System.out.print(message);
        }
        
        // Return good input
        int result = cin.nextInt();
        cin.nextLine();
        return result;
    }

    private static int promptForInt2(String message)
    {
        // ask for input
        System.out.print(message);
        
        // Get input
        String line = cin.nextLine();
        Integer result = stringToInt(line);
        
        while(result == null)
        {            
            // keep asking
            System.err.println("Invaild input: " + line + " is not one int");
            System.out.print(message);

            // Get input
            line = cin.nextLine();
            result = stringToInt(line);
        }
        
        // Return good input
        return result;
    }
    
    private static Integer stringToInt(String s)
    {
        Scanner stringScanner = new Scanner(s);

        if (!stringScanner.hasNextInt())
        {
            return null;
        }
        
        int result = stringScanner.nextInt();
        
        if (stringScanner.hasNext())
        {
            return null;
        }
        
        return result;
    }

}
