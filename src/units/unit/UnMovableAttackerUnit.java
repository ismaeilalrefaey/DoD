package units.unit;

import tactics.attack_tactics.AttackTactic;
import users.team.TeamType;
import units.additional.*;
import javagame.util.DorD;
import java.util.List;

public class UnMovableAttackerUnit extends AttackerUnit{
    public UnMovableAttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit, int price, TeamType teamType,
            int attackRange, int attackDamage, double attackSpeed, AttackTactic tactic, List<TypeOfUnit> targetsTypes) {
        super(health, armor, radius, typeOfUnit, price, teamType, attackRange, attackDamage, attackSpeed, tactic, targetsTypes);
    }
    
    @Override
    public void run(){
        if( DorD.getOpj().getIsPaused() ){
            pause();
        } else{
            if( !isAlife ) destroyUnit();
            else   implementDoingState();
        }
    }
}
