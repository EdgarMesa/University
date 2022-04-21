
import java.awt.Point;
import java.io.Serializable;
import java.util.Scanner;

// This is a "model" class for holding information about a maze and the current
// state of a search. It is used to store the data needed to display the search.
public class Maze extends AbstractModel implements Serializable
{
    public static final int OPEN_MASK      = 1;
    public static final int SEARCHING_MASK = 1 << 1;
    public static final int CLOSED_MASK    = 1 << 2;
    public static final int PATH_MASK      = 1 << 3;
    
    // The maze is will be stored in [row][col] form
    private final int[][] maze;
    
    // Points will be used to hold locations in the maze. y = row, x = col.
    private final Point start   = new Point(-1,-1); 
    private final Point end     = new Point(-1,-1); 
    private final Point current = new Point(-1,-1); 
    
    // Constructor builds a maze based on the given scanner. Scanner should
    // "contain" a maze in the form
    //
    // X Y
    // line1
    // line2
    // ...
    // lineX
    //
    // where X and Y are positive integers, each line has length Y,
    // each line only contains characters in ".*SE". Exactly one S and E
    // appear in all of the lines. Periods denote open spaces, asterisks closed
    // spaces, S that starting location, and E the ending location.
    //
    // Throws an IllegalArgumentException if the scanner does not hold data of
    // the correct form.
    public Maze(Scanner in)
    {
        try
        {
            // The first line should be two integers separated by a space. Make sure
            // that we have two tokens separated by a space.
            int numberOfRow = in.nextInt();
            int numberOfCol = in.nextInt();
            in.nextLine();

            // Make sure that we have valid numbers.
            if (numberOfRow < 1 || numberOfCol < 1)
            {
                throw new IllegalArgumentException("Number of rows and columns must be >= 1!");
            }

            // Create a new array to hold the maze. Entries default to being
            // filled. A filled border will be added to the maze to prevent
            // searches from leaving the maze.
            maze = new int[numberOfRow + 2][numberOfCol + 2];

            // Read in the remaining lines
            for (int row = 1; row <= numberOfRow; row++)
            {
                String line = in.nextLine();

                // Check that our line has the right length.
                if (line.length() != numberOfCol)
                {
                    throw new IllegalArgumentException("Line #" + row + " has an invalid line length!");
                }

                for (int col = 1; col <= numberOfCol; col++)
                {
                    // If we have a '*' this position is impassable 0.
                    if(line.charAt(col - 1) != '*') maze[row][col] = OPEN_MASK;

                    // Have we found the starting location
                    if (line.charAt(col - 1) == 'S')
                    {
                        if (start.y != -1 || start.x != -1)
                        {
                            throw new IllegalArgumentException("Found two starting positions: [" + row + ", " + col + "] and [" + start.y + ", " + start.x + "]!");
                        }
                        start.y = row;
                        start.x = col;
                    } else if (line.charAt(col - 1) == 'E') // Have we found the goal
                    {
                        if (end.y != -1 || end.x != -1) {
                            throw new IllegalArgumentException("Found two ending positions: [" + row + ", " + col + "] and [" + end.y + ", " + end.x + "]!");                        }
                        end.y = row;
                        end.x = col;
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("There was an error reading from the file.");
        }
    }
    // Inspectors
    public boolean isGoal(int row, int col)    {return end.y     == row && end.x     == col;}
    public boolean isStart(int row, int col)   {return start.y   == row && start.x   == col;}
    public boolean isCurrent(int row, int col) {return current.y == row && current.x == col;}

    public int getEndRow()   {return end.y;}
    public int getEndCol()   {return end.x;}
    public int getStartRow() {return start.y;}
    public int getStartCol() {return start.x;}
    
    public int getNumberOfRows(){return maze.length;}
    public int getNumberOfColumns(){return maze[0].length;}

    public boolean isOpen(int row, int col)         {return (maze[row][col] & OPEN_MASK) > 0;}
    public boolean isOnSearchList(int row, int col) {return (maze[row][col] & SEARCHING_MASK) > 0;}
    public boolean isOnClosedList(int row, int col) {return (maze[row][col] & CLOSED_MASK) > 0;}
    public boolean isOnPath(int row, int col)       {return (maze[row][col] & PATH_MASK) > 0;}
    public boolean isFilled(int row, int col)       {return !isOpen(row, col);}
    
    // Mutators
    private void updateMaze(int row, int col, int mask, boolean set)
    {
        int old = maze[row][col];
        if(set) maze[row][col] |=  mask;
        else    maze[row][col] &= ~mask;
        if(old != maze[row][col]) firePropertyChange("maze " + row + ", " + col, old, maze[row][col]);        
    }
    private void setMaze(int row, int col, int mask)   {updateMaze(row, col, mask, true);}
    private void clearMaze(int row, int col, int mask) {updateMaze(row, col, mask, false);}
        
    public void setOnSearchList(int row, int col) {setMaze(row, col, SEARCHING_MASK);}    
    public void setOnClosedList(int row, int col) {setMaze(row, col, CLOSED_MASK);}
    public void setOnPath(int row, int col)       {setMaze(row, col, PATH_MASK);}

    public void clearOnSearchList(int row, int col) {clearMaze(row, col, SEARCHING_MASK);}
    public void clearOnClosedList(int row, int col) {clearMaze(row, col, CLOSED_MASK);}
    public void clearOnPath(int row, int col)       {clearMaze(row, col, PATH_MASK);}
    public void clearAll(int row, int col)          {clearMaze(row, col, SEARCHING_MASK | CLOSED_MASK | PATH_MASK);}
    
    public void setCurrent(int row, int col) {
        Point old = new Point(current);
        current.setLocation(col, row);
        if(!old.equals(current)) firePropertyChange("Current Location", old, new Point(current));
    }
}
