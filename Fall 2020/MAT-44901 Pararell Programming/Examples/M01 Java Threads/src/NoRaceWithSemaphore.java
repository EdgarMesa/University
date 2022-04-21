import java.util.concurrent.Semaphore;

public class NoRaceWithSemaphore implements Runnable
{

    private static volatile int i = 0;
    private static final int N = 10000;
    private static final Semaphore mutex = new Semaphore(1);

    private void up()
    {
        i++;
    }

    private void down()
    {
        i--;
    }

    @Override
    public void run()
    {
        // Create a new thread
        WorkerThread bob = new WorkerThread();
        bob.start();

        // Do some work of our own
        for (int count = 0; count < N; count++)
        {
            try
            {
                mutex.acquire();
            }
            catch (InterruptedException ex)
            {
                System.err.println("Interrupted on acquire");
                System.exit(1);
            }
            down();
            mutex.release();
        }

        try
        {
            bob.join();
        }
        catch (InterruptedException ex)
        {
            System.err.println("Interrupted on join");
            System.exit(1);
        }

        System.out.println("i: " + i);
    }

    private class WorkerThread extends Thread
    {

        @Override
        public void run()
        {
            for (int count = 0; count < N; count++)
            {
                try
                {
                    mutex.acquire();
                }
                catch (InterruptedException ex)
                {
                    System.err.println("Interrupted on acquire");
                    System.exit(1);
                }
                up();
                mutex.release();
            }
        }
    }

    public static void main(String[] args)
    {
        new NoRaceWithSemaphore().run();
    }
}