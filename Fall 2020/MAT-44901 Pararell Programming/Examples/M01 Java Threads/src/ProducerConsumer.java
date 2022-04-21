// A "taking turns" solution to the Producer-Consumer problem
public class ProducerConsumer
{

    // A buffer of one... Later we will solve this will a larger buffer
    private static class Buffer
    {
        volatile int value = -1;

        public synchronized int get()
        {
            // Do not allow the Consumer to get a value before the Producer has set one
            while(value == -1)
            {
                try
                {
                    wait();
                } catch (InterruptedException ex)
                {
                    System.err.println("Hmm... consumer was Interrupted.");
                }
            }


            System.out.printf("Got: %d\n", value);

            // Clear the value
            int oldValue = value;
            value = -1;

            // Let the Producer know we got the value
            notify();

            return oldValue;
        }

        public synchronized void put(int value)
        {
            // Do not allow the Producer to set a new value before the Consumer has got it
            while(this.value != -1)
            {
                try
                {
                    wait();
                } catch (InterruptedException ex)
                {
                    System.err.println("Hmm... Producer was Interrupted.");
                }
            }

            this.value = value;
            System.out.printf("Put: %d\n", value);

            // Let the Consumer know we got the value
            notify();
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