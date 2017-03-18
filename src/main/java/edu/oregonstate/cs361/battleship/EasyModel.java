package edu.oregonstate.cs361.battleship;

import java.util.Random;

/**
 * Created by geoscow on 3/18/17.
 */
public class EasyModel extends BattleshipModel {
    @Override
    public void placeComputerShips() {
        computer_aircraftCarrier.setLocation(new Coordinate(2,2), new Coordinate(2,6));
        computer_battleship.setLocation(new Coordinate(2,8), new Coordinate(5,8));
        computer_clipper.setLocation(new Coordinate(4,1), new Coordinate(4,3));
        computer_dinghy.setLocation(new Coordinate(7,3), new Coordinate(7,3));
        computer_submarine.setLocation(new Coordinate(9,6), new Coordinate(9,7));
    }

    @Override
    public void shootAtPlayer() {
        Random random = new Random();
        Coordinate shot;

        do {
            int randRow = random.nextInt(this.MAX - this.MIN + 1) + this.MIN;
            int randCol = random.nextInt(this.MAX - this.MIN + 1) + this.MIN;
            shot = new Coordinate(randRow, randCol);
        } while (!validShotTest(shot));

        playerShot(shot);
    }
}
