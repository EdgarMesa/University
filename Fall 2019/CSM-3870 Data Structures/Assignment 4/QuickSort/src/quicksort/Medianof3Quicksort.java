
package quicksort;

import java.util.Arrays;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;


public class Medianof3Quicksort {
    
    
    
    private static boolean less(Comparable a,Comparable b)
    {
     return a.compareTo(b) < 0; 
    }
    
    
    private static void exch(Comparable[] a, int i, int j)
{
    Comparable swap = a[i];
    a[i] = a[j];
    a[j] = swap;
}
    
    private static int partition(Comparable[] a, int lo, int hi,Comparable pivot)
    {
        int i = lo;
        int j = hi;
        boolean done = false;
        while(!done)
        {
            while(less(a[i],pivot))
            {
                i++;   
            }
            
            while(less(pivot,a[j])){j--;}
            
            if(i >= j){done = true;}
            
            else
            {
            exch(a,i,j);
            i++;
            j--;}
        }
        
        
        return j;
        
        
    }
    
    public static void sort(Comparable[] a)
    {
        int lo = 0;
        int hi = a.length-1;
        StdRandom.shuffle(a);
        sort(a,lo,hi);
    }
    
    private static void sort(Comparable[] a,int lo, int hi)
    {
        
        if (lo >= hi)  
        {      
          
            return;    
        }   
        
        
        Comparable m = medianOf3(a, lo, hi);   
 
        
        int j = partition(a, lo, hi,m);  
        sort(a, lo, j);    
        sort(a, j+1, hi);
        
        
        
    
    }
    
    public static Comparable medianOf3(Comparable[] a,int lo,int hi)
    {
                
        int mid = (lo+hi)/2;
        
        if(less(a[mid],a[lo])){exch(a,lo,mid);}
        if(less(a[hi],a[lo])){exch(a,lo,hi);}
        if(less(a[hi],a[mid])){exch(a,mid,hi);}
        
        exch(a,mid,hi);
        return a[hi];

        
        
    }
    
    private static void insertion(Comparable[] a,int low,int hi)
 {
    int N = a.length;
    for (int i = 0; i < N; i++){
        for (int j = i; j > 0; j--){
            if (less(a[j], a[j-1]))
            exch(a, j, j-1);
        else break;}
    }
 }
    
    public static void main(String[] args) 
    {
    
        Comparable[] a = new Comparable[10];
        for(int i = 0 ; i < a.length;i++)
        {
            a[i] = StdRandom.uniform(1000);
        }
        
        Stopwatch watch = new Stopwatch();
        
        System.out.println("Array not sorted: " +Arrays.toString(a));
    
        sort(a);
        double time = watch.elapsedTime();
        System.out.println(time);
        System.out.println("Array sorted:     " + Arrays.toString(a));
        
        
        
        
        
        
        
    }
    
}
