
package quicksort;

import java.util.Stack;
import java.util.Arrays;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;

public class NonrecursiveQuicksort {
    
    
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
    
    
    private static int partition(Comparable[] input, int pos, int lo, int hi) {
        int l = lo;
        int h = hi - 2;
        Comparable pivot = input[pos];
        exch(input, pos, hi - 1);

        while (l < h) {
            if (less(input[l],pivot) ){l++;}
            
            else if (less(pivot,input[h])){h--;}
            
            else 
            {
                exch(input, l, h);
            }
        }
        
        int idex = h;
        
        if (less(input[h] , pivot)) 
        {
            idex++;
        }
        exch(input, hi - 1, idex);
        return idex;
    }
    
    public static void sort(Comparable[] a)
    {   
        Stack stack = new Stack();
        stack.push(0);
        stack.push(a.length);
        
        while (!stack.isEmpty()) 
        { 
            int hi = (int) stack.pop();
            int lo = (int) stack.pop(); 
            if (hi - lo < 2){continue;} 
            
            int pivot = lo + ((hi - lo) / 2); 
            pivot = partition(a,pivot, lo, hi);
            
            stack.push(pivot + 1);
            stack.push(hi); 
            
            stack.push(lo);
            stack.push(pivot); }


    
    }
    
    public static void main(String[] args) {
        
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
