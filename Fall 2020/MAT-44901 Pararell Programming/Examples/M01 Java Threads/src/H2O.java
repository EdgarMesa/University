// From the Little Book of Semaphores
//
// "This problem has been a staple of the Operating Systems class at U.C. Berkeley
// for at least a decade. It seems to be based on an exercise in Andrews’s
// Concurrent Programming.
//
// There are two kinds of threads, oxygen and hydrogen. In order to assemble
// these threads into water molecules, we have to create a barrier that makes each
// thread wait until a complete molecule is ready to proceed.
//
// As each thread passes the barrier, it should invoke bond. You must guarantee
// that all the threads from one molecule invoke bond before any of the threads
// from the next molecule do.
//
// In other words:
//     • If an oxygen thread arrives at the barrier when no hydrogen threads are
//       present, it has to wait for two hydrogen threads.
//     • If a hydrogen thread arrives at the barrier when no other threads are
//       present, it has to wait for an oxygen thread and another hydrogen thread.
//
// We don’t have to worry about matching the threads up explicitly; that is,
// the threads do not necessarily know which other threads they are paired up
// with. The key is just that threads pass the barrier in complete sets; thus, if we
// examine the sequence of threads that invoke bond and divide them into groups
// of three, each group should contain one oxygen and two hydrogen threads.
//
// Puzzle: Write synchronization code for oxygen and hydrogen molecules that
// enforces these constraints."

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class H2O
{

    // Counters
    private static int oxygenCounter = 0;
    private static int hydrogenCounter = 0;

    // A mutex to protect the counters
    private static final Semaphore mutex = new Semaphore(1, true);

    // Semaphores to be used as wait queues for the oxygen and hydrogen threads to wait on while
    // waiting to bond.
    private static Semaphore oxygenQueue = new Semaphore(0, true);
    private static Semaphore hydrogenQueue = new Semaphore(0, true);

    // After bonding the threads will wait at this barrier to release the mutex and let the next
    // set in. Note that barriers can be given a runnable or lambda to invoke when the barrier
    // is tripped. This acction is performed by the last thread entering the barrier. Another
    // approach would be to have the oxygen thread release the mutex as there is only one
    // oxygen in each set.
    private static CyclicBarrier barrier = new CyclicBarrier(3, () ->
    {
        checkBond();
        mutex.release();
    });

    public static void main(String[] args)
    {
        int oxygenLeft = 10000;
        int hydrogenLeft = oxygenLeft * 2;
        Random rand = new Random();

        while (oxygenLeft > 0 || hydrogenLeft > 0)
        {
            // If we are out of oxygen make hydrogen
            if (oxygenLeft <= 0)
            {
                new Hydrogen().start();
                hydrogenLeft--;
            } // If we are out of hydrogen make oxygen
            else if (hydrogenLeft <= 0)
            {
                new Oxygen().start();
                oxygenLeft--;
            } // otherwise pick one at random
            else if (rand.nextInt(3) == 0)
            {
                new Oxygen().start();
                oxygenLeft--;
            } else
            {
                new Hydrogen().start();
                hydrogenLeft--;
            }
        }
    }

    private static int numberOfBonds = 0;
    private static StringBuffer bondBuffer = new StringBuffer(3);

    // Adds the given char to the bond
    private static void bond(char c)
    {
        bondBuffer.append(c);
    }

    // Check that a bond is valid i.e. only has two H's and one O in any order
    private static void checkBond()
    {
        int oCount = 0;
        int hCount = 0;
        for (int i = 0; i < bondBuffer.length(); i++)
        {
            switch (bondBuffer.charAt(i))
            {
                case 'O':
                    oCount++;
                    break;
                case 'H':
                    hCount++;
                    break;
                default:
                    System.out.println("Bad bond");
                    System.exit(1);
            }
        }

        if (oCount == 1 && hCount == 2)
        {
            System.out.printf("Made H2O number %d\n", ++numberOfBonds);
        } else
        {
            System.out.println("Bad bond");
            System.exit(1);
        }

        bondBuffer = new StringBuffer(3);
    }

    private static class Oxygen extends Thread
    {

        @Override
        public void run()
        {
            // Access counters
            mutex.acquireUninterruptibly();

            // Update oxygen count
            oxygenCounter++;

            // If there are 2 hydrogens waiting, wake them up and an oxygen can procced as well
            if (hydrogenCounter >= 2)
            {
                hydrogenQueue.release(2);
                hydrogenCounter -= 2;
                oxygenQueue.release();
                oxygenCounter--;
            } else
            {
                // Otherwise relese the counters and wait
                mutex.release();
            }

            // If there were 2 or more hydrogens then we will be able to pass otherwise we will wait
            oxygenQueue.acquireUninterruptibly();
            bond('O');

            // Wait a the barrier
            try
            {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex)
            {
                System.err.println("Exception on wait");
                System.exit(1);
            }
        }

    }

    private static class Hydrogen extends Thread
    {

        @Override
        public void run()
        {
            // Access counters
            mutex.acquireUninterruptibly();

            // Update hydrogen count
            hydrogenCounter++;

            // If there are 2 hydrogens waiting and an oxygen waiting wake them up
            if (hydrogenCounter >= 2 && oxygenCounter >= 1)
            {
                hydrogenQueue.release(2);
                hydrogenCounter -= 2;
                oxygenQueue.release();
                oxygenCounter--;
            } else
            {
                // Otherwise relese the counters and wait
                mutex.release();
            }

            // If there were 2 or more hydrogens then we will be able to pass otherwise we will wait
            hydrogenQueue.acquireUninterruptibly();
            bond('H');

            // Wait a the barrier
            try
            {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex)
            {
                System.err.println("Exception on wait");
                System.exit(1);
            }
        }

    }

}
