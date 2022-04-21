// A "taking turns" non-solution to the Producer-Consumer problem with a buffer larger than one
public class ProducerConsumerBoundedBufferIncorrect
{

    private static class Buffer
    {
        private final CircularQueue<Integer> queue = new CircularQueue<>(10);

        public synchronized int get()
        {
            // Do not allow the Consumer to get a value before the Producer has set one
            while(queue.size() == 0)
            {
                try
                {
                    wait();
                } catch (InterruptedException ex)
                {
                    System.err.println("Hmm... consumer was Interrupted.");
                }
            }

            int result = queue.dequeue();

            System.out.printf("Got: %d\n", result);

            // Wake the Producer if buffer is empty
            if(queue.size() == 0)
            {
                notify();
            }

            return result;
        }

        public synchronized void put(int value)
        {
            // Do not allow the Producer add to a full buffer
            while(queue.freeSpace() == 0)
            {
                try
                {
                    wait();
                } catch (InterruptedException ex)
                {
                    System.err.println("Hmm... Producer was Interrupted.");
                }
            }

            queue.enqueue(value);
            System.out.printf("Put: %d\n", value);

            // Wake the Consumer if buffer is full
            if(queue.freeSpace()== 0)
            {
                notify();
            }
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