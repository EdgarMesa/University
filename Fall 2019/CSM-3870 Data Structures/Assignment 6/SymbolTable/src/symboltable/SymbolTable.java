
package symboltable;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author Edgar Mesa
 */
public class SymbolTable {

    
    public static void main(String[] args) {
        
        
        int distinct = 0, words = 0;
        int minlen = Integer.parseInt(args[0]);
        ST<String, Integer> st = new ST<String, Integer>();

        // compute frequency counts
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (key.length() < minlen) continue;
            words++;
            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
            }
            else {
                st.put(key, 1);
                distinct++;
            }
        }
        
        
        Comparable[] Keys = st.getKeys();
        Comparable[] Values = st.getValues();
        
        Quicksort.sort(Values, Keys);
        
        StdOut.println("\n---------------------------------------------------------");
        StdOut.println("ORDERED TABLE");
        StdOut.println("---------------------------------------------------------");

        StdOut.println();

        
        for(int i = 0;i < Keys.length ;i++)
        {
          
            StdOut.printf( "%s: %s\n",Keys[i],Values[i]);

        }
        
                   
        StdOut.println("\n---------------------------------------------------------");
        StdOut.println("UNORDERED TABLE");
        StdOut.println("---------------------------------------------------------");

        StdOut.println();
       





        for (String word : st.keys()) {
            
             StdOut.printf( "%s: %s\n",word, st.get(word));
          
        }
    }
    
   
    
}
