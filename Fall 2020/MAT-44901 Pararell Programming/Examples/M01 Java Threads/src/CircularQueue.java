public class CircularQueue<E>
{
    private final Object[] data;

    // Going to use three values to help avoid overflow issues
    private int front;
    private int back;
    private int size;

    public CircularQueue(int capacity)
    {
        if(capacity < 1) throw new IllegalArgumentException("capacity must be > 0");

        data = new Object[capacity];
        front = 0;
        back = 0;
        size = 0;
    }

    public int size()
    {
        return size;
    }

    public int capacity()
    {
        return data.length;
    }

    public int freeSpace()
    {
        return data.length - size;
    }

    public void enqueue(E item)
    {
        // size + 1 < 0 is for the case that the size is max_int
        if(size + 1 > data.length || size + 1 < 0) throw new IllegalStateException("Queue is full");

        // add item
        data[back] = item;

        // Update index and size
        back = back + 1 == data.length ? 0 : back + 1;
        size++;

    }

    public E dequeue()
    {
        if(size == 0) throw new IllegalStateException("Queue is empty");

        // Get item
        E result = (E) data[front];

        // Update index and size
        front = front + 1 == data.length ? 0 : front + 1;
        size--;

        // Return the item
        return result;

    }

}