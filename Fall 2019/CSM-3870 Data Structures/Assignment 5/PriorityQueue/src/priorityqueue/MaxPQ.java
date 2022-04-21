
package priorityqueue;

/**
 *
 * @author Edgar Mesa Perez
 */
public class MaxPQ<Key extends Comparable<Key>> {
    
    
        public  Key[] Maxpq;
        public int Nmax;
        public int size = 0;

     public MaxPQ(int capacity)
     {
          Maxpq = (Key[]) new Comparable[capacity+1]; 

     }
    
    public boolean isEmpty(){return Nmax == 0;} 
    
    private boolean less(int a,int b,Key[] arr)
    {
        return arr[a].compareTo(arr[b]) < 0; 
    }
    
    
    private void exch(Key[] a, int i, int j)
    {
        Key swap = a[i];
        a[i] = a[j];
        a[j] = swap;
        
    }
    
    
    public Key findMax(){return Maxpq[1];}

    
    private void sinkMax(int k) 
    {   
        while (2*k <= Nmax)   
        {      
          int j = 2*k;    
          if (j < Nmax && less(j, j+1,Maxpq)) j++;   
          if (!less(k, j,Maxpq)) break;    
          exch(Maxpq,k, j);      
          k = j;  
        }
    } 
    
    public Key delMax()
    {   
        
        IdexException();
        Key max = Maxpq[1];   
        exch(Maxpq,1, Nmax--);  
        sinkMax(1); 
        Maxpq[Nmax+1] = null;  
        size--;
        return max; 
    }
    
    private void swimMax(int k) 
    { 
    while (k > 1 && less(k/2, k,Maxpq))
    {     
        exch(Maxpq,k, k/2);      
        k = k/2;  
    }
    }
    
    public void insert(Key x) 
    {  
        Nmax++;
        IdexException();
        Maxpq[Nmax] = x;   
        swimMax(Nmax);
        size++;
       
    }
    
    
    private void IdexException()
    {   
        if(isEmpty() || Nmax == Maxpq.length-1)
        throw new IndexOutOfBoundsException(" Out of bounds!");
    }
    
}
