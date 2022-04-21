package kd.tree;



import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;


/**
 *
 * @author Edgar Mesa Perez
 */
public class PointSET {
    
    private TreeSet<Point2D> points;
    
    
    
    public PointSET()
    {
        points = new TreeSet();
    }
    
    public boolean IsEmpty(){return points.isEmpty();}
    
    public int size(){return points.size();}
    
    public void insert(Point2D point)
    {
        nullError(point);
        if(!points.contains(point)){points.add(point);}
    }
    
    public boolean contains(Point2D point)
    {
        nullError(point);
        return points.contains(point);
    }
    
    
    public Iterable<Point2D> range(RectHV rect)
    {
        nullError(rect);

        List<Point2D> pointsInside = new LinkedList();
        
        for(Point2D p : points)
        {
            if(rect.contains(p)){pointsInside.add(p);}
        }
        
        return pointsInside;
    }            
    
    public Point2D nearest(Point2D p)
    {
        nullError(p);
        if(IsEmpty()){return null;}
        
        Point2D closestPoint = null;
        double minDist = 0.0;
        
        for(Point2D candidate : points)
        {
            if(p.distanceSquaredTo(candidate) < minDist)
            {
                minDist = p.distanceSquaredTo(candidate);
                closestPoint = candidate;
            }
            
        }
        
        return closestPoint;

    }
    
    
    
    private void nullError(Object p)
    {
        
        if(p == null)
        {
            throw new NullPointerException();
        }
    }
    
    
    public static void main(String[] args){}
    

    
}
