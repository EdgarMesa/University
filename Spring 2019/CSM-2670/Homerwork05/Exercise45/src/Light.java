import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class Light extends JFrame {

    private final DrawPanel myPanel;


    public static class DrawPanel extends JPanel
    {

        LinkedList<Point> presses = new LinkedList<>();
        private double WINDOWS_HEIGHT;
        private double WINDOWS_WIDTH;

        private double REC_WIDTH = 50;
        private double REC_HEIGHT = 50;

        public DrawPanel()
        {
            setBackground(Color.BLACK);
            setForeground(Color.GRAY);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { //gets a point every time the mouse is clicked
                    presses.add(e.getPoint()); //adds it to the LinkedList
                    repaint();
                }
            });
             addMouseMotionListener(new MouseAdapter() { //gets a point every time the mouse is dragged
                 @Override
                 public void mouseDragged(MouseEvent e) {
                     presses.add(e.getPoint()); //adds it to the LinkedList
                     repaint();
                 }
             });

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
        protected void paintComponent(Graphics g)
        {
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D)g.create();
            DrawBackground(g2d);
            DrawRectangles(g2d);
            DrawLines(g2d);

        }

        /**
         * Draws the outlines of the rectangles creating the grid
         * @param g2d Graphics
         */
        protected void DrawLines(Graphics2D g2d)
        {
            g2d.setColor(Color.GRAY);
            Point2D.Double one = new Point2D.Double(0,0); //top left corner of th window
            Point2D.Double two = new Point2D.Double(getWidth(),0); //top right corner of the window
            Point2D.Double three = new Point2D.Double(0,getHeight()); //bottom left corner of the window

            double DX = REC_WIDTH;//moved the X coordinate by the width of each rectangle
            double DY = REC_HEIGHT; //moved the Y coordinate by the height of each rectangle
            double numberCol = WINDOWS_WIDTH/DX; //maximum number of columns possible in the window
            double numberRow = WINDOWS_HEIGHT/DY; //maximum number of rows possible in the window

            //loop for the rows
            for(int i = 0; i <=numberRow ; i++)
            {
                Line2D.Double LINEHORI = new Line2D.Double(one.x,one.y,two.x,two.y);
                g2d.draw(LINEHORI);
                one.y += DY;
                two.y += DY;
            }

            //resets the point to left top corner
            one.setLocation(0,0);

            //loop for the columns
            for(int j = 0; j <= numberCol; j++)
            {
                Line2D.Double LINEVERTI = new Line2D.Double(one.x,one.y,three.x,three.y);
                g2d.draw(LINEVERTI);

                one.x += DX;
                three.x += DX;
            }
            repaint();
        }

        /**
         * Draws the Rectangles in the panel
         * @param g2d
         */
        private void DrawRectangles(Graphics2D g2d)
        {

            double x = 0;
            double y = 0;
            double numberCol = WINDOWS_WIDTH/REC_WIDTH; //max number of columns that fits on the screen
            double numberRow = WINDOWS_HEIGHT/REC_HEIGHT; //max number of rows that fits on the screen

            //outer loop for the rows
            for(int i = 0; i < numberRow; i++)
            {
                //inner loop for the number of columns
                for(int j = 0; j < numberCol; j++)
                {
                    g2d.setColor(Color.BLACK); //set the color to back for the default
                    for(Point point : presses)
                    {
                        boolean onof = OnOrOff(x,y); //checks if the rectangle is On or off

                        //if the coordinates of the points are inside a rectangle and is also on, set the color to white
                        if ((point.x > x && point.x < x + REC_WIDTH && point.y > y && point.y < y + REC_HEIGHT) && onof)
                        {
                            g2d.setColor(Color.WHITE);
                            break;
                        }
                    }
                    Rectangle2D.Double rec = new Rectangle2D.Double(x,y,REC_WIDTH,REC_HEIGHT);
                    g2d.fill(rec);
                    x += REC_WIDTH;
                }

                x = 0;
                y += REC_HEIGHT;

            }

            repaint();
        }

        /**
         * Counts the number of points that are inside a certain rectangle
         * @param x coordinate of the rectangle
         * @param y coordinate of the rectangle
         * @return true for odd number of Points inside a determined rectangle. False for a even result
         */
        protected Boolean OnOrOff(double x,double y)
        {
            int count = 0;
            //check for every point how many are inside the rectangle
            for(Point point : presses)
            {
                //if the X and Y coordinates of the points are inside a rectangle
                if (point.x > x && point.x < x + REC_WIDTH && point.y > y && point.y < y + REC_HEIGHT)
                {
                    count++;
                }
            }
            //Even = Off. Odd = On
            if(count % 2 == 0){return false;}
            else {return true;}
        }

        /**
         * Draws a black background
         * @param g2d
         */
        protected void DrawBackground(Graphics2D g2d)
        {
            g2d.setColor(Color.BLACK);
            Rectangle REC = new Rectangle(0,0,getWidth(),getHeight());
            Rectangle2D.Double background = new Rectangle2D.Double(REC.GetX(),REC.GetY(),REC.GetWidth(),REC.GetHeight());
            g2d.fill(background);
            repaint();
        }
    }

    public Light()
    {
        setBounds(50, 50, 400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        myPanel = new DrawPanel();
        add(myPanel);
    }

    public static void main(String[] args)
    {
	Light demo = new Light();

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

        java.awt.EventQueue.invokeLater(() ->
        {
            demo.setVisible(true);
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        demo.myPanel.WINDOWS_WIDTH = screenSize.getWidth();
        demo.myPanel.WINDOWS_HEIGHT = screenSize.getHeight();


    }


}
