package javagame.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import files.logFile.WriteToLogFile;
import units.additional.TypeOfUnit;
import units.factory.UnitFactory;
import units.additional.Position;
import users.team.AttackerTeam;
import users.team.DefenderTeam;
import users.team.TeamType;
import users.team.Player;
import units.unit.Unit;
import java.util.List;
import grid.Areana;

public class DorD extends Game {
    private AttackerTeam attackerTeam;
    private DefenderTeam defenderTeam;
    private final int playersPoints;
    private UnitFactory unitFactory;
    private int attackerTeamUnits;
    private int defenderTeamUnits;
    private boolean isPaused;
    private Areana areana;
    private Unit Base;
    
    private long gameTimeLastTime;
    private long gameTimeDelta;
    private int remainingTime;
    private long gameTime;
    
    public DorD(int numOfCells ,int points){
        this.attackerTeam  = new AttackerTeam();
        this.defenderTeam  = new DefenderTeam();
        this.unitFactory   = UnitFactory.getOpj();
        this.gameTime      = 300000l;
        this.Base          = unitFactory.createUnit(TypeOfUnit.MAINBASE, TeamType.Defender);
        this.areana        = new Areana(numOfCells);
        this.playersPoints = points;
        this.isPaused      = false;
        this.attackerTeamUnits = 0;
        this.defenderTeamUnits = 1;
        makeSingelton();
    }
    
    public void pause(){
        gameTimeLastTime = System.currentTimeMillis();
        
        List< Unit > units = areana.getUnits();
        ExecutorService executorService = Executors.newFixedThreadPool( units.size() );
        for(int i = 0 ; i < units.size() ; i++) executorService.submit( units.get(i) );
        
        try {
            executorService.shutdown();
            executorService.awaitTermination( 10, TimeUnit.SECONDS );
        } catch( InterruptedException ex ) { 
            System.out.println("executorService Error !!" + ex.getMessage());
        }
    }
    
    public void update() {
        long gameTimeCurTime = System.currentTimeMillis();
        gameTimeDelta       += (gameTimeCurTime - gameTimeLastTime);
        gameTimeLastTime     = gameTimeCurTime;

        if( gameTimeDelta >= 1000 ){
            gameTimeDelta -= 1000;
            gameTime      -= 1000;
            remainingTime  = (int)(gameTime / 1000l);
        }
        
        List< Unit > units = areana.getUnits();
        ExecutorService executorService = Executors.newFixedThreadPool( units.size() );
        for(int i = 0 ; i < units.size() ; i++) executorService.submit( units.get(i) );
        
        try {
            executorService.shutdown();
            executorService.awaitTermination( 10, TimeUnit.SECONDS );
        } catch( InterruptedException ex ) { 
            System.out.println("executorService Error !!" + ex.getMessage());
        }
    }
    
    public void addPlayer( Player player ){
        if( player.getTeamType() == TeamType.Defender ) {
            defenderTeam.addPlayer(player);
        } else {
            attackerTeam.addPlayer(player);
        }
    }

    public void initTimeOfGame(){
        WriteToLogFile.clearTheFile();
        this.gameTimeDelta    = 0;
        this.remainingTime    = (int) (this.gameTime / 1000); 
        this.gameTimeLastTime = System.currentTimeMillis();
    }
    
    public void setIsPaused(){
        this.isPaused = !this.isPaused;
    }
    
    public boolean getIsPaused(){
        return this.isPaused;
    }
    
    public DefenderTeam getDefenderTeam(){
        return this.defenderTeam;
    }
    
    public AttackerTeam getAttackerTeam(){
        return this.attackerTeam;
    }
    
    public int getPlayersPoints(){
        return this.playersPoints;
    }
    
    public Unit getBase(){
        return this.Base;
    }
    
    public Position getBasePosition(){
        return Base.getPosition();
    }
    
    public int getRemainingTime(){
        return this.remainingTime;
    }
    
    public int getAttackerTeamUnits(){return this.attackerTeamUnits;}

    public void subAttackerUnits(){ this.attackerTeamUnits --; }

    public void subDefenderUnits(){ this.defenderTeamUnits --; }

    public void addAttackerUnits(){ this.attackerTeamUnits ++; }

    public void addDefenderUnits(){ this.defenderTeamUnits ++; }
    
    /// --- Make Singelton --- ///
    private static DorD dord = null;  
    
    public static DorD getOpj(){
        return DorD.dord;
    }
    
    private void makeSingelton(){
        DorD.dord = this;
    }
}
