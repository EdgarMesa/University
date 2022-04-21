
package symboltable;
import java.util.Arrays;

import java.util.Arrays;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;


public class Quicksort {
    
    
    
    private static boolean less(Comparable a,Comparable b)
    {
     return b.compareTo(a) < 0; 
    }
    
    
    private static void exch(Comparable[] a, int i, int j,Comparable[] b)
{
    Comparable swap = a[i];
    a[i] = a[j];
    a[j] = swap;
    
    swap = b[i];
    b[i] = b[j];
    b[j] = swap;
    
    
}
    
     private static void exch(Comparable[] a, int i, int j)
{
    Comparable swap = a[i];
    a[i] = a[j];
    a[j] = swap;
    
    
}
    
    private static int partition(Comparable[] a, int lo, int hi,Comparable pivot,Comparable[] b)
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
            exch(a,i,j,b);
            i++;
            j--;}
        }
        
        
        return j;
        
        
    }
    
    public static void sort(Comparable[] a,Comparable[] b)
    {
        int lo = 0;
        int hi = a.length-1;
        StdRandom.shuffle(a);
        sort(a,lo,hi,b);
    }
    
    private static void sort(Comparable[] a,int lo, int hi,Comparable[] b)
    {
        
        if (lo >= hi)  
        {      
          
            return;    
        }   
        
        
        Comparable m = medianOf3(a, lo, hi);   
 
        
        int j = partition(a, lo, hi,m,b);  
        sort(a, lo, j,b);    
        sort(a, j+1, hi,b);
        
        
        
    
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
    
    private static void insertion(Comparable[] a,int low,int hi,Comparable[] b)
 {
    int N = a.length;
    for (int i = 0; i < N; i++){
        for (int j = i; j > 0; j--){
            if (less(a[j], a[j-1]))
            exch(a, j, j-1,b);
        else break;}
    }
 }
    
   
       
    
}