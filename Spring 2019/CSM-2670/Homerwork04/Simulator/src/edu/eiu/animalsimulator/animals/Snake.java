package edu.eiu.animalsimulator.animals;
import edu.eiu.animalsimulator.Position;


public class Snake extends Animal {

    private enum State{South,East}
    private State state = State.South;
    private int incre = 1;


    /** Simulates the movement of a Snake ("slithers"  left and right in increasing length)
     * @return Position.
     */
    protected Position getMove(){

        switch (state)
        {
            case South:
                state = State.East;
                return new Position(0,incre);

                default:
                    state = State.South;
                    Position position = new Position(incre,0);
                    incre++;
                    return position;

        }

    }
    public String toString(){return"S";}
}
