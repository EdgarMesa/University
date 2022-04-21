import java.util.concurrent.Semaphore;


// A solution to the Producer-Consumer problem with a buffer larger than one
public class ProducerConsumerBoundedBuffer
{

    private static class Buffer
    {
        private final CircularQueue<Integer> queue;
        private final Semaphore mutex;
        private final Semaphore numberOfItemsInBuffer;
        private final Semaphore openSpacesInBuffer;

        public Buffer(int cap)
        {
            queue = new CircularQueue<>(cap);
            mutex = new Semaphore(1);
            numberOfItemsInBuffer = new Semaphore(0);
            openSpacesInBuffer = new Semaphore(cap);
        }

        public int get()
        {
            // Do not allow the Consumer to get a value before the Producer has set one
            numberOfItemsInBuffer.acquireUninterruptibly();

            // Lock for queue
            mutex.acquireUninterruptibly();

            // Get item
            int result = queue.dequeue();
            System.out.printf("Got: %d\n", result);

            // Unlock queue
            mutex.release();

            // Let Producer know there is space open
            openSpacesInBuffer.release();

            return result;
        }

        public void put(int value)
        {
            // Do not allow the Producer add to a full buffer
            openSpacesInBuffer.acquireUninterruptibly();

            // Lock for queue
            mutex.acquireUninterruptibly();

            // Put item
            queue.enqueue(value);
            System.out.printf("Put: %d\n", value);

            // Unlock queue
            mutex.release();

            // Let Consumer know that an item has been added
            numberOfItemsInBuffer.release();
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

    private static final Buffer BUFFER = new Buffer(10);
    private static final int MAX_VALUE = 100;
    public static void main(String[] args)
    {
        new Producer().start();
        new Consumer().start();
    }

}