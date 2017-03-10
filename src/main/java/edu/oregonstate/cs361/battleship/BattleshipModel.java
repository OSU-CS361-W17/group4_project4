package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michaelhilton on 1/4/17.
 */
public class BattleshipModel {
    private static int MIN = 1;
    private static int MAX = 10;
    private MilitaryShip aircraftCarrier = new MilitaryShip("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    private StealthShip battleship = new StealthShip("Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    private CivilianShip clipper = new CivilianShip("Clipper",3, new Coordinate(0,0),new Coordinate(0,0));
    private CivilianShip dinghy = new CivilianShip("Dinghy",1, new Coordinate(0,0),new Coordinate(0,0));
    private StealthShip submarine = new StealthShip("Submarine",2, new Coordinate(0,0),new Coordinate(0,0));

    private MilitaryShip computer_aircraftCarrier = new MilitaryShip("Computer_AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    private StealthShip computer_battleship = new StealthShip("Computer_Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    private CivilianShip computer_clipper = new CivilianShip("Computer_Clipper",3, new Coordinate(0,0),new Coordinate(0,0));
    private CivilianShip computer_dinghy = new CivilianShip("Computer_Dinghy",1, new Coordinate(0,0),new Coordinate(0,0));
    private StealthShip computer_submarine = new StealthShip("Computer_Submarine",2, new Coordinate(0,0),new Coordinate(0,0));

    ArrayList<Coordinate> playerHits;
    private ArrayList<Coordinate> playerMisses;
    ArrayList<Coordinate> computerHits;
    private ArrayList<Coordinate> computerMisses;

    private ArrayList<Ship> playerShips;
    private ArrayList<Ship> computerShips;

    boolean scanResult = false;
    boolean isEasy = true;
    private String errorMessage = "none";



    public BattleshipModel() {
        placeComputerShips();
        playerHits = new ArrayList<>();
        playerMisses= new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses= new ArrayList<>();
    }


    public Ship getShip(String shipName) {
        if (shipName.equalsIgnoreCase("aircraftcarrier"))
            return aircraftCarrier;
        if(shipName.equalsIgnoreCase("battleship"))
            return battleship;
        if(shipName.equalsIgnoreCase("clipper"))
            return clipper;
        if(shipName.equalsIgnoreCase("dinghy"))
            return dinghy;
        if(shipName.equalsIgnoreCase("submarine"))
            return submarine;
        else
            return null;
    }

    public void createShipArrays() {
        //player ship array
        playerShips = new ArrayList<>();
        playerShips.add(aircraftCarrier);
        playerShips.add(battleship);
        playerShips.add(clipper);
        playerShips.add(dinghy);
        playerShips.add(submarine);

        //computer ship array
        computerShips = new ArrayList<>();
        computerShips.add(computer_aircraftCarrier);
        computerShips.add(computer_battleship);
        computerShips.add(computer_clipper);
        computerShips.add(computer_dinghy);
        computerShips.add(computer_submarine);
    }

    public void deleteShipArrays() {
        playerShips = null;
        computerShips = null;
    }

    private void placeComputerShips() {
        if(this.isEasy) {
            computer_aircraftCarrier.setLocation(new Coordinate(2,2), new Coordinate(2,6));
            computer_battleship.setLocation(new Coordinate(2,8), new Coordinate(5,8));
            computer_clipper.setLocation(new Coordinate(4,1), new Coordinate(4,3));
            computer_dinghy.setLocation(new Coordinate(7,3), new Coordinate(7,3));
            computer_submarine.setLocation(new Coordinate(9,6), new Coordinate(9,7));
        }

        else {
            //for (Ship s : computerShips) {

            //}
        }
    }

    public BattleshipModel placeShip(String shipName, String row, String col, String orientation) {
        Coordinate start = new Coordinate(Integer.parseInt(row), Integer.parseInt(col));
        Coordinate end;
        Ship ship  = getShip(shipName);

        if(orientation.equals("horizontal")) {
            end = new Coordinate(start.getRow(), start.getCol() + ship.getLength() - 1);
        } else {
            end = new Coordinate(start.getRow() + ship.getLength() - 1, start.getCol());
        }

        ship.setLocation(start, end);

        if(start.getRow() < this.MIN || start.getCol() < this.MIN
            || end.getRow() > this.MAX || end.getCol() > this.MAX || overlapLoop(ship, this.playerShips)) {
            this.errorMessage = "Cannot place ship here.";
            ship.removeCoordinates();
            return this;
        }

        return this;
    }

    private boolean overlapLoop(Ship test, ArrayList<Ship> ships) {
        for(Ship s: ships) {
            if(s.overlapTest(test))
                return true;
        }

        return false;
    }

    public boolean shootAtComputer(int row, int col) {
        Coordinate shot = new Coordinate(row, col);

        if (!validShotTest(shot)) {
            errorMessage = "This shot is invalid.";
            return true;
        }

        for(Ship s: computerShips) {
            if (s.covers(shot)) {
                s.shipHit(shot, computerHits);
                return true;
            }
        }

        computerMisses.add(shot);
        return false;
    }

    public boolean validShotTest(Coordinate shot) {
        //loops to check shot validity
        for(Coordinate c: computerHits) {
            if(shot.sameCoordinateTest(c))
                return false;
        }
        for(Coordinate c: computerMisses) {
            if(shot.sameCoordinateTest(c))
                return false;
        }
        if(shot.getRow() > this.MAX || shot.getRow() < this.MIN
                || shot.getCol() > this.MAX || shot.getCol() < this.MIN)
            return false;

        return true;
    }

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

    void playerShot(Coordinate coor) {
        if(playerMisses.contains(coor)){
            System.out.println("Dupe");

        }

        for(Ship s: playerShips) {
            if (s.covers(coor)) {
                s.shipHit(coor, playerHits);
                return;
            }
        }

        playerMisses.add(coor);
    }


    public void scan(int row, int col) {
        Coordinate c = new Coordinate(row, col);
        scanResult = false;
        for(Ship s: computerShips) {
            if (s.scan(c))
                scanResult = true;
        }
    }
}