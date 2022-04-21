/**
 * A simple class to demonstrate a function for printing a message repeatedly.
 */
public class EchoMain
{
    /**
     * Prints the given String the given number of times. No space is placed
     * in between messages and no new line is printed unless one is in the
     * message.
     *
     * @param message the string to be printed repeatedly
     * @param times the number of times to print the message
     */
    public static void echoPrint(String message, int times)
    {
        for (int count = 0; count < times; count++)
        {
            System.out.print(message);
        }
    }

    public static void main(String[] args)
    {
        echoPrint("*", 10);
        System.out.println();

        echoPrint("Printing Is Fun", 4);
        System.out.println();
    }
}
