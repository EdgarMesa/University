
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;
import java.util.Arrays;

public class KruskalMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight = Integer.MAX_VALUE;           
    private double WEIGHTS[];                        // weight of MST
// weight of MST
    private Queue<Edge>[] MST;
    private Queue<Edge> mst = new Queue<Edge>();

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public KruskalMST(EdgeWeightedGraph G) {
        // more efficient to build heap by passing array of edges
        MinPQ<Edge> pq = new MinPQ<Edge>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }
       

        CC conect = new CC(G);
        int count = conect.count();
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[count];
        
        for(int i= 0; i < count; i++)
        {
            components[i] = new Queue<Integer>();
        }
        
        for (int v = 0; v < G.V(); v++) 
        {
            components[conect.id(v)].enqueue(v);
        }
        MST = (Queue<Edge>[]) new Queue[count];
        
        
        for(int i= 0; i < count; i++)
        {
            
            MST[i] = new Queue<Edge>();
        }
        
        WEIGHTS = new double[count];

        for(int c = 0; c < count; c++)
        {
            
            for(int vertice : components[c])
            {
                    // run greedy algorithm
            UF uf = new UF(G.V());
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);


            if (uf.find(v) != uf.find(w)) 
                { // v-w does not create a cycle
                uf.union(v, w);  // merge v and w components
                MST[conect.id(v)].enqueue(e);  // add edge e to mst
                


                WEIGHTS[conect.id(v)] = WEIGHTS[conect.id(v)] + e.weight();
                }

            }


        }
        
            int index = 0;

            double[] sorted_array = Arrays.copyOf(WEIGHTS, count);
            Arrays.sort(sorted_array);

            weight = sorted_array[0];
            for(int i = 0; i < count;i++)
            {
                if(WEIGHTS[i] == weight)index = i;
            }
            mst = MST[index];
            

        
        // check optimality conditions
        assert check(G);
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        return weight;
    }
    
    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(EdgeWeightedGraph G) {

        // check total weight
        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) == uf.find(w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.find(v) != uf.find(w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }
            
            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (uf.find(x) != uf.find(y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }


    /**
     * Unit tests the {@code KruskalMST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        

        System.out.println("MINIMUN SPANING FOREST");
        KruskalMST mst = new KruskalMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }

}