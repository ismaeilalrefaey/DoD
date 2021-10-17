package units.unit;

import files.logFile.WriteToLogFile;
import javagame.util.DorD;
import users.team.TeamType;
import units.additional.*;
import grid.Areana;

public abstract class Unit extends Thread {
    private static int staticId = 0;
    
    protected TypeOfUnit typeOfUnit;
    protected TeamType teamType;
    protected Position position;
    protected Position guiPosition;
    protected boolean isAlife;
    
    protected final double radius;
    protected final double armor;
    protected final int price;
    protected double health;
    protected int id;
    
    public Unit(double health, double armor,double radius ,TypeOfUnit typeOfUnit ,int price ,TeamType teamType) {
        this.isAlife    = true;
        this.health     = health;
        this.armor      = armor;
        this.price      = price;
        this.typeOfUnit = typeOfUnit;
        this.teamType   = teamType;
        this.radius     = radius;
        
        Unit.staticId += 1;
        this.id = Unit.staticId;
    }
    
    public void hitUnit( int attackDamage ){
        this.health -= (double)attackDamage - ((double)attackDamage * this.armor );
        
        if( this.health <= 0.0 ) destroyUnit();
    }
    
    protected synchronized void destroyUnit(){
        if (teamType == TeamType.Attacker) {
            DorD.getOpj().subAttackerUnits();
        } else {
            DorD.getOpj().subDefenderUnits();
        }

        WriteToLogFile.write(typeOfUnit + " " + id + " " + teamType + " Is Destroyed !!!!! ---------" );
        Areana areana = Areana.getOpj();
        areana.removeUnit( this );
        this.isAlife = false;
    }
    
    public boolean getIsAlife(){
        return this.isAlife;
    }
    
    public double getHealth(){
        return this.health;
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public TypeOfUnit getTypeOfUnit(){
        return this.typeOfUnit;
    }
    
    public Position getGuiPosition(){
        return this.guiPosition;
    }
    
    public Position getPosition(){
        return this.position;
    }

    public double getArmor(){ return this.armor; }

    public void setPosition( Position position ){
        this.position = position;
        this.guiPosition = new Position((int)(this.position.getX() - this.radius), (int)(this.position.getY() - this.radius));
    }
    
    public TeamType getTeamType(){
        return this.teamType;
    }
    
    public double getRadius(){
        return this.radius;
    }
    
    public int getUnitId(){
        return this.id;
    }
}
