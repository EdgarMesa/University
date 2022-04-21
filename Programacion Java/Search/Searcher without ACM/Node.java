
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;


/**
 * This class represents a position with in a maze and the path used to get
 * there.
 */
public class Node implements Comparable<Node>
{
    // Going to assume that there is only one search going on in one maze.
    // Thus the maze will be shared by all nodes.
    private static WeakReference<Maze> mazeRef = new WeakReference<>(null);
    
    // Likewise I am going to set which heuristic to use for the whole search.
    private static boolean useAStarHeuristic = true;
    
    public static void setMaze(Maze m)
    {
        mazeRef = new WeakReference<>(m);
    }
    
    public static void setUseAStarHeuristic(boolean flag)
    {
        useAStarHeuristic = flag;
    }
    
    public static boolean useAStarHeuristic()
    {
        return useAStarHeuristic;
    }
        
    private String path;
    private int row, col;

    public String getPath()
    {
        return path;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    /**
     * Two Nodes are equivalent if they have the same position in the
     * same maze (only checking position).
     */
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Node))
        {
            return false;
        }
        return ((Node) obj).row == row && ((Node) obj).col == col;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 17 * hash + this.row;
        hash = 17 * hash + this.col;
        return hash;
    }

    /**
     * Constructs a node with the given position and an empty path.
     *
     * @param row row location
     * @param col column location
     */
    public Node(int row, int col)
    {
        this(row, col, "");
    }

    /**
     * Constructs a node with the given position and path.
     *
     * @param row row location
     * @param col column location
     * @param path path from start to current location
     */
    public Node(int row, int col, String path)
    {
        this.row = row;
        this.col = col;
        this.path = path;
    }

    /**
     * Determine if this node is located at the goal.
     *
     * @return true if the position of this node is at the goal of the maze
     */
    public boolean isGoal()
    {
        Maze m = mazeRef.get();
        if(m == null) throw new IllegalStateException("maze is null");
        return m.isGoal(row, col);
    }

    /**
     * Generates a list of all of the positions that are reachable from this
     * node.
     *
     * @return a list of all of the nodes reachable from this node
     */
    public List<Node> getChildren()
    {
        Maze m = mazeRef.get();
        if(m == null) throw new IllegalStateException("maze is null");
        
        List<Node> childrenList = new LinkedList<>();
        if (m.isOpen(row - 1, col))
        {
            childrenList.add(new Node(row - 1, col, path + "U"));
        }
        if (m.isOpen(row + 1, col))
        {
            childrenList.add(new Node(row + 1, col, path + "D"));
        }
        if (m.isOpen(row, col - 1))
        {
            childrenList.add(new Node(row, col - 1, path + "L"));
        }
        if (m.isOpen(row, col + 1))
        {
            childrenList.add(new Node(row, col + 1, path + "R"));
        }
        return childrenList;
    }

    public int heuristic()
    {
        Maze m = mazeRef.get();
        if(m == null) throw new IllegalStateException("maze is null");
        
        return Math.abs(row - m.getEndRow()) + Math.abs(col - m.getEndCol()) + 
                (useAStarHeuristic ? path.length() : 0);
    }

    @Override
    public int compareTo(Node o)
    {
        return Integer.compare(heuristic(), o.heuristic());
    }
}
