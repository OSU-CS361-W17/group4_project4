package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;

/**
 * Created by geoscow on 3/3/17.
 */
public class CivilianShip extends Ship {
    public CivilianShip(String n, int l, Coordinate s, Coordinate e) {
        super(n, l, s, e); //calls constructor of Ship
    }


    //adds all coordinates of the ship to the hit list,
    //only for civilian ships
    @Override
    public void shipHit(Coordinate c, ArrayList<Coordinate> list) {
        //horizontal
        if (this.isHorizantal()) {
            Coordinate temp = start;
            do {
                list.add(temp);
                temp = new Coordinate(temp.getRow(),temp.getCol()+1);
            } while(temp.getCol() <= end.getCol());
        }

        //vertical
        else {
            Coordinate temp = start;
            do {
                list.add(temp);
                temp = new Coordinate(temp.getRow()+1, temp.getCol());
            } while(temp.getRow() <= end.getRow());
        }
    }
}
