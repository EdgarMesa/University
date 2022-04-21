import java.util.Arrays;
import java.util.Random;

public class SortDemo
{
    private static final int MAX_INT = 5;
    private static final int N = 20;

    public static void main(String[] args)
    {
        // Build a random array of size N with values between [0, MAX_INT]
        final Random rgen = new Random();
        int[] randomArray = new int[N];
        for(int index = 0; index < randomArray.length; index++)
        {
            randomArray[index] = rgen.nextInt(MAX_INT + 1);
        }
        
        System.out.println(Arrays.toString(randomArray));
        
        // Tally the random array
        int[] tallyArray = new int[MAX_INT + 1];
        for(int value : randomArray)
        {
            tallyArray[value]++;
        }
        
        // Place array in sorted order.
        int randomArrayIndex = 0;
        for(int index = 0; index < tallyArray.length; index++)
        {
            for(int count = 0; count < tallyArray[index]; count++)
            {
                randomArray[randomArrayIndex++] = index;
            }
        }
        
        System.out.println(Arrays.toString(randomArray));
    }
    
}
