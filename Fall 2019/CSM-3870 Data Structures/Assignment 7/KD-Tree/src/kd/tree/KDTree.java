package kd.tree;



import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.*;

/**
 *
 * @author Edgar Mesa Perez
 */
public class KDTree {


    
    private Node2d root;
    private int size;
    
    
    
    public KDTree()
    {
        root = null;
        size = 0;
    }
    
    public boolean IsEmpty(){return size == 0;}
    
    public int size(){return size;}
    
    public void insert(Point2D point)
    {
        nullError(point);
        if(!contains(point))
        {
            root = insert(root,point,true);
            size++;
        }
    }
    
    private Node2d insert(Node2d root, Point2D point, boolean Hsplit)
    {
        if(root == null){return new Node2d (point, null, null,Hsplit);}
    
    
        if(Hsplit)
        {
            if(point.x() < root.point.x())
            {
                root.left = insert(root.left,point,!Hsplit);
            }
            else{root.right = insert(root.right,point,!Hsplit);}
        }
        
        else
        {
        if(point.y() < root.point.y())
            {
                root.left = insert(root.left,point,!Hsplit);
            }
            else{root.right = insert(root.right,point,!Hsplit);}
        }
    
    return root;
    
    }
    
    public boolean contains(Point2D point)
    {
        nullError(point);
        
            
       return(contains(root,point));
    }
    
    private boolean contains(Node2d root, Point2D point)
    {
        if(root == null)return false;
        
        if(point.equals(root.point)){return true;}
        
        if (root.Hsplit && point.x() < root.point.x() ||
            !root.Hsplit && point.y() < root.point.y()) {
            return contains(root.left, point);
        } else {
            return contains(root.right, point);
        }
    }
    
    
        public Iterable<Point2D> range(RectHV rect)
    {
        
        nullError(rect);

        Set<Point2D> pointsInside = new HashSet();
        
        if(!IsEmpty())
        {
            range(root,rect,pointsInside);
        }
        
        
        return pointsInside;
    }    
    
    
     public void range(Node2d current,RectHV rect,Set<Point2D> poinsInside)
    {
        if(current == null){return;}
        
        boolean intersects ;

        Point2D p = current.point;
        
        if(rect.contains(p))
        {
            poinsInside.add(p);
            intersects = true;
            
        }
        
        
         else if (current.Hsplit && rect.intersects(new RectHV(current.point.x(), 0.0, current.point.x(), 1.0)) ||
            !current.Hsplit && rect.intersects(new RectHV(0.0, current.point.y(), 1.0, current.point.y()))) 
         {
            // rect intersects with current node
            intersects = true;
        } else 
         {
            intersects = false;
        }
        
        
        
        if (intersects) 
        {
            range(current.left, rect, poinsInside);
            range(current.right, rect, poinsInside);
        } else 
        
        {
            if ((current.Hsplit && p.x() > rect.xmin()) || (!current.Hsplit && p.y() > rect.ymin())) 
            {
                range(current.left, rect, poinsInside);
            } else 
            {
                range(current.right, rect, poinsInside);
            }
        }
        

    
    }
     
     
     public Point2D nearest(Point2D point)
    {
        nullError(point);
        if(IsEmpty()){return null;}
        

        Point2D closestPoint = nearest(root, point, root).point;

        
        return closestPoint;
    }
    
    
     private Node2d nearest(Node2d current, Point2D point, Node2d nearest) 
     {
        if (current == null || nearest.point.distanceSquaredTo(point) < current.point.distanceSquaredTo(point)) 
        {
            return nearest;
        }

        if (current.point.distanceSquaredTo(point) < nearest.point.distanceSquaredTo(point)) 
        {
            nearest = current;
        }

        Node2d left = nearest(current.left, point, nearest);
        if (left.point.distanceSquaredTo(point) < nearest.point.distanceSquaredTo(point)) 
        {
            nearest = left;
        }

        Node2d right = nearest(current.right, point, nearest);
        if (right.point.distanceSquaredTo(point) < nearest.point.distanceSquaredTo(point)) 
        {
            nearest = right;
        }

        return nearest;
    }
    
    
    
    private void nullError(Object p)
    {
        
        if(p == null)
        {
            throw new NullPointerException();
        }
    }
    
    
    public void draw() 
    {
        if (!IsEmpty()) 
        {
            root.draw();
        }
    }
    
    private void print() 
    {
        root.print(0);
    }
    
    
    
    
    private class Node2d
    {
        private Node2d left;
        private Node2d right;
        private boolean Hsplit;
        private Point2D point;
        
        public Node2d(Point2D point,Node2d left,Node2d right,boolean split)
        {
            this.point = point;
            this.left = left;
            this.right = right;
            this.Hsplit = split;
           
        }
        
        private void draw() {
            if (left != null) {
                left.draw();
            }
            point.draw();
            if (right != null) {
                right.draw();
            }
        }

        private void print(int level) {
            System.out.println(point + " level: " + level + " horizontalSplit: " + Hsplit);
            if (left != null) {
                System.out.print("  left of" + point + ": ");
                left.print(level + 1);
            }
            if (right != null) {
                System.out.print("  right of" + point + ": ");
                right.print(level + 1);
            }
        }
    }
    
    
    public static void main(String[] args) {
       
        
    }
    
}
