package edu.eiu.animalsimulator.animals;
import edu.eiu.animalsimulator.Position;


public class Mouse extends Animal {

    private enum State{West,North}
    private State state = State.West;

    /** Simulates the movement of a Mouse ( 1 step West 1 step North. Zig, zag to the northwest)
     * @return Position.
     */
    public Position getMove(){

        switch (state)
        {
            case West:
                state = State.North;
                return new Position(-1,0);

                default:
                    state = State.West;
                    return new Position(0,-1);
        }
    }

    public String toString(){return "M";}
}
