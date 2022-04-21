package edu.eiu.animalsimulator.animals;
import edu.eiu.animalsimulator.Position;


public class Turtle extends Animal {

    private enum State{South,West,North,East}
    private State state = State.South;

    /** Simulates the movement of a Turtle( clockwise box of length 5)
     * @return Position.
     */
    protected Position getMove(){
        switch (state)
        {
            case South:
                state = State.West;
                return new Position(0,5);
            case West:
                state = State.North;
                return new Position(-5,0);
            case North:
                state = State.East;
                return new Position(0,-5);

                default:
                    state = State.South;
                    return new Position(5,0);
        }
    }

    public String toString(){return "T";}
}
