package tactics.move_tactics;

import files.logFile.WriteToLogFile;
import units.additional.*;
import units.unit.*;
import grid.Areana;

public class M_Defender extends MoveTactic {
    private int flag = 0;
    
    @Override
    public void doMove(Unit unit){
        Areana areana = Areana.getOpj();
        
        Position unitPos = unit.getPosition();
        Position result = unitPos;

        areana.removeUnit( unit );
        
        if( flag < 100 ) {
            Position newPos = new Position(unitPos.getX() - 1 ,unitPos.getY());
            if( areana.isValid(newPos ,unit) ) result = newPos;
        } else if( flag < 200 ) {
            Position newPos = new Position(unitPos.getX() ,unitPos.getY() + 1);
            if( areana.isValid(newPos ,unit) ) result = newPos;
        } else if( flag < 300 ) {
            Position newPos = new Position(unitPos.getX() + 1 ,unitPos.getY());
            if( areana.isValid(newPos ,unit) ) result = newPos;
        } else if( flag < 400 ) {
            Position newPos = new Position(unitPos.getX() ,unitPos.getY() - 1);
            if( areana.isValid(newPos ,unit) ) result = newPos;
        }

        flag += 1;
        flag %= 400;
        
        unit.setPosition( result );
        WriteToLogFile.write(unit.getTypeOfUnit() + " " + unit.getUnitId() + " " + unit.getTeamType() 
                + "Team -> Move From ( " + unitPos.getX() + " , " + unitPos.getY() + " ) to ( " + 
                result.getX() + " , " + result.getY() + " ) " );
        areana.addUnit( unit );
    }
}
