import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class KeyDemo extends JFrame
{    
    private class DrawingPanel extends JPanel
    {        
        private int x;
        private int y;
        private boolean left = false;
        private boolean right = false;
        private boolean up = false;
        private boolean down = false;
        private static final int SPEED = 1;
        private static final int DT = 33;
        
        
        public DrawingPanel()
        {
            super.setBackground(Color.black);
            super.setForeground(Color.green);            
            x = 400;
            y = 400;
            super.addKeyListener(new KeyAdapter()
            {                
                @Override
                public void keyReleased(KeyEvent e)
                {
                    switch(e.getKeyCode())
                    {
                        case KeyEvent.VK_RIGHT:
                            right = false;
                            break;
                        case KeyEvent.VK_LEFT:
                            left = false;
                            break;
                        case KeyEvent.VK_UP:
                            up = false;
                            break;
                        case KeyEvent.VK_DOWN:
                            down = false;
                            break;
                    }
                }

                @Override
                public void keyPressed(KeyEvent e)
                {
                    switch(e.getKeyCode())
                    {
                        case KeyEvent.VK_RIGHT:
                            right = true;
                            break;
                        case KeyEvent.VK_LEFT:
                            left = true;
                            break;
                        case KeyEvent.VK_UP:
                            up = true;
                            break;
                        case KeyEvent.VK_DOWN:
                            down = true;
                            break;
                    }
                }
            });
            
            new Timer(DT, (ActionEvent e) ->
            {
                if(left)  x -= SPEED;
                if(right) x += SPEED;
                if(up)    y -= SPEED;
                if(down)  y += SPEED;
                repaint();
            }).start();
        }
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.fillOval(x, y, 50, 50);
        }   
    }
    
    DrawingPanel bob;
    
    public KeyDemo()
    {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(800, 800);
        bob = new DrawingPanel();
        super.add(bob);
    }
    
    @Override
    public void setVisible(boolean f)
    {
       super.setVisible(f);
       bob.requestFocusInWindow();       
    }
    
    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(() ->
        {
            new KeyDemo().setVisible(true);
        });
    }

}