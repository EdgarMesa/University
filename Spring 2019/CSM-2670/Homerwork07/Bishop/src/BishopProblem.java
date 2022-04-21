
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

public class BishopProblem extends JFrame {

    private final DrawPanel myPanel;

    private static class DrawPanel extends JPanel
    {
        private Square MAIN_SQUARE;
        private Color color1 = Color.RED;
        private Color color2 = Color.BLUE;
        private int N = 2;
        private int[][] result = {{0,0}};
//        private List<List<Integer>> result = new LinkedList<>()



        public DrawPanel()
        {
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g.create();

            drawMainSquare(g2d);
            drawSquares(g2d);
            drawLines(g2d);
        }

        /**
         * Draw a square board centered which width and height are determined by the smallest value
         * between the width or the height of the current window.
         * @param g2d = Graphics2D
         */
        private void drawMainSquare(Graphics2D g2d)
        {
            //allow us to draw a square with decimal coordinates

            double width = getWidth();
            double height = getHeight();
            double lenght = height; //window will be automatically displayed as a rectangle where the height is smaller than the width

            double percentX = width/2-height/2; //calculating the percent of the squares´s width over the window´s width


            double x;
            double y;

            if(height <= width)
            {
                x = percentX;
                y = 0;
            }
            else
            {
                lenght = width;
                double percentY = height/2-lenght/2; //calculating the percent of the squares´s height over the window´s height

                x = 0;
                y = percentY;
            }

            MAIN_SQUARE = new Square(x,y,lenght,Color.black);
            g2d.setColor(MAIN_SQUARE.GetColor());
            Rectangle2D.Double rect = new Rectangle2D.Double(MAIN_SQUARE.GetX(),MAIN_SQUARE.GetY(),MAIN_SQUARE.GetWidth(),MAIN_SQUARE.GetHeight());
            g2d.draw(rect);
        }

        /**
         * draws the squares of the board altering between two colors
         * @param g2d = Graphics2D
         */
        private void drawSquares(Graphics2D g2d)
        {
            //sets the length of each brick equals to the height divided by 8
            double CheLenght = MAIN_SQUARE.GetHeight()/N;
            double CheX = MAIN_SQUARE.GetX();
            double CheY = MAIN_SQUARE.GetY();

            // outside loop for the rows
            for(int i = 1; i <= N; i++)
            {
                //inner loop for the 8 squares in each row
                for(int j = 1; j <= N; j++)
                {
                    //odd rows start with one of the colors
                    if(i % 2 != 0)
                    {
                        //even columns same colors
                        if (j % 2 == 0)
                        {
                            g2d.setColor(color1);
                            //odd columns the other color
                        }else{g2d.setColor(color2);}
                    }else
                    {
                        if(j % 2 != 0)
                        {
                            g2d.setColor(color1);
                        }else{g2d.setColor(color2);}
                    }
                    Rectangle2D.Double rect2 = new Rectangle2D.Double(CheX,CheY,CheLenght,CheLenght);
                    g2d.fill(rect2);

                    if(result.length > 0)
                    {

                        for(int[] arr : result)
                        {
                            if (i == arr[0] && j == arr[1]) {
                                Color color = Color.BLACK;
                                drawChecker(g2d, CheX, CheY, CheLenght, color);
                            }
                        }

                    }

                    //next x coordinate moved by the length of the square
                    CheX = CheX + CheLenght;
                }
                //resets the x coordinate to the beginning of the row
                CheX = MAIN_SQUARE.GetX();

                //next y coordinate moved by the length of the square. to move from row to row
                CheY = CheY+CheLenght;
            }

        }

        /**
         * Draws the checkers in the desired squares
         * @param g2d = 2DGraphic
         * @param x = x coordinate
         * @param y = Y coordinate
         * @param radius = radius of each checker
         * @param color = color of the checker
         */
        private void drawChecker(Graphics2D g2d,double x,double y,double radius,Color color)
        {
            g2d.setColor(color);
            //80% of the size of the square
            double size = radius*0.8;
            //Creates the checker in each Squared centered
            Ellipse2D.Double checker = new Ellipse2D.Double(x+radius*0.1,y+radius*0.1,size,size);
            g2d.fill(checker);


        }

        /** creates the outlines of the board, highlighting the different squares
         * @param g2d = Graphics2D
         */
        private void drawLines(Graphics2D g2d)
        {
            double dx = MAIN_SQUARE.GetWidth()/N;   //what the line x coordinate is going to me move
            double dy = MAIN_SQUARE.GetHeight()/N;  //what the line y coordinate is going to me move
            Point2D.Double p1 = new Point2D.Double(MAIN_SQUARE.GetX(),MAIN_SQUARE.GetY());//top left corner of the board
            Point2D.Double p2 = new Point2D.Double( MAIN_SQUARE.GetX(),MAIN_SQUARE.GetHeight()+MAIN_SQUARE.GetY());//bottom left corner of the board
            Point2D.Double p3 = new Point2D.Double(MAIN_SQUARE.GetWidth()+ MAIN_SQUARE.GetX(),MAIN_SQUARE.GetY());//top right corner of the board


            g2d.setColor(Color.GRAY);

            // 8x8 board
            for(int i = 0; i < N+1; i++)
            {
                Line2D.Double verticalline = new Line2D.Double(p1.x,p1.y,p2.x,p2.y);
                g2d.draw(verticalline);
                //moves the X coordinate for the next line
                p1.x += dx;
                p2.x += dx;
            }
            //resets the origin coordinates to the top left corner of the board
            p1.x = MAIN_SQUARE.GetX();
            p1.y = MAIN_SQUARE.GetY();
            for(int i = 0; i < N+1; i++)
            {
                Line2D.Double horizontalline = new Line2D.Double(p1.x,p1.y,p3.x,p3.y);
                g2d.draw(horizontalline);

                //moves the Y coordinate for the next line
                p1.y += dy;
                p3.y += dy;
            }
        }
    }

    public BishopProblem()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(400,70,500,300);
        myPanel = new DrawPanel();
        add(myPanel);
    }

    public static void main(String[] args) {



        BishopProblem board = new BishopProblem();
        java.awt.EventQueue.invokeLater(() ->
        {
            board.setVisible(true);
        });


        List<List<Integer>> result = new ArrayList<>();


        List<Integer> b = new ArrayList<>(2*2);
        for (int i = 0; i < 4; i++)
        {
            b.add(i);
        }


        SolveBishop solve = new SolveBishop(2,2,result,b);



//        int index = 0;
//        int[][][] list = {{{4,2},{4,8},{1,2},{5,5},{3,7}},{{5,7},{8,9},{1,7},{1,5},{2,7}},{{8,9},{5,8},{7,7},{1,2},{3,3}},{{4,4},{6,6},{1,1},{5,5},{7,7}}};
        while (true) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException ex) {}



                List<List<Integer>> temp = solve.findBishop(0);

                System.out.println(temp.size());
                int[][] arr =new int[temp.size()+1][temp.size()+1];
                if(temp.size() == 0 )
                {
                    arr [0][0] = 0;

                    board.myPanel.result = arr;
                    board.myPanel.repaint();
                }

                int index = 0;
                for(List<Integer> l : temp)
                {
                    arr[index][0] = l.get(0);
                    arr[index][1] = l.get(1);


                }

                board.myPanel.result = arr;
                board.myPanel.repaint();

                temp.clear();



            }

//            board.myPanel.result = list[index];
//            board.myPanel.repaint();
//            index++;
//            if(index == list.length){index = 0;}


        }




    }

