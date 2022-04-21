package edu.eiu.animalsimulator;

import java.util.Objects;

/**
 * Represents a location or amount of movement in the 2D simulation.
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
        this(0,0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Moves the receiver by the amount given in p. Nothing is done in the case p is null.
     * @param p amount to move the receiver by in each dimension.
     */
    public void move(Position p)
    {
        if(p == null) return;
        x += p.x;
        y += p.y;
    }

    /**
     * Two Positions are equal if they have the same x and y values.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
