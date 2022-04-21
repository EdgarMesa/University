
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;



/**
 *
 * @author Edgar Mesa
 */
public class Degrees {
    
    private final int V;           // number of vertices in this digraph
    private int E;                 // number of edges in this digraph
    private int[] indegree;        // indegree[v] = indegree of vertex v
    private Bag<Integer>[] adj;    // adj[v] = adjacency list for vertex v


    
    Degrees(Digraph G)
    {
    
        this.V = G.V();
        this.E = G.E();
        indegree = new int[V];
        
        for(int i = 0; i < V; i++)
        {
         for(int e : G.adj(i))indegree[e]++;
            
        }
        
        adj = G.ADJ();
    }
       
    
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
           
    int indegree(int v)
    {
    validateVertex(v);
    return indegree[v];
    
    }
    
    int outdegree(int v) 
    {
        validateVertex(v);
        return adj[v].size();

    }
    

    Iterable<Integer> sources() 
    {
    
        Queue<Integer> list = new Queue<Integer>();
        for(int v = 0; v < V;v++ )
        {
            if(indegree(v) == 0) list.enqueue(v);
            
        }
        
        return list;
    }
    
    Iterable<Integer> sinks() 
    {
    
        Queue<Integer> list = new Queue<Integer>();
        for(int v = 0; v < V;v++ )
        {
            if(outdegree(v) == 0) list.enqueue(v);
            
        }
        
        return list;

    }
    
    boolean isMap()
    {
        
        for(int v = 0; v < V;v++ )
        {
            if(outdegree(v) != 1 ) return false;
            
        }
        
        return true;
    }
    

    
    public static void main(String[] args) {
    In in = new In(args[0]);


    Digraph G = new Digraph(in);
    Degrees deg = new Degrees(G);

    StdOut.println(G);
    
    StdOut.println("--------------------------------------");
    for(int v = 0;v < G.V(); v++)
    {
    
        StdOut.println("For vertex "+v+" indegree is "+deg.indegree(v)+ " and outdegree is "+deg.outdegree(v));
       
    }
        
        
    StdOut.println("--------------------------------------");
    StdOut.println("Sources: "+deg.sources());
    StdOut.println("Sinks: "+deg.sinks());
    
    //ENABLE SEL-LOOP in the Diagraph class by removing the function PararellOrSelf
    //from the Digraph(In in) constructor
    StdOut.println("Is Map?: "+deg.isMap());


    
    




    }
    
    
}
