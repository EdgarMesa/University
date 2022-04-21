
package priorityqueue;

import java.util.Arrays;

/**
 *
 * @author Edgar Mesa Perez
 */
public class MedianFinding<Key extends Comparable<Key>>  {
    
    private MaxPQ<Integer> Maxpq;
    private MinPQ<Integer> Lowpq;
    private int LenMax;
    private int LenMin;

     

        
     public MedianFinding(int capacity)
     {
        Maxpq = new MaxPQ(capacity+1); 
        Lowpq = new MinPQ(capacity+1); 
     }
     
     
     public Double findMedian() 
     {
        int cuantityMax = Maxpq.size;
        int cuantityMin = Lowpq.size;
        if (cuantityMax == cuantityMin)
        {
           
            if((Integer)Maxpq.findMax() == null && (Integer)Lowpq.findLow() != null ) return (double)Lowpq.findLow() / 2;

            
            if((Integer)Maxpq.findMax() != null && (Integer)Lowpq.findLow() == null) return (double)Maxpq.findMax() / 2;
            if((Integer)Maxpq.findMax() == null && (Integer)Lowpq.findLow() == null) return 0.0;

            
            return ((double)Lowpq.findLow() + (double)Maxpq.findMax()) / 2;
        }
        
        else if (cuantityMax > cuantityMin)return (double)Maxpq.findMax();
        else return (double)Lowpq.findLow();
     }

    public void insert(int key) {
        double median = findMedian();
        int cuantityMax = Maxpq.size;
        int cuantityMin = Lowpq.size;
        if (key <= median) 
        {
            Maxpq.insert(key);
            if (cuantityMax - cuantityMin > 1)
            Lowpq.insert(Maxpq.delMax());
        }
        else 
        {
            Maxpq.insert(key);
            if (cuantityMin - cuantityMax > 1)
            Maxpq.insert(Lowpq.delMin());
        }
    }

    public void removeMedian() {
        int cuantityMax = Maxpq.size;
        int cuantityMin = Lowpq.size;

        if (cuantityMax > cuantityMin) {Maxpq.delMax();}

        else {Lowpq.delMin();}
    }


     public static void main(String[] args) {
         
             
        MedianFinding q = new MedianFinding(20);
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


        System.out.println(q.findMedian());
        

        
         
        
    }
    
}
