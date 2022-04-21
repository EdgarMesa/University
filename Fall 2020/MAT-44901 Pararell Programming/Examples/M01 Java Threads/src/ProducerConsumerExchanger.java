import java.util.concurrent.Exchanger;

// A "taking turns" solution to the Producer-Consumer problem using an Exchanger
public class ProducerConsumerExchanger
{

    private static final Exchanger<Integer> buffer = new Exchanger<>();

    private static class Producer extends Thread
    {

        @Override
        public void run()
        {
            for (int value = 0; value <= MAX_VALUE; value++)
            {
                try
                {
                    buffer.exchange(value);
                } catch (InterruptedException ex)
                {
                    System.err.println("Exchange interrupted.");
                }
            }
        }
    }

    private static class Consumer extends Thread
    {

        @Override
        public void run()
        {
            int value = -1;
            do
            {
                try
                {
                    value = buffer.exchange(null);
                    System.out.println("Got: " + value);
                } catch (InterruptedException ex)
                {
                    System.err.println("Exchange interrupted.");
                }
            } while (value < MAX_VALUE);
        }
    }

    private static final int MAX_VALUE = 100;

    public static void main(String[] args)
    {
        new Producer().start();
        new Consumer().start();
    }

}