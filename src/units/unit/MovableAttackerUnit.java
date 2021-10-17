package units.unit;

import tactics.attack_tactics.AttackTactic;
import tactics.attack_types.NormalAttack;
import tactics.move_tactics.M_Attacker;
import tactics.move_tactics.M_Defender;
import tactics.move_tactics.MoveTactic;
import interfaces.IF_UnitManager;
import interfaces.IF_Moveable;
import users.team.TeamType;
import units.additional.*;
import units.unit_plane.*;
import java.util.List;

public abstract class MovableAttackerUnit extends AttackerUnit implements IF_Moveable {
    
    protected final double moveSpeed;
    protected MoveTactic moveTactic;
    protected long moveLastTime;
    protected long canMoveEach;
    protected boolean canMove;
    protected long moveDelta;

    protected List< PlaneOfUnit > planeOfUnit;
    protected int idxOfPlane;

    protected long planeLastTime;
    
    public MovableAttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit, int price,
            TeamType teamType, int attackRange, int attackDamage, double attackSpeed, AttackTactic tactic,
            List<TypeOfUnit> targetsTypes ,double moveSpeed ,List< PlaneOfUnit > planeOfUnit) {
        super(health, armor, radius, typeOfUnit, price, teamType, attackRange,
                attackDamage, attackSpeed ,tactic, targetsTypes);
        this.moveSpeed = moveSpeed;
        
        this.canMoveEach = (long) (1000 / moveSpeed);
        this.canMove     = true;
        
        this.moveTactic = (teamType == TeamType.Defender) ? new M_Defender() : new M_Attacker(); 
        
        this.planeOfUnit = planeOfUnit;
        this.idxOfPlane  = 0;
    }
    
    protected class UnitManager implements IF_UnitManager {
        @Override
        public void doingState() {
            update();
            UnitState unitState = updateUnitState();
            
            if( unitState == UnitState.Attack ){
                acceptAttack( new NormalAttack() );
            } else if( unitState == UnitState.Move ) {
                if( idxOfPlane < planeOfUnit.size() ) {
                    if( planeOfUnit.get( idxOfPlane ) instanceof P_Wait ){
                        long planeCurTime = System.currentTimeMillis();
                        long planeDelta   = (planeCurTime - planeLastTime);
                        planeLastTime     = planeCurTime;
                        ((P_Wait)planeOfUnit.get( idxOfPlane )).sub(planeDelta);
                        if( ((P_Wait)planeOfUnit.get( idxOfPlane )).getRemainingTime() <= 0l )
                            idxOfPlane += 1;

                    } else if( planeOfUnit.get( idxOfPlane ) instanceof P_Move ) {
                        initPlane();
                        acceptMove( ((P_Move)(planeOfUnit.get( idxOfPlane ))).getMoveTactic() );
                        if( position.getX() == ((P_Move)(planeOfUnit.get( idxOfPlane ))).getTargetPos().getX() &&
                            position.getY() == ((P_Move)(planeOfUnit.get( idxOfPlane ))).getTargetPos().getY()  )
                                idxOfPlane += 1;
                    }
                } else acceptMove( moveTactic );
            }
        }
    }

    @Override
    public void implementDoingState(){
        UnitManager unitManager = new UnitManager();
        unitManager.doingState();
    }
    
    public void initPlane(){
        planeLastTime = System.currentTimeMillis();
    }
    
    public void initMove(){
        moveLastTime = System.currentTimeMillis();
        moveDelta = 0;
    }
    
    @Override
    public UnitState updateUnitState(){
        UnitState unitState = super.updateUnitState();
        
        if( unitState == UnitState.standing )
            if( canMove ) unitState = UnitState.Move;
        
        return unitState;
    }
    
    @Override
    protected void pause(){
        super.pause();
        planeLastTime = System.currentTimeMillis();
        moveLastTime  = System.currentTimeMillis();
    }
    
    public P_Move getMovePlane(){
        return (P_Move)this.planeOfUnit.get( idxOfPlane );
    }
    
    public MoveTactic getMoveTactic(){
        return this.moveTactic;
    }
    
    @Override
    public void acceptMove(MoveTactic typeOfMove) {
        if( canMove ) {
            canMove = false;
            typeOfMove.doMove( this );
        }
    }
}
