package units.unit;

import tactics.move_tactics.M_Attacker;
import tactics.move_tactics.M_Defender;
import tactics.move_tactics.MoveTactic;
import units.unit_plane.PlaneOfUnit;
import units.additional.TypeOfUnit;
import units.additional.UnitState;
import interfaces.IF_UnitManager;
import units.unit_plane.P_Move;
import units.unit_plane.P_Wait;
import interfaces.IF_Moveable;
import users.team.TeamType;
import java.util.List;

public abstract class MovableUnAttackerUnit extends UnAttackerUnit implements IF_Moveable {
    
    protected UnitManager unitManager;
    protected final double moveSpeed;
    protected MoveTactic moveTactic;
    protected long moveLastTime;
    protected long canMoveEach;
    protected boolean canMove;
    protected long moveDelta;
    
    protected List< PlaneOfUnit > planeOfUnit;
    protected int idxOfPlane;

    protected long planeLastTime;
    
    public MovableUnAttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit,
            int price, TeamType teamType ,double moveSpeed , List< PlaneOfUnit > planeOfUnit) {
        super(health, armor, radius, typeOfUnit, price, teamType);
        this.moveSpeed = moveSpeed;
        
        this.canMoveEach = (long) (1000 / moveSpeed);
        this.canMove     = true;
        
        this.moveTactic = (teamType == TeamType.Defender) ? new M_Defender() : new M_Attacker();
        this.unitManager = new UnitManager();
        
        this.planeOfUnit = planeOfUnit;
        this.idxOfPlane  = 0;
    }
    
    public void initPlane(){
        planeLastTime = System.currentTimeMillis();
    }
    
    public void initMove(){
        moveLastTime = System.currentTimeMillis();
        moveDelta = 0;
    }
    
    protected class UnitManager implements IF_UnitManager {
        @Override
        public void doingState() {
            update();
            UnitState unitState = updateUnitState();
            
            if( unitState == UnitState.Move ) {
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

    public void implementDoingState(){
        unitManager.doingState();
    }
   
    public UnitState updateUnitState(){
        UnitState unitState = UnitState.standing;
        if( canMove ) unitState = UnitState.Move;
        return unitState;
    }    
    
    protected void pause(){
        moveLastTime  = System.currentTimeMillis();
        planeLastTime = System.currentTimeMillis();
    }
    
    public abstract void update();
    
    public P_Move getMovePlane(){
        return (P_Move)this.planeOfUnit.get( idxOfPlane );
    }
    
    public MoveTactic getMoveTactic(){
        return this.moveTactic;
    }
    
    @Override
    public void acceptMove(MoveTactic typeOfMove) {
        if( canMove ){
            canMove = false;
            typeOfMove.doMove( this );
        }
    }
}
