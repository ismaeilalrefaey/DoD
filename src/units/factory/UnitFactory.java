package units.factory;

import java.util.concurrent.ExecutionException;
import tactics.attack_tactics.HighDamageTactic;
import tactics.attack_tactics.LowHealthTactic;
import tactics.attack_tactics.PriorityTactic;
import java.util.concurrent.ExecutorService;
import tactics.attack_tactics.RandomTactic;
import tactics.attack_tactics.AttackTactic;
import files.read_from_file.ReadLineNum;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.logging.Level;
import users.team.TeamType;
import java.util.ArrayList;
import units.unit_plane.*;
import units.additional.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import units.unit.*;

public class UnitFactory {
    private Map<TypeOfUnit , Integer> idxOfType = null;
    private String[] attriputes = null;
    
    public final UnMovableAttackerUnit patriotMissileSystem;
    public final GroundMovableAttackerUnit tankDestroyer;
    public final GroundMovableAttackerUnit grizzlyTank;
    public final GroundMovableAttackerUnit mirageTank;
    public final GroundMovableAttackerUnit prismTank;
    public final AerialMovableAttakerUnit blackEagle;
    public final GroundMovableAttackerUnit teslaTank;
    public final GroundMovableAttackerUnit infantry;
    public final GroundMovableAttackerUnit navySEAL;
    public final UnMovableAttackerUnit grandCannon;
    public final GroundMovableAttackerUnit sniper;
    public final UnMovableAttackerUnit prismTower;
    public final UnMovableAttackerUnit pillbox;
    
    private UnitFactory(){
        idxOfType = new HashMap();
      
        idxOfType.put(TypeOfUnit.PatriotMissileSystem ,13);
        idxOfType.put(TypeOfUnit.TankDestroyer ,6);
        idxOfType.put(TypeOfUnit.GrandCannon ,10);
        idxOfType.put(TypeOfUnit.BlackEagle ,12);
        idxOfType.put(TypeOfUnit.GrizzlyTank ,4);
        idxOfType.put(TypeOfUnit.MirageTank ,2);
        idxOfType.put(TypeOfUnit.PrismTower ,9);
        idxOfType.put(TypeOfUnit.MAINBASE ,11);
        idxOfType.put(TypeOfUnit.PrismTank ,7);
        idxOfType.put(TypeOfUnit.TeslaTank ,0);
        idxOfType.put(TypeOfUnit.Infantry ,3);
        idxOfType.put(TypeOfUnit.NavySEAL ,5);
        idxOfType.put(TypeOfUnit.Pillbox ,8);
        idxOfType.put(TypeOfUnit.Sniper ,1);
        
        attriputes = new String[13];
        
        patriotMissileSystem = (UnMovableAttackerUnit) createUnit(TypeOfUnit.PatriotMissileSystem, TeamType.Examples);
        tankDestroyer        = (GroundMovableAttackerUnit) createUnit(TypeOfUnit.TankDestroyer, TeamType.Examples);
        grizzlyTank          = (GroundMovableAttackerUnit) createUnit(TypeOfUnit.GrizzlyTank, TeamType.Examples);
        mirageTank           = (GroundMovableAttackerUnit) createUnit(TypeOfUnit.MirageTank, TeamType.Examples);
        prismTank            = (GroundMovableAttackerUnit) createUnit(TypeOfUnit.PrismTank, TeamType.Examples);
        teslaTank            = (GroundMovableAttackerUnit) createUnit(TypeOfUnit.TeslaTank, TeamType.Examples);
        blackEagle           = (AerialMovableAttakerUnit) createUnit(TypeOfUnit.BlackEagle, TeamType.Examples);
        infantry             = (GroundMovableAttackerUnit) createUnit(TypeOfUnit.Infantry, TeamType.Examples);
        navySEAL             = (GroundMovableAttackerUnit) createUnit(TypeOfUnit.NavySEAL, TeamType.Examples);
        grandCannon          = (UnMovableAttackerUnit) createUnit(TypeOfUnit.GrandCannon, TeamType.Examples);
        sniper               = (GroundMovableAttackerUnit) createUnit(TypeOfUnit.Sniper, TeamType.Examples);
        prismTower           = (UnMovableAttackerUnit) createUnit(TypeOfUnit.PrismTower, TeamType.Examples);
        pillbox              = (UnMovableAttackerUnit) createUnit(TypeOfUnit.Pillbox, TeamType.Examples);
    }
    
