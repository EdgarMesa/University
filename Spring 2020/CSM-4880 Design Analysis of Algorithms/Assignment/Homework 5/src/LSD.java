
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class LSD {
    private int w;
    private String[] a;
    private int R;
    private int minValue = Integer.MAX_VALUE;
    

    public LSD(String[] a,int w,int r)
    {
        this.w = w;
        this.a = a;
        this.R = r;
        
        for(String s : a)
        {
            if(s.charAt(0) < minValue)minValue = s.charAt(0);    
        }
        System.out.println("MINVALUE: "+ minValue);
    }
    
    public String[] array(){return a;}

    // do not instantiate
    private LSD() { }

   /**  
     * Rearranges the array of w-character strings in ascending order.
     *
     * @param a the array to be sorted
     * @param w the number of characters per string
     */
     public void sort() {
        int n = a.length;
        String[] aux = new String[n];

        for (int d = w-1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R+1];
            for (int i = 0; i < n; i++)
                count[(a[i].charAt(d) + 1) - minValue]++;

            // compute cumulates
            for (int r = 0; r < R; r++)
                count[r+1] += count[r];

            // move data
            for (int i = 0; i < n; i++)
                aux[count[(a[i].charAt(d))- minValue]++] = a[i];

            // copy back
            for (int i = 0; i < n; i++)
                a[i] = aux[i];
        }
    }

   

    /**
     * Reads in a sequence of fixed-length strings from standard input;
     * LSD radix sorts them;
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();

        int n = a.length;
        LSD lsd = new LSD(a,a[0].length(),256);

        // check that strings have fixed length
        int w = a[0].length();
        for (int i = 0; i < n; i++)
            assert a[i].length() == w : "Strings must have fixed length";

        // sort the strings
        lsd.sort();

        // print results
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}