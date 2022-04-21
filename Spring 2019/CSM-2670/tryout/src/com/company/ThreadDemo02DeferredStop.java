package com.company;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import static java.lang.Thread.sleep;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ThreadDemo02DeferredStop extends JFrame
{

    private final JTextArea outputArea = new JTextArea();
    private int threadCount = 0;
    private final List<SpamThread> threadList = new LinkedList<SpamThread>();

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> new ThreadDemo02DeferredStop().setVisible(true));
    }

    public final void println(String s)
    {
        outputArea.setText(s + "\n" + outputArea.getText());
        outputArea.setCaretPosition(0);
    }

    public ThreadDemo02DeferredStop()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(600, 600));
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        JButton startButton = new JButton("start");
        startButton.addActionListener((ActionEvent e) ->
        {
            SpamThread thread = new SpamThread();
            threadList.add(thread);
            thread.start();
        });
        JButton stopButton = new JButton("stop");
        stopButton.addActionListener((ActionEvent e) ->
        {
            if (!threadList.isEmpty())
            {
                SpamThread thread = threadList.remove(0);
                thread.stopRunning();
                println("Thread " + thread.threadNumber + ": asked to stop");
            }
        });
        topPanel.add(startButton);
        topPanel.add(stopButton);
        add(topPanel, BorderLayout.NORTH);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));
    }

    private class SpamThread extends Thread
    {

        private int threadNumber;
        private boolean running = true;

        public boolean keepRunning()
        {
            return running;
        }

        public void stopRunning()
        {
            running = false;
        }

        public SpamThread()
        {
            threadNumber = threadCount++;
        }

        @Override
        public void run()
        {
            while (keepRunning())
            {
                println("Thread " + threadNumber + ": still running.");
                try
                {
                    sleep(1000);
                }
                catch (InterruptedException ex)
                {
                }
            }
            println("Thread " + threadNumber + ": ending!");
        }
    }
}
