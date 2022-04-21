import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DrawingLinesDemo extends JFrame
{    
    private class DrawingPanel extends JPanel
    {
        private class Line
        {
            private Point start;
            private Point end;

            public Line(Point start, Point end)
            {
                this.start = start;
                this.end = end;
            }

            public void setStart(Point start)
            {
                this.start = start;
            }

            public void setEnd(Point end)
            {
                this.end = end;
            }
             
            public void draw(Graphics g)
            {
                g.drawLine(start.x, start.y, end.x, end.y);
            }
        }
        
        private final List<Line> lines = new LinkedList<>();
        
        public DrawingPanel()
        {
            super.setBackground(Color.black);
            super.setForeground(Color.green);

            super.addMouseListener(new MouseAdapter()
            {

                @Override
                public void mousePressed(MouseEvent e)
                {
                    lines.add(new Line(e.getPoint(), e.getPoint()));
                }                
            });
            
            super.addMouseMotionListener(new MouseAdapter()
            {
                @Override
                public void mouseDragged(MouseEvent e)
                {
                    lines.get(lines.size() - 1).setEnd(e.getPoint());
                    repaint();
                }
                
            });
            
        }
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            for(Line line : lines) line.draw(g);
        }   
    }
    
    public DrawingLinesDemo()
    {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(800, 800);        
        super.add(new DrawingPanel());                
    }
        
    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(() ->
        {
            new DrawingLinesDemo().setVisible(true);
        });
    }

}