
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lenovo
 */
public class DigraphTestClient 
{
    
    public static void main(String[] args) {
    In in = new In(args[0]);
    In in2 = new In(args[1]);
    In in3 = new In(args[2]);

    Digraph G = new Digraph(in);
    Digraph G2 = new Digraph(G);




    StdOut.println(G);
    
    StdOut.println("--------------------------------------");
    G.addEdge(2, 7);
    for(int v = 0;v < G.V(); v++)
    {
    
        StdOut.println("For vertex "+v+" indegree is "+G.indegree(v)+ " and outdegree is "+G.outdegree(v));
       
    }
        
        
    StdOut.println("--------------------------------------");
    
    
    StdOut.println("Reverse Digraph");
    Digraph reverse = new Digraph(G);
    reverse = reverse.reverse();
    StdOut.println(reverse);
   
    
    StdOut.println("--------------------------------------");

    StdOut.println("Is 5 conected to 4?  " + G2.hasEdge(5, 4));
    StdOut.println("Is 4 conected to 5?  " + G2.hasEdge(4, 5));

    
    StdOut.println("--------------------------------------");
    StdOut.println("Copied Digraph");
    StdOut.println(G2);
    
    StdOut.println("--------------------------------------");


    //Activate to check for self-loop and pararell edges

    //Digraph Gselfloop = new Digraph(in2);

    //Digraph Gpararell = new Digraph(in3);
 

    }
    
    
}
