import java.util.ArrayList;
import java.util.Random;

public class ParallelReduce
{

    public static void main(String[] args)
    {

        // Create a list of Integer values.
        final ArrayList<Double> data = new ArrayList<>();
        final int size = 1000;
        final Random rand = new Random();
        for(int i = 0; i < size; i++)
        {
            data.add((double) rand.nextInt(10000));
        }

        // Stream to sum the square roots of its values
        System.out.println(data.stream().reduce(0.0, (a, b) -> a + Math.sqrt(b)) +
                " (Steam)");

        // Parallel stream that tries to sum the square roots its values but fails as
        // the partial results are combined by taking the square root of one of them.
        System.out.println(data.parallelStream().reduce(0.0, (a, b) -> a + Math.sqrt(b)) +
                " (Parallel Stream)");


        // Parallel stream that works as it uses a combiner that just adds the partial results.
        // Note that there still may be a difference from the Sequential result (think about
        // why that is).
        System.out.println(data.parallelStream().reduce(0.0, (a, b) -> a + Math.sqrt(b), (a, b) -> a + b) +
                " (Parallel Stream with Combiner)");
    }
}
