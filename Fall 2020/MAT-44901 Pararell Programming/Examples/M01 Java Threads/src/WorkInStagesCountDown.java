// We are going to simulate working on a problem that can be broken into
// two stages where the first stage can be parallelized, but the second stage
// can not proceed until all of the work from the first stage is complete.

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class WorkInStagesCountDown
{

    // For now we are going to manage a pool of threads and jobs for
    // problem by hand. Later we will see that Java can do a lot of this
    // work for us.
    //
    // Think about what other options we have for data structures here.
    private static final int NUMBER_OF_THREADS = 10;
    private static final int NUMBER_OF_JOBS = 100;
    private static final CountDownLatch latch = new CountDownLatch(NUMBER_OF_JOBS);
    private static Queue<Job> openJobsQueue = new LinkedList<>();
    private static Job[] assignedJobsArray = new Job[NUMBER_OF_THREADS];
    private static List<Job> completedJobsList = new ArrayList<>(NUMBER_OF_JOBS);
    private static final Random rand = new Random();

    // A thread should call assignNextJob when it does not currently have a task to work on
    // and it wants a new job. Returns the assigned job or null if there are no jobs left.
    private static Job assignNextJob(int threadIndex)
    {
        // Use class monitor for access to job collections. One thing I like about synchronized
        // code blocks is that I do not have to include unlock statements for each path out of
        // the block.
        synchronized (WorkInStagesCountDown.class)
        {
            // Check if we are done
            if (completedJobsList.size() == NUMBER_OF_JOBS)
            {
                System.out.printf("Thread %d asked for a job but we are done.\n", threadIndex);
                return null;
            }

            // If there is an open job assign it
            if (!openJobsQueue.isEmpty())
            {
                Job job = openJobsQueue.poll();
                assignedJobsArray[threadIndex] = job;
                System.out.printf("Thread %d assigned %s\n", threadIndex, job.toString());
                return job;
            }

            // Otherwise get a job from the assigned jobs. This is a linear scan and can lead to
            // clustering. Think about how this could be better.
            int index = threadIndex;
            for (int count = 0; count < NUMBER_OF_THREADS; count++)
            {
                index = index + 1 == NUMBER_OF_THREADS ? 0 : index + 1;
                Job job = assignedJobsArray[index];
                if (job != null && job.getOutput() == null)
                {
                    assignedJobsArray[threadIndex] = job;
                    System.out.printf("Thread %d reassigned %s\n", threadIndex, job.toString());
                    return job;
                }
            }

            System.out.printf("Thread %d asked for a job but we did not find one.\n", threadIndex);
            return null;
        }
    }

    // A thread should call doneWithJob when it has completed its task
    private static void doneWithJob(int threadIndex)
    {
        // Use class monitor for access to job collections.
        synchronized (WorkInStagesCountDown.class)
        {
            // Check that the job is done
            Job job = assignedJobsArray[threadIndex];
            if (job == null)
            {
                throw new IllegalStateException("Thread " + threadIndex + " did not have a job.");
            }

            if (job.getOutput() == null)
            {
                throw new IllegalStateException("Thread " + threadIndex + "'s job is not done.");
            }

            // Turn the job in. We are recording them in the order they are turned in, which
            // is not always desirable.
            assignedJobsArray[threadIndex] = null;
            completedJobsList.add(job);
            System.out.printf("Thread %d done with %s\n", threadIndex, job.toString());
            latch.countDown();
        }
    }

    public static void main(String[] args)
    {
        // Build some jobs
        for (int input = 0; input < NUMBER_OF_JOBS; input++)
        {
            openJobsQueue.add(new Job(input));
        }

        // Build some threads
        for (int id = 0; id < NUMBER_OF_THREADS; id++)
        {
            new WorkThread(id).start();
        }

        // Wait for all of the threads. In this case we could just join on the threads
        // However the power of a latch is that any number of threads can be waiting on
        // it and they are all released together.
        try
        {
            latch.await();
        } catch (InterruptedException ex)
        {
            System.err.println("Latch interrupted");
        }

        // Check that all jobs are really in
        synchronized (WorkInStagesCountDown.class)
        {
            System.out.println("Latch passed, check started");

            boolean[] found = new boolean[NUMBER_OF_JOBS];
            for (Job j : completedJobsList)
            {
                if (j.getOutput() == null)
                {
                    System.out.println("Found incomplete " + j);
                }
                found[j.input] = true;
            }

            for (int i = 0; i < found.length; i++)
            {
                if (!found[i])
                {
                    System.out.printf("Job %d not found\n", i);
                }
            }
            System.out.println("Check done");
        }
    }

    // A simple class to represent some work (take some input and store the output)
    private static class Job
    {

        private final int input;
        private Integer output;

        public Job(int input)
        {
            this.input = input;
            output = null;
        }

        public int getInput()
        {
            return input;
        }

        public synchronized Integer getOutput()
        {
            return output;
        }

        public synchronized void setOutput(Integer output)
        {
            if (this.output != null)
            {
                throw new IllegalStateException("Output was already set.");
            }

            this.output = output;
        }

        @Override
        public int hashCode()
        {
            return Integer.hashCode(input);
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final Job other = (Job) obj;
            return this.input == other.input;
        }

        @Override
        public String toString()
        {
            Integer result = getOutput();
            if (result == null)
            {
                return "Job " + input;
            }
            return "Job " + input + " -> " + result;
        }
    }

    private static class WorkThread extends Thread
    {

        private final int threadId;

        public WorkThread(int id)
        {
            threadId = id;
        }

        @Override
        @SuppressWarnings("SleepWhileInLoop")
        public void run()
        {
            System.out.println("Thread " + threadId + " starting");

            // Get a job
            for (Job job = assignNextJob(threadId); job != null; job = assignNextJob(threadId))
            {
                // Simulate some work
                try
                {
                    if (rand.nextInt(100) < 15)
                    {
                        Thread.sleep(rand.nextInt(900) + 100);
                    } else
                    {
                        Thread.sleep(rand.nextInt(90) + 10);
                    }
                } catch (InterruptedException ex)
                {
                }

                synchronized (job)
                {
                    if (job.getOutput() == null)
                    {
                        job.setOutput(threadId);
                        doneWithJob(threadId);
                    }
                }
            }

            System.out.println("Thread " + threadId + " ending");
        }

    }
}
