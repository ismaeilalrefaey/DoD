package units.unit;

import users.team.TeamType;
import units.additional.*;
import units.unit_plane.*;
import javagame.util.DorD;
import java.util.List;
import grid.Areana;

public class GroundMovableUnAttackerUnit extends MovableUnAttackerUnit {
    
    private boolean inRiver;

    public GroundMovableUnAttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit, int price,
            TeamType teamType, double moveSpeed ,List< PlaneOfUnit > planeOfUnit) {
        super(health, armor, radius, typeOfUnit, price, teamType, moveSpeed ,planeOfUnit);
        this.inRiver = false;
    }
    
    @Override
    public void update(){
        this.inRiver = Areana.getOpj().isRiver(this.position);
        
        if( canMove == false ){
            long moveCurTime = System.currentTimeMillis();
            moveDelta       += (moveCurTime - moveLastTime);
            moveLastTime     = moveCurTime;
            
            if( moveDelta >= this.calcCanMoveEach() ){
                moveDelta -= this.canMoveEach;
                canMove    = true;
            }
        } else initMove();
    }
    
    private long calcCanMoveEach(){
        return inRiver ? canMoveEach + canMoveEach / 2 : canMoveEach;
    }
    
    @Override
    public void run(){
        if( DorD.getOpj().getIsPaused() ){
            pause();
        } else {
            if( !isAlife ) destroyUnit();
            else   implementDoingState();
        }
    }
}
