import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class RandomColorGrid extends JFrame {
    private final DrawPanel myPanel;

    public static class DrawPanel extends JPanel {
        LinkedList<Rectangle> LIST_REC = new LinkedList<>(); //LinkedList to store the rectangles
        private double WINDOWS_HEIGHT;
        private double WINDOWS_WIDTH;


        private double REC_WIDTH = 50;
        private double REC_HEIGHT = 50;

        public DrawPanel() {
            setBackground(Color.BLACK);
            setForeground(Color.BLUE);

        }

        /**
         * Sets the width to a desired number
         * @param width
         */
        protected void SetWidth(int width){REC_WIDTH = width;}
        /**
         * Sets the height to a desired number
         * @param height
         */
        protected void SetHeight(int height){REC_HEIGHT = height;}


        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D) g.create();

            DrawBackground(g2d); //Draws the Backgrounds
            DrawRectangles(g2d); //Draws the rectangles with randoms colors
            DrawLines(g2d); //Draws the outlines

        }

        /**
         * Draws the outlines of the rectangles creating the grid
         * @param g2d Graphics
         */
        protected void DrawLines(Graphics2D g2d) {
            g2d.setColor(Color.GRAY);
            Point2D.Double one = new Point2D.Double(0, 0); //top left corner of th window
            Point2D.Double two = new Point2D.Double(getWidth(), 0); //top right corner of the window
            Point2D.Double three = new Point2D.Double(0, getHeight()); //bottom left corner of the window

            double DX = REC_WIDTH; //moved the X coordinate by the width of each rectangle
            double DY = REC_HEIGHT; //moved the Y coordinate by the height of each rectangle
            double numberCol = WINDOWS_WIDTH/DX; //maximum number of columns possible in the window
            double numberRow = WINDOWS_HEIGHT/DY; //maximum number of rows possible in the window

            //loop for the rows
            for (int i = 0; i <= numberRow; i++) {

                Line2D.Double LINEHORI = new Line2D.Double(one.x, one.y, two.x, two.y);
                g2d.draw(LINEHORI);
                one.y += DY;
                two.y += DY;
            }

            //resets the point to left top corner
            one.setLocation(0, 0);

            //loop for the columns
            for (int j = 0; j <= numberCol; j++) {

                Line2D.Double LINEVERTI = new Line2D.Double(one.x, one.y, three.x, three.y);
                g2d.draw(LINEVERTI);

                one.x += DX;
                three.x += DX;
            }
            repaint();
        }

        /**
         * Fills ups the LinkedList with rectangles with the right coordinates
         */
        protected void CreateList()
        {
            double x = 0;
            double y = 0;
            double numberCol = WINDOWS_WIDTH/REC_WIDTH; //max number of columns that fits on the screen
            double numberRow = WINDOWS_HEIGHT/REC_HEIGHT; //max number of rows that fits on the screen

            //outer loop for the rows
            for (int i = 0; i < numberRow; i++) {

                //inner loop for the number of columns
                for (int j = 0; j < numberCol; j++) {

                    LIST_REC.add(new Rectangle(x, y, REC_WIDTH, REC_HEIGHT));
                    x += REC_WIDTH;
                }
                x = 0; //resets the x coordinate to the beginning of the row
                y += REC_HEIGHT;
                repaint();
            }
        }

        /**
         * Draws the Rectangles in the panel
         * @param g2d
         */
        protected void DrawRectangles(Graphics2D g2d) {


            //for each rectangle in the LinkedList, draw it.
            for (Rectangle rec : LIST_REC) {

                g2d.setColor(rec.GetColor());
                Rectangle2D.Double rect = new Rectangle2D.Double(rec.GetX(), rec.GetY(), rec.GetWidth(), rec.GetHeight());
                g2d.fill(rect);
            }

            repaint();
        }

        /**
         * Draws a black background
         * @param g2d
         */
        protected void DrawBackground(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            Rectangle REC = new Rectangle(0, 0, getWidth(), getHeight());
            Rectangle2D.Double background = new Rectangle2D.Double(REC.GetX(), REC.GetY(), REC.GetWidth(), REC.GetHeight());
            g2d.fill(background);
            repaint();
        }
    }


    public RandomColorGrid() {
        setBounds(50, 50, 400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        myPanel = new DrawPanel();
        add(myPanel);
    }


    public static void main(String[] args) {


    RandomColorGrid demo = new RandomColorGrid();

        if(args.length < 3 && args.length > 0) //if there are at least two parameters in the command line
        {
            try
            {
                demo.myPanel.SetWidth(Integer.valueOf(args[0])); //if numbers, use them. Otherwise, use the defaults
                demo.myPanel.SetHeight(Integer.valueOf(args[1]));
            }catch (NumberFormatException ex)
            {
                demo.myPanel.SetWidth(50);
                demo.myPanel.SetHeight(50);
                System.err.println("Not a number! Use of the default values");
            }
        }

        java.awt.EventQueue.invokeLater(()->

    {
        demo.setVisible(true);
    });
        java.awt.EventQueue.invokeLater(()->

        {
            demo.myPanel.CreateList(); //Creating the rectangles and storing them in the LinkedList
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        demo.myPanel.WINDOWS_WIDTH = screenSize.getWidth();
        demo.myPanel.WINDOWS_HEIGHT = screenSize.getHeight();



    }
}

