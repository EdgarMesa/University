import java.util.ArrayList;
import java.util.Random;

// Some simple examples of using streams
public class SumFilteredStringsWithStreams
{

    public static void main(String[] args)
    {
        // Make a list of random strings of numbers to simulate some data
        final int size = 1000000;
        final Random rand = new Random();
        final ArrayList<String> data = new ArrayList<>(size);
        for(int i = 0; i < size; i++)
        {
            data.add("" + rand.nextInt(1000));
        }
        Stopwatch watch = new Stopwatch();
        int sum = 0;
        for(String item : data)
        {
            Integer i = Integer.valueOf(item);
            if(i % 2 == 0)
            {
                sum += i;
            }
        }
        System.out.printf("Sum = %d, Time = %f (For each loop)\n", sum, watch.elapsedTime());

        watch = new Stopwatch();
        sum = data.parallelStream().map(item -> Integer.valueOf(item)).filter(item -> item % 2 == 0)
                .reduce(0, Integer::sum);
        System.out.printf("Sum = %d, Time = %f (Parallel Map reduce)\n", sum, watch.elapsedTime());
    }

}