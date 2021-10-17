package units.unit;

import tactics.attack_tactics.AttackTactic;
import users.team.TeamType;
import units.additional.*;
import units.unit_plane.*;
import javagame.util.DorD;
import java.util.List;
import grid.Areana;

public class GroundMovableAttackerUnit extends MovableAttackerUnit {
    
    private boolean inRiver; // rediuse by 50%

    public GroundMovableAttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit, int price,
            TeamType teamType, int attackRange, int attackDamage, double attackSpeed, AttackTactic tactic,
            List<TypeOfUnit> targetsTypes, double moveSpeed ,List< PlaneOfUnit > planeOfUnit) {
        super(health, armor, radius, typeOfUnit, price, teamType, attackRange, attackDamage, attackSpeed,
                tactic, targetsTypes, moveSpeed ,planeOfUnit);
        this.inRiver = false;
    }
    
    @Override
    public void update(){
        super.update();
        this.inRiver = Areana.getOpj().isRiver( this.position );
        
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
