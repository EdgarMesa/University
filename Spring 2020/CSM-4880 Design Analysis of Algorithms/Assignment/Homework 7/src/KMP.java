
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class KMP {
    private final int R;       // the radix
    private final int m;       // length of pattern
    private int[][] dfa;       // the KMP automoton
    private int count;
    private ArrayList<Integer> occurences;
    

    /**
     * Preprocesses the pattern string.
     *
     * @param pat the pattern string
     */
    public KMP(String pat) {
        this.R = 256;
        this.m = pat.length();
        
        

        // build DFA from pattern
        dfa = new int[R][m]; 
        dfa[pat.charAt(0)][0] = 1; 
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++) 
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases. 
            dfa[pat.charAt(j)][j] = j+1;   // Set match case. 
            x = dfa[pat.charAt(j)][x];     // Update restart state. 
        } 
    } 
    
    
    public int count(String text)
    {   
        
        count = 0;
        int n = text.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[text.charAt(i)][j];
            
            if (j == m)
            {
                count += 1;
                j = 0;
            } 
        }
    
        return count;
    }
    
    
    public ArrayList<Integer> serachAll(String text)
    {
        occurences = new ArrayList<Integer>();
        int n = text.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) 
        {
            j = dfa[text.charAt(i)][j];
            
            if (j == m)
            {
                occurences.add(i-(m-1));
                j = 0;
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
     *         in the text string; N if no such match
     */
    public int search(String txt) {

        // simulate operation of DFA on text
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m) return i - m;    // found
        return n;                    // not found
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


        KMP kmp1 = new KMP(pat);
        int count = kmp1.count(txt);
        occurences = kmp1.serachAll(txt);



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