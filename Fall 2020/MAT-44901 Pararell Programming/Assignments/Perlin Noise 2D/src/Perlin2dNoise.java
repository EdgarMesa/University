import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.util.concurrent.ConcurrentLinkedDeque;


public class Perlin2dNoise {

    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 2048;
    private static final int MIN_THREADS = 1;
    private static final int MAX_THREADS = 32;
    private static final int MIN_IMAGES = 1;
    private static final int MAX_IMAGES = 200;
    private static final String[] MODES = new String[] {"RowStride", "BlockStride", "PixelStride", "NextFreeRow", "NextFreePixel", "NextFreeBlock"};
    private static final int FREQUENCY = 26;

    private static final ConcurrentLinkedDeque<Integer> openRowsQueue = new ConcurrentLinkedDeque<>();
    private static final ConcurrentLinkedDeque<Integer> openBlocksQueue = new ConcurrentLinkedDeque<>();
    private static final ConcurrentLinkedDeque<Integer> openPixelsQueue = new ConcurrentLinkedDeque<>();

    public static int BLOCK_SIZE = 0;


    public static void main(String [] args)
    {

        if (args.length != 4)
        {
            printUsage("Must have 4 command line arguments.");
            System.exit(1);

        }


        int size, num_images, numberOfThreads;
        String mode;

        try{
            size = parseInt(args[0], "size", MIN_SIZE, MAX_SIZE);
            num_images = parseInt(args[1], "number of images", MIN_IMAGES, MAX_IMAGES);
            numberOfThreads = parseInt(args[2], "threads", MIN_THREADS, MAX_THREADS);
            mode = parseCorrectMode(args[3], "mode");

        } catch (NumberFormatException ex)
        {
         printUsage(ex.getMessage());
         System.exit(2);
         return;
        }

        // create the Queues for the next free row, block, pixel
        BLOCK_SIZE = size / (numberOfThreads * 5);

        // Make space for the image
        List<int[]> imagesData = new LinkedList<>();
        for (int i = 0; i < num_images; i++)
        {
            int[] ImageData = new int[size * size];
            imagesData.add(ImageData);
        }

        final Stopwatch watch = new Stopwatch();

        for (int[] imageData : imagesData)
        {

            if (numberOfThreads != 1) {
                if (mode.equalsIgnoreCase("NextFreeRow") || mode.equalsIgnoreCase("NextFreePixel") || mode.equalsIgnoreCase("NextFreeBlock"))
                {
                    createDequeues(size, mode);
                }

                final List<NoiseDrawingThread> noiseDrawingThreads = new LinkedList<>();
                for (int threadNumber = 0; threadNumber < numberOfThreads; threadNumber++)
                {
                    NoiseDrawingThread thread = new NoiseDrawingThread(imageData, threadNumber, numberOfThreads, size, mode);
                    noiseDrawingThreads.add(thread);
                    thread.start();
                }


                // Wait for the threads to be done
                for (NoiseDrawingThread t : noiseDrawingThreads)
                {
                    try
                    {
                        t.join();
                    } catch (InterruptedException ex)
                    {
                        System.err.println("Execution was Interrupted!");
                    }
                }

            } else
                {
                pixelStride(0, imageData, size, true, 1);

                }

        }
        double time = watch.elapsedTime();
        String message = String.format("Drawing took %f seconds for %d threads\n", time);
        appendToFile("src\\time.txt", message);
        System.out.printf("Drawing took %f seconds for %d threads\n", time, numberOfThreads);



        for(int[] imageData : imagesData)
        {
            displayImage(imageData, size);
        }


    }

    // Clear and recalculate the queues.
    public static void createDequeues(int size, String model)
    {

        if (model.equalsIgnoreCase("nextfreerow")) {
            openRowsQueue.clear();
            for (int i = 0; i < size; i++) {

                openRowsQueue.add(i);
            }
        }

        else if (model.equalsIgnoreCase("nextfreeblock")) {
            openBlocksQueue.clear();
            for(int i = 0; i < size; i += BLOCK_SIZE) {

                openBlocksQueue.add(i);
            }
        }

        else{
                openPixelsQueue.clear();
                for (int i = 0; i < size * size; i++) {

                    openPixelsQueue.add(i);
            }
        }
    }

