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

    public int getCol() {
        return Down;
    }

    public int getRow() {
        return Across;
    }
}
