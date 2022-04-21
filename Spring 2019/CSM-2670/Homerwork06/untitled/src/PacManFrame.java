
import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.List;

public class PacManFrame extends JFrame{

    private static final Random RAND = new Random();
    private static double DIMENSIONSW;
    private static double DIMENSIONSH;
    private static List<HuntModeThread> threads = new LinkedList<>();


    private final DrawPanel myPanel;


    public static class PacMan extends Particle
    {
        private int dir;
        private double CellW;
        private double CellH;
        private double LimitWidth;
        private BufferedImage img;
        private BufferedImage img2;
        private BufferedImage img3;
        private BufferedImage img4;
        private BufferedImage img5;
        private BufferedImage img6;
        private BufferedImage img7;
        private BufferedImage img8;
        private BufferedImage img9;

        private BufferedImage dimg;
        private BufferedImage dimg2;
        private BufferedImage dimg3;
        private BufferedImage dimg4;
        private BufferedImage dimg5;
        private BufferedImage dimg6;
        private BufferedImage dimg7;
        private BufferedImage dimg8;
        private BufferedImage dimg9;
        private BufferedImage dimg10;
        private BufferedImage dimg11;
        private BufferedImage i;
        private boolean alive = true;


        private int changealive = 1;
        private int changeadeath = 0;
        private boolean readytospawn = false;



        // the limits are going to be used to respawn the pacman in the other side of the gameboard
        public PacMan(double x, double y, double radius,double LimitWidth,double LimitHeight,double CellW, double CellH)
        {
            super(x,y,radius);
            this.LimitWidth = LimitWidth;
            this.LimitHeight = LimitHeight;

            this.CellW = CellW;
            this.CellH = CellH;



        }

        /**
         * @return true if pacman is alive
         */
        public Boolean IsAlive(){return alive;}


        public void draw(Graphics2D g2d)
        {

            // in case of no direction, pointing upwards
            if(dir == 0)
            {
                i = img6;
            }

            g2d.drawImage(i,(int)x+1,(int)y+1,null);

        }

