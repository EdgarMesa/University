package edu.eiu.animalsimulator.animals;
import edu.eiu.animalsimulator.Position;


public class Rabbit extends Animal {

    private enum State{North,East,South}
    private State state = State.North;

    /** Simulates the movement of a Rabbit (2 jumps to the North, 2 the East, to the South)
     * @return Position.
     */
    protected Position getMove(){

        switch (state)
        {
            case North:
                state = State.East;
                return new Position(0,-2);

                case East:
                    state = State.South;
                    return new Position(2,0);

                default:
                    state = State.North;
                    return new Position(0,2);
        }
    }

    public String toString(){return"R";}
}
