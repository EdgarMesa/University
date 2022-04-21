
package priorityqueue;

/**
 *
 * @author Edgar Mesa Perez
 */
public class MinPQ<Key extends Comparable<Key>> {
    
    
    public Key[] Lowpq;
    public int Nlow;
    public int size = 0;

    
     public MinPQ(int capacity)
     {
          Lowpq = (Key[]) new Comparable[capacity+1]; 

     }
 
    public boolean isEmpty(){return Nlow == 0;} 

    private boolean more(int a,int b,Key[] arr)
    {
        return arr[a].compareTo(arr[b]) > 0; 
    }
    
    private void exch(Key[] a, int i, int j)
    {
        Key swap = a[i];
        a[i] = a[j];
        a[j] = swap;
        
    }
    
    public Key findLow(){return Lowpq[1];}

    public Key delMin()
    {   
        IdexException();
        Key max = Lowpq[1];   
        exch(Lowpq,1, Nlow--);  
        sinkLow(1); 
        Lowpq[Nlow+1] = null;  
        size--;
        return max; 
    } 
    
    private void sinkLow(int k) 
    {   
        while (2*k <= Nlow)   
        {      
          int j = 2*k;    
          if (j < Nlow && more(j, j+1,Lowpq)) j++;   
          if (!more(k, j,Lowpq)) break;    
          exch(Lowpq,k, j);      
          k = j;  

        }
    } 
    private void swimLow(int k) 
    { 
    while (k > 1 && more(k/2, k,Lowpq))
    {     
        exch(Lowpq,k, k/2);     
        k = k/2;  
    }
    } 
    
    public void insert(Key x) 
    {   
        Nlow++;
        IdexException();
        Lowpq[Nlow] = x;   
        swimLow(Nlow);
        size++;

    }
    
    private void IdexException()
    {   
        if(isEmpty() || Nlow == Lowpq.length-1)
        throw new IndexOutOfBoundsException(" Out of bounds!");
    }
    
}
