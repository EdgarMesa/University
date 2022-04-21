import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Random;

public class RandomWalk2 extends JFrame {

    private final DrawPanel myPanel;

    public static class DrawPanel extends JPanel
    {
        LinkedList<Rectangle> LIST_REC = new LinkedList<>();
        LinkedList<Rectangle> LIST_MARCKED = new LinkedList<>();


        private double WINDOWS_HEIGHT;
        private double WINDOWS_WIDTH;
        private double PANEL_HEIGHT;
        private double PANEL_WIDTH;

        // This would be the coordinates of my pointers that will randomly color the rectangles
        private double StepX ;
        private double StepY ;

        private double REC_WIDTH = 50;
        private double REC_HEIGHT = 50;
        Random ran = new Random();

        public DrawPanel()
        {
            setBackground(Color.BLACK);
            setForeground(Color.GRAY);

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
            super.paintComponent(g);
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
         * Fills ups the LinkedList with rectangles with the right coordinates and color depending on the pointer
         */
        protected void CreateList()
        {
            //Timer to update the LinkedList to update the pointer location
            Timer timer = new Timer(33, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    double x = 0;
                    double y = 0;
                    double numberCol = WINDOWS_WIDTH/REC_WIDTH; //max number of columns that fits on the screen
                    double numberRow = WINDOWS_HEIGHT/REC_HEIGHT; //max number of rows that fits on the screen

                    //outer loop for the rows
                    for (int i = 0; i < numberRow; i++)
                    {

                        //inner loop for the number of columns
                        for (int j = 0; j < numberCol; j++)
                        {
                            Color color = Color.BLACK;

                            //if the pointer is in the rectangle, changes its color. Otherwise black
                            if(StepX > x&& StepX < x+REC_WIDTH && StepY > y && StepY < y+REC_HEIGHT)
                            {
                                color = Color.RED;

                            }


                            Rectangle FinalRec = new Rectangle(x, y, REC_WIDTH, REC_HEIGHT,color);

                            LIST_REC.add(FinalRec); //add to the LinkedList
                            x += REC_WIDTH;
                        }
                        x = 0; //resets the x coordinate to the beginning of the row
                        y += REC_HEIGHT;
                        repaint();
                    }

                }
            });
            timer.start();

        }

        /**
         * Draws the Rectangles in the panel
         * @param g2d
         */
        protected void DrawRectangles(Graphics2D g2d) {

            //for each rectangle in the LinkedList, draw it.
            for (Rectangle rec : LIST_REC)
            {

                g2d.setColor(rec.GetColor());
                Rectangle2D.Double rect = new Rectangle2D.Double(rec.GetX(), rec.GetY(), rec.GetWidth(), rec.GetHeight());
                g2d.fill(rect);
            }

            repaint();
        }


        /**
         * Creates a random new coordinate for the pointer.Starts in the middle rectangle and
         * will move a rectangle at a time
         */
        protected void Random() {


            //Timer controlling how fast the pointer moves to the nex position
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

//                    System.out.println(LIST_REC);
                    for(Rectangle rec : LIST_REC)
                    {
                        if(rec.GetColor().equals(Color.RED))
                        {
                            LIST_MARCKED.add(rec);
                        }
                    }
                    double first;


                    // randomly going from 0 to 1. Zero = change in the X coordinate. One = Change in the Y coordinate
                    int second = ran.nextInt(2);

                    for(Rectangle redrec : LIST_MARCKED)
                    {
//                        System.out.println("STEP X NO CHANGE   " +StepX);
//                        System.out.println("STEP Y NO CHANGE   " +StepY);
                        //if change in the X coordinate.
                        if(second == 0)
                        {
                            boolean b = ran.nextBoolean();
                            if(b){first = REC_WIDTH;} //Change of the X equals to the rectangles´s width to move one at a time
                            else{first = -REC_WIDTH;}

                            //if reaches one of the limits of the window, go opposite direction
                            if(StepX + first <= 0 || StepX + first >= getWidth())
                            {
                                StepX -= first;
                                if(StepX  <= redrec.GetX() || StepX  >= redrec.GetX()+redrec.GetWidth())
                                {
                                    StepX += first;
                                }
                            }
                            else
                                {
                                    StepX += first;
                                    if(StepX  <= redrec.GetX() || StepX  >= redrec.GetX()+redrec.GetWidth())
                                    {
                                        StepX -= first;
                                    }
                                }
                        }
                        else
                        {
                            boolean b = ran.nextBoolean();
                            if(b){first = REC_HEIGHT;} //Change of the Y equals to the rectangles´s height to move one at a time
                            else{first = -REC_HEIGHT;}

                            //if reaches one of the limits of the window, go opposite direction
                            if(StepY +first <= 0 || StepY+first >= getHeight())
                            {
                                StepY -= first;
                                if(StepY <= redrec.GetY() || StepY >= redrec.GetY()+redrec.GetHeight())
                                {
                                    StepY += first;
                                }
                            }
                            else{
                                StepY += first;
                                if(StepY  <= redrec.GetY() || StepY >= redrec.GetY()+redrec.GetHeight())
                                {
                                    StepY -= first;
                                }

                            }
                        }
//                        System.out.println("STEP X    " +StepX);
//                        System.out.println("STEP Y    " +StepY);
//
//                        System.out.println();
//                        System.out.println();


                        repaint();
                }}
            });
            timer.start();
        }

//        protected void AlradyRed(double first)
//        {
//            for(Rectangle redrec : LIST_REC)
//            {
//                if(StepX + first <= redrec.GetX() || StepX + first >= redrec.GetX()+redrec.GetWidth())
//                {
//                    first = 0;
//                    StepX += first;
//                }
//                else{ StepX += first;}
//
//                 //if reaches one of the limits of the window, go opposite direction
//                if(StepY +first <= redrec.GetY() || StepY+first >= redrec.GetY()+redrec.GetHeight())
//                {
//                    first = 0;
//                    StepY += first;
//                }
//                else{ StepY += first;}
//
//            }
//        }

        /**
         * Draws a black background
         * @param g2d
         */
        protected void DrawBackground(Graphics2D g2d) {

            Rectangle REC = new Rectangle(0, 0, getWidth(), getHeight(),Color.BLACK);
            g2d.setColor(REC.GetColor());
            Rectangle2D.Double background = new Rectangle2D.Double(REC.GetX(), REC.GetY(), REC.GetWidth(), REC.GetHeight());
            g2d.fill(background);
            repaint();
        }

    }
    public RandomWalk2()
    {
        setBounds(50,50,400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        myPanel = new DrawPanel();
        add(myPanel);
    }

    public static void main(String[] args)
    {
        RandomWalk2 demo = new RandomWalk2();

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
            //getting the width and height of the first window displayed to calculate the center of the window
            demo.myPanel.PANEL_WIDTH = demo.myPanel.getWidth();
            demo.myPanel.PANEL_HEIGHT = demo.myPanel.getHeight();

            demo.myPanel.StepX =  demo.myPanel.PANEL_WIDTH*0.50;
            demo.myPanel.StepY =  demo.myPanel.PANEL_HEIGHT*0.50;

        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        demo.myPanel.WINDOWS_WIDTH = screenSize.getWidth();
        demo.myPanel.WINDOWS_HEIGHT = screenSize.getHeight();
        demo.myPanel.CreateList(); //Creating the rectangles and storing them in the LinkedList
        demo.myPanel.Random(); //Change of the coordinates of the pointer

    }
}
