package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;

/**
 * Created by geoscow on 3/3/17.
 */
public class CivilianShip extends Ship {
    public CivilianShip(String n, int l, Coordinate s, Coordinate e) {
        super(n, l, s, e); //calls constructor of Ship
    }

    public boolean covers(Coordinate test, ArrayList<Coordinate> playerHits) {
        //horizontal
        if(start.getAcross() == end.getAcross()){
            if(test.getAcross() == start.getAcross()){
                if((test.getDown() >= start.getDown()) &&
                        (test.getDown() <= end.getDown())) {
                    sinkShip(playerHits);
                    return true;
                }
            } else {
                return false;
            }
        }
        //vertical
        else{
            if(test.getDown() == start.getDown()){
                if((test.getAcross() >= start.getAcross()) &&
                        (test.getAcross() <= end.getAcross())) {
                    sinkShip(playerHits);
                    return true;
                }
            } else {
                return false;
            }

        }
        return false;
    }

    //adds all coordinates of the ship to the hit list,
    //only for civilian ships
    private void sinkShip(ArrayList<Coordinate> playerHits) {
        //horizontal
        if (start.getAcross() == end.getAcross()) {
            Coordinate temp = start;
            do {
                playerHits.add(temp);
                temp = new Coordinate(temp.getAcross(),temp.getDown()+1);
            } while(temp.getDown() <= end.getDown());
        }

        //vertical
        else {
            Coordinate temp = start;
            do {
                playerHits.add(temp);
                temp = new Coordinate(temp.getAcross()+1, temp.getDown());
            } while(temp.getAcross() <= end.getAcross());
        }
    }
}
