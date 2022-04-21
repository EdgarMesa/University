package edu.eiu.animalsimulator.animals;

import edu.eiu.animalsimulator.Position;
import edu.eiu.animalsimulator.RandomGenerator;


import java.util.ArrayList;

 public class Bird extends Animal {


     /** Simulates the movement of a Bird (Moves randomly 1 steps in one of the four directions each time)
      * @return Position.
      */
    protected Position getMove(){

        ArrayList<Integer> l = new ArrayList<>(2);

        int r1 = RandomGenerator.getRandomGenerator().nextInt(2)*2-1;
        int r2 = RandomGenerator.getRandomGenerator().nextInt(2);
        l.add(0);
        l.add(0);
        l.set(r2,r1);
        return new Position(l.get(0),l.get(1));

    }

    public String toString(){return "B";}
}

