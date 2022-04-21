
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;

public class PolygonController implements PropertyChangeListener
{
    private final PolygonModel model;
    private final PolygonView view;
    
    // Build our controller
    private final static PolygonController controller = new PolygonController();

    public int getNumberOfSides()
    {
        return model.getNumberOfSides();
    }
    
    public PolygonModel getModel()
    {
        return model;
    }

    public PolygonView getView()
    {
        return view;
    }
    
    public PolygonController()
    {
        // Setup model and view for this controller
        model = new PolygonModel();
        model.addPropertyChangeListener(this);
        view = new PolygonView(this);        
    }
    
    public void setNumberOfSides(int size)
    {
        model.setNumberOfSides(size);
    }
    
    public static void main(String[] args)
    {        
        // Setup and open window
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(controller.view);       
        EventQueue.invokeLater(() -> {frame.setVisible(true);});
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        // When the model changes update the view
        view.repaint();
    }
    
}
