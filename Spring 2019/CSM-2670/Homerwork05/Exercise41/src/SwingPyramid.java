

import java.awt.geom.Rectangle2D;
import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;

public class SwingPyramid extends JFrame {

    private final DrawPanel myPanel;


    //panel class
    private static class DrawPanel extends JPanel
    {
        //main rectangle
        private Rectangle MAIN_REC;

        private double BRICK_WIDTH,BRICK_HEIGHT;

        //constants
        private int BRICKS_IN_BASE = 50;
        private int ROWS =50;

        public DrawPanel()
        {
            setBackground(Color.BLACK);
            setForeground(Color.BLUE);

        }
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            drawRectangle(g);
            drawBricks(g);
        }

        /** draw a centered Rectangle which height and width are 80% of the window
         *
         * @param g = Graphics
         */
        private void drawRectangle(Graphics g)
        {
            //main rectangle which height and width are 80% of the window
            MAIN_REC =  new Rectangle((int)(getWidth()*0.1),(int)(getHeight()*0.1),(int)(getWidth()*0.8),(int)(getHeight()*0.8));

            // width of each brick is equal to the width of the main rectangle divided by the number of bricks in the base
            BRICK_WIDTH = (float) MAIN_REC.GetWidth() / BRICKS_IN_BASE;
            //height of each brick is equal to the height of te main rectangle divided by the number of rows
            BRICK_HEIGHT = (float)MAIN_REC.GetHeight() / ROWS;

            // draw the main rectangle
            g.fillRect((int)MAIN_REC.GetX(),(int)MAIN_REC.GetY(),(int)MAIN_REC.GetWidth(),(int)MAIN_REC.GetHeight());
            g.drawRect((int)MAIN_REC.GetX(),(int)MAIN_REC.GetY(),(int)MAIN_REC.GetWidth(),(int)MAIN_REC.GetHeight());

        }

        /** draw a pyramid of bricks that perfectly fits in an rectangle centered which height and width are 80% of the window
         *
         * @param g = Graphics
         */
        private void drawBricks(Graphics g)
        {
            MAIN_REC =  new Rectangle((int)(getWidth()*0.1),(int)(getHeight()*0.1),(int)(getWidth()*0.8),(int)(getHeight()*0.8));

            // to draw the bricks with decimal coordinates
            Graphics2D g2d = (Graphics2D)g.create();

            double x = MAIN_REC.GetX();

            //starts on the bottom left of the main rectangle
            double y = ((MAIN_REC.GetHeight()+MAIN_REC.GetY())- BRICK_HEIGHT);

            //outer loop for the number of rows
            for(int j = 1; j <= ROWS;j++)
            {
                //inner loop for the number of bricks of each row
                for(int i = BRICKS_IN_BASE; i >= j;i--)
                {
                    g2d.setColor(Color.ORANGE);
                    Rectangle2D.Double rect = new Rectangle2D.Double(x,y,BRICK_WIDTH,BRICK_HEIGHT);
                    g2d.fill(rect);
                    g2d.setColor(Color.RED);
                    Rectangle2D.Double rect2 = new Rectangle2D.Double(x,y,BRICK_WIDTH,BRICK_HEIGHT);
                    g2d.draw(rect2);

                    x += BRICK_WIDTH;
                }
                //moves up by the height of the brick new row
                y -= BRICK_HEIGHT;
                //moves half of bricks width to decrease by one the number of bricks for each row
                x = MAIN_REC.GetX() + j*BRICK_WIDTH/2;

            }
        }

    }

    public SwingPyramid()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(320,70,500,500);
        myPanel = new DrawPanel();
        add(myPanel);
    }



    public static void main(String[] args) {
        SwingPyramid piramid = new SwingPyramid();
        java.awt.EventQueue.invokeLater(() ->
        {
            piramid.setVisible(true);
        });
    }
}
