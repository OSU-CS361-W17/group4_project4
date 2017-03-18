package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by michaelhilton on 2/7/17.
 */
class BattleshipModelTest {

    @Test
    void getShip() {
        BattleshipModel model = new BattleshipModel();
        assertEquals("AircraftCarrier",model.getShip("AircraftCarrier").getName());
        assertEquals("Battleship",model.getShip("battleship").getName());
        assertEquals("Clipper",model.getShip("Clipper").getName());
        assertEquals("Dinghy",model.getShip("Dinghy").getName());
        assertEquals("Submarine",model.getShip("Submarine").getName());
        assertNull(model.getShip("SS Minnow"));
    }

    Boolean testIfCovers(BattleshipModel model,String shipName, String row, String col, String orientation,int intRow, int intCol){
      return  model.placeShip(shipName,row,col,orientation).getShip(shipName).covers(new Coordinate(intRow,intCol));
    }

    @Test
    void placeShip() {
        BattleshipModel model = new BattleshipModel();
        assertEquals(true,
                testIfCovers(model, "AircraftCarrier","1","1","horizontal",1,1));
        assertEquals(true,
                testIfCovers(model, "AircraftCarrier","1","1","vertical",1,1));
        assertEquals(false,
                testIfCovers(model, "AircraftCarrier","1","1","horizontal",9,9));
        assertEquals(false,
                testIfCovers(model, "AircraftCarrier","1","1","vertical",9,9));

        assertEquals(true,
                    testIfCovers(model, "Battleship","1","1","horizontal",1,1));
        assertEquals(true,
                testIfCovers(model, "Battleship","1","1","vertical",1,1));
        assertEquals(false,
                testIfCovers(model, "Battleship","1","1","horizontal",9,9));
        assertEquals(false,
                testIfCovers(model, "Battleship","1","1","vertical",9,9));

        assertEquals(true,
                testIfCovers(model, "Clipper","1","1","horizontal",1,1));
        assertEquals(true,
                testIfCovers(model, "Clipper","1","1","vertical",1,1));
        assertEquals(false,
                testIfCovers(model, "Clipper","1","1","horizontal",9,9));
        assertEquals(false,
                testIfCovers(model, "Clipper","1","1","vertical",9,9));

        assertEquals(true,
                testIfCovers(model, "Dinghy","1","1","horizontal",1,1));
        assertEquals(true,
                testIfCovers(model, "Dinghy","1","1","vertical",1,1));
        assertEquals(false,
                testIfCovers(model, "Dinghy","1","1","horizontal",9,9));
        assertEquals(false,
                testIfCovers(model, "Dinghy","1","1","vertical",9,9));

        assertEquals(true,
                testIfCovers(model, "Submarine","1","1","horizontal",1,1));
        assertEquals(true,
                testIfCovers(model, "Submarine","1","1","vertical",1,1));
        assertEquals(false,
                testIfCovers(model, "Submarine","1","1","horizontal",9,9));
        assertEquals(false,
                testIfCovers(model, "Submarine","1","1","vertical",9,9));

        assertNull(model.placeShip("Submarine","1","1","horizontal").getShip("USS Minnow"));


    }

    //Name change should look back in Coordinate
    @Test
    void shootAtComputer() {
        BattleshipModel model = new BattleshipModel();
        model.shootAtComputer(1,1) ;
        assertEquals(true, model.computerHits.isEmpty());

        model.shootAtComputer(2,3) ;
        assertEquals(2, model.computerHits.get(0).getRow());
        assertEquals(3, model.computerHits.get(0).getCol());

        model.shootAtComputer(6,8) ;
        assertEquals(6, model.computerHits.get(1).getRow());
        assertEquals(8, model.computerHits.get(1).getCol());

        model.shootAtComputer(4,4) ;
        assertEquals(4, model.computerHits.get(2).getRow());
        assertEquals(4, model.computerHits.get(2).getCol());

        model.shootAtComputer(7,3) ;
        assertEquals(7, model.computerHits.get(3).getRow());
        assertEquals(3, model.computerHits.get(3).getCol());

        model.shootAtComputer(9,6) ;
        assertEquals(9, model.computerHits.get(4).getRow());
        assertEquals(6, model.computerHits.get(4).getCol());
    }


    @Test
    void shootAtPlayer() {
        BattleshipModel model = new BattleshipModel();
        model.placeShip("Aircraftcarrier","1","5","horizontal");
        model.placeShip("Battleship","2","4","horizontal");
        model.placeShip("Cruiser","3","3","horizontal");
        model.placeShip("Destroyer","4","2","horizontal");
        model.placeShip("Submarine","5","1","horizontal");

        model.playerShot(new Coordinate(9,9));
        assertEquals(true, model.playerHits.isEmpty());

        model.playerShot(new Coordinate(1,5));
        assertEquals(1, model.playerHits.get(0).getRow());
        assertEquals(5, model.playerHits.get(0).getCol());

        model.playerShot(new Coordinate(2,4));
        assertEquals(2, model.playerHits.get(1).getRow());
        assertEquals(4, model.playerHits.get(1).getCol());

        model.playerShot(new Coordinate(3,3));
        assertEquals(3, model.playerHits.get(2).getRow());
        assertEquals(3, model.playerHits.get(2).getCol());

        model.playerShot(new Coordinate(4,2));
        assertEquals(4, model.playerHits.get(3).getRow());
        assertEquals(2, model.playerHits.get(3).getCol());

        model.playerShot(new Coordinate(5,1));
        assertEquals(5, model.playerHits.get(4).getRow());
        assertEquals(1, model.playerHits.get(4).getCol());
    }

    @Test
    void testsScan() {
        BattleshipModel model = new BattleshipModel();
        model.scan(2,2);
        assertEquals(true,model.getScanResult());

        model.scan(6,6);
        assertEquals(false,model.getScanResult());

        model.shootAtComputer(1,1) ;
        assertEquals(true, model.computerHits.isEmpty());

        model.shootAtComputer(2,3) ;
        assertEquals(2, model.computerHits.get(0).getRow());
        assertEquals(3, model.computerHits.get(0).getCol());

        model.shootAtComputer(6,8) ;
        assertEquals(6, model.computerHits.get(1).getRow());
        assertEquals(8, model.computerHits.get(1).getCol());

        model.shootAtComputer(4,4) ;
        assertEquals(4, model.computerHits.get(2).getRow());
        assertEquals(4, model.computerHits.get(2).getCol());

        model.shootAtComputer(7,3) ;
        assertEquals(7, model.computerHits.get(3).getRow());
        assertEquals(3, model.computerHits.get(3).getCol());

        model.shootAtComputer(9,6) ;
        assertEquals(9, model.computerHits.get(4).getRow());
        assertEquals(6, model.computerHits.get(4).getCol());
    }





}
