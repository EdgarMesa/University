
package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 *
 * @author Edgar Mesa Perez
 */
public class PercolationStats {
    
    double[] T;
    
    
     // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if( n < 1 || trials < 1)
        {
            throw new IllegalArgumentException("Neither n or T can be < 1");
        }
        
        Percolation perco;
        T = new double[trials];
        
        for(int i = 0; i < trials; i++)
        {   
            int count = 0;
            perco = new Percolation(n);
            
            int row = StdRandom.uniform(1,n+1);
            int col = StdRandom.uniform(1,n+1);
            
            while(!perco.percolates())
            {
                if(!perco.isOpen(row, col)){perco.open(row, col);}
                count++;
                row = StdRandom.uniform(1,n+1);
                col = StdRandom.uniform(1,n+1);
            }

            T[i] = (double)count/ n/ n;
        }
    }    
    


    // sample mean of percolation threshold
    public double mean(){return StdStats.mean(T);}

    // sample standard deviation of percolation threshold
    public double stddev(){return StdStats.stddev(T);}

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - 1.96 * stddev() / Math.sqrt(T.length);
    
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + 1.96 * stddev() / Math.sqrt(T.length);
    }

   // test client (see below)
   public static void main(String[] args){
   
       int n = Integer.valueOf(args[0]);
       int trials = Integer.valueOf(args[1]);
       
       Stopwatch watch = new Stopwatch();
       
       PercolationStats stats = new PercolationStats(n,trials);
       
       System.out.printf("mean                    = %f\n",stats.mean());
       System.out.printf("stddev                  = %f\n",stats.stddev());
       System.out.printf("95%% confidence interval = [%f,%f]\n",stats.confidenceLo(),stats.confidenceHi());
       
       System.out.printf("Total time for N : %d and T : %d = %fsec\n",n,trials,watch.elapsedTime());
  
   }
    
    
}
