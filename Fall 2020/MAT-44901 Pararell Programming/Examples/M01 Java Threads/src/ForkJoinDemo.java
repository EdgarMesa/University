// The ForkJoin framework is well suited to the divide and conquer strategy.
// This example transforms an array by recursively breaking it into pieces
// until we reach a point where it will be faster to to do sequentially. Note
// the JavaDoc for ForkJoinTask says
//
// "ForkJoinTasks should perform relatively small amounts of computation.
// Large tasks should be split into smaller subtasks, usually via recursive
// decomposition. As a very rough rule of thumb, a task should perform more
// than 100 and less than 10000 basic computational steps, and should avoid
// indefinite looping. If tasks are too big, then parallelism cannot improve
// throughput. If too small, then memory and internal task maintenance
// overhead may overwhelm processing."
// https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ForkJoinTask.html

import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;

// Demonstrate parallel execution.  
public class ForkJoinDemo
{

    // This RecursiveAction transforms the elements of an array of doubles
    // into their square roots.
    private static class SqrtTransform extends RecursiveAction
    {
        // This threshold is arbitrarily set. For real world code, this value
        // should be determined by profiling and experimentation.  
        final int threshold = 1000;

        // Array to be accessed. 
        double[] data;

        // Determines what part of data to process. 
        int start, end;

        SqrtTransform(double[] values, int start, int end)
        {
            data = values;
            this.start = start;
            this.end = end;
        }

        // This is the method in which parallel computation will occur and is analogous to run
        // or call
        @Override
        protected void compute()
        {

            // If number of elements is below the sequential threshold, 
            // then process sequentially. 
            if ((end - start) < threshold)
            {
                // Transform each element into its square root. 
                for (int i = start; i < end; i++)
                {
                    data[i] = Math.sqrt(data[i]);
                }
            } else
            {
                // Otherwise, continue to break the data into smaller pieces. 

                // Find the midpoint. 
                int middle = start + (end - start) / 2;

                // Invoke new tasks, using the subdivided data.
                // Note that invokeAll lets us invoke many tasks
                // given either as parameters or in a collection.
                invokeAll(new SqrtTransform(data, start, middle),
                        new SqrtTransform(data, middle, end));
            }
        }
    }

    public static void main(String args[])
    {
        // Create a task pool using the default level of parallelism
        // (i.e. parallelism equal to Runtime.availableProcessors()). 
        ForkJoinPool pool = new ForkJoinPool();

        // Build the data
        double[] nums = new double[100000];
        for (int i = 0; i < nums.length; i++)
        {
            nums[i] = (double) i;
        }

        // Print out some of the data
        for (int i = 0; i < 10; i++)
        {
            System.out.print(nums[i] + " ");
        }
        System.out.print("... ");
        for (int i = 10; i > 0 ; i--)
        {
            System.out.print(nums[nums.length - i] + " ");
        }
        System.out.println("\n");

        // Start the main ForkJoinTask. 
        pool.invoke(new SqrtTransform(nums, 0, nums.length));

        // Print out some of the data
        for (int i = 0; i < 10; i++)
        {
            System.out.format("%.4f ", nums[i]);
        }
        System.out.print("... ");
        for (int i = 10; i > 0 ; i--)
        {
            System.out.format("%.4f ", nums[nums.length - i]);
        }
        System.out.println();
    }
}