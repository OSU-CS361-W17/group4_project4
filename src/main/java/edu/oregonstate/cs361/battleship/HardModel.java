package edu.oregonstate.cs361.battleship;


import java.util.ArrayList;
import java.util.Random;
import java.lang.*;

import java.util.Random;


/**
 * Created by geoscow on 3/18/17.
 */
public class HardModel extends BattleshipModel {

    Random rand = new Random();
    private Coordinate Last_hit;
    private int Count_size = 0;
    private int mod = 0;
    private Coordinate mod_one_point;
    private int modone_ini=0;
    private int mod_three_ini=0;
    private boolean engage=false;
    private int countRow = 0;
    private int countCol = 0;
    ArrayList<Coordinate> poss = new ArrayList<Coordinate>();


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


        Coordinate shot =  ai_openfire();
        this.errorMessage = this.engage + " smart AI shoots at "+ shot.getRow() + "," + shot.getCol();
        Last_hit=shot;
        playerShot(shot);
    }

    public boolean AIvalidShotTest(Coordinate shot) {
        //loops to check shot validity
        if(listcontains(playerHits, shot))
            return false;
        if(listcontains(playerMisses, shot))
            return false;

        if(shot.getRow() > this.MAX || shot.getRow() < this.MIN
                || shot.getCol() > this.MAX || shot.getCol() < this.MIN)
            return false;

        return true;
    }

    //AI function, main function which return the next shot coordinate
    public Coordinate ai_openfire(){
        Coordinate next_shot = null;
            for(Coordinate p : playerHits){
        System.out.println("Hit Coordinate: ("+p.getRow()+","+p.getCol()+")");}
        //if(Last_hit!=null){
          //  System.out.println("Last Hit point: ("+Last_hit.getRow()+","+Last_hit.getCol()+")");
            //System.out.println(listcontains(playerHits, Last_hit));
            //System.out.println(engage);
        //}

        if(listcontains(playerHits, Last_hit) && !engage){
            engage=true;
            mod=1;
            mod_one_point=this.Last_hit;
        }
        if(!this.engage){
            return Random_firing();
        }
        if(mod==1){
            //System.out.println("stage at Mod 1");
            next_shot=Found_line();
            if(next_shot==null){
                engage=false;
                mod=0;
                next_shot=Random_firing();
            }
        }
        else if(mod==2){
            next_shot=in_line();
            if(next_shot==null){
                System.out.println("argc finish, wow");
                engage=false;
                mod=0;
                next_shot=Random_firing();}

        }
        else if(mod==3){
            System.out.println("Stage in mod 3");
            next_shot=other_side();
            if(next_shot==null){
                System.out.println("argc finish, wow");
                engage=false;
                mod=0;
                next_shot=Random_firing();
            }

        }

        //Last_hit=next_shot;
        System.out.println("Shot point : (" + next_shot.getRow() + "," + next_shot.getCol()+")");
        return next_shot;
    }

    //AI function, return coordinate to AI main, call when randomly select points
    public Coordinate Random_firing() {
        Coordinate random_shot;
        do {
            int randRow = rand.nextInt(10) - 1;
            int randCol = rand.nextInt(10) - 1;
            random_shot = new Coordinate(randRow,randCol);
        }while(!AIvalidShotTest(random_shot));
        return random_shot;
    }

    //AI function Mode 1, find ship in cross
    private Coordinate Found_line(){
        if(modone_ini==0) {
            //System.out.println("1");
            poss=new ArrayList<Coordinate>();
            Coordinate poss1 = new Coordinate(mod_one_point.getRow(), mod_one_point.getCol() - 1);
            Coordinate poss2 = new Coordinate(mod_one_point.getRow(), mod_one_point.getCol() + 1);
            Coordinate poss3 = new Coordinate(mod_one_point.getRow() - 1, mod_one_point.getCol());
            Coordinate poss4 = new Coordinate(mod_one_point.getRow() + 1, mod_one_point.getCol());
            if (AIvalidShotTest(poss1)){
                poss.add(poss1);}
            if (AIvalidShotTest(poss2)){
                poss.add(poss2);}
            if (AIvalidShotTest(poss3)){
                poss.add(poss3);}
            if (AIvalidShotTest(poss4)){
                poss.add(poss4);}
            modone_ini=1;
        }
        if(Last_hit==mod_one_point){
            Count_size++;
            return poss.get(Count_size-1);
        }
        else if(!this.listcontains(playerHits, Last_hit) && Count_size <=poss.size()){
            Count_size++;
            return poss.get(Count_size-1);
        }
        else if(this.listcontains(playerHits, Last_hit)){
            Count_size=0;
            mod=2;
            modone_ini=0;
            countRow=mod_one_point.getRow()-Last_hit.getRow();
            countCol=mod_one_point.getCol()-Last_hit.getCol();
            if(Last_hit.getRow()==mod_one_point.getRow() || Last_hit.getCol()==mod_one_point.getCol()){
                Coordinate next=new Coordinate(Last_hit.getRow()-countRow, Last_hit.getCol()-countCol);
                if(AIvalidShotTest(next))
                    return next;
                else {
                    mod=3;
                    return other_side();
                }}
        }
        if(Count_size>=4){modone_ini=0;}
        return null;
    }

    //AI function Mode 2, use line found in mode one til it miss
    private Coordinate in_line(){
        System.out.println("Whenever call stage 2");
        if(!listcontains(playerHits, Last_hit)){
            System.out.println("Check this out!");
            mod=3;
            return other_side();
        }
        Coordinate next=new Coordinate(Last_hit.getRow()-countRow, Last_hit.getCol()-countCol);
        System.out.println("In the center of stage 2");
        if(AIvalidShotTest(next))
            return next;
        else
            return other_side();

    }

    //AI function mode 3, to different side til it miss
    private Coordinate other_side(){
        if(mod_three_ini==0) {
            //System.out.println("Mod 3 ini start");
            Coordinate next = new Coordinate(mod_one_point.getRow() + countRow, mod_one_point.getCol() + countCol);
            mod_three_ini=1;
            //System.out.println("ini done");
            if(AIvalidShotTest(next)){
                //System.out.println("first shot after ini");
                return next;
            }
            else{
                //System.out.println("Finish argc");
                return null;}
        }
        //System.out.println("still in argc");
        Coordinate next = new Coordinate(Last_hit.getRow()+countRow, Last_hit.getCol()+countCol);
        if(!listcontains(playerHits, Last_hit))
            return null;
        if(AIvalidShotTest(next)){
            //System.out.println("still in argc XD");
            return next;}
        else{
            //System.out.println("finish ~");
            return null;}
    }

    public boolean listcontains(ArrayList<Coordinate> list, Coordinate q){
        for(Coordinate p : list){
            if(p.getCol()==q.getCol()){
                if(p.getRow()==q.getRow()){
                    return true;
                }
            }
        }
        return false;
    }

        this.errorMessage = "smart AI shoots";
        //playerShot(shot);
    }



}
