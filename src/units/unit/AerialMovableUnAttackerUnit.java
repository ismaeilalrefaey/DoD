package units.unit;

import users.team.TeamType;
import units.additional.*;
import units.unit_plane.*;
import javagame.util.DorD;
import java.util.List;

public class AerialMovableUnAttackerUnit extends MovableUnAttackerUnit {

    public AerialMovableUnAttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit, int price,
            TeamType teamType, double moveSpeed , List< PlaneOfUnit > planeOfUnit) {
        super(health, armor, radius, typeOfUnit, price, teamType, moveSpeed , planeOfUnit);
    }

    @Override
    public void update(){
        if( canMove == false ){
            long moveCurTime = System.currentTimeMillis();
            moveDelta       += (moveCurTime - moveLastTime);
            moveLastTime     = moveCurTime;
            
            if( moveDelta >= this.canMoveEach ){
                moveDelta -= this.canMoveEach;
                canMove    = true;
            }
        } else initMove();
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
