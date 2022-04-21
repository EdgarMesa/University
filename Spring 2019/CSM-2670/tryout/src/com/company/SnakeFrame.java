package com.company;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;


public final class SnakeFrame extends JFrame
{
    private static final Random RAND = new Random();
    private final DrawPanel myPanel;


    private static abstract class Bit
    {
        private final Point location;
        public static final int DOT_SIZE = 20;

        abstract Color getColor();

        public Bit(Point location)
        {


            this.location = location;
        }

        public Point getLocation()
        {
            return location;
        }
        public void translate(int dir)
        {
            switch(dir)
            {
                case KeyEvent.VK_UP:
                    location.y -= DOT_SIZE;
                    break;
                case KeyEvent.VK_DOWN:
                    location.y += DOT_SIZE;
                    break;
                case KeyEvent.VK_LEFT:
                    location.x -= DOT_SIZE;
                    break;
                case KeyEvent.VK_RIGHT:
                    location.x += DOT_SIZE;
                    break;
            }

            if(location.y + DOT_SIZE/2 < 0)   location.y += 600 ;
            if(location.y - DOT_SIZE/2 > 600) location.y -= 600;
            if(location.x - DOT_SIZE/2 < 0)   location.x += 600;
            if(location.x + DOT_SIZE/2 > 600) location.x -= 600 ;
        }

        public void draw(Graphics g)
        {
            g.setColor(getColor());
            g.fillOval(location.x - DOT_SIZE / 2, location.y - DOT_SIZE / 2, DOT_SIZE, DOT_SIZE);
        }
    }

    private static class FoodBit extends Bit
    {

        public FoodBit(Point location)
        {
            super(location);
        }

        @Override
        Color getColor()
        {
            return Color.red;
        }

    }

    private static class SnakeBit extends Bit
    {

        public SnakeBit(Point location)
        {
            super(location);
        }

        @Override
        Color getColor()
        {
            return Color.green;
        }
    }

    private static class Snake
    {
        private final LinkedList<SnakeBit> snakeList = new LinkedList<>();
        private int dir = KeyEvent.VK_RIGHT;

        public Snake()
        {
            for(int i = 0; i < 10; i++)
            {
                snakeList.add(new SnakeBit(new Point(300 - i * SnakeBit.DOT_SIZE, 300)));
            }
        }

        public Point getLocation()
        {
            return snakeList.getFirst().getLocation();
        }

        public void translate()
        {
            SnakeBit lastBit  = snakeList.pollLast();
            SnakeBit firstBit = snakeList.getFirst();

            lastBit.getLocation().x = firstBit.getLocation().x;
            lastBit.getLocation().y = firstBit.getLocation().y;
            lastBit.translate(dir);

            snakeList.addFirst(lastBit);
        }

        public void draw(Graphics g)
        {
            for(SnakeBit bit : snakeList)
            {
                bit.draw(g);
            }
        }

        private void grow()
        {
            snakeList.add(new SnakeBit(new Point(snakeList.getLast().getLocation())));
        }

    }

    private static final class DrawPanel extends JPanel
    {

        private int score = 0;


        private Snake snake;
        private Map<String, FoodBit> foodMap = new TreeMap<>();

        public DrawPanel()
        {
            super.setBackground(Color.BLUE);
            super.setForeground(Color.green);
            super.addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyPressed(KeyEvent e)
                {
                    if (snake == null)
                    {
                        return;
                    }


                    int newKey = e.getKeyCode();
                    if((newKey == KeyEvent.VK_RIGHT && snake.dir != KeyEvent.VK_LEFT)  ||
                            (newKey == KeyEvent.VK_LEFT  && snake.dir != KeyEvent.VK_RIGHT) ||
                            (newKey == KeyEvent.VK_UP    && snake.dir != KeyEvent.VK_DOWN)  ||
                            (newKey == KeyEvent.VK_DOWN  && snake.dir != KeyEvent.VK_UP))
                    {
                        snake.dir = newKey;
                    }

                }
            });

        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(new Color(96, 128, 255));


            for(FoodBit b : foodMap.values())
            {
                b.draw(g);
            }


            if (snake != null)
            {
                g.drawString("SCORE: " + score, 30, 30);

                snake.draw(g);
            }
        }
    }

    public SnakeFrame()
    {
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setBounds(50, 50, 600, 600);
        super.setResizable(false);
        myPanel = new DrawPanel();
        super.add(myPanel);

        myPanel.snake = new Snake();
        for(int i = 0; i < 5; i++)
        {
            FoodBit food = new FoodBit(new Point(RAND.nextInt(30) * Bit.DOT_SIZE,
                    RAND.nextInt(30) * Bit.DOT_SIZE));
            myPanel.foodMap.put(food.getLocation().toString(), food);
        }
    }

    public static void main(String[] args)
    {
        SnakeFrame myDemo = new SnakeFrame();
        Timer gameTimer = new Timer(100, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                final Snake s = myDemo.myPanel.snake;
                final Map<String, FoodBit> foodMap = myDemo.myPanel.foodMap;


                // Move snake
                s.translate();

                // Did the snake hit any food
                FoodBit food = foodMap.get(s.getLocation().toString());
                if(food != null)
                {
                    myDemo.myPanel.score++;
                    // Snake gets bigger
                    s.grow();

                    // Remove old food
                    foodMap.remove(s.getLocation().toString());

                    // Add new food
                    food = new FoodBit(new Point(RAND.nextInt(30) * Bit.DOT_SIZE,
                            RAND.nextInt(30) * Bit.DOT_SIZE));
                    foodMap.put(food.getLocation().toString(), food);
                }

                // Did the snake hit itself
                Iterator<SnakeBit> iter = s.snakeList.iterator();
                SnakeBit head = iter.next();
                while(iter.hasNext())
                {
                    SnakeBit b = iter.next();
                    if(head.getLocation().equals(b.getLocation()))
                    {
                        // I leave it to the reader to do something more
                        // interesting here.
                        System.exit(0);
                    }
                }

                myDemo.myPanel.repaint();
            }
        });

        java.awt.EventQueue.invokeLater(()
                ->
        {
            myDemo.setVisible(true);
            myDemo.myPanel.requestFocusInWindow();
            gameTimer.start();

        });
    }
}