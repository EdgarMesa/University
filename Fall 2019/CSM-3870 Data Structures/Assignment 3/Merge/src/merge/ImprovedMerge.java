
package merge;

import java.util.Arrays;

/**
 *
 * @author Edgar Mesa Perez
 */
public class ImprovedMerge {
    
    public static void main(String[] args) {
 
        Comparable[] a = new Comparable[]{5,2,3,6,7,8,4,9,5,1,2,0,3,5,3,2,1,6,7,8,9,7,6,5,4,3,2,3,4,5};

        System.out.println("Array not sorted: " + Arrays.toString(a));
        sort(a);
        System.out.println("Array sorted:     " + Arrays.toString(a));
    
    }
    
    public static boolean less(Comparable a,Comparable b)
    {
     return a.compareTo(b) < 0; 
    }
    private static boolean equalorBigger(Comparable a,Comparable b)
    {
     return a.compareTo(b) >= 0; 
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
     
        int i = low;
        int j = mid+1;
        
        for(int k = low; k <= hi;k++)
        {
            if (i > mid)                    {aux[k] = arr[j++];}
            else if (j > hi)                {aux[k] = arr[i++];}
            else if (less(arr[j], arr[i]))  {aux[k] = arr[j++];}
            else                            {aux[k] = arr[i++];}
        }
    }
    
    private static void sort(Comparable[] arr,Comparable[] aux,int low,int hi)
    {
        if (hi <= low + 7 - 1)
        {
            insertion(aux, low, hi);
            return;
        }

        int mid = low + (hi-low)/2;
        sort(aux,arr,low,mid);
        sort(aux,arr,mid+1,hi);
        if (!equalorBigger(arr[mid+1], arr[mid])){;return;}
        merge(arr,aux,low,mid,hi);
    
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
    
    private static void exch(Comparable[] a, int i, int j)
{
    Comparable swap = a[i];
    a[i] = a[j];
    a[j] = swap;
}
    
    public static void sort(Comparable[] a)
 {
    Comparable[] aux = new Comparable[a.length];
       for(int i = 0; i < a.length ;i++)
        {
            aux[i] = a[i];
        }         
      
    sort(aux, a, 0, a.length - 1);
 }
    
}
