package edu.eiu.animalsimulator;

import java.util.Random;
import java.util.List;

/**
 * This class creates an object factory method for generating a single RandomGenerator while also adding a few new
 * methods.
 */
public class RandomGenerator extends Random {
    private static RandomGenerator rand;

    /**
     * Returns the same RandomGenerator object for each call. This can be used to ensure only one generator is used.
     */
    public static RandomGenerator getRandomGenerator()
    {
        if(rand == null) rand = new RandomGenerator();
        return rand;
    }

    /**
     * Returns a random int in the inclusive range [start, end] where start <= end.
     */
    public int nextInt(int start, int end)
    {
        if(start > end) throw new IllegalArgumentException("Start must be <= end");
        return nextInt(end - start + 1) + start;
    }

    /**
     * Returns a random double in the inclusive range [start, end] where start <= end.
     */
    public double nextDouble(double start, double end)
    {
        if(start > end) throw new IllegalArgumentException("Start must be <= end");
        return nextDouble() * (end - start) + start;
    }

    /**
     * Returns a random item from a list.
     */
    public <T> T nextItem(List<T> list)
    {
        return list.get(nextInt(list.size()));
    }

    /**
     * Returns a random item from an array.
     */
    public <T> T nextItem(T[] array)
    {
        return array[nextInt(array.length)];
    }

}
