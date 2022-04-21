package edu.eiu.animalsimulator.animals;
import edu.eiu.animalsimulator.Position;
import edu.eiu.animalsimulator.RandomGenerator;

import java.util.ArrayList;


public class Frog extends Animal {

    protected   ArrayList<Integer> l = new ArrayList<>(2);
    protected int r2 = RandomGenerator.getRandomGenerator().nextInt(2);
    protected int r1 = RandomGenerator.getRandomGenerator().nextInt(2)*6-3;


    /** Simulates the movement of a Frog (Moves randomly 3 steps in one of the four directions)
     * @return Position.
     */
    protected Position getMove(){
        l.add(0);
        l.add(0);
        l.set(r2,r1);
        return new Position(l.get(0),l.get(1));

    }

    public String toString(){return "F";}
}
