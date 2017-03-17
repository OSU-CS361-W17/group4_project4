package edu.oregonstate.cs361.battleship;
import java.util.*;
import java.lang.*;


/**
 * Created by 10463 on 2017/3/17.
 */
public class AI_fire {
    Random rand = new Random();
    private List<Coordinate> LastRound_Hit;
    private Coordinate Last_hit;
    private Coordinate LastLast_hit;
    private int Count_four = 0;
    private int Count_size = 0;
    private int mod = 0;
    private Coordinate mod_one_point;
    private int modone_ini=0;
    private int mod_three_ini=0;
    private boolean engage=false;
    private int countRow = 0;
    private int countCol = 0;
    private int modtwo_type;
    ArrayList<Coordinate> poss = new ArrayList<Coordinate>();
    public Coordinate ai_openfire(BattleshipModel Model){
        Coordinate next_shot = null;
        if(Model.computerHits.contains(Last_hit) && this.engage==false){
            this.engage=true;
            this.mod=1;
            this.mod_one_point=this.Last_hit;
        }
        if(!this.engage){
            return Random_firing(Model);
        }
        if(mod==0){
            next_shot=Found_line(Model);
            if(next_shot==null){
                engage=false;
                mod=0;
                next_shot=Random_firing(Model);
            }
        }
        else if(mod==1){
            next_shot=in_line(Model);
        }
        else if(mod==2){
            next_shot=other_side(Model);
            if(next_shot==null){
                engage=false;
                mod=0;
                next_shot=Random_firing(Model);
            }

        }

        Last_hit=next_shot;
        return next_shot;
        
    }

    public Coordinate Random_firing(BattleshipModel Model) {
        Coordinate random_shot;
        do {
            int randRow = rand.nextInt(10) - 1;
            int randCol = rand.nextInt(10) - 1;
            random_shot = new Coordinate(randRow,randCol);
        }while(!Model.validShotTest(random_shot));
        return random_shot;
    }

    //Mode 1, find ship in cross
    private Coordinate Found_line(BattleshipModel Model){
        if(modone_ini==0) {
            poss=new ArrayList<Coordinate>();
            Coordinate poss1 = new Coordinate(mod_one_point.getRow(), mod_one_point.getCol() - 1);
            Coordinate poss2 = new Coordinate(mod_one_point.getRow(), mod_one_point.getCol() + 1);
            Coordinate poss3 = new Coordinate(mod_one_point.getRow() - 1, mod_one_point.getCol());
            Coordinate poss4 = new Coordinate(mod_one_point.getRow() + 1, mod_one_point.getCol());
            if (!Model.validShotTest(poss1))
                poss.add(poss1);
            if (!Model.validShotTest(poss2))
                poss.add(poss2);
            if (!Model.validShotTest(poss3))
                poss.add(poss3);
            if (!Model.validShotTest(poss4))
                poss.add(poss4);
            modone_ini=1;
        }
        if(Last_hit==mod_one_point){
            Count_size++;
            return poss.get(Count_size-1);
        }
        else if(!Model.computerHits.contains(Last_hit) && Count_size <=4){
            Count_size++;
            return poss.get(Count_size-1);
        }
        else if(Model.computerHits.contains(Last_hit)){
            Count_size=0;
            mod=1;
            modone_ini=0;
            countRow=mod_one_point.getRow()-Last_hit.getRow();
            countCol=mod_one_point.getCol()-Last_hit.getCol();
            if(Last_hit.getRow()==mod_one_point.getRow())
                return new Coordinate(Last_hit.getRow()+countRow, Last_hit.getCol()+countCol);
        }
        if(Count_size>=4){modone_ini=0;}
        return null;
    }

    //Mode 2, use line found in mode one til it miss
    private Coordinate in_line(BattleshipModel Model){
        if(!Model.computerHits.contains(Last_hit)){
            mod=2;
            return other_side(Model);
        }
        Coordinate next=new Coordinate(Last_hit.getRow()+countRow, Last_hit.getCol()+countCol);
        if(!Model.validShotTest(next))
            return other_side(Model);
        return next;

    }

    //mode 3, to different side til it miss
    private Coordinate other_side(BattleshipModel Model){
        if(mod_three_ini==0) {
            Coordinate next = new Coordinate(mod_one_point.getRow() - countRow, mod_one_point.getCol() - countCol);
            mod_three_ini=1;
            if(Model.validShotTest(next)){
                return next;
            }
            else
                return null;
        }
        Coordinate next = new Coordinate(Last_hit.getRow()-countRow, Last_hit.getCol()-countCol);
        if(Model.validShotTest(next))
            return next;
        else
            return null;
    }
}
