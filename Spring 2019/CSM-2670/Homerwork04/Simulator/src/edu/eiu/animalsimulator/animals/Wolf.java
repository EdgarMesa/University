package edu.eiu.animalsimulator.animals;
import edu.eiu.animalsimulator.Position;


public class Wolf extends Animal {

    private enum State{North,East}
    private State state = State.North;

    /** Simulates the movement of a Wolf (6 steps to the North, + steps to the east)
     * @return Position.
     */
    protected Position getMove(){
        switch (state)
        {
            case North:
                state = State.East;
                return new Position(0,-6);

                default:
                    state = State.North;
                    return new Position(4,0);
        }
    }

    public String toString(){return"W";}
}
