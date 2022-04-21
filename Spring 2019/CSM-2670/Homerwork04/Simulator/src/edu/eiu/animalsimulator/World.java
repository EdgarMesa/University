package edu.eiu.animalsimulator;

import edu.eiu.animalsimulator.animals.*;

import java.util.ArrayList;

/**
 * A 2D Grid World for our Animal objects to live in. Topologically the World is a torus (i.e. the top and bottom are
 * connected as are the left and right side of the grid).
 */
public class World {

    private final int worldWidth;
    private final int worldHeight;
    private ArrayList<Animal> animalList = new ArrayList<>();

    /**
     * Creates a Grid World of the given size and populates it with one of each type of animal. The animals will be
     * placed in random locations and may start colliding.
     * @param worldWidth the width of the grid (must be > 0)
     * @param worldHeight the height of the grid (must be > 0)
     */
    public World(int worldWidth, int worldHeight)
    {
        if(worldHeight <=0 || worldWidth <=0 ) throw new IllegalArgumentException("worldHeight and worldWidth must be >= 0");

        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;

        // Add one of each animal
        animalList.add(new Bird());
        animalList.add(new Frog());
        animalList.add(new Mouse());
        animalList.add(new Rabbit());
        animalList.add(new Snake());
        animalList.add(new Turtle());
        animalList.add(new Wolf());

        // Reduce the location of each animal so it fits in the world
        for(Animal a : animalList)
        {
            reducePosition(a.getPosition());
        }
    }

    /**
     * Ensure the given position fits in the current world.
     */
    private void reducePosition(Position p)
    {
        if(p == null) return;
        int x = p.getX() % worldWidth;
        int y = p.getY() % worldHeight;
        if(x < 0) x += worldWidth;
        if(y < 0) y += worldHeight;
        p.setX(x);
        p.setY(y);
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public int getWorldWidth() {
        return worldWidth;
    }


    @Override
    public String toString()
    {
        // Make a string builder with enough space to hold our world.
        StringBuilder sb = new StringBuilder((worldWidth + 1) * worldHeight);

        // Fill the string builder with dots
        for(int row = 0; row < worldHeight; row++) {
            for (int col = 0; col < worldWidth; col++) {
                sb.append('.');
            }
            sb.append('\n');
        }

        // Add a letter to the string for each animal
        for(Animal a : animalList)
        {
            char letter;
            try
            {
                letter = a.toString().charAt(0);
            }
            catch (IndexOutOfBoundsException ex)
            {
                System.err.println("Animal's toString returned an empty string, using E for its marker.");
                letter = 'E';
            }
            sb.setCharAt(a.getPosition().getX() + a.getPosition().getY() * (worldWidth + 1), letter);
        }

        return sb.toString();
    }

    public int numberOfAnimals()
    {
        return animalList.size();
    }

    /**
     * Update the world by moving each animal. In the case of a collision only one animal in that location will
     * survive to the next world state :(
     */
    public void update() {
        // Move each animal
        for(Animal a : animalList)
        {
            a.makeMove();
            reducePosition(a.getPosition());
        }

        // Look for any collisions
        ArrayList<Animal> aliveList = new ArrayList<>(animalList.size());
        boolean[] animalChecked = new boolean[animalList.size()];
        for(int animalIndex1 = 0; animalIndex1 < animalList.size(); animalIndex1++)
        {
            // If this animal has already been checked then there is nothing more to do.
            if(animalChecked[animalIndex1]) continue;

            // Otherwise find all of the animals it has collided with
            ArrayList<Animal> collisionList = new ArrayList<>(animalList.size());
            collisionList.add(animalList.get(animalIndex1));
            animalChecked[animalIndex1] = true;
            for(int animalIndex2 = animalIndex1 + 1; animalIndex2 < animalList.size(); animalIndex2++)
            {
                if(animalList.get(animalIndex1).getPosition().equals(animalList.get(animalIndex2).getPosition()))
                {
                    collisionList.add(animalList.get(animalIndex2));
                    animalChecked[animalIndex2] = true;
                }
            }

            // Pick one animal at random to live
            aliveList.add(RandomGenerator.getRandomGenerator().nextItem(collisionList));
        }

        // Keep only the animals that lived
        animalList = aliveList;
    }
}