    public Unit createUnit(TypeOfUnit typeOfUnit ,TeamType teamType){
        String line = null;
        
        try {
            ExecutorService exucutorService = Executors.newSingleThreadExecutor();
            Future< String > future = exucutorService.submit( new ReadLineNum( "units.txt" , idxOfType.get( typeOfUnit ) ) );
            line = future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(UnitFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        readUnitLine(line ,teamType);
        
        Unit unit = null;
        
        if( typeOfUnit == TypeOfUnit.TeslaTank     || typeOfUnit == TypeOfUnit.Sniper   ||
            typeOfUnit == TypeOfUnit.MirageTank    || typeOfUnit == TypeOfUnit.Infantry ||
            typeOfUnit == TypeOfUnit.GrizzlyTank   || typeOfUnit == TypeOfUnit.NavySEAL ||
            typeOfUnit == TypeOfUnit.TankDestroyer || typeOfUnit == TypeOfUnit.PrismTank ){
                unit = new GroundMovableAttackerUnit(Double.parseDouble(attriputes[0]), Double.parseDouble(attriputes[1]),
                        Double.parseDouble(attriputes[2]), typeOfUnit, Integer.parseInt(attriputes[4]), teamType,
                        Integer.parseInt(attriputes[6]) ,Integer.parseInt(attriputes[7]) ,Double.parseDouble(attriputes[8]) ,
                        toAttackTactic(attriputes[9]), listOfTargetType(attriputes[10]) ,Double.parseDouble(attriputes[11]) ,toPlaneOfUnit( attriputes[12] ));
        }
      
        else if(( teamType == TeamType.Defender        || teamType == TeamType.Examples ) && 
                ( typeOfUnit == TypeOfUnit.Pillbox     || typeOfUnit == TypeOfUnit.PrismTower ||
                  typeOfUnit == TypeOfUnit.GrandCannon || typeOfUnit == TypeOfUnit.PatriotMissileSystem ) ){
                unit = new UnMovableAttackerUnit(Double.parseDouble(attriputes[0]), Double.parseDouble(attriputes[1]),
                        Double.parseDouble(attriputes[2]), typeOfUnit, Integer.parseInt(attriputes[4]), teamType,
                        Integer.parseInt(attriputes[6]) ,Integer.parseInt(attriputes[7]) ,Double.parseDouble(attriputes[8]) ,
                        toAttackTactic(attriputes[9]), listOfTargetType(attriputes[10]));
        }
        
        else if( ( teamType == TeamType.Defender || teamType == TeamType.Examples) && typeOfUnit == TypeOfUnit.MAINBASE ){
                unit = new UnMovableUnAttackerUnit(Double.parseDouble(attriputes[0]), Double.parseDouble(attriputes[1]),
                        Double.parseDouble(attriputes[2]), typeOfUnit, Integer.parseInt(attriputes[4]), teamType);
        }
        
        else if( ( teamType == TeamType.Attacker || teamType == TeamType.Examples ) && typeOfUnit == TypeOfUnit.BlackEagle ){
                unit = new AerialMovableAttakerUnit(Double.parseDouble(attriputes[0]), Double.parseDouble(attriputes[1]),
                        Double.parseDouble(attriputes[2]), typeOfUnit, Integer.parseInt(attriputes[4]), teamType,
                        Integer.parseInt(attriputes[6]) ,Integer.parseInt(attriputes[7]) ,Double.parseDouble(attriputes[8]) ,
                        toAttackTactic(attriputes[9]), listOfTargetType(attriputes[10]), Double.parseDouble(attriputes[11]) ,toPlaneOfUnit( attriputes[12] ));
        }
        
        return unit;
    }
    
    private void readUnitLine(String line ,TeamType teamType){
        Scanner stream = new Scanner( line );
        
        for(int i = 0 ; stream.hasNext() ; i++){
            String read = stream.next();
            switch(i){ 
                case 0:  attriputes[3]  = read; break;
                case 1:  attriputes[0]  = read; break;
                case 2:  attriputes[1]  = read; break;
                case 3:  attriputes[7]  = read; break;
                case 4:  attriputes[6]  = read; break;
                case 5:  attriputes[8]  = read; break;
                case 6:  attriputes[2]  = read; break;
                case 7:  attriputes[11] = read; break;
                case 8:  attriputes[9]  = read; break;
                case 9:  attriputes[10] = read; break;
                case 10: attriputes[4]  = read.equals("-") ? "0" : read; break;
                case 11: attriputes[12] = read; break;
            }
        } attriputes[5] = teamType.toString();
        // casr 5:attackspeed in BlackEagle = "-"
    }
    
    private List< TypeOfUnit > listOfTargetType( String allTypes ){
        List< TypeOfUnit > list = new ArrayList();
        Scanner stream = new Scanner(allTypes);  stream.useDelimiter(",");
        while( stream.hasNext() ) list.add( toTargetType(stream.next()) );
        return list;
    }
    
    private TypeOfUnit toTargetType( String type ){
        TypeOfUnit typeOfUnit = null;
        
        switch (type) {
            case "PatriotMissileSystem":
                typeOfUnit = TypeOfUnit.PatriotMissileSystem;
                break;
            case "TankDestroyer":
                typeOfUnit = TypeOfUnit.TankDestroyer;
                break;
            case "GrandCannon":
                typeOfUnit = TypeOfUnit.GrandCannon;
                break;
            case "GrizzlyTank":
                typeOfUnit = TypeOfUnit.GrizzlyTank;
                break;
            case "PrismTower":
                typeOfUnit = TypeOfUnit.PrismTower;
                break;
            case "BlackEagle":
                typeOfUnit = TypeOfUnit.BlackEagle;
                break;
            case "MirageTank":
                typeOfUnit = TypeOfUnit.MirageTank;
                break;
            case "PrismTank":
                typeOfUnit = TypeOfUnit.PrismTank;
                break;
            case "TeslaTank":
                typeOfUnit = TypeOfUnit.TeslaTank;
                break;
            case "Infantry":
                typeOfUnit = TypeOfUnit.Infantry;
                break;
            case "NavySEAL":
                typeOfUnit = TypeOfUnit.NavySEAL;
                break;
            case "MAINBASE":
                typeOfUnit = TypeOfUnit.MAINBASE;
                break;
            case "Pillbox":
                typeOfUnit = TypeOfUnit.Pillbox;
                break;
            case "Sniper":
                typeOfUnit = TypeOfUnit.Sniper;
                break;
        }
        
        return typeOfUnit;
    }
    
    private List< PlaneOfUnit > toPlaneOfUnit( String allPlane ){
        List< PlaneOfUnit > planeOfUnit = new ArrayList();
        Scanner stream = new Scanner( allPlane );
        stream.useDelimiter(",");
        
        while( stream.hasNext() ){
            int type = stream.nextInt();
            
            switch( type ){
                case 1:
                    long time = stream.nextLong();
                    planeOfUnit.add( new P_Wait( time ) );
                    break;
                case 2:
                    int x = stream.nextInt();
                    int y = stream.nextInt();
                    planeOfUnit.add( new P_Move( new Position(x, y) ) );
                    break;
                default:
                    break;
            }
        }
        
        return planeOfUnit;
    }
    
    private AttackTactic toAttackTactic( String attackTactic ){
        AttackTactic tactic = null;
        
        switch (attackTactic) {
            case "HighDamageTactic":
                tactic = new HighDamageTactic();
                break;
            case "LowHealthTactic":
                tactic = new LowHealthTactic();
                break;
            case "PriorityTactic":
                tactic = new PriorityTactic();
                break;
            case "RandomTactic":
                tactic = new RandomTactic();
                break;
        }
        
        return tactic;
    }
    
    // Singelton Design Pattren
    private static UnitFactory unitFactory = null;
    
    public static UnitFactory getOpj() {
        if( UnitFactory.unitFactory == null )  UnitFactory.unitFactory = new UnitFactory();
        return UnitFactory.unitFactory;
    }
}

// help
// System.out.println(Double.parseDouble(attriputes[0]) + " " + Double.parseDouble(attriputes[1])+ " " +Double.parseDouble(attriputes[2])+ " " + typeOfUnit+ " " + Integer.parseInt(attriputes[4])+ " " + teamType+ " " +Integer.parseInt(attriputes[6]) + " " +Integer.parseInt(attriputes[7]) + " " +Double.parseDouble(attriputes[8]) + " " +toAttackTactic(attriputes[9])+ " " + listOfTargetType(attriputes[10]) + " " + Double.parseDouble(attriputes[11]));
// TypeName MaxHealth Armor AttackDamge	AttackRange AttackSpeed	Radius MovementSpeed Tactic CanTarget UnitPrice
