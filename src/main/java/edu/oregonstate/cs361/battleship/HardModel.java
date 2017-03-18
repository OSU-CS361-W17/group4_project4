package edu.oregonstate.cs361.battleship;

import java.util.Random;

/**
 * Created by geoscow on 3/18/17.
 */
public class HardModel extends BattleshipModel {
    @Override
    public void placeComputerShips() {
        Random random = new Random();
        Coordinate start, end;
        boolean placed = false;

        for (Ship s : computerShips) {
            do {
                int randRow = random.nextInt(this.MAX - this.MIN + 1) + this.MIN;
                int randCol = random.nextInt(this.MAX - this.MIN + 1) + this.MIN;
                start = new Coordinate(randRow, randCol);
                //horizontal
                if(((randRow+randCol+randRow) % 2) == 0) {
                    end = new Coordinate(start.getRow(), start.getCol() + s.getLength() - 1);
                }
                //vertical
                else {
                    end = new Coordinate(start.getRow() + s.getLength() - 1, start.getCol());
                }

                s.setLocation(start, end);
                if(start.getRow() >= this.MIN && start.getCol() >= this.MIN
                        && end.getRow() <= this.MAX && end.getCol() <= this.MAX
                        && !overlapLoop(s, this.computerShips)) {
                    placed = true;
                }
            } while (!placed);

            placed = false;
        }
    }

    //this is where the smart AI will need to go, Danny should implement this here
    @Override
    public void shootAtPlayer() {
        //Coordinate shot = null;
        this.errorMessage = "smart AI shoots";
        //playerShot(shot);
    }


}
