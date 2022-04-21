import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.*;


    //Frame class
public class Particles extends JFrame {

    private final DrawPanel myPanel;

    //Panel class
    private static class DrawPanel extends JPanel
    {
        //Attributes
        public static final int Apperance_rate = 3000;
        public static final int DEFAULT_LIVE_TIME = Apperance_rate +2000;
        public static int Live_time = 5000;
        public static final int Frame_per_second = 33;
        private final int DOT_SIZE = 10;
        private final int MAX_Speed = 25;

        //LinkedList to store the particles and their properties
        private LinkedList<Particle> LIST_DOTS  = new LinkedList<>();


        public DrawPanel()
        {
            setBackground(Color.BLACK);
            setForeground(Color.green);


        }

        /** draws the components in the Panel
         * @param g
         */
         @Override
        protected void paintComponent(Graphics g)
         {

             super.paintComponent(g);

             //loop to go through the particles in the LinkedList and draw them
             for(Particle particle : LIST_DOTS)
                {
                    g.setColor(particle.GetColor());
                    g.fillOval((int)particle.GetX(),(int)particle.GetY(),particle.GetRadius()*2,particle.GetRadius()*2);
                }
             }

        /**
         * Method change the X and Y position of each article. Also if the particle
         * touches the limits, it bounced to the opposite direction
         */
        private void move()
         {
             //timer to control the frames per seconds (moves of the ball)
             Timer timer = new Timer(Frame_per_second , new ActionListener() {

                 // loop to go through the LinkedList of particles
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     for(Particle particle : LIST_DOTS){

                         particle.moveX(); //changes the x coordinate
                         particle.moveY(); //changes the y coordinate

                         // if it touches any of the horizontal boundaries changes its direction
                         if(particle.GetX() < 0 ||particle.GetX()+DOT_SIZE*2 >= getWidth() )
                         {
                             particle.SetDX(particle.GetDX()*-1);
                             particle.moveX(); //changes the x coordinate

                         }
                         // if it touches any of the vertical boundaries changes its direction
                         if(particle.GetY() < 0 ||particle.GetY()+DOT_SIZE*2 >= getHeight() )
                         {
                             particle.SetDY(particle.GetDY()*-1);
                             particle.moveY(); //changes the y coordinate

                         }
                     }
                     repaint();
             }});
             timer.start();
         }

        /**
         * Create a particle every desired time
         */
         private void CreateParticle(){

             Timer timer = new Timer(Apperance_rate, new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     LIST_DOTS.add(new Particle((getWidth()/2),(getHeight()/2),DOT_SIZE,MAX_Speed));
                     repaint();
                 }
             });

             timer.start();
        }

        /**
         * removes a particle every desired time
         */
        private void remove(){

            if(Live_time < Apperance_rate){Live_time = DEFAULT_LIVE_TIME;System
            .err.println("Live time too short. Set to the default");}

            Timer timer = new Timer(Live_time, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LIST_DOTS.removeFirst();
                    System.err.println("Ball removed!");

                }
            });
            timer.start();
        }
    }

    public Particles()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(50,50,400,400);
        myPanel = new DrawPanel();
        add(myPanel);
    }


    public static void main(String[] args) {

        Particles demo = new Particles();
        java.awt.EventQueue.invokeLater(() -> {
            demo.setVisible(true);

            demo.myPanel.LIST_DOTS.add(new Particle((  demo.myPanel.getWidth()/2),(  demo.myPanel.getHeight()/2),  demo.myPanel.DOT_SIZE,  demo.myPanel.MAX_Speed));
            demo.myPanel.CreateParticle();
            demo.myPanel.move();
            demo.myPanel.remove();

        });


    }

    }
