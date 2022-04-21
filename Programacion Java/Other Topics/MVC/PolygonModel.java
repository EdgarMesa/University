
import java.io.Serializable;

public class PolygonModel extends AbstractModel implements Serializable
{
    private int numberOfSides = 3;
    public static final int maxNumberOfSides = 20;
    public static final int minNumberOfSides = 3;

    public int getNumberOfSides()
    {
        return numberOfSides;
    }

    public void setNumberOfSides(int numberOfSides)
    {
        // Keap number of side within a given range
        if(numberOfSides < minNumberOfSides) numberOfSides = minNumberOfSides;
        else if (numberOfSides > maxNumberOfSides) numberOfSides = maxNumberOfSides;
        
        // Update and fire property change if needed
        int old = this.numberOfSides;
        this.numberOfSides = numberOfSides;
        if(old != numberOfSides) firePropertyChange("Number of sides", old, numberOfSides);        
    }
}
