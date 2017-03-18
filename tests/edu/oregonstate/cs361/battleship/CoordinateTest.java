package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by michaelhilton on 2/7/17.
 */
class CoordinateTest {
    @Test
    void getCol() {
        Coordinate c = new Coordinate(1,2);
        assertEquals(2,c.getCol());
    }

    
    }

    @Test
    void getRow() {
        Coordinate c = new Coordinate(1,2);
        assertEquals(1,c.getAcross());
    }

   /* @Test
    void setAcross() {
        Coordinate c = new Coordinate(1,1);
        c.setAcross(9);
        assertEquals(9,c.getAcross());
    }
    @Test
    void setDown() {
        Coordinate c = new Coordinate(1,1);
        c.setDown(9);
        assertEquals(9,c.getCol());*/

}