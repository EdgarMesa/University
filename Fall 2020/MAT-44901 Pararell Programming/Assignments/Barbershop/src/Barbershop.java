import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Barbershop {

    private static final int N = 10;
    private static final int TIME_TO_CUT = 8000;
    private static final int ARRIVAL_RATE = 1000;
    private static final ConcurrentLinkedDeque<Customer> CUSTOMERS_LINE = new ConcurrentLinkedDeque<>();
    private static final Barber BARBER = new Barber();
    private static Customer customerGettingHaircut = null;
    private static final Semaphore mutex = new Semaphore(0);
    private static final Semaphore working = new Semaphore(0);
    private static int NUMBER_OF_CUSTOMERS = 0;
    private static boolean WORK = false;
    private static final Random RAN = new Random();
    private static final ReentrantLock lock = new ReentrantLock(true);
    private static final int SIZE = 700;
    private static DrawingPanel panel;

    public static void main(String[] args) {

        BARBER.start();
        int freeSlotNumber = 1;
        paint();

        // This part needs to be fixed. We have to make sure the code runs after the pain thread is made.
        try
        {
            Thread.sleep(100); // wait a bit to create the frame and panel.
        }catch (InterruptedException e){
            System.out.println("Caught: " + e);
        }

        while (true)
        {

            try
            {
                Thread.sleep(ARRIVAL_RATE); // Randomly creating a customer with a arrival rate
            }catch (InterruptedException e){
                System.out.println("Caught: " + e);
            }

            int random = RAN.nextInt(2);
            if(random == 0)
            {
                Customer newCustomer = new Customer(NUMBER_OF_CUSTOMERS);
                newCustomer.start();

                if (CUSTOMERS_LINE.size() >= N) // if no seats, balk. Otherwise, add it to the linked queue
                {
                    newCustomer.interrupt();
                }
                else
                    {
                        CUSTOMERS_LINE.add(newCustomer);
                    }

            }else{panel.setArriveInformation(String.format("New arrival:    Free Slot number %d.", freeSlotNumber++));}

            panel.setSeatsLeft(String.format("Seats left: %d", N + - CUSTOMERS_LINE.size()));

        }

    }

    // display the image that the threads already created
    private static void paint()
    {
        SwingUtilities.invokeLater(() ->
        {
            // Make a frame
            JFrame f = new JFrame("Barber Shop Simulation");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(SIZE, SIZE);
            f.setBackground(Color.white);

            // Add the drawing panel
            panel = new DrawingPanel();

            f.add(panel);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            f.pack();
            // Sets the location of the Frame in the center of the screen
            f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
            f.setResizable(false);
            f.setVisible(true);

        });
    }

    private static void getHairCut(int customerNumber)
    {
        try
        {
            lock.lockInterruptibly(); //lock instead of a synchronized block or method to use fairness.
            WORK = true;
        }catch (InterruptedException e){System.out.println("Caught: " + e);}


        if (!CUSTOMERS_LINE.isEmpty()) //If we have customer...
        {
            customerGettingHaircut = CUSTOMERS_LINE.pollFirst();
            panel.setHairCut2Information(String.format("Customer %d invokes getHairCut.\n", customerNumber));
            mutex.release(); //Let the Barber (Consumer) know that we have a next client.
            working.acquireUninterruptibly(); // Wait until the work of the barber is dode.
        }
        WORK = false;
        lock.unlock(); // unlock the lock
    }

    public static class Barber extends Thread
    {

        private static void cutHair()
        {
            panel.setHairCutInformation(String.format("Barber invokes cutHair on Customer %d.\n", customerGettingHaircut.customerNumber));
            try
            {
                Thread.sleep(TIME_TO_CUT);   // Simulate some work

            }catch(InterruptedException e){
                System.out.println("Caught: " + e);}
            customerGettingHaircut.interrupt(); // Finish the Customer thread in the next iteration of it while loop.
        }

        @Override
        public void run() {

            while (!Thread.interrupted())
            {
                mutex.acquireUninterruptibly(); // Sleep if no more customers.
                cutHair();
                panel.setHairCutInformation("");
                panel.setHairCut2Information("");
                working.release(); // let the Customer


            }

        }
    }

    public static class Customer extends Thread
    {

        private int customerNumber;

        public Customer(int number)
        {
            this.customerNumber = number;
            NUMBER_OF_CUSTOMERS++;
            panel.setArriveInformation(String.format("New arrival:    Customer %s arriving.\n", customerNumber));
        }

        @Override
        public void run() {
            while (!Thread.interrupted())
            {
                getHairCut(customerNumber);
            }
            panel.setBalkInformation(String.format("Customer %s leaves.\n", customerNumber));
        }
    }


    private static class DrawingPanel extends JPanel
    {

        private String arrivalInformation;
        private String haircutInformation;
        private String haircut2Information;
        private String balkInformation;
        private String extraInformation;
        private String seatsLeft;
        private Font haircutFont;
        private Font otherFont;

        public DrawingPanel()
        {
            this.setBackground(Color.white);
            int size = SIZE;
            this.setPreferredSize(new Dimension(size + 300, size));
            Timer timer = new Timer(30, e -> repaint());
            timer.start();
            extraInformation = "Nobody in the barbershop. Barber sleeping.";
            seatsLeft = String.format("Seats left: %d", N);
            haircutFont = new Font("Serif", Font.PLAIN, 30);
            otherFont = new Font("Serif", Font.PLAIN, 16);
        }

        // all the text.
        private void setArriveInformation(String info){arrivalInformation = info;}
        private void setHairCutInformation(String info){haircutInformation = info;}
        private void setHairCut2Information(String info){haircut2Information = info;}
        private void setBalkInformation(String info){balkInformation = info;}
        private void setExtraInformation(String info){extraInformation = info;}
        private void setSeatsLeft(String info){seatsLeft = info;}

        @Override
        protected void paintComponent(Graphics g)
        {

            super.paintComponent(g);
            g.setFont(otherFont);
            g.setColor(Color.BLACK);

            if (CUSTOMERS_LINE.isEmpty() && !WORK){panel.setExtraInformation("Nobody in the barbershop. Barber sleeping.");}
            else{panel.setExtraInformation("");}

            if (arrivalInformation != null)
            {
                g.drawString(arrivalInformation, 0, 60);

            }

            if (balkInformation != null)
            {
                g.drawString(balkInformation, 0, 90);
            }

            if (extraInformation != null)
            {
                g.drawString(extraInformation, 0, 30);
            }

            if (seatsLeft != null)
            {
                g.drawString(seatsLeft, 0, 120);
            }
            int gab = 0;
            g.drawString("Customer Line: ", 800, 30);
            for(Customer costu : CUSTOMERS_LINE)
            {
                g.drawString(String.format("Customer %d", costu.customerNumber), 900, 30 + gab);
                gab += 20;
            }

            g.setColor(Color.red);
            g.setFont(haircutFont);
            if (haircutInformation != null)
            {
                g.drawString(haircutInformation, 280, 300);
            }

            if (haircut2Information != null)
            {
                g.drawString(haircut2Information, 280, 330);
            }


        }
    }
}
