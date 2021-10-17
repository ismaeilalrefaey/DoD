package users.team;

import units.additional.TypeOfUnit;
import units.factory.UnitFactory;
import java.util.ArrayList;
import javagame.util.DorD;
import units.unit.Unit;
import java.util.List;

public class Player {
    private static int staticId = 0;
    
    private List< Unit > units = null;
    private TeamType teamType;
    private int points;
    private int id;
    
    public Player(int points ,TeamType teamType){
        this.id       = Player.staticId; 
        this.points   = points;
        this.teamType = teamType;
        Player.staticId ++;
    }
    
    public void buyUnit( TypeOfUnit typeOfUnit ){
        UnitFactory unitFactory = UnitFactory.getOpj();
        Unit unit = unitFactory.createUnit(typeOfUnit, teamType);
        points   -= unit.getPrice();

        if (teamType == TeamType.Attacker) {
            DorD.getOpj().addAttackerUnits();
        } else {
            DorD.getOpj().addDefenderUnits();
        }

        addUnit( unit );
    }
    
    private void addUnit(Unit unit){
        if( units == null ) units = new ArrayList();
        units.add( unit );
    }
    
    public TeamType getTeamType(){
        return this.teamType;
    }
    
    public int getId(){
        return this.id;
    }

    public int getPoints() {
        return this.points;
    }

    public List< Unit > getUnits() {
        return this.units;
    }
}
