public class NoRaceWithSync implements Runnable
{

    private static volatile int i = 0;
    private static final int N = 10000;

    private synchronized void up()
    {
        i++;
    }

    private synchronized void down()
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
            down();
        }

        try
        {
            bob.join();
        }
        catch (InterruptedException ex)
        {
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
                up();
            }
        }
    }

    public static void main(String[] args)
    {
        new NoRaceWithSync().run();
    }
}
