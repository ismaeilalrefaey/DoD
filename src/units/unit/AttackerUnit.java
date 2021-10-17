package units.unit;

import tactics.attack_tactics.AttackTactic;
import tactics.attack_types.NormalAttack;
import tactics.attack_types.AttackType;
import interfaces.IF_UnitManager;
import interfaces.IF_Attacker;
import java.util.ArrayList;
import users.team.TeamType;
import units.additional.*;
import java.util.List;
import grid.Areana;

public abstract class AttackerUnit extends Unit implements IF_Attacker {
    
    protected final AttackTactic tactic;
    protected final int attackDamage;
    protected final int attackRange;
    protected final double attackSpeed; // num of hitts / secund

    protected boolean canAttack;
    protected long attackLastTime;
    protected long canAttackEach;
    protected long attackDelta;
    
    protected List< TypeOfUnit > targetsTypes; // has the types that can unit attack!
    protected List< Unit > unitsAround;
    
    public AttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit, int price, TeamType teamType,
            int attackRange, int attackDamage, double attackSpeed, AttackTactic tactic ,List< TypeOfUnit > targetsTypes) {
        super(health, armor, radius, typeOfUnit, price, teamType);
        this.attackRange   = attackRange;
        this.attackDamage  = attackDamage;
        this.attackSpeed   = attackSpeed;
        this.tactic        = tactic;
        this.targetsTypes  = targetsTypes;
        
        this.canAttack     = true;
        this.canAttackEach = (long) (1000 / attackSpeed);
        
        this.unitsAround  = new ArrayList();
    }
    
    protected class UnitManager implements IF_UnitManager {
        @Override
        public void doingState() {
            update();
            UnitState unitState = updateUnitState();
            if( unitState == UnitState.Attack ){
                acceptAttack( new NormalAttack() );
            }
        }
    }

    public void implementDoingState(){
        UnitManager unitManager = new UnitManager();
        unitManager.doingState();
    }
    
    public void initAttack(){
        attackLastTime = System.currentTimeMillis();
        attackDelta    = 0;
    }

    public UnitState updateUnitState(){
        unitsAround.clear();
        unitsAround = Areana.getOpj().scanAround( this );
        
        UnitState unitState = ( unitsAround.size() > 0 ) ? UnitState.Attack : UnitState.standing;
        return unitState;
    }
    
    protected void update(){
        if( canAttack == false ){
            long attackCurTime = System.currentTimeMillis();
            attackDelta       += (attackCurTime - attackLastTime);
            attackLastTime     = attackCurTime;
            
            if( attackDelta >= this.canAttackEach ){
                attackDelta -= canAttackEach;
                canAttack    = true;
            }
        } else initAttack();
    }
    
    protected void pause(){
        attackLastTime = System.currentTimeMillis();
    }
    
    public List< TypeOfUnit > getTargetsTypes(){
        return this.targetsTypes;
    }
    
    public List< Unit > getUnitsAround(){
        return this.unitsAround;
    }
    
    public int getAttackDamage(){
        return this.attackDamage;
    }
    
    public int getAttackRange(){
        return this.attackRange;
    }
    
    public AttackTactic getAttackTactic(){
        return this.tactic;
    }
    
    @Override
    public void acceptAttack( AttackType typeOfAttack ) {
        if( canAttack ) {
            canAttack = false;
            Unit target = tactic.getTarget( this );
            typeOfAttack.doAttack(this ,target);
        }
    }
}
