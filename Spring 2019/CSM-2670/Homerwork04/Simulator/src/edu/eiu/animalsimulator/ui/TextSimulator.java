package edu.eiu.animalsimulator.ui;

import edu.eiu.animalsimulator.World;

/**
 * An Animal simulator that displays its world as text on the console.
 */
public class TextSimulator {

    private static final int DEFAULT_SLEEP_TIME   = 500;
    private static final int DEFAULT_WORLD_WIDTH  =  10;
    private static final int DEFAULT_WORLD_HEIGHT =  10;

    /**
     * Entry point of the simulation.
     * @param args arg[0] is the width of the world, arg[1] the height, and arg[2] the sleep time. Defaults will be
     *             used for any omitted or invalid arguments. Other arguments will be ignored.
     */
    public static void main(String[] args)
    {
        // Check args
        final int worldWidth  = checkArg(args, 0, DEFAULT_WORLD_WIDTH, "width");
        final int worldHeight = checkArg(args, 1, DEFAULT_WORLD_HEIGHT, "height");
        final int sleepTime   = checkArg(args, 2, DEFAULT_SLEEP_TIME, "sleep time");

        // Build simulation
        World w = new World(worldWidth, worldHeight);

        // Run simulation until only one animal is left
        while(w.numberOfAnimals() > 1)
        {
            System.out.println(w);

            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException ex)
            {
                System.err.println("Sleep interrupted.");
            }

            w.update();
        }
    }

    /**
     * Converts args[index] into an int. On any error the default value is returned instead and an error message is
     * printed.
     */
    private static int checkArg(String[] args, int index, int defaultValue, String valueName)
    {
        try
        {
            return Integer.valueOf(args[index]);
        }
        catch(Exception ex)
        {
            System.err.printf("The default value of %d will be used for %s.\n", defaultValue, valueName);
            return defaultValue;
        }
    }
}