    // Function to append scores to the time.txt file
    public static void appendToFile(String fileName, String str)
    {
        try {

            // Open given file in append mode.
            BufferedWriter file = new BufferedWriter(
                    new FileWriter(fileName, true));
            file.write(str);
            file.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    // display the image that the threads already created
    private static void displayImage(int[] imageData, int size)
    {
        SwingUtilities.invokeLater(() ->
        {
            // Make a frame
            JFrame f = new JFrame("Perlin 2D Noise");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(size, size);

            // Add the drawing panel
            DrawingPanel panel = new DrawingPanel(imageData, size);
            f.add(panel);
            panel.setPreferredSize(new Dimension(size, size));
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            f.pack();
            // Sets the location of the Frame in the center of the screen
            f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
            f.setResizable(false);
            f.setVisible(true);
        });
    }

    // Function that creates the noise effect, it takes as input the row and column of the image.
    // It always give the same outputs for the same inputs.
    private static double noise(double x, double y)
    {
        // select what rectangle is the perlin noise method pointing by eliminating the decimal part of our input x and y
        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;
        // Gradient vectors using random permutation for all corners of the rectangle
        int g1 = p[p[xi] + yi];
        int g2 = p[p[xi + 1] + yi];
        int g3 = p[p[xi] + yi + 1];
        int g4 = p[p[xi + 1] + yi + 1];

        // Gets the distance from the cell that we are to each gradient vector
        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);

        // Calculates the dot product between the distance vector and the gradient vector.
        double d1 = grad(g1, xf, yf);
        double d2 = grad(g2, xf - 1, yf);
        double d3 = grad(g3, xf, yf - 1);
        double d4 = grad(g4, xf - 1, yf - 1);

        // Interpolation for final cell value and fade function to add extra smoothness
        double u = fade(xf);
        double v = fade(yf);
        double x1Inter = interpolation(u, d1, d2); //Interpolation the x axis
        double x2Inter = interpolation(u, d3, d4);

        return interpolation(v, x1Inter, x2Inter);

    }

    // Linear interpolation method
    private static double interpolation(double amount, double left, double right)
    {
        return ((1 - amount) * left + amount * right);
    }

    // Fade function
    private static double fade(double t)
    {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    // Function to calculate the dot product for each vector
    private static double grad(int hash, double x, double y)
    {
        switch(hash & 3)
        {
            case 0: return x + y;
            case 1: return -x + y;
            case 2: return x - y;
            case 3: return -x - y;
            default: return 0;
        }
    }

    // Permutation table, this is why we do not need a random function for perlin noise
    static final int[] p = new int[512];
    static final int[] permutation = { 151,160,137,91,90,15,
            131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
            190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
            88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
            77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
            102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
            135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
            5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
            223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
            129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
            251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
            49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
            138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
    };
    static { for (int i=0; i < 256 ; i++) p[256+i] = p[i] = permutation[i]; }

    // Print error message and print usage rules
    private static void printUsage(String error)
    {
        System.out.println(error);
        System.err.printf("\tsize: the height and width for the image [%d, %d]\n", MIN_SIZE, MAX_SIZE);
        System.err.printf("\tthreads: the number of threads to use [%d, %d]\n", MIN_IMAGES, MAX_IMAGES);
        System.err.printf("\tthreads: the number of threads to use [%d, %d]\n", MIN_THREADS, MAX_THREADS);
        System.err.printf("\tmode: the different modes to choose are %s:\n", Arrays.toString(MODES));

    }

    // Parses the integers
    private static int parseInt(String s, String name, int min, int max)
        {
            final int result;
            try {
                result = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                throw new NumberFormatException(String.format("Value, %s, given for %s is not a number", s, name));
            }

            if (result < min || result > max) {
                throw new NumberFormatException(String.format("Value, %d, given for %s is not in the range [%d, %d]",
                        result, name, min, max));
            }

            return result;
        }

    // Will make sure that the mode introduced is one of the list
    private static String parseCorrectMode(String mode, String name)
    {
     mode = mode.toLowerCase();

     for(String mod : MODES)
     {
         if (mod.toLowerCase().equals(mode)){return mode;}
     }

     throw new NumberFormatException(String.format("Value %s, given for %s is not in the list of possible modes: %s", mode, name, Arrays.toString(MODES)));
    }

    // Will create the corresponding noise color from a given x and y coordinate
    private static int noiseColor(int row, int column, int size)
     {
         double dx = (double) column / size;
         double dy = (double) row / size;
         double noise = noise((dx * FREQUENCY), (dy * FREQUENCY));
         noise = (noise - 1) / 2; // change the range from 0 to FREQUENCY
         int b = (int)(noise * 0xFF);
         int g = b * 0x100;
         int r = b * 0x10000;
         return r + g +b;
     }

     private static void rowStride(int startingRow, int[] imageData, int size, boolean running, int numberOfThreads)
     {

         // Keep drawing rows as long as we have rows to paint
         for (int row = startingRow; running && row < size; row += numberOfThreads)
         {
             for (int column = 0; column < size; column++)
             {
//                 System.out.printf("THREAD: %d ROW: %d  COLUMN: %d\n",startingRow, row, column);
//                 System.out.println(row * size + column);
                 imageData[row * size + column] = noiseColor(row, column, size);
             }
         }
     }
    private static void pixelStride(int startingRow, int[] imageData, int size, boolean running, int numberOfThreads)
    {
        // Keep drawing rows as long as we have rows to paint
        for (int row = 0; running && row < size; row++)
        {
            for (int column = startingRow; column < size; column += numberOfThreads)
            {
//                System.out.printf("THREAD: %d ROW: %d  COLUMN: %d\n",startingRow, row, column);
//                System.out.println(row * size + column);
                imageData[row * size + column] = noiseColor(row, column, size);
            }
        }
    }

    private static void blockStride(int startingRow, int[] imageData, int size, boolean running, int numberOfThreads)
    {
        int row;


        for (int block = startingRow * BLOCK_SIZE; running && block < size; block = row + ((numberOfThreads- 1)  * BLOCK_SIZE))
            {
//                System.out.printf("Thread: %d   block = %d\n", startingRow, block);
                for(row = block; row < size && row < block + BLOCK_SIZE; row++)
                {

                    for(int column = 0; column < size; column++)

                    {
//                        String s = String.format("THREAD: %d ROW: %d  COLUMN: %d\n"+row * size + column+ "\n",startingRow, row, column);
//                        appendToFile("src\\time.txt", s);

                        imageData[row * size + column] = noiseColor(row, column, size);
                    }

                }
            }

    }

    private static void nextFreeRow(int[] imageData, int size, boolean running)
    {
        while (!openRowsQueue.isEmpty() && running)
        {
            int row;
            synchronized (openRowsQueue)
            {
                if (openRowsQueue.peekFirst() == null)
                {
                    continue;
                }else{row = openRowsQueue.pollFirst();}
            }
            for (int column = 0; column < size; column++)
            {
//             System.out.printf("THREAD: %d ROW: %d  COLUMN: %d\n",startingRow, row, column);
//             System.out.println(row * size + column);
                imageData[row * size + column] = noiseColor(row, column, size);

            }
        }


    }

    private static void nextFreePixel(int[] imageData, int size, boolean running) {

        int pixel;
        while (!openPixelsQueue.isEmpty() && running)
        {

            synchronized (openPixelsQueue)
            {
                if (openPixelsQueue.peekFirst() == null)
                {
                    continue;
                } else {
                    pixel = openPixelsQueue.pollFirst();
                }
            }

            int row;
            int column;

//              System.out.printf("THREAD: %d ROW: %d  COLUMN: %d\n",startingRow, row, column);
//              System.out.println(row * size + column);
            row = pixel / size;
            column = pixel % size;
            imageData[pixel] = noiseColor(row, column, size);

        }
    }

    private static void nextFreeBlock(int[] imageData, int size, boolean running) {

        int block;
        while (!openBlocksQueue.isEmpty() && running)
        {
            synchronized (openBlocksQueue)
            {
                if (openBlocksQueue.peekFirst() == null) {
                    continue;
                } else {
                    block = openBlocksQueue.pollFirst();
                }
            }

//          System.out.printf("Thread: %d   block = %d\n", startingRow, block);
            for (int row = block; row < size && row < block + BLOCK_SIZE; row++)
            {

                for (int column = 0; column < size; column++) {
//                     System.out.printf("THREAD: %d ROW: %d  COLUMN: %d\n",startingRow, row, column);
//                     System.out.println(row * size + column);

                    imageData[row * size + column] = noiseColor(row, column, size);
                }
            }
        }


    }

     private static class NoiseDrawingThread extends Thread
         {
             private volatile boolean running = true;
             private final int numberOfThreads, size, startingRow;
             private final int[] imageData;
             private final String mode;

             public NoiseDrawingThread(int[] imageData, int startingRow, int numberOfThreads, int size, String mode)
             {
                 super("Noise Drawing Thread: " + startingRow + "/" + (numberOfThreads - 1));
                 this.imageData = imageData;
                 this.startingRow = startingRow;
                 this.numberOfThreads = numberOfThreads;
                 this.size = size;
                 this.mode = mode;

             }

             public void stopRunning()
             {
                 running = false;
             }

             @Override
             public void run()
             {
                 switch (mode) {
                     case "rowstride":
                         rowStride(startingRow, imageData, size, running, numberOfThreads);
                         break;
                     case "blockstride":
                         blockStride(startingRow, imageData, size, running, numberOfThreads);
                         break;
                     case "pixelstride":
                         pixelStride(startingRow, imageData, size, running, numberOfThreads);
                         break;
                     case "nextfreerow":
                         nextFreeRow(imageData, size, running);
                         break;
                     case "nextfreepixel":
                         nextFreePixel(imageData, size, running);
                         break;
                     case "nextfreeblock":
                         nextFreeBlock(imageData, size, running);
                         break;
                     default:
                         throw new IllegalStateException("Unexpected value: " + mode);
                 }
             }
         }

    private static class DrawingPanel extends JPanel
        {

            public final BufferedImage image;

            public DrawingPanel(int[] imageData, int size)
            {
                image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
                // Changes the pixel of the image so it has the new noise value.
                for(int y = 0; y < size; y++)
                    {
                       for(int x = 0; x < size; x++)
                        {
                            image.setRGB(x, y, imageData[y * size + x]);
                        }
                    }
            }

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        }
}