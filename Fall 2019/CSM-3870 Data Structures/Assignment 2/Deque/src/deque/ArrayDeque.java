
package deque;

import java.util.Iterator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayDeque<Item> implements Iterable<Item> {
    
 private Item[] s;
 private int sizearray = 1;
 private int head = 0;
 private int tail = 0;
 private int size = 0;

    
    public ArrayDeque()
    {
        this.s = (Item[]) new Object[this.sizearray];
    }
    

    // is the deque empty?
    public boolean isEmpty(){return size() == 0;}
    public boolean isFull(){return size() == sizearray;}


    // return the number of items on the deque
    public int size(){return size; }

    // add the item to the front
    public void addFirst(Item item)
    {
   
        //System.out.println("lenght" +s.length);
        if(head == 0)
        {
            head = sizearray-1;
            s[head]=item;
        }
        else
        {
            s[--head] = item;
        }
        size++;
       

        if(isFull()){resize(sizearray*2);}
        
   
    }

    public void ArgumentException(Item item){if (item == null){throw new IllegalArgumentException();}}

    // add the item to the back
    public void addLast(Item item)
    {

         if(tail == sizearray-1){tail = 0;}
     else
     {
         s[++tail] = item;
     }
     size++;
     
     if(isFull()){resize(sizearray*2);}
    }

    public void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
           
        
                
        int offset = 0;
        boolean loop = false;
        for(int i = 0; i < size;i++)
        {
            if(head+i == sizearray){head = 0;loop = true;}
            
            if(!loop)
            {
            copy[i] = s[head+i];
            offset++;
            }
            else{copy[i] = s[head+i-offset];}
            
        }
        tail = size-1;
        head = 0;

        s = copy;
        sizearray = capacity;
        
      

    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        ElemenExecption();

        Item item = s[head];
        s[head] = null;
        size--;
        if(head == sizearray-1){head = 0;}
        else{head++;}
        
        if(size > 0 && size == sizearray/4)
        {
             resize(sizearray/2);
        }

        return item; 

    }

    public void ElemenExecption(){if(isEmpty()){throw new NoSuchElementException();}}


    // remove and return the item from the back
    public Item removeLast()
    {
        ElemenExecption();

        Item item = s[tail];
        s[tail] = null;
        size--;
        if(tail == 0 && size >= 2){tail = sizearray-1;}
        else if(size < 2){tail=0;}
        else{tail--;}
        
        if(size > 0 && size == sizearray/4)
        {
             resize(sizearray/2);
        }

        return item; 
    }
    
    
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){return new ArrayDequeIterator(); }

    private class ArrayDequeIterator<Item> implements Iterator<Item>
    {

        private int current = head;
        private boolean loop = false;


       public boolean hasNext() {
           
           //if(s[current] != null){System.out.println("current null=" + "True");}
           //if(current != tail){System.out.println("finito =" + "True");}
           return (current != head || !loop) && s[current] != null;
       }

       public Item next() 
       {
           if(s[current] == null){throw new NoSuchElementException();}
           Item item = (Item) s[current];
           
           
           if(current == sizearray-1){current=0;}
           else{current++;}
           loop = true;
      
           return item;
           
            
           }

       public void remove() {
           throw new UnsupportedOperationException();
       }

    }
    // unit testing (required)
    public static void main(String[] args)
    {
        
        ArrayDeque list = new ArrayDeque();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addFirst(5);
        list.removeFirst();
        list.removeFirst();
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        list.addFirst(1);
        list.removeLast();
        list.removeFirst();
        list.removeFirst();
        
        
       System.out.println("Size:" + list.size());


        Iterator it = list.iterator();
        
        while(it.hasNext())
        {
           
            System.out.println(it.next());
        }
        
    }

}
