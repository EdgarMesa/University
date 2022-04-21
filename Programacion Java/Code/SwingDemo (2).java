package mat2670;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwingDemo extends JFrame
{
    private final DrawPanel myPanel;
    
    private static class DrawPanel extends JPanel
    {
        private static final int N = 50;
        private int linesToDraw = 0;
        
        public DrawPanel()
        {
            setBackground(Color.black);
            setForeground(Color.green);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            drawLines(g);
        }

        private void drawLines(Graphics g)
        {
            double dx = ((double) getWidth()) / (N - 1);
            double dy = ((double) getHeight()) / (N - 1);
            double x2 = 0;
            double y1 = 0;
            for(int line = 0; line < linesToDraw; line++)
            {
                g.drawLine(0, (int)y1, (int)x2, getHeight());
                x2 += dx;
                y1 += dy;
            }            
        }

        private int delta = 1;
        
        private void nextLine()
        {
            linesToDraw += delta;
            
            if(linesToDraw > N || linesToDraw < 0)
            {
                delta *= -1;
            }
            
            repaint();
            
            try
            {
                Thread.sleep(33);
            }
            catch (InterruptedException ex)
            {
            }
        }
    }
    
    public SwingDemo()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(50, 50, 300, 400);
        myPanel = new DrawPanel();
        add(myPanel);
    }
    
    public static void main(String[] args)
    {
        SwingDemo myDemo = new SwingDemo();
        
        java.awt.EventQueue.invokeLater(() ->
        {
            myDemo.setVisible(true);
        });
        
        while(true)
        {
            myDemo.myPanel.nextLine();
        }
    }    
}
