

package merge;

import java.util.Arrays;


/**
 *
 * @author Edgar Mesa Perez
 */
public class FasterMerge {
    
    public static void main(String[] args) {
        
        Comparable[] a = new Comparable[]{5,2,3,6,7,8,4,9,1,2};
        
        System.out.println("Array not sorted: " + Arrays.toString(a));
        sort(a);
       System.out.println("Array sorted:     " + Arrays.toString(a));

        
     
    }
    
    private static boolean less(Comparable a,Comparable b)
    {
     return a.compareTo(b) < 0; 
    }
    
    private static boolean IsSorted(Comparable[] arr)
    {
        for(int i = 1;i <= arr.length;i++)
        {
            if(less(i,i-1)){return false;}
        }
        
        return true;
    }
    
    private static void merge(Comparable[] arr,Comparable[] aux,int low,int mid,int hi)
    {
        for(int i = low; i <= mid;i++)
        {
            aux[i] = arr[i];
        }
         
       
        for(int t = mid+1; t <= hi;t++)
        {
            aux[t] = arr[hi-t+mid+1];
        }

        
        int i = low;
        int j = hi;
        
        for(int k = low; k <= hi;k++)
        {
            if(less(aux[i],aux[j])){arr[k] = aux[i++];}
            else{arr[k] = aux[j--];}
        }
       
    }
    
    private static void sort(Comparable[] arr,Comparable[] aux,int low,int hi)
    {
        if(hi <= low){return;}
        int mid = low + (hi-low)/2;
        sort(arr,aux,low,mid);
        sort(arr,aux,mid+1,hi);
        merge(arr,aux,low,mid,hi);
    
    }
    
    public static void sort(Comparable[] a)
 {
    Comparable[] aux = new Comparable[a.length];
    sort(a, aux, 0, a.length - 1);
 }
    
    
    
    
    
}
