package tactics.move_tactics;

import files.logFile.WriteToLogFile;
import javagame.util.DorD;
import units.additional.*;
import units.unit.*;
import grid.Areana;

public class M_Attacker extends MoveTactic {
    private final int dx1[] = {-1 ,-1 , 0 ,+1 ,-1 , 0 ,+1 ,+1};
    private final int dy1[] = {-1 , 0 ,-1 ,-1 ,+1 ,+1 , 0 ,+1};
    
    private final int dx2[] = {+1 ,+1 , 0 ,+1 ,-1 , 0 ,-1 ,-1};
    private final int dy2[] = {-1 , 0 ,-1 ,+1 ,-1 ,+1 , 0 ,+1};
        
    private final int dx3[] = {-1 ,-1 ,-1 , 0 , 0 ,+1 ,+1 ,+1};
    private final int dy3[] = { 0 ,-1 ,+1 ,-1 ,+1 ,-1 ,+1 , 0};
    
    private final int dx4[] = { 0 ,-1 ,+1 ,-1 ,+1 ,+1 ,-1 , 0};
    private final int dy4[] = {-1 ,-1 ,-1 , 0 , 0 ,+1 ,+1 ,+1};
    
    @Override
    public void doMove( Unit unit ){        
        DorD dord = DorD.getOpj();
        Areana areana = Areana.getOpj();
        
        Position basePos = dord.getBasePosition();
        Position unitPos = unit.getPosition();
        
        areana.removeUnit( unit );
        
        Position result = new Position(unitPos.getX() ,unitPos.getY());
        
        if( basePos.getX() < unitPos.getX() ){
            if( basePos.getY() < unitPos.getY() ){ // dx1
                for(int move = 0 ; move < 8 ; move++){
                    int newX = unitPos.getX() + dx1[ move ];
                    int newY = unitPos.getY() + dy1[ move ];
                    
                    Position newPos = new Position(newX ,newY);
                    if( areana.isValid( newPos , unit ) ){
                        result = newPos;
                        break;
                    }
                }
            } else if( basePos.getY() > unitPos.getY() ){ // dx2'
                for(int move = 7 ; move >= 0 ; move--){
                    int newX = unitPos.getX() + dx2[ move ];
                    int newY = unitPos.getY() + dy2[ move ];
                    
                    Position newPos = new Position(newX ,newY);
                    if( areana.isValid( newPos , unit ) ){
                        result = newPos;
                        break;
                    }
                }
            } else { // dx3
                for(int move = 0 ; move < 8 ; move++){ 
                    int newX = unitPos.getX() + dx3[ move ];
                    int newY = unitPos.getY() + dy3[ move ];
                    
                    Position newPos = new Position(newX ,newY);
                    if( areana.isValid( newPos , unit ) ){
                        result = newPos;
                        break;
                    }
                }
            }
        } else if( basePos.getX() > unitPos.getX() ){
            if( basePos.getY() < unitPos.getY() ){ // dx2
                for(int move = 0 ; move < 8 ; move++){
                    int newX = unitPos.getX() + dx2[ move ];
                    int newY = unitPos.getY() + dy2[ move ];
                    
                    Position newPos = new Position(newX ,newY);
                    if( areana.isValid( newPos , unit ) ){
                        result = newPos;
                        break;
                    }
                }
            } else if( basePos.getY() > unitPos.getY() ){ // dx1'
                for(int move = 7 ; move >= 0 ; move--){
                    int newX = unitPos.getX() + dx1[ move ];
                    int newY = unitPos.getY() + dy1[ move ];
                    
                    Position newPos = new Position(newX ,newY);
                    if( areana.isValid( newPos , unit ) ){
                        result = newPos;
                        break;
                    }
                }
            } else { // dx3'
                for(int move = 7 ; move >= 0 ; move--){
                    int newX = unitPos.getX() + dx3[ move ];
                    int newY = unitPos.getY() + dy3[ move ];
                    
                    Position newPos = new Position(newX ,newY);
                    if( areana.isValid( newPos , unit ) ){
                        result = newPos;
                        break;
                    }
                }
            } 
        } else {
            if( basePos.getY() < unitPos.getY() ){ // dx4
                for(int move = 0 ; move < 8 ; move++){
                    int newX = unitPos.getX() + dx4[ move ];
                    int newY = unitPos.getY() + dy4[ move ];
                    
                    Position newPos = new Position(newX ,newY);
                    if( areana.isValid( newPos , unit ) ){
                        result = newPos;
                        break;
                    }
                }
            } else if( basePos.getY() > unitPos.getY() ){ // dx4'
                for(int move = 7 ; move >= 0 ; move--){
                    int newX = unitPos.getX() + dx4[ move ];
                    int newY = unitPos.getY() + dy4[ move ];
                    
                    Position newPos = new Position(newX ,newY);
                    if( areana.isValid( newPos , unit ) ){
                        result = newPos;
                        break;
                    }
                }
            } else {
                /// ---  Do Nothing --- /// 
            }
        }
        
        unit.setPosition(result);
        WriteToLogFile.write(unit.getTypeOfUnit() + " " + unit.getUnitId() + " " + unit.getTeamType() 
                + "Team -> Move From ( " + unitPos.getX() + " , " + unitPos.getY() + " ) to ( " + 
                result.getX() + " , " + result.getY() + " ) " );
        areana.addUnit( unit );
    }
}
