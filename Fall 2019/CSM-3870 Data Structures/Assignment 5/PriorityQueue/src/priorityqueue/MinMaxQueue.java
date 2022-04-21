
package priorityqueue;




import java.util.Arrays;

/**
 *
 * @author Edgar Mesa Perez
 */
public class MinMaxQueue<Key extends Comparable<Key>> {
    
        public MaxPQ Maxpq;
        public MinPQ Lowpq;


     public MinMaxQueue(int capacity)
     {
          Maxpq = new MaxPQ(capacity+1); 
          Lowpq = new MinPQ(capacity+1); 

     }
     
   
    
    private Key findMax(){return (Key)Maxpq.findMax();}
    private Key findLow(){return (Key)Lowpq.findLow();}


    public Key delMax()
    {   
       return (Key) Maxpq.delMax();
      
    } 
    
    public Key delMin()
    {   
        return (Key)Lowpq.delMin();
    } 
    

    
    public void insert(Key x) 
    { 
        Maxpq.insert(x);
        Lowpq.insert(x);

    }
    

     public static void main(String[] args) {
         
    
        MinMaxQueue q = new MinMaxQueue(20);
        q.insert(1);
        q.insert(2);
        q.insert(5);
        q.insert(6);
        q.insert(3);
        q.insert(4);
        q.insert(10);
        q.insert(7);
        q.insert(3);
        q.insert(9);
        
        System.out.println("MaxHEAP     " + Arrays.toString(q.Maxpq.Maxpq));
        System.out.println("MinHEAP     " + Arrays.toString(q.Lowpq.Lowpq));

        System.out.println();


        
        Comparable l = q.delMin();
        Comparable l2 = q.delMin();

        Comparable l3 = q.delMin();
        

        
        System.out.println(String.format("min1: %d   min2: %d   min3: %d", l,l2,l3));

        
        Comparable m = q.delMax();
        Comparable m2 = q.delMax();

        Comparable m3 = q.delMax();

        
        System.out.println(String.format("max1: %d   max2: %d   max3: %d", m,m2,m3));
        
        
        
        System.out.println("MaxHEAP     " + Arrays.toString(q.Maxpq.Maxpq));
        System.out.println("MinHEAP     " + Arrays.toString(q.Lowpq.Lowpq));

        
        System.out.println(q.findLow());
        
        System.out.println(q.findMax());
        
         
        
        



        
       
    }
     
     
     
    
}
