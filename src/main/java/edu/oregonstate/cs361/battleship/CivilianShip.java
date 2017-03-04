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
        if (start.getAcross() == end.getAcross()) {
            Coordinate temp = start;
            do {
                list.add(temp);
                temp = new Coordinate(temp.getAcross(),temp.getDown()+1);
            } while(temp.getDown() <= end.getDown());
        }

        //vertical
        else {
            Coordinate temp = start;
            do {
                list.add(temp);
                temp = new Coordinate(temp.getAcross()+1, temp.getDown());
            } while(temp.getAcross() <= end.getAcross());
        }
    }
}
