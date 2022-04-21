
import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;
import javax.swing.JPanel;

// This is a "view" class for displaying mazes and the current state of a
// search.
public class MazePanel extends JPanel implements PropertyChangeListener
{
    private static final Color CURRENT_COLOR = Color.cyan;
    private static final Color OPEN_COLOR = Color.green;
    private static final Color FILLED_COLOR = Color.red;
    private static final Color START_COLOR = Color.orange;
    private static final Color GOAL_COLOR = Color.magenta;
    private static final Color SEARCH_COLOR = Color.blue;
    private static final Color CLOSED_COLOR = Color.black;
    private static final Color PATH_COLOR = Color.white;
    private static final Color GRID_COLOR = Color.gray;
    private static final Color BACKGROUND_COLOR = Color.black;
    private static final int GRID_SIZE = 1;
    
    private WeakReference<Maze> mazeRef;

    public MazePanel()
    {
        mazeRef = new WeakReference<>(null);
        setBackground(BACKGROUND_COLOR);
    }
    
    public void setMaze(Maze m) 
    {
        Maze old = mazeRef.get();
        if(m != old)
        {
            if(old != null) old.removePropertyChangeListener(this);
            mazeRef = new WeakReference<>(m);
            m.addPropertyChangeListener(this);
        }        
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        final Maze m = mazeRef.get();
        if(m == null) return;

        // Get our height and width and other size information for this paint
        final int width = getWidth();
        final int height = getHeight();
        final double size = Math.min(width, height);
        final int numberOfColumns = m.getNumberOfColumns();
        final int numberOfRows = m.getNumberOfRows();
        final double dx = size / numberOfColumns;
        final double dy = size / numberOfRows;
        
        // Starting y (top of grid) 
        double cellY = (height - size) / 2;

        // Draw each cell of the maze
        for(int row = 0; row < numberOfRows; row++)
        {
            // Starting x for each row.
            double cellX = (width - size) / 2;
            for(int column = 0; column < numberOfColumns; column++)
            {
                // Set the color for this cell
                Color c;
                if(m.isOnPath(row, column))            c = PATH_COLOR;
                else if(m.isCurrent(row, column))      c = CURRENT_COLOR;
                else if(m.isOnClosedList(row, column)) c = CLOSED_COLOR;
                else if(m.isOnSearchList(row, column)) c = SEARCH_COLOR;
                else if(m.isGoal(row, column))         c = GOAL_COLOR;
                else if(m.isStart(row, column))        c = START_COLOR;
                else if(m.isOpen(row, column))         c = OPEN_COLOR;
                else c = FILLED_COLOR;
                
                // Draw the cell
                g.setColor(c);
                g.fillRect((int) cellX, (int) cellY, (int)(cellX + dx - (int)cellX), (int)(cellY + dy - (int)cellY));
                cellX += dx;
            }
            cellY += dy;
        }

        // Draw the grid (go back to the top left of the grid)
        double cellX = (width - size) / 2;
        cellY = (height - size) / 2;
        g.setColor(GRID_COLOR);
        for(int i = 0; i <= numberOfColumns; i++)
        {
            g.fillRect((int)cellX, (int)cellY, GRID_SIZE, (int)(dy * numberOfRows));
            cellX += dx;
        }
        cellX = (width - size) / 2;
        for(int i = 0; i <= numberOfRows; i++)
        {
            g.fillRect((int)cellX, (int)cellY, (int)(dx * numberOfColumns), GRID_SIZE);
            cellY += dy;
        }        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        repaint();
    }
        
}
