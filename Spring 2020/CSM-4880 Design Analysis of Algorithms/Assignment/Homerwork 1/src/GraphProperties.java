/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Cycle;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jjcarr3
 */
public class GraphProperties
{
    private Graph g;
    private CC cc;
    private ArrayList <Integer> theGraphs;
    private boolean isConnected = true;
    
    public GraphProperties(Graph G)
    {
        g = new Graph(G);
        cc = new CC(G);
        theGraphs = new ArrayList<Integer>();
        
        if (cc.count()>1)
        {
            System.out.println("This is a disconnected graph.");
            isConnected = false;
        }
    }
    
    public int eccentricity(int v) 
    {
        BreadthFirstPaths bfs = new BreadthFirstPaths(g,v);
        int maxDistance = 0;
        
        //eccentricity of v
        if (!isConnected)
        {
            System.out.println("This has an infinite eccentricity since"
                    + " it is disconnected.");
        }
        else
        {
            // cycle through the all vertices
            for (int i = 0; i < g.V(); i++)
            {
                if (bfs.distTo(i) > maxDistance)
                {
                    maxDistance = bfs.distTo(i);
                }
            }
        }
        return maxDistance;
    }
    
    public int diameter() 
    {
        //diameter of G
        BreadthFirstPaths bfs;
        int maxDistance = 0;
        
        // cycle through the all vertices
        if (isConnected)
        {    
            for (int i = 0; i < g.V(); i++)
            {
                bfs = new BreadthFirstPaths(g,i);
                for (int j = 0; j < g.V(); j++)
                {
                    if (bfs.distTo(j) > maxDistance)
                    {
                        maxDistance = bfs.distTo(j);
                    }
                }
            }
        }
        return maxDistance;
    }
    
    public int radius()
    {
        //radius of G
        int gDiameter = diameter();
        return Math.floorDiv(gDiameter, 2);
    }

    public int center()
    {
        //a center of G
        return 0;
    }
    
    public int girth()
    {
        BreadthFirstPaths bfs;
        int smallest = g.V();
        int count=0;
        int lastVertex=0;
        
//        Run BFS from each vertex. The shortest cycle containing s is a shortest
//        path from s to some vertex v, plus the edge from v back to s .
        
        for (int s = 0; s < g.V(); s++)
        {
            bfs = new BreadthFirstPaths(g,s);
            for (int v = 0; v < g.V(); v++)
            {
                if (bfs.hasPathTo(v))
                {   
                    count = 1;
                    for (int w : bfs.pathTo(v))
                    {
                        count += 1;
                        lastVertex = w;
                    }
                    for (int w : g.adj(lastVertex))
                    {
                        if (w==s && count < smallest) { smallest = count; }
                    }
                }
            }
        }
        return smallest;
    }
    
    public void cycleShow()
    {
        Cycle c = new Cycle(g);
        if (c.hasCycle()) {
            for (int v : c.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("Graph is acyclic");
        }
    }
    
    public void show()
    {
       System.out.println(g.toString());
    }
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        Graph myG = new Graph(in);
        GraphProperties myHW = new GraphProperties(myG);
        
//        Scanner input = new Scanner(System.in);      
//        System.out.println("Please enter a vertex number:");        
//        int vertex = input.nextInt();
//        System.out.println("Eccentricity of "+vertex+" is "+
//                myHW.eccentricity(vertex));
//        
//        System.out.println("The diameter of the graph is "+
//                myHW.diameter()+".");
        
//        myHW.show();
        System.out.println("The girth is "+myHW.girth()+".");
    }
}
