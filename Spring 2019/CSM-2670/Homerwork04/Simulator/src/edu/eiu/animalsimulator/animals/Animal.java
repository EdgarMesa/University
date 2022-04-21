package edu.eiu.animalsimulator.animals;

import edu.eiu.animalsimulator.Position;
import edu.eiu.animalsimulator.RandomGenerator;

public abstract class Animal {
    private final Position position;

    public Animal() {
        // Start at a random position
        position = new Position(RandomGenerator.getRandomGenerator().nextInt(),
                                RandomGenerator.getRandomGenerator().nextInt());
    }

    protected abstract Position getMove();

    public void makeMove()
    {
        position.move(getMove());
    }

    public Position getPosition() {
        return position;
    }
}
