// A synchronized but incorrect solution to the Producer-Consumer problem
public class ProducerConsumerIncorrect
{

    // A buffer of one...
    private static class Buffer
    {
        int value = -1;

        public synchronized int get()
        {
            System.out.printf("Got: %d\n", value);
            return value;
        }

        public synchronized void put(int value)
        {
            this.value = value;
            System.out.printf("Put: %d\n", value);
        }
    }

    private static class Producer extends Thread
    {
        @Override
        public void run()
        {
            for(int value = 0; value <= MAX_VALUE; value++) BUFFER.put(value);
        }
    }

    private static class Consumer extends Thread
    {
        @Override
        public void run()
        {
            int value;
            do
            {
                value = BUFFER.get();
            }while(value < MAX_VALUE);
        }
    }

    private static final Buffer BUFFER = new Buffer();
    private static final int MAX_VALUE = 100;
    public static void main(String[] args)
    {
        new Producer().start();
        new Consumer().start();
    }

}