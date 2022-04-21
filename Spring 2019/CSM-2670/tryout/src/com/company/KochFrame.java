package com.company;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/*
 * Animates the drawing of an order 5 Koch fractal curve.
 */
public class KochFrame extends JFrame {

    // A reference to the drawing panel so our main thread can easily animate
    // it.
    private final DrawPanel myPanel;

    // A Panel that can draw a Koch fractal for a given order
    private static class DrawPanel extends JPanel {

        private int order = 0; // The order to be drawn (draw will use a copy)
        private double angle; // The direction we are currently drawing in
        private double x;       // Current x location while drawing
        private double y;       // Current y location while drawing

        // A setter for the order. The panel will draw the new fractal.
        public void setOrder(int order) {
            this.order = order;
            repaint();
        }

        public int getOrder() {
            return order;
        }

        public DrawPanel() {
            setBackground(Color.black);
            setForeground(Color.green);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Find a good size for the length of a side of the snowflake
            int length = (int) (0.80 * Math.min(getWidth(), getHeight()));

            // Find the center of the panel
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Start at the upper left corner of the triangle that forms the
            // snowflake facing right.
            angle = 0;
            x = centerX - length / 2;
            y = centerY - length * Math.tan(Math.PI / 6) / 2;

            // Draw the three sides of the snowflake turning to the right by
            // 2 pi / 3 each time.
            for(int i = 0; i < 3; i++)
            {
                draw(g, order, length);
                angle -= 2* Math.PI / 3;
            }
        }

        private void draw(Graphics g, int order, double length) {
            // base case: draw straight line
            if (order == 0) {
                // Find how far we are moving in x and y based on the current
                // angle
                double dx = length * Math.cos(angle);
                double dy = length * Math.sin(angle);

                // Draw the line
                g.drawLine((int) x, (int) y, (int) (x + dx), (int) (y - dy));

                // Update the position
                x += dx;
                y -= dy;
            }
            // recursive case: draw 4 times with decreased order and length
            else {
                // Draw the pattern _/\_
                draw(g, order - 1, length / 3.0);
                angle += Math.PI / 3;             // turn left                
                draw(g, order - 1, length / 3.0);
                angle -= 2 * Math.PI / 3;         // turn right
                draw(g, order - 1, length / 3.0);
                angle += Math.PI / 3;             // turn left
                draw(g, order - 1, length / 3.0);
            }
        }
    }

    public KochFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(50, 50, 800, 800);
        myPanel = new DrawPanel();
        add(myPanel);
    }

    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) {
        KochFrame myDemo = new KochFrame();

        java.awt.EventQueue.invokeLater(() -> {
            myDemo.setVisible(true);
        });

        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
            }
            int newOrder = (myDemo.myPanel.getOrder() + 1) % 6;
            myDemo.myPanel.setOrder(newOrder);
        }
    }
}