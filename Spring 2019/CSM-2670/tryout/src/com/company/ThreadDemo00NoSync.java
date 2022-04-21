package com.company;
public class ThreadDemo00NoSync
{

    private static final int N = 100;

    synchronized static void down()
    {
        for (int count = -1; count >= -N; count--)
        {
            System.out.print(count + " ");
        }
        System.out.println();
    }

    synchronized static void up()
    {
        for (int count = 1; count <= N; count++)
        {
            System.out.print(count + " ");
        }
        System.out.println();
    }

    private static class WorkerThread extends Thread
    {

        @Override
        public void run()
        {
            up();
        }
    }

    public static void main(String[] args)
    {
        // Create a new thread
        WorkerThread bob = new WorkerThread();
        bob.start();

        down();

        // Do some work of our own

    }
}
