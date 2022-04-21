package percolation;

import java.io.*;
import java.util.*;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.QuickFindUF;


/**
 *
 * @author Edgar Mesa Perez
 */
public class Percolation {
    
    private boolean[] open;
    private WeightedQuickUnionUF qu;
    private QuickFindUF qf;
    private int gridW;
    
        // test client

    public static void main(String[] args) {
        
        Percolation perco = new Percolation(4);

        System.out.println(perco.percolates());
        
    }
    
     // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if(n <= 0)
        {
            throw new IllegalArgumentException(" n must be > 1") ;
        }
        qu = new WeightedQuickUnionUF(n*n+2);
        //qf = new QuickFindUF(n*n+2);
        open = new boolean[n*n];
        gridW = n;
        
        connectTopandBotoom();
        
        
    }
    
    //connects the virtual top to the first row and the virtual bottom to the last row
    public void connectTopandBotoom()
    {
        //ilegalexception(row,col,gridW);
        for(int i = 1; i <= gridW; i++)
        {
            qu.union(0, i);
            qu.union(gridW*gridW+1,gridW*gridW+1-i);
            
            //qf.union(0, i);
            //qf.union(gridW*gridW+1,gridW*gridW+1-i);            
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        ilegalexception(row,col,gridW);
        
        if(isOpen(row,col)){return;}
        
        else
        {
            int index = gridW * (row - 1) +col;
            
            int indexforopens = gridW * (row - 1) +col-1;

            open[indexforopens] = true;
           
            if(col - 1 > 0 && isOpen(row,col-1))
            {
                qu.union(index, (row-1) * gridW + col -1);
                //qf.union(index, (row-1) * gridW + col -1);
            }
            
            if(col + 1 < gridW && isOpen(row,col+1))
            {
                qu.union(index, (row-1) * gridW + col +1);
                //qf.union(index, (row-1) * gridW + col +1);
               
            }
            
            if(row - 1 > 0 && isOpen(row -1,col))
            {               
                qu.union(index, (row-2) * gridW + col);
                //qf.union(index, (row-2) * gridW + col);
            }
            
            if(row +1 < gridW && isOpen(row +1,col))
            {
                qu.union(index, row * gridW + col);
                //qf.union(index, row * gridW + col);
            }
        }
  
    }
    
    public void ilegalexception(int row,int col, int gridW)
    {
        if(row < 1 || row > gridW  || col< 1 || col > gridW )
        {
            throw new IllegalArgumentException(" the row and column must be < n and > 1") ;
        } 
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        ilegalexception(row,col,gridW);
        int index = gridW * (row - 1) +col-1;
                
        return open[index];}
    
    
    

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        ilegalexception(row,col,gridW);
        int index = gridW * (row - 1) +col-1;
        
        return !open[index];
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        int count = 0;
        for(boolean trues : open)
        {
        if(trues){count++;}
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return qu.connected(0,gridW*gridW+1);
        //return qf.connected(0,gridW*gridW+1);
    }
        


    
}
