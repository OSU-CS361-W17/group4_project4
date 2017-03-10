package edu.oregonstate.cs361.battleship;

/**
 * Created by michaelhilton on 1/8/17.
 */
public class Coordinate {
    private int Across;
    private int Down;

    public Coordinate(int row, int col) {
        Across = row;
        Down = col;
    }

    public boolean sameCoordinateTest(Coordinate c) {
        if (c.getRow() == this.Across && c.getCol() == this.Down) {
            return true;
        }

        return false;
    }

    public int getCol() {
        return Down;
    }

    public int getRow() {
        return Across;
    }
}
