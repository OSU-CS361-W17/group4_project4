package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;

/**
 * Created by michaelhilton on 1/5/17.
 */
public class Ship {
    private String name;
    private int length;
    protected Coordinate start;
    protected Coordinate end;

    protected Ship(String n, int l,Coordinate s, Coordinate e) {
        name = n;
        length = l;
        start = s;
        end = e;
    }

    public void setLocation(Coordinate s, Coordinate e) {
        start = s;
        end = e;

    }

    //returns a list of points to be marked as hits, or null if
    public boolean covers(Coordinate test) {
        //horizontal
        if(start.getRow() == end.getRow()){
            if(test.getRow() == start.getRow()){
                if((test.getCol() >= start.getCol()) &&
                        (test.getCol() <= end.getCol()))
                    return true;
            } else {
                return false;
            }
        }
        //vertical
        else{
            if(test.getCol() == start.getCol()){
                if((test.getRow() >= start.getRow()) &&
                        (test.getRow() <= end.getRow()))
                    return true;
            } else {
                return false;
            }

        }
        return false;
    }

    public void shipHit(Coordinate c, ArrayList<Coordinate> list) {
        list.add(c);
    }

    public String getName() {
        return name;
    }
    public int getLength() { return length; }
    public Coordinate getStart() { return this.start; }
    public Coordinate getEnd() { return this.end; }

    public boolean scan(Coordinate coor) {
        if(covers(coor)){
            return true;
        }
        if(covers(new Coordinate(coor.getRow()-1,coor.getCol()))){
            return true;
        }
        if(covers(new Coordinate(coor.getRow()+1,coor.getCol()))){
            return true;
        }
        if(covers(new Coordinate(coor.getRow(),coor.getCol()-1))){
            return true;
        }
        if(covers(new Coordinate(coor.getRow(),coor.getCol()+1))){
            return true;
        }
        return false;
    }

    public boolean overlapTest(Ship s) {
        //checks for overlap check of same boat
        if (s.getName() == this.name) {
            return false;
        }
        //checks if both lines horizantal and overlapping
        if (s.isHorizantal() == false && this.isHorizantal() == false
                && s.getEnd().getCol() == this.start.getCol()
                && s.getEnd().getRow() >= this.start.getRow()) {
            return true;
        }
        //checks if both lines vertical and overlapping
        else if (s.isHorizantal() == true && this.isHorizantal() == true
                && s.getEnd().getRow() == this.start.getRow()
                && s.getEnd().getCol() >= this.start.getCol()) {
            return true;
        }
        //checks if lines are perpendicular and overlapping while s is horizontal
        else if (s.isHorizantal()
                && s.getEnd().getRow() >= this.start.getRow()         //new.end.y >= test.start.y
                && s.getEnd().getRow() <= this.end.getRow()         //new.end.y <= test.end.y
                && s.getStart().getCol() <= this.start.getCol()     //new.start.x <= test.start.x
                && s.getEnd().getCol() >= this.start.getCol()) {    //new.end.x >= test.start.x
            return true;
        }
        //checks if lines are perp and overlapping while s is vertical
        else if (this.end.getRow() >= s.getStart().getRow()
                && this.end.getRow() <= s.getEnd().getRow()
                && this.start.getCol() <= this.start.getCol()
                && this.end.getCol() >= this.start.getCol()) {
            return true;
        }

        return false;
    }

    public boolean isHorizantal() {
        if (start.getRow() == end.getRow()) {
            return true;
        } else if (start.getCol() == end.getCol()) {
            return false;
        }
        return false;
    }

    public void removeCoordinates() {
        this.start = new Coordinate(0,0);
        this.end = new Coordinate(0,0);
    }
}
