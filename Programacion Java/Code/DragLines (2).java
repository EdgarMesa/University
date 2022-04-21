package mat2670;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DragLines extends JFrame
{

    private final DrawPanel myPanel;

    private static class Line
    {

        private Point start;
        private Point end;

        public Line(Point start, Point end)
        {
            this.start = start;
            this.end = end;
        }

        public Point getStart()
        {
            return start;
        }

        public Point getEnd()
        {
            return end;
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
            if (start != null && end != null)
            {
                g.drawLine(start.x, start.y, end.x, end.y);
            }
        }
    }

    private static class DrawPanel extends JPanel
    {
        private List<Line> lines = new LinkedList<>();
        
        public DrawPanel()
        {
            setBackground(Color.black);
            setForeground(Color.green);
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    lines.add(new Line(e.getPoint(), null));                    
                }

                @Override
                public void mouseReleased(MouseEvent e)
                {
                }
            });
            addMouseMotionListener(new MouseMotionAdapter()
            {

                @Override
                public void mouseDragged(MouseEvent e)
                {
                    Line lastLine = lines.get(lines.size() - 1);
                    lastLine.setEnd(e.getPoint());
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            for(Line l : lines)
            {
                l.draw(g);
            }
        }
    }

    public DragLines()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(50, 50, 800, 800);
        myPanel = new DrawPanel();
        add(myPanel);
    }

    public static void main(String[] args)
    {
        DragLines myDemo = new DragLines();

        java.awt.EventQueue.invokeLater(() ->
        {
            myDemo.setVisible(true);
        });
    }
}
