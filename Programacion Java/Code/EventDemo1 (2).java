package mat2670;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EventDemo1 extends JFrame
{

    private final DrawPanel myPanel;

    private static class DrawPanel extends JPanel
    {

        private static final int DOT_SIZE = 50;
        private final List<Point> presses = new LinkedList<>();

        public DrawPanel()
        {
            setBackground(Color.black);
            setForeground(Color.green);
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    // Note this location
                    presses.add(e.getPoint());

                    // Ask for paint to be called
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            for(Point p : presses)
            {
                g.fillOval(p.x - DOT_SIZE / 2,
                        p.y - DOT_SIZE / 2,
                        DOT_SIZE, DOT_SIZE);
            }
        }
    }

    public EventDemo1()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(50, 50, 800, 800);
        myPanel = new DrawPanel();
        add(myPanel);
    }

    public static void main(String[] args)
    {
        EventDemo1 myDemo = new EventDemo1();

        java.awt.EventQueue.invokeLater(() ->
        {
            myDemo.setVisible(true);
        });
    }
}
