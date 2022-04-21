import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo
{

    private static final int numberOfMessages = 5;
    private static final int numberOfJobs = 26;
    private static final int sizeOfPool = 5;
    private static final CountDownLatch latch = new CountDownLatch(numberOfJobs);

    private static class WorkerThread extends Thread
    {

        private final char message;

        public WorkerThread(char message)
        {
            this.message = message;
        }

        @Override
        public void run()
        {
            for (int i = 0; i < numberOfMessages; i++)
            {
                System.out.println(message + ": " + i);
            }
            latch.countDown();
        }
    }

    public static void main(String[] args)
    {
        // Build a pool of threads
        ExecutorService service = Executors.newFixedThreadPool(sizeOfPool);

        // Add jobs for the pool to do
        char message = 'A';
        for (int i = 0; i < numberOfJobs; i++)
        {
            service.execute(new WorkerThread(message++));
        }

        // Wait for all of the jobs to finish
        try
        {
            latch.await();
        } catch (InterruptedException ex)
        {
            System.err.println("Wait on latch Interrupted.");
            System.exit(1);
        }

        // Stop ExecutorService, if we do not do this then our program will still be
        // running even after main ends.
        service.shutdown();
    }

}