
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class Fractal extends JFrame{

    private final DrawPanel myPanel;
    private static JComboBox box; //combo box to select theorder



    // A Panel that can draw a coastline fractal for a given order chosen by the user
    private static class DrawPanel extends JPanel {

        private int order ; // The order to be drawn (draw will use a copy)
        private double angle; // The direction we are currently drawing in
        private double x;       // Current x location while drawing
        private double y;       // Current y location while drawing


        /**
         * TO be able to set the order for the next fractal
         * @param order = order to be drawn
         */
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

            // Start at the upper left corner of the triangle
            angle = 0;
            x = centerX - length /2;
            y = centerY - length * Math.tan(Math.PI / 6) / 2;

                //draws a part of the fractal
                draw(g, order, length);
                angle -= 2* Math.PI / 3;

        }

        private void draw(Graphics g, int order, double length) {

            Random ran = new Random(); //Random object to switch between moving up or down creating a random coastline fractal

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
            else {
                switch (ran.nextInt(2))
                {
                    case 0:
                        // Draw the pattern _/\_
                        draw(g, order - 1, length / 3.0);
                        angle += Math.PI / 3;             // turn left
                        draw(g, order - 1, length / 3.0);
                        angle -= 2 * Math.PI / 3;         // turn right
                        draw(g, order - 1, length / 3.0);
                        angle += Math.PI / 3;             // turn left
                        draw(g, order - 1, length / 3.0);


                    case 1:

                        // Draw the pattern _    _
                        //                    \/
                        draw(g, order - 1, length / 3.0);
                        angle -= Math.PI / 3;             // turn right
                        draw(g, order - 1, length / 3.0);
                        angle += 2 * Math.PI / 3;         // turn left
                        draw(g, order - 1, length / 3.0);
                        angle -= Math.PI / 3;             // turn right
                        draw(g, order - 1, length / 3.0);

                }

            }
        }
    }

    public Fractal() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBounds(50, 50, 800, 800);
        myPanel = new DrawPanel();


        //JPanel that will go on top  holding a JLabel and the JCombo box
        JPanel upper = new JPanel();
        upper.setLayout(new FlowLayout());
        JLabel label = new JLabel("Pick an order");
        Integer[] orders = {0, 1, 2,3,4,5}; //integers to pick
        box = new JComboBox(orders);


        //adding them to the upper panel
        upper.add(label);
        upper.add(box);
        //adding the upper panel to the frame in the north
        add(upper,BorderLayout.NORTH);


        //adding the Drawing Panel in the center
        add(myPanel,BorderLayout.CENTER);
    }



    public static void main(String[] args) {

        Fractal myDemo = new Fractal();

        //action listener to register every time an item is selected
      box.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {

              Integer number = (Integer) box.getSelectedItem(); //we will get the items of a combo box as
                                                                //Their object class so we have to downcast it
              myDemo.myPanel.order = number.intValue(); //downcast
              myDemo.myPanel.repaint(); //repainting the panel
          }
      });



        java.awt.EventQueue.invokeLater(() -> {
            myDemo.setVisible(true);
        });



    }
}
