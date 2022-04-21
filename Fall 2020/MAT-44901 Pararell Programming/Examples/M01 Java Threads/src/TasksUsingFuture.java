// We are going to simulate working on a problem where our sub tasks need to return a result.

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TasksUsingFuture
{

    private static final int NUMBER_OF_THREADS = 10;
    private static final int NUMBER_OF_JOBS = 500;
    private static final Random rand = new Random();

    // A simple class to represent the result of a calculation (in this case a mapping from an
    // int to an int).
    private static class Result
    {

        private final int input;
        // We could use exceptions for failure, but here we will just use a null output for that.
        private final Integer output;

        public Result(int input, Integer output)
        {
            this.input = input;
            this.output = output;
        }
    }

    // A simple class to represent some work (take some input and store the output)
    private static class Job implements Callable<Result>
    {

        private final int input;

        public Job(int input)
        {
            this.input = input;
        }

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public Result call() throws Exception
        {
            // Simulate some work
            try
            {
                // Sometimes we fail
                if (rand.nextInt(100) < 15)
                {
                    return new Result(input, null);
                } // Sometimes we are slow
                else if (rand.nextInt(100) < 30)
                {
                    Thread.sleep(rand.nextInt(900) + 100);
                } // Sometimes we are fast
                else
                {
                    Thread.sleep(rand.nextInt(90) + 10);
                }
            } catch (InterruptedException ex)
            {
            }

            return new Result(input, 2 * input);
        }
    }

    public static void main(String[] args)
    {
        // We use an ExecutorService to manage our threads
        final ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        // Submit all of our starting jobs
        final Queue<Future<Result>> futures = new LinkedList<>();
        for (int input = 0; input < NUMBER_OF_JOBS; input++)
        {
            futures.add(service.submit(new Job(input)));
        }

        // While there are jobs run them. In this case we are keeping a final list of results
        // but that is just so we can test them later. If we were not doing this we could just have
        // a counter and use the results as they come in.
        ArrayList<Result> completedJobsList = new ArrayList<>(NUMBER_OF_JOBS);
        while (completedJobsList.size() < NUMBER_OF_JOBS)
        {
            // Look at each of our futures
            while (!futures.isEmpty())
            {
                // Get the first future we have
                Result r = null;
                try
                {
                    r = futures.poll().get();
                } catch (InterruptedException | ExecutionException ex)
                {
                    System.err.println("Exception while waiting for task.");
                    System.exit(1);
                }

                // If it has an answer print and save it
                if (r.output != null)
                {
                    System.out.printf("%d -> %d\n", r.input, r.output);
                    completedJobsList.add(r);
                } // Otherwise reschedule it
                else
                {
                    futures.add(service.submit(new Job(r.input)));
                }
            }
        }

        // Check that all jobs are really in
        boolean[] found = new boolean[NUMBER_OF_JOBS];
        for (Result r : completedJobsList)
        {
            if (r.output == null)
            {
                System.out.println("Found incomplete " + r.input);
            }
            found[r.input] = true;
        }

        for (int i = 0; i < found.length; i++)
        {
            if (!found[i])
            {
                System.out.printf("Job %d not found\n", i);
            }
        }
        System.out.println("Check done");
        service.shutdown();
    }
}
