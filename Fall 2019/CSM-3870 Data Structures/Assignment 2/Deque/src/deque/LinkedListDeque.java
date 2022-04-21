
package deque;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedListDeque<Item> implements Iterable<Item> {
    
    Node first,last;
    private int size = 0;
    
    
    private class Node
    {
        Item item;
        Node next;
        Node prev;
    }
    
    // construct an empty deque
 public LinkedListDeque(){}
 
 
 // is the deque empty?
 public boolean isEmpty(){return size == 0;}
 
 // return the number of items on the deque
 public int size(){return size;}
 
 // add the item to the front
 public void addFirst(Item item)
 {
    ArgumentException(item);
    Node oldfirst = first;
    first = new Node();
    first.next = oldfirst;
    first.item = item;
    if(isEmpty()){ last= first;}
    else{oldfirst.prev = first;}
    size++;
 
 }
 
 public void ArgumentException(Item item){if (item == null){throw new IllegalArgumentException();}}
 
 // add the item to the back
 public void addLast(Item item)
 {
    ArgumentException(item);
    Node oldlast = last;
    last = new Node();
    last.next = null;
    last.item = item;
    last.prev = oldlast;
    if(isEmpty()){first = last;}
    else{oldlast.next = last;}
    size++;
 }
 
 // remove and return the item from the front
 public Item removeFirst()
 {
      ElemenExecption();
      Item item = first.item;
      if(size > 1)
      {
        first = first.next;
        first.prev = null;
      
      }
      else
      {
        first = null;
        last = null;
      }
      size--;
      
      return item;
      
 }
 
 public void ElemenExecption(){if(isEmpty()){throw new NoSuchElementException();}}
 
 
 // remove and return the item from the back
 public Item removeLast()
 {
     ElemenExecption();
     Item item = last.item;
     
     if(size > 1)
     {
         last = last.prev;
         last.next = null;
         
     }
     else{
         
         first = null;
         last = null; 
     }
     
     size--;
     return item;
     
 }
 // return an iterator over items in order from front to back
 public Iterator<Item> iterator(){return new LinkedListDequeIterator();}
 
 private class LinkedListDequeIterator<Item> implements Iterator<Item>
 {
     private Node Cposition = first;


    public boolean hasNext() {return Cposition != null;}


    public Item next() 
    {
        if(Cposition == null){throw new NoSuchElementException();}
        Item item = (Item) Cposition.item;
        Cposition = Cposition.next;
        return item;

        }


    public void remove() {
        throw new UnsupportedOperationException();
    }
 
 }
 
 
 // unit testing (required)
 public static void main(String[] args)
 {
        

     LinkedListDeque list = new LinkedListDeque();
     
     list.addFirst(1);
     list.addLast(2);
     list.addLast(3);
     list.addFirst(4);
     list.addLast(5);

     
     list.removeFirst();
     list.removeLast();
     list.removeLast();
     
     System.out.println("Size: " +list.size());
     


     Iterator it = list.iterator();
    
     
     while(it.hasNext())
     {
         
         System.out.println(it.next());
     }    
 }
    
}
