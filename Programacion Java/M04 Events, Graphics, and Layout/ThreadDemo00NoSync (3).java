
public class ThreadDemo00NoSync
{

    private static final int N = 100;

    private static class WorkerThread extends Thread
    {

        @Override
        public void run()
        {
            for (int count = 1; count <= N; count++)
            {
                System.out.print(count + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        // Create a new thread
        WorkerThread bob = new WorkerThread();
        bob.start();

        // Do some work of our own
        for (int count = -1; count >= -N; count--)
        {
            System.out.print(count + " ");
        }
        System.out.println();
    }
}
