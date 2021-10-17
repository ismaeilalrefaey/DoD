package units.unit;

import users.team.TeamType;
import units.additional.*;

public abstract class UnAttackerUnit extends Unit {
    public UnAttackerUnit(double health, double armor, double radius, TypeOfUnit typeOfUnit, int price, TeamType teamType) {
        super(health, armor, radius, typeOfUnit, price, teamType);
    }
}
