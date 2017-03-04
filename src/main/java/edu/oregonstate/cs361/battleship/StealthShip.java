package edu.oregonstate.cs361.battleship;

/**
 * Created by geoscow on 3/3/17.
 */

//class for ships that are undetectable by scan
public class StealthShip extends MilitaryShip {
    public StealthShip(String n, int l, Coordinate s, Coordinate e) {
        super(n, l, s, e); //calls constructor of Ship
    }

    @Override
    public boolean scan(Coordinate coor) {
        return false;
    }
}