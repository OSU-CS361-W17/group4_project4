package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michaelhilton on 1/4/17.
 */
public class BattleshipModel {
    protected static int MIN = 1;
    protected static int MAX = 10;
    protected MilitaryShip aircraftCarrier = new MilitaryShip("AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    protected StealthShip battleship = new StealthShip("Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    protected CivilianShip clipper = new CivilianShip("Clipper",3, new Coordinate(0,0),new Coordinate(0,0));
    protected CivilianShip dinghy = new CivilianShip("Dinghy",1, new Coordinate(0,0),new Coordinate(0,0));
    protected StealthShip submarine = new StealthShip("Submarine",2, new Coordinate(0,0),new Coordinate(0,0));

    protected MilitaryShip computer_aircraftCarrier = new MilitaryShip("Computer_AircraftCarrier",5, new Coordinate(0,0),new Coordinate(0,0));
    protected StealthShip computer_battleship = new StealthShip("Computer_Battleship",4, new Coordinate(0,0),new Coordinate(0,0));
    protected CivilianShip computer_clipper = new CivilianShip("Computer_Clipper",3, new Coordinate(0,0),new Coordinate(0,0));
    protected CivilianShip computer_dinghy = new CivilianShip("Computer_Dinghy",1, new Coordinate(0,0),new Coordinate(0,0));
    protected StealthShip computer_submarine = new StealthShip("Computer_Submarine",2, new Coordinate(0,0),new Coordinate(0,0));

    ArrayList<Coordinate> playerHits;
    protected ArrayList<Coordinate> playerMisses;
    ArrayList<Coordinate> computerHits;
    protected ArrayList<Coordinate> computerMisses;

    protected ArrayList<Ship> playerShips;
    protected ArrayList<Ship> computerShips;

    boolean scanResult = false;
    boolean gameInProgress = false;
    boolean playerWon = false;
    protected String errorMessage = "none";



    public BattleshipModel() {
        playerHits = new ArrayList<>();
        playerMisses= new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses= new ArrayList<>();
    }

    public void startGame() {
        placeComputerShips();
        gameInProgress = true;
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

    public void placeComputerShips() {

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

    protected boolean overlapLoop(Ship test, ArrayList<Ship> ships) {
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
                if(computerHits.size() == 15) {
                    playerWon = true;
                    gameInProgress = false;
                }
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

    }

    public void playerShot(Coordinate coor) {
        for(Ship s: playerShips) {
            if (s.covers(coor)) {
                s.shipHit(coor, playerHits);
                if(playerHits.size() == 15) {
                    playerWon = false;
                    gameInProgress = false;
                }
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