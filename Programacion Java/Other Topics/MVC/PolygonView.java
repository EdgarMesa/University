
import java.awt.Graphics;
import java.awt.Polygon;
import java.lang.ref.WeakReference;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class PolygonView extends JPanel
{
    private final JSlider slider;
    private final WeakReference<PolygonController> controllerRef;

    public PolygonView(PolygonController controller)
    {
        controllerRef = new WeakReference<>(controller);
        slider = new JSlider(PolygonModel.minNumberOfSides, PolygonModel.maxNumberOfSides, PolygonModel.minNumberOfSides);
        slider.addChangeListener((ChangeEvent e) ->
        {
            controllerRef.get().setNumberOfSides(slider.getValue());
        });
        add(slider);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int numberOfSides = controllerRef.get().getNumberOfSides();
        int w = getWidth();
        int h = getHeight();
        int centerX = w / 2;
        int centerY = h / 2;
        double r = Math.min(w, h) * .4;
        double angle = Math.PI * 2 / numberOfSides;
        Polygon p = new Polygon();
        for(int pointNumber = 0; pointNumber < numberOfSides; pointNumber++)
        {
            int x = centerX + (int)(r * Math.cos(angle * pointNumber));
            int y = centerY - (int)(r * Math.sin(angle * pointNumber));
            p.addPoint(x, y);
        }
        g.drawPolygon(p);
    }
    
}
