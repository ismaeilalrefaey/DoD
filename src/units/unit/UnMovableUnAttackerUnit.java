package units.unit;

import users.team.TeamType;
import units.additional.*;

public class UnMovableUnAttackerUnit extends UnAttackerUnit {
    public UnMovableUnAttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit, int price, TeamType teamType) {
        super(health, armor, radius, typeOfUnit, price, teamType);
    }
    
    @Override
    public void run(){
        if( !isAlife ) destroyUnit();
    }
}
