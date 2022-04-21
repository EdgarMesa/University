
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class BoyerMoore {
    private final int R;     // the radix
    private int[] right;     // the bad-character skip array

    private char[] pattern;  // store the pattern as a character array
    private String pat;      // or as a string
    
    private int count;
    private ArrayList<Integer> occurences;

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public BoyerMoore(String pat) {
        this.R = 256;
        this.pat = pat;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
    }

    
     public int count(String text)
    {   
        count = 0;

        int m = pat.length();
        int n = text.length();
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m-1; j >= 0; j--) {
                if (pat.charAt(j) != text.charAt(i+j)) {
                    skip = Math.max(1, j - right[text.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0)
            {
                count += 1;
                i += m;
                
            }    
            
        }
        return count;                       
    }
     
     
      public ArrayList<Integer> serachAll(String text)
    {
        occurences = new ArrayList<Integer>();

        int m = pat.length();
        int n = text.length();
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m-1; j >= 0; j--) {
                if (pat.charAt(j) != text.charAt(i+j)) {
                    skip = Math.max(1, j - right[text.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0)
            {
                occurences.add(i);
                i += m;
            }    
            
        }
        return occurences; 

    }

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param  txt the text string
     * @return the index of the first occurrence of the pattern string
     *         in the text string; n if no such match
     */
    public int search(String txt) {
        int m = pat.length();
        int n = txt.length();
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m-1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return n;                       // not found
    }





    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        ArrayList<Integer> occurences;
        


        BoyerMoore boyermoore1 = new BoyerMoore(pat);
        int count = boyermoore1.count(txt);
        occurences = boyermoore1.serachAll(txt);

        StdOut.println("text:       " + txt);
        StdOut.print("Occurences: ");
                
        int prev = 0;
        int forw = 0;
        for(int index : occurences)
        {
            forw = index;
            for (int i = prev; i < forw; i++)
            {
                StdOut.print(" ");
            }
            
            prev = index+pat.length();
            StdOut.print(txt.substring(index,index+pat.length()));
        }
        StdOut.println();

        StdOut.println("pattern:    " + pat);
        StdOut.println("number of occurences: " + count);  


}
    
}