        /**imagines for when pacman is alive*/
        public void RenderingImageAlive() {

            // gets all the img to create pacman
            File directory = new File("src\\img");
            File[] arr = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains("pacman.");
                }
            });

            //sort them
            Arrays.sort(arr);

            try {
                img = ImageIO.read(new File(arr[0].toString()));
                img2 = ImageIO.read(new File(arr[1].toString()));
                img3 = ImageIO.read(new File(arr[2].toString()));
                img4 = ImageIO.read(new File(arr[3].toString()));
                img5 = ImageIO.read(new File(arr[4].toString()));
                img6 = ImageIO.read(new File(arr[5].toString()));
                img7 = ImageIO.read(new File(arr[6].toString()));
                img8 = ImageIO.read(new File(arr[7].toString()));
                img9 = ImageIO.read(new File(arr[8].toString()));

            } catch (Exception ex) {System.err.println("No image loaded");System.exit(0);}



            //depending on the the direction, will use a different st of img
            // inside the main switch statement there is another that keeps tract of the sequence
            // that must follow to create the right pacman motion
            switch (dir)
                {
                    case KeyEvent.VK_UP:
                        switch (changealive) {
                            case 0:
                                changealive = 1;
                                i = img;
                                break;


                            case 1:
                                changealive = 2;
                                i = img6;
                                break;

                            case 2:
                                changealive = 0;
                                i = img7;
                                break;

                        }
                        break;


                    case KeyEvent.VK_DOWN:
                        switch (changealive) {
                            case 0:
                                changealive = 1;
                                i = img;
                                break;


                            case 1:
                                changealive = 2;
                                i = img8;
                                break;

                            case 2:
                                changealive = 0;
                                i = img9;
                                break;

                        }
                        break;

                    case KeyEvent.VK_LEFT:
                        switch (changealive) {
                            case 0:
                                changealive = 1;
                                i = img;
                                break;


                            case 1:
                                changealive = 2;
                                i = img2;
                                break;

                            case 2:
                                changealive = 0;
                                i = img3;
                                break;

                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        switch (changealive) {
                            case 0:
                                changealive = 1;
                                i = img;
                                break;


                            case 1:
                                changealive = 2;
                                i = img4;
                                break;

                            case 2:
                                changealive = 0;
                                i = img5;
                                break;
                        }
                        break;
                }

            }

        /**
         * images for the death animation
         */
        public void RenderingImageDeath()
        {
            readytospawn = false;

            //gets all the picture that creates the death motion
            File directory = new File("src\\img");
            File[] arrd = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains("death");
                }
            });
            Arrays.sort(arrd);

            try {
                dimg = ImageIO.read(new File(arrd[0].toString()));
                dimg2 = ImageIO.read(new File(arrd[1].toString()));
                dimg3 = ImageIO.read(new File(arrd[2].toString()));
                dimg4 = ImageIO.read(new File(arrd[3].toString()));
                dimg5 = ImageIO.read(new File(arrd[4].toString()));
                dimg6 = ImageIO.read(new File(arrd[5].toString()));
                dimg7 = ImageIO.read(new File(arrd[6].toString()));
                dimg8 = ImageIO.read(new File(arrd[7].toString()));
                dimg9 = ImageIO.read(new File(arrd[8].toString()));
                dimg10 = ImageIO.read(new File(arrd[9].toString()));
                dimg11 = ImageIO.read(new File(arrd[10].toString()));
            } catch (Exception ex) {System.err.println("No image loaded");System.exit(0);}


            BufferedImage[] collec = { dimg, dimg2, dimg3, dimg4, dimg5, dimg6, dimg7, dimg8, dimg9, dimg10, dimg11};

            // for each frame change the img
            i = collec[changeadeath];

            //plays the sounds when pacman dies
            if(changeadeath == 1){new Play("death").play();}

            //restart the sequence if gets to the end
            if(changeadeath == 10){changeadeath = 0;readytospawn = true;}
            // keeps tract of the sequence
            else{ changeadeath++;}

        }

        @Override
        Color GetColor(){return Color.YELLOW;}


        /**
         * takes care of pacman´s move per frame
         * @param dir = pacman´s direction
         */
        public void move(int dir)
        {
            //switch statement to alternate direction depending on the key pressed
            //Arrows
            switch(dir)
            {
                case KeyEvent.VK_UP:
                    y -= CellH/3;
                    break;


                case KeyEvent.VK_DOWN:
                    y += CellH/3;
                    break;

                case KeyEvent.VK_LEFT:
                    x -= CellW/3;
                    break;

                case KeyEvent.VK_RIGHT:
                    x += CellW/3;
                    break;
            }

            //if it gets to the end of the gameboard, spawn in the other side
            if(y < DIMENSIONSH *0.1) y = DIMENSIONSH *0.1;
            if(y + radius > DIMENSIONSH*0.9) y = DIMENSIONSH*0.898-radius;
            if(x < DIMENSIONSW *0.1) x += LimitWidth;
            if(x + radius > DIMENSIONSW*0.9) x -= LimitWidth ;

        }

        /**
         * method that predicts the location of pacman in the next frame
         * @return a 2DPoint with the future coordinates of pacman
         */
        public Point2D.Double[] prediction() {

            Point2D.Double[] l = new Point2D.Double[2];
            l[0] = new Point2D.Double(x, y);
            l[1] = new Point2D.Double(x, y);

            if (i != null) {
                //top left point
                Point2D.Double topleft = new Point2D.Double(x, y);
                //top right point
                Point2D.Double topright = new Point2D.Double(x + i.getWidth(), y);
                //bottom left point
                Point2D.Double bottomleft = new Point2D.Double(x, y + i.getHeight());
                //bottom right point
                Point2D.Double bottomright = new Point2D.Double(x + i.getWidth(), y + i.getHeight());


                // for each direction the points that will detect the colittion change.
                // when is going upwards(topleft, topright), down (bottomleft,bottom right)
                //left(topleft,bottomleft) and right(topright, bottom right)
                switch (dir) {
                    case KeyEvent.VK_UP:
                        topleft.y -= CellH / 3;
                        topright.y -= CellH / 3;
                        l[0] = topleft;
                        l[1] = topright;
                        return l;


                    case KeyEvent.VK_DOWN:
                        bottomleft.y += CellH / 3;
                        bottomright.y += CellH / 3;
                        l[0] = bottomleft;
                        l[1] = bottomright;
                        return l;

                    case KeyEvent.VK_LEFT:
                        topleft.x -= CellW / 3;
                        bottomleft.x -= CellW / 3;
                        l[0] = topleft;
                        l[1] = bottomleft;
                        return l;


                    case KeyEvent.VK_RIGHT:
                        topright.x += CellW / 3;
                        bottomright.x += CellW / 3;
                        l[0] = topright;
                        l[1] = bottomright;
                        return l;
                }

            }
            return l;
        }



        /**ejecuted the movement*/
        public  void translate(){this.move(dir);}

    }


    public static class Ghost extends Particle
    {

        private double cellw;
        private double cellh;

        private int dir;
        private String DIR = "UP";
        private int counter = 0;

        private BufferedImage img;
        private BufferedImage img2;
        private BufferedImage img3;
        private BufferedImage img4;
        private BufferedImage img5;
        private BufferedImage i;
        private Random ran = new Random();
        private boolean huntmode = false;
        LinkedList<Rectangle> CELLS;


        public Ghost(double x, double y, double radius, double cellw,double cellh,LinkedList<Rectangle> CELLS)
        {
            super(x,y,radius);
            this.cellw = cellw;
            this.cellh = cellh;
            this.CELLS = CELLS;

        }

        /**ejecuted the move*/
        public  void translate(){this.move(dir);}


        @Override
        void draw(Graphics2D g2d)
        {

            //if the huntmode is on, changes the look of the ghost
            if(!huntmode)
            {
                RenderingImageNoHuntMode();

            }

            // no huntmode, normal look
            else{RenderingImageHUNTMODE();}
            g2d.drawImage(i,(int)x,(int)y,null);

        }


        /** method that calculates the random move of the ghost*/
        @Override
        void move(int dir)
        {
            boolean next = false;

            double dx =  x;
            double dy = y ;

            //while the next move it is not inside a playable cell, keeps going
            while(!next)
            {
                dx = x;
                dy = y;

                //for the 4 directions. It will set the DIR of the ghost to be able to change the image
                //depending on the ghost´s direction
                switch (ran.nextInt(4)) {

                    case 0:
                        dy -= cellh;
                        DIR = "UP";
                        break;

                    case 1:

                        dy += cellh;
                        DIR = "DOWN";
                        break;

                    case 2:
                        dx -= cellw;
                        DIR = "LEFT";

                        break;

                    default:
                        dx += cellw;
                        DIR = "RIGHT";

                        break;
                }
                if(NextCell(dx,dy)){next = true;}

                //if the next move is false, counter up that will tell us when the ghost is trapt
                else{counter++;}
            }
            counter = 0;
            x = dx;
            y = dy;

        }

        // sets the List of playables cells
        public void SetList(LinkedList<Rectangle> cells){CELLS = cells;}

        /**
         * @param dx x coordinate
         * @param dy y coordinate
         * @return true if the next moves is inside a playable cell, false otherwise
         */
        public boolean NextCell(double dx, double dy)
        {

            for(Rectangle cell : CELLS)
            {

                // if the counter is greater than 50 means that the ghost is stuck, so move eithr way
                if(counter > 50)
                {
                    if(dx > cell.GetX() && dy >cell.GetY() && dx < cell.GetX()+cell.GetWidth() && dy < cell.GetY()+cell.GetHeight())
                    {
                        return true;
                    }
                }


                // inside a playable cell and not visited yet
                if(dx > cell.GetX() && dy >cell.GetY() && dx < cell.GetX()+cell.GetWidth() && dy < cell.GetY()+cell.GetHeight() && cell.GetState() != 1)
                {
                    return true;
                }

            }
            return false;


        }

        /**
         * gets the image for the huntmode
         */
        public void RenderingImageHUNTMODE()
        {
            File directory = new File("src\\img");
            File[] arr = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains("ghostdeath");
                }
            });

            Arrays.sort(arr);

            try {img5 = ImageIO.read(new File(arr[0].toString()));}
            catch (Exception ex) {System.err.println("No image loaded");System.exit(0);}
            i = img5;

        }

        /**
         * gets the images when no huntmode
         */
        public void RenderingImageNoHuntMode()
        {
            File directory = new File("src\\img");
            File[] arr = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains("ghost.");
                }
            });

            Arrays.sort(arr);

            try {
                img = ImageIO.read(new File(arr[0].toString()));
                img2 = ImageIO.read(new File(arr[1].toString()));
                img3 = ImageIO.read(new File(arr[2].toString()));
                img4 = ImageIO.read(new File(arr[3].toString()));

            } catch (Exception ex) {System.err.println("No image loaded");System.exit(0);}

            //depending on the direction it will look one way or the other
            switch (DIR)
            {
                case "UP":
                    i = img2;
                    break;

                case "DOWN":
                    i = img;
                    break;
                case "LEFT":
                    i = img4;
                    break;
                default:
                    i = img3;
                    break;
            }
        }

        @Override
        Color GetColor(){return new Color(RAND.nextInt(256),RAND.nextInt(256),RAND.nextInt(256));}
    }






    public static class FoodBit extends Particle
    {
        public FoodBit(double x, double y, double radius){super(x,y,radius);}

        @Override
        Color GetColor(){return Color.YELLOW;}


        @Override
        public void move(int dir){}


        public void draw(Graphics2D g2d)
        {
            //simple wieght ellipse to represent the food and the superfood
            Ellipse2D.Double food = new Ellipse2D.Double(x,y,radius,radius);
            g2d.fill(food);
        }


    }

    private static class DrawPanel extends JPanel
    {

        private LinkedList<Rectangle> WALLS = new LinkedList<>();
        private LinkedList<Rectangle> POWER = new LinkedList<>();
        private LinkedList<Rectangle> LIST_CELLS = new LinkedList<>();
        private LinkedList<Rectangle> FOOD = new LinkedList<>();
        private LinkedList<PacmanSprite> LIVES = new LinkedList<>();
        private LinkedList<Ghost> GHOST = new LinkedList<>();

        private Map<Rectangle, FoodBit> foodMap = new HashMap<>();
        private Map<Rectangle, FoodBit> powerfood = new HashMap<>();

        Rectangle spawnpac;
        Rectangle spawnghost;
        Rectangle spawnghost2;

        private Play intro;

        private boolean huntmode = false;
        private boolean win = false;
        private boolean gameover = false;


        private BufferedImage GameOverorWiner;


        PacMan pacman;

        private int fontSize = 20;
        private int Score;

        private double CellDimensionW;
        private double CellDimensionH;
        private double Columns = 42;
        private double Rows = 32;
        private int LivesNumber = 3;
        private int NumberOfGhosts = 4;

        private int Key = 0;

        private Rectangle MAIN_REC;
        private double GAMEFIELD_HEIGHT = DIMENSIONSH* 0.80;
        private double GAMEFIELD_WIDTH = DIMENSIONSW* 0.80;
        private double GAMEFIELDX = DIMENSIONSW *0.1 ;
        private double GAMEFIELDY = DIMENSIONSH *0.1 ;

        private Point2D.Double pacmanspawn = new Point2D.Double(GAMEFIELD_WIDTH*0.5+GAMEFIELDX,GAMEFIELD_HEIGHT*0.86+GAMEFIELDY);
        private Point2D.Double ghostspawn = new Point2D.Double(GAMEFIELD_WIDTH*0.5+GAMEFIELDX,GAMEFIELD_HEIGHT*0.5+GAMEFIELDY);




        public DrawPanel()
        {
            setBackground(Color.black);
            setForeground(Color.blue);
            setPreferredSize(new Dimension((int)DIMENSIONSW,(int)DIMENSIONSH));

            if(getWidth() < 0)return;

            MAIN_REC = new Rectangle(0,0,DIMENSIONSW,DIMENSIONSH,Color.BLACK);



            //saves the key pressed for the direction of pacman
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e)
                {

                    Key = e.getKeyCode();
                    pacman.dir = Key;
                }
            });

        }

        @Override
        protected void paintComponent(Graphics g)
        {

            Graphics2D g2d = (Graphics2D)g.create();
            MAIN_REC.draw(g2d);



            //draws the walls
            for(Rectangle wall : WALLS)
            {
                wall.draw(g2d);
                repaint();

            }


            //draws the food and power food
            g2d.setColor(Color.WHITE);
            if(foodMap.size() != 0 || powerfood.size() != 0)
            {
                for(FoodBit powerfood : powerfood.values())
                {
                    powerfood.draw(g2d);
                }
                for(FoodBit food : foodMap.values())
                {
                    food.draw(g2d);
                }

            }

            if(pacman != null)
            {

                pacman.draw(g2d); //draws pacman

                if(LIVES.size() != 0)
                {
                    Iterator<PacmanSprite> it = LIVES.iterator(); //draws the lives left in the score
                    it.next();
                    while (it.hasNext())
                    {
                        it.next().draw(g2d);
                    }
                }

                //draws the score
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Monospaced",Font.PLAIN, fontSize));
                g.drawString("SCORE: "+ Score, 95, 68);
                g.drawString("LIVES: ", 240, 68);


            }

        //draws the ghosts
        if(GHOST.size() != 0)
        {
            for(Ghost ghost : GHOST)
            {
                ghost.draw(g2d);

            }
        }

        //draws HUNTMODE on the screen whe is activated
        if(huntmode)
        {
            g.drawString("HUNTMODE ON!!!! ", 20, 20);
        }


         //if game over draws the game over picture
        if(gameover)
        {
            Display("GameOver",g2d,GameOverorWiner);

            if(Key == KeyEvent.VK_N)
            {
                restart();
            }
            else if(Key == KeyEvent.VK_X)
            {
                System.exit(0);
            }
        }

        //if win draw the win picture
        if(win)
        {
            Display("GameWinner",g2d,GameOverorWiner);
            if(Key == KeyEvent.VK_N)
            {
                restart();
            }
            else if(Key == KeyEvent.VK_X)
            {
                System.exit(0);
            }

        }
            repaint(); }



            /** returns a list with the picture that contains the string given*/
        private File[] Getpic(String s)
        {
            File directory = new File("src\\img");
            File[] arr = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains(s);
                }
            });

            Arrays.sort(arr);
            return arr;

        }

        /**Displays the image in the screen*/
        private void Display(String path,Graphics2D g2d,BufferedImage img)
        {
            File [] arr = Getpic(path);
            try
            {
                GameOverorWiner = ImageIO.read(new File(arr[0].toString()));
            } catch (Exception ex) {System.err.println("No image loaded");System.exit(0);}

            g2d.drawImage(img,(int)MAIN_REC.GetWidth()/2-GameOverorWiner.getWidth()/2,(int)MAIN_REC.GetHeight()/2-GameOverorWiner.getHeight()/2,null);
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Monospaced",Font.PLAIN, fontSize));
            g2d.drawString("Press n to play again or press x to exit", 470, 660);
            pacman.dir =0;
        }


        /**Creates Pacman and the ghost in their starting positions */
        private void InPacman()
        {
            if(pacman == null)
            {

                //plays the intro of the beggining
                intro = new Play("beginning");
                intro.play();

                double size = (CellDimensionW + CellDimensionH)/2;
                double pointerx = pacmanspawn.x;
                double pointery = pacmanspawn.y;

                double pointerxghost = ghostspawn.x;
                double pointeryghost = ghostspawn.y;

                    for (Rectangle cell : LIST_CELLS)
                    {
                        //displays pacman in the respawn cell
                        if (pointerx > cell.GetX() && pointerx < cell.GetX() + cell.GetWidth() && pointery > cell.GetY() && pointery < cell.GetY() + cell.GetHeight()) {
                            cell.SetState(1);
                            pacman = new PacMan(0, 0, size*0.80 , GAMEFIELD_WIDTH, GAMEFIELD_HEIGHT,CellDimensionW,CellDimensionH);
                            double x = cell.GetX() + 1;
                            double y = cell.GetY()+ 1;
                            pacmanspawn.x = x;
                            pacmanspawn.y = y;

                            pacman.x = x;
                            pacman.y = y;
                        }

                        //respawn all the ghost in the respawn cell
                        if (pointerxghost > cell.GetX() && pointerxghost < cell.GetX() + cell.GetWidth() && pointeryghost > cell.GetY() && pointeryghost < cell.GetY() + cell.GetHeight())
                        {
                            double x = cell.GetX() + 1;
                            double y = cell.GetY()+ 1;


                            for(int g = 0; g < NumberOfGhosts;g++)
                            {
                                GHOST.add(new Ghost(x,y,size,CellDimensionW,CellDimensionH,LIST_CELLS));
                                GHOST.get(g).x = ghostspawn.x;
                                GHOST.get(g).y = ghostspawn.y;

                            }
                        }
                    }

                    // crates the images for the lives
                    for(int i = 0; i < LivesNumber+1;i ++)
                {
                    LIVES.add(new PacmanSprite(330+size*i, 85, 30));

                }

            }
        }


        /**Creates the map between the cells and the foodbits*/
        private void SpawnFood()
        {
            if(foodMap.size() == 0 && Score == 0)
            {

                Iterator<Rectangle> it = FOOD.iterator();
                while(it.hasNext())
                {
                    Rectangle cell = it.next();
                    foodMap.put(cell,new FoodBit(cell.GetX()+cell.GetWidth()/2+cell.GetWidth()*0.15,cell.GetY()+cell.GetHeight()/2+cell.GetWidth()*0.15,cell.GetWidth()*0.3));
                }

            }


        }


        /**Create the walls and tore them into their own list*/
        private void CreateWalls()
        {
            int [][] a = {{0,0,2,14},{18,0,2,14},{0,2,40,1},{31,2,40,1},{2,-2,2,13},{19,-2,2,14},{1,20,2,3},{28,20,2,3}
                    ,{2,12,7,2},{2,23,7,2},{2,8,1,2},{2,10,1,2},{2,31,1,2},{2,33,1,2},{2,3,1,1},{3,4,1,1},{3,5,1,1},{2,6,1,1}
                    ,{2,35,1,1},{3,36,1,1},{3,37,1,1},{2,38,1,1},{28,12,7,2},{28,23,7,2},{28,8,1,2},{28,10,1,2},{28,31,1,2}
                    ,{28,33,1,2},{28,3,1,1},{29,4,1,1},{29,5,1,1},{28,6,1,1},{28,35,1,1},{29,36,1,1},{29,37,1,1},{28,38,1,1}
                    ,{13,18,1,5},{18,18,6,1},{13,23,1,5},{13,19,1,1},{13,22,1,1},{7,2,2,4},{7,38,2,4},{21,2,2,4},{21,38,2,4}
                    ,{5,3,16,1},{5,23,16,1},{26,3,16,1},{26,23,16,1},{11,16,1,4},{17,16,1,4},{11,17,3,1},{11,22,4,1},{12,25,1,3}
                    ,{17,25,1,4},{20,17,8,1},{7,5,3,7},{7,34,3,7},{18,5,3,7},{18,34,3,7},{12,3,1,2},{12,38,1,2},{18,3,1,2},{18,38,1,2}
                    ,{15,2,9,2},{15,31,9,2},{7,9,7,2},{7,26,7,2},{23,9,7,2},{23,26,7,2},{5,20,2,3},{24,20,2,3},{9,17,8,1},{22,17,8,1}
                    ,{12,14,1,8},{12,27,1,8},{12,8,2,2},{12,32,2,2},{18,8,2,2},{18,32,2,2},{10,9,2,1},{10,14,2,1},{10,26,2,1},{10,31,2,1}
                    ,{21,9,2,1},{21,14,2,1},{21,26,2,1},{21,31,2,1},{11,12,1,4},{11,29,1,4},{17,12,1,4},{17,29,1,4},{14,19,1,4},{14,22,1,4}
                    ,{17,20,2,1}};




            if(WALLS.size() ==0)
            {

                for(int i = 0; i < a.length;i++)
                {
                    WALLS.add(new Rectangle(LIST_CELLS.get(a[i][0]*(int)Columns+a[i][1]).GetX(),LIST_CELLS.get(a[i][0]*(int)Columns+a[i][1]).GetY(),CellDimensionW*a[i][2],CellDimensionH * a[i][3],Color.BLUE));
                }

            }
        }

        /**
         * checks if a point is inside a rectangle
         * @param p = 2d point to check
         * @param REC = Rectangle
         * @return true if it is indide, flase otherwise
         */
        private Boolean intersection(Point2D.Double p, Rectangle REC)
        {

            if(p.x > REC.GetX() && p.y > REC.GetY() && p.x < REC.GetX()+REC.GetWidth() && p.y < REC.GetY()+REC.GetHeight())
            {
                return true;
            }

            return false;
        }



        /**eliminates the cell covered by walls leaving just the playable cell*/
        private void PlayableCells()
        {
            if(POWER.size() == 0)
            {

                //create the playable cells
                CreatePlayable(WALLS,LIST_CELLS);

                CreatepowerFood();
                //add the respawn area to the no playable cells
                WALLS.add(new Rectangle(FOOD.get(14*(int)Columns+19).GetX(),FOOD.get(14*(int)Columns+19).GetY(),CellDimensionW*4,CellDimensionH*4,Color.CYAN));
                WALLS.add(new Rectangle(FOOD.get(13*(int)Columns+20).GetX(),FOOD.get(13*(int)Columns+20).GetY(),CellDimensionW*2,CellDimensionH,Color.CYAN));

                //adds pacman´s respawn
                WALLS.add(new Rectangle(FOOD.get(27*(int)Columns+21).GetX(),FOOD.get(27*(int)Columns+21).GetY(),CellDimensionW,CellDimensionH,Color.CYAN));

                //eliminate in both spawn the food
                CreatePlayable(WALLS,FOOD);


                //removes pacman´s respawn
                spawnpac = WALLS.removeLast();
                //remove the respawn area from the collision list
                spawnghost2 = WALLS.removeLast();
                spawnghost = WALLS.removeLast();


                //set the List of the playable cells for each ghost
                for(Ghost g : GHOST)
                {
                    g.CELLS = LIST_CELLS;
                }
            }
        }


        /**eliminate the cells from one set to the other*/
        private void CreatePlayable(LinkedList<Rectangle> walls,LinkedList<Rectangle> cells)
        {
            LinkedList<Rectangle> temp = new LinkedList<>();

            for(Rectangle cell : cells)
            {
                Iterator<Rectangle> it = walls.iterator();
                while(it.hasNext())
                {
                    Rectangle wall = it.next();

                    //if it is inside, delete it from the cell list
                    if(cell.Inside(wall))
                    {
                        temp.add(cell);
                    }
                }
            }
            cells.removeAll(temp);
        }

        private void CreatepowerFood()
        {
            //coordinates for the power food
            int [][] power = {{44},{81},{187},{190},{432},{449},{464}
            ,{501},{688},{697},{936},{953},{1224},{1253}};

            //creates the map between cell and superfood
           for(int i = 0; i < power.length; i++)
           {
               Rectangle cell = FOOD.get(power[i][0]);
               powerfood.put(cell,new FoodBit(cell.GetX()+cell.GetWidth()/2+cell.GetWidth()*0.25,cell.GetY()+cell.GetHeight()/2+cell.GetWidth()*0.25,
                       cell.GetWidth()*0.5));
               POWER.add(cell);
           }
        }


        /**Create the cells inside the gameboard*/
        private void CreateCells()
        {
            CellDimensionW = GAMEFIELD_WIDTH / Columns;
            CellDimensionH = GAMEFIELD_HEIGHT / Rows;
            if(LIST_CELLS.size() == 0 && FOOD.size() == 0)
            {
                double x = GAMEFIELDX;
                double y = GAMEFIELDY;
                double numberCol = GAMEFIELD_WIDTH/CellDimensionW;
                double numberRow = GAMEFIELD_HEIGHT/CellDimensionH;


                //outer loop for the rows
                for (int i = 0; i < numberRow; i++) {

                    //inner loop for the number of columns
                    for (int j = 0; j < numberCol; j++) {

                        LIST_CELLS.add(new Rectangle(x, y, CellDimensionW, CellDimensionH,Color.CYAN));
                        FOOD.add(new Rectangle(x, y, CellDimensionW, CellDimensionH,Color.CYAN));
                        x += CellDimensionW;

                    }

                    x = GAMEFIELDX; //resets the x coordinate to the beginning of the row
                    y += CellDimensionH;
                    repaint();

                }

            }else{return;}
        }

        /**Restarts all the parameters and list for a new game*/
        private void restart()
        {
            LIST_CELLS.clear();
            GHOST.clear();
            FOOD.clear();
            POWER.clear();
            LIVES.clear();
            pacman.dir =0;
            pacman = null;
            foodMap.clear();
            powerfood.clear();
            Score = 0;
            win = false;
            gameover = false;

        }

        /**Respawn indivifual ghost*/
        private void RespawnGhost(Ghost g)
        {
            g.x = ghostspawn.x;
            g.y = ghostspawn.y;
            // new list of playable cells reset after it dies
            ResetCell(g);
        }

        /**Respawns all the ghost when pacman dies or neew game*/
        private void RespawnALLGhost()
        {
            for(Ghost g : GHOST)
            {
                g.x = ghostspawn.x;
                g.y = ghostspawn.y;
                ResetCell(g);
            }

        }


        /**Resets all the cells to "no visited yet*/
        private void ResetCell(Ghost g)
        {
            for(Rectangle cell : LIST_CELLS)
            {
                cell.SetState(0);
            }

            g.SetList(LIST_CELLS);
        }

        //Respawn pacman*//
        private void RespawnPacman()
        {
            LIVES.removeLast();
            RespawnALLGhost();
            pacman.alive = true;

            pacman.dir = 0;
            pacman.x = pacmanspawn.x;
            pacman.y = pacmanspawn.y;

        }

    }


    public PacManFrame()
    {

        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setBounds((int)(DIMENSIONSW/0.7)/2-(int)DIMENSIONSW/2,0, (int)DIMENSIONSW, (int)DIMENSIONSH);
        super.setResizable(false);
        myPanel = new DrawPanel();
        super.add(myPanel);

    }



    public static void main(String[] args) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        DIMENSIONSW = screenSize.getWidth()*0.7;
        DIMENSIONSH = screenSize.getHeight()*0.9;


        PacManFrame myDemo = new PacManFrame();

        Timer timer = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myDemo.myPanel.CreateCells();
                myDemo.myPanel.CreateWalls();
                myDemo.myPanel.InPacman();
                myDemo.myPanel.PlayableCells();


                LinkedList<Rectangle> CELLS = myDemo.myPanel.LIST_CELLS;
                LinkedList<Rectangle> WALLS = myDemo.myPanel.WALLS;

                LinkedList<Rectangle> power = myDemo.myPanel.POWER;
                LinkedList<Ghost> GHOST = myDemo.myPanel.GHOST;
                LinkedList<Rectangle> FOOD = myDemo.myPanel.FOOD;

                Map<Rectangle, FoodBit> powerfood = myDemo.myPanel.powerfood;
                List<HuntModeThread> threads = PacManFrame.threads;
                Map<Rectangle, FoodBit> foodMap = myDemo.myPanel.foodMap;



                 PacMan pacman = myDemo.myPanel.pacman;
                 Play intro = myDemo.myPanel.intro;

                boolean huntmode = myDemo.myPanel.huntmode;


                 myDemo.myPanel.SpawnFood();


                 //gets the prediction of pacman
                Point2D.Double[] nextPoints = pacman.prediction();
                boolean move = true;

                for(Rectangle wall : WALLS )
                {
                    //no move if any of both points collide
                    if (myDemo.myPanel.intersection(nextPoints[0], wall) || myDemo.myPanel.intersection(nextPoints[1], wall)) {move = false;}
                }

                //if next move do not collide, pacman is alive, and the intro music is not running, can start moving
                if(move && pacman.IsAlive() && !intro.GetClip().isActive()){pacman.translate();}



                Iterator<Rectangle> it = CELLS.iterator();
                //for each cll, look for coalitions
                while(it.hasNext())
                {
                    Rectangle cell = it.next();
                    for(Ghost g : GHOST)
                    {
                        if (pacman.InCell(cell)) {cell.SetState(0);} //if pacman visits a cell, reset it
                        else if (g.InCell(cell)) {cell.SetState(1);} // if a ghost visits a cell, state 1 == can not visit again

                        //if pacman and a ghots in the same cell. Huntmode == true will kill the ghost, otherwise kill pacman
                        if (pacman.InCell(cell) && g.InCell(cell) && huntmode)
                        {
                            new Play("eatghost").play();
                            myDemo.myPanel.RespawnGhost(g);
                            myDemo.myPanel.RespawnGhost(g);
                        }
                        if (pacman.InCell(cell) && g.InCell(cell)){pacman.alive = false;}

                    }
                }

                //if pacman visited a cell with food. Increase score, delate food
                for(Rectangle foodcell : FOOD)
                {
                    if (pacman.InCell(foodcell) && foodMap.containsKey(foodcell))
                    {
                        foodMap.remove(foodcell);
                        myDemo.myPanel.Score++;
                    }
                }
                //if pacman visited a cell with a superfood. delate food and create the huntmode

                for(Rectangle c : power)
                {
                    if (pacman.InCell(c) && powerfood.containsKey(c))
                    {
                        //turn on the hundmode on and plays the sound
                        Play intermission = new Play("intermission");
                        intermission.play();

                        powerfood.remove(c);

                        HuntModeThread hunt = new HuntModeThread(huntmode);
                        hunt.start();

                        threads.add(hunt);

                        //Eliminates the previous thread in case of using two at the same tie to avoid double effect
                        if(threads.size() > 1)
                        {
                            threads.remove(0);

                        }
                    }
                }
                if(threads.size() > 0)
                {
                    boolean hundMode = threads.get(0).GetMode();
                    myDemo.myPanel.huntmode = hundMode;

                    //sets the huntmode on for eac ghost what wil make them change aspect
                    for(Ghost g : GHOST)
                    {
                        g.huntmode = hundMode;
                    }

                }

                //if no food. Win
                if(foodMap.size() == 0 && powerfood.size() == 0)
                {
                    threads.clear();
                    myDemo.myPanel.win = true;


                }

                //if no lives left, lost
                if(myDemo.myPanel.LIVES.size() == 0)
                {
                    myDemo.myPanel.gameover = true;
                }

                myDemo.myPanel.repaint();

            }
        });

        /**
         * timer to control the move of the ghost/*
         */
        Timer timer3 = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PacMan pacman = myDemo.myPanel.pacman;
                Play intro = myDemo.myPanel.intro;
                LinkedList<Ghost> GHOST = myDemo.myPanel.GHOST;

                // if pacman has a direction and the intro is done. Move the ghosts
                if(pacman.dir != 0 && !intro.GetClip().isActive())
                {
                    for(Ghost g : GHOST){g.translate();}

                }
            }
        });

        /**takes care of the sprites for pacman and the ghosts*/
        Timer timer2 = new Timer(120, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PacMan pacman = myDemo.myPanel.pacman;

                //depending if are alive or not, chane their aspect
                if(pacman !=null)
                {
                    if(pacman.IsAlive())
                    {
                        pacman.RenderingImageAlive();
                    }
                    else if(!pacman.IsAlive())
                    {
                        pacman.RenderingImageDeath();
                        if(pacman.readytospawn)
                        {
                            myDemo.myPanel.RespawnPacman();

                        }
                    }
                }
            }
        });


        java.awt.EventQueue.invokeLater(()
        ->
        {
            myDemo.setVisible(true);
            myDemo.pack();
            myDemo.myPanel.requestFocusInWindow();
            timer2.start();
            timer.start();
            timer3.start();


        });


    }
}
