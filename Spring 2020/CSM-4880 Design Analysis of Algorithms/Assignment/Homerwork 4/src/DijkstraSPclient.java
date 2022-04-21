
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class DijkstraSPclient {
    
    
        public static void main(String[] args) {
            
            
            In in = new In(args[0]);
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
            double greater = 0;
            int source = 0;
            int to = 0;
            
            for(int v = 0; v < G.V();v++)
            {
            
                DijkstraSP sp = new DijkstraSP(G,v);
                
                for (int t = 0; t < G.V(); t++) 
                {
                    if (sp.hasPathTo(t)) 
                    {
                        if(sp.distTo(t) > greater)
                        {
                            greater = sp.distTo(t);
                            source = v;
                            to = t;
                        }


                    }

                }
                
        
                
            }
            
            StdOut.println("RADIUS OF THE GRAPH:  "+greater);        
            StdOut.println("FROM VERTEX: "+ source + " TO: "+ to);        
        
        }
    
}
