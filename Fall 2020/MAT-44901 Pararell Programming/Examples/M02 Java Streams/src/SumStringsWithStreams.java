import java.util.ArrayList;
import java.util.Random;

// Some simple examples of using streams
public class SumStringsWithStreams
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
            sum += Integer.valueOf(item);
        }
        System.out.printf("Sum = %d, Time = %f (For each loop)\n", sum, watch.elapsedTime());

        watch = new Stopwatch();
        sum = data.stream().map(item -> Integer.valueOf(item)).reduce(0, (a, b) -> a + b);
        System.out.printf("Sum = %d, Time = %f (Map reduce 1)\n", sum, watch.elapsedTime());


        watch = new Stopwatch();
        sum = data.stream().map(item -> Integer.valueOf(item)).reduce(0, Integer::sum);
        System.out.printf("Sum = %d, Time = %f (Map reduce 2)\n", sum, watch.elapsedTime());

        watch = new Stopwatch();
        sum = data.stream().mapToInt(item -> Integer.valueOf(item)).sum();
        System.out.printf("Sum = %d, Time = %f (Map reduce 3)\n", sum, watch.elapsedTime());

        watch = new Stopwatch();
        sum = data.parallelStream().map(item -> Integer.valueOf(item)).reduce(0, (a, b) -> a + b);
        System.out.printf("Sum = %d, Time = %f (Parallel Map reduce 1)\n", sum, watch.elapsedTime());

        watch = new Stopwatch();
        sum = data.parallelStream().map(item -> Integer.valueOf(item)).reduce(0, Integer::sum);
        System.out.printf("Sum = %d, Time = %f (Parallel Map reduce 2)\n", sum, watch.elapsedTime());

        watch = new Stopwatch();
        sum = data.parallelStream().mapToInt(item -> Integer.valueOf(item)).sum();
        System.out.printf("Sum = %d, Time = %f (parallel Map reduce 3)\n", sum, watch.elapsedTime());
    }

}