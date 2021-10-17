package grid;

import files.logFile.WriteToLogFile;
import java.util.ArrayList;
import units.additional.*;
import java.util.HashSet;
import java.util.TreeMap;
import javafx.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import units.unit.*;

public class Areana {
    
    private TreeMap<Long , Pair<Boolean , List< Unit > > > sortedUnits; // bool for ground // list for all
    private TreeMap<Long , Unit> reserved;       // ground visited positions
    public static int numOfCells;
    private Set< Long > bridges;
    private Set< Long > rivers;
    private Set< Long > closed;
    private Radar radar;
    
    public Areana(int numOfCells) {
        Areana.numOfCells = numOfCells;
        this.sortedUnits  = new TreeMap();
        this.reserved     = new TreeMap();
        this.closed       = new HashSet();
        this.rivers       = new HashSet();
        this.bridges      = new HashSet();
        this.radar        = new Radar();
        makeSingelton();
        fillRVB(); // fill rivers ,closed and bridges maps
    }
    
    public class Radar  {
        public synchronized List< Unit > scanAround(AttackerUnit unit){
            List< Pair<Long , Unit> > units = getListOfUntis();  // list of all unit at a moment for binarySearch
            Position unitPos = unit.getPosition();
            int attackRange  = unit.getAttackRange();

            Set< TypeOfUnit > can = new HashSet();
            List< TypeOfUnit > unitTargets = unit.getTargetsTypes();
            for(int i = 0 ; i < unitTargets.size() ; i++)
                can.add( unitTargets.get(i) );

            Set< Unit > unitsAround = new HashSet();

            try{
                for(int xAxis = unitPos.getX() - attackRange ; xAxis <= unitPos.getX() + attackRange ; xAxis ++){
                    int xAxisLIdx = smalestBinarySearch(0 ,units.size() - 1 ,xAxis ,unitPos ,attackRange ,true ,units); // log
                    int xAxisRIdx = largestBinarySeaech(0 ,units.size() - 1 ,xAxis ,unitPos ,attackRange ,true ,units); // log

                    if( xAxisLIdx != -1 ){
                        int smalestYAxisNeeded = unitPos.getY() - attackRange;
                        int largestYAxisNeeded = unitPos.getY() + attackRange;

                        int yAxisLIdx = smalestBinarySearch(xAxisLIdx ,xAxisRIdx ,smalestYAxisNeeded ,unitPos ,attackRange ,false ,units); // log
                        int yAxisRIdx = largestBinarySeaech(xAxisLIdx ,xAxisRIdx ,largestYAxisNeeded ,unitPos ,attackRange ,false ,units); // log

                        if( yAxisLIdx != -1 )
                            for(int i = yAxisLIdx ; i <= yAxisRIdx ; i++)
                                if( unit.getTeamType() != units.get(i).getValue().getTeamType() ) // filltering \
                                    if( can.contains( units.get(i).getValue().getTypeOfUnit() ) ) // cur unit
                                        unitsAround.add( units.get(i).getValue() );
                    }
                }
            } catch(Exception ex) {
                System.out.println("scanAround Error !! " + ex.getMessage());
            }
            
            List< Unit > ans = new ArrayList(); for( Unit i : unitsAround ) ans.add(i);
            return ans;
        }

        private int smalestBinarySearch(int L ,int R ,int val ,Position mainUnitPos ,int mainUnitRange ,boolean xAxisNeeded ,List< Pair<Long , Unit> > units){
            int result = -1;

            try{
                while( L <= R ){
                    int Mid = (L + R) / 2;

                    Position midUnitPos = deHash( units.get(Mid).getKey() );

                    if( (xAxisNeeded ? midUnitPos.getX() : midUnitPos.getY()) > val ) {
                        if( !xAxisNeeded && isInTheRange(midUnitPos ,mainUnitPos ,mainUnitRange) ){
                            result = Mid;
                        }
                        R = Mid - 1;
                    } else if( (xAxisNeeded ? midUnitPos.getX() : midUnitPos.getY()) < val ) {
                        if( !xAxisNeeded && isInTheRange(midUnitPos ,mainUnitPos ,mainUnitRange) ){
                            result = Mid;
                        }
                        L = Mid + 1;
                    } else {
                        result = Mid;
                        R = Mid - 1;
                    }
                }
            } catch(Exception ex) {
                System.out.println("smalestBinarySearch Error !! " + ex.getMessage());
            }

            return result;
        }

        private int largestBinarySeaech(int L ,int R ,int val ,Position mainUnitPos ,int mainUnitRange ,boolean xAxisNeeded ,List< Pair<Long , Unit> > units){
            int result = -1;

            try{
                while( L <= R ){
                    int Mid = (L + R) / 2;

                    Position midUnitPos = deHash( units.get(Mid).getKey() );

                    if( (xAxisNeeded ? midUnitPos.getX() : midUnitPos.getY()) > val ) {
                        if( !xAxisNeeded && isInTheRange(midUnitPos ,mainUnitPos ,mainUnitRange) ){
                            result = Mid;
                        }
                        R = Mid - 1;
                    } else if( (xAxisNeeded ? midUnitPos.getX() : midUnitPos.getY()) < val ) {
                        if( !xAxisNeeded && isInTheRange(midUnitPos ,mainUnitPos ,mainUnitRange) ){
                            result = Mid;
                        }
                        L = Mid + 1;
                    } else {
                        result = Mid;
                        L = Mid + 1;
                    }
                }
            } catch( Exception ex ) {
                System.out.println("largestBinarySeaech Error !! " + ex.getMessage());
            }
            return result;
        }

        private List< Pair<Long , Unit> > getListOfUntis(){
            List< Pair<Long , Unit> > units = new ArrayList();
            List< Pair<Long ,Unit> > units1 = new ArrayList();
            List< Pair<Long ,Unit> > units2 = new ArrayList();

            for(Map.Entry<Long ,Unit> i : reserved.entrySet())
                units1.add( new Pair(i.getKey() ,i.getValue()) );

            for(Map.Entry<Long ,Pair<Boolean ,List< Unit > > > i : sortedUnits.entrySet())
                for(int j = 0 ; j < i.getValue().getValue().size() ; j++)
                    if( i.getValue().getValue().get(j) instanceof AerialMovableAttakerUnit )
                        units2.add( new Pair(hash( i.getValue().getValue().get(j).getPosition() ) , i.getValue().getValue().get(j) ) );

            int i = 0 ,j = 0;
            int n = units1.size();
            int m = units2.size();

            try{
                while( i < n && j < m ){
                    if(units1.get(i).getKey()<= units2.get(j).getKey()) {
                        units.add( units1.get(i) );
                        i++;
                    } else {
                        units.add( units2.get(j) );
                        j++;
                    }
                }

                while( i < n ) {
                    units.add( units1.get(i) );
                    i++;
                }

                while( j < m ) {
                    units.add( units2.get(j) );
                    j++;
                }
            } catch (Exception ex) {
                System.out.println("getListOfUntis Error !! " + ex.getMessage());
            }
            
            return units;
        }

        private boolean isInTheRange(Position unitPos ,Position position ,int range){
            return (position.getY() - range <= unitPos.getY() && unitPos.getY() <= position.getY() + range);
        }
    }
    
    public List< Unit > scanAround(AttackerUnit unit){
        return radar.scanAround( unit );
    }
        
    private void fillRVB(){
        for(int x = 4000 ,y = 2000 ; x < 6000 ; x += 100 ,y += 50)
            for(int i = x ; i < x + 100 ; i ++)
                for(int j = y ; j < y + 100 ; j ++)
                    rivers.add( hash( new Position(i ,j) ) );
        
        closed.add( hash( new Position(2000 ,2000) ) );
        closed.add( hash( new Position(6000 ,7000) ) );
        closed.add( hash( new Position(4994 ,5950) ) );
        for(int i = 4994 ; i < 5000 ; i ++)
            for(int j = 5950 ; j < 5955 ; j ++)
                closed.add( hash( new Position(i ,j) ) );
                
        for(int i = 4994 ,j = 5950 ; i <= 5000 ; i++ ,j++){
            bridges.add( hash( new Position(i ,j) ) );
            bridges.add( hash( new Position(i + 1 ,j) ) );
            bridges.add( hash( new Position(i ,j - 1) ) );
            bridges.add( hash( new Position(i + 2 ,j) ) );
            bridges.add( hash( new Position(i ,j - 2) ) );
            bridges.add( hash( new Position(i + 3 ,j) ) );
            bridges.add( hash( new Position(i ,j - 3) ) );
        }
    }
    
    public synchronized void addUnit( Unit unit ){
        Long pos = hash( unit.getPosition() );
        
        if( !sortedUnits.containsKey(pos) )
            sortedUnits.put( pos , new Pair(false ,new ArrayList() ) );
        
        if( unit instanceof GroundMovableAttackerUnit || unit instanceof GroundMovableUnAttackerUnit ||
            unit instanceof UnMovableAttackerUnit     || unit instanceof UnMovableUnAttackerUnit  ){
            //add in sorted Units map
            try{
                Pair<Boolean ,List< Unit > > temp = new Pair(true ,sortedUnits.get(pos).getValue());
                temp.getValue().add( unit );
                
                sortedUnits.remove(pos ,sortedUnits.get(pos));
                sortedUnits.put(pos ,temp);
            } catch( Exception ex ){
                System.out.println("addUnit Error !! " + ex.getMessage());
            }
            
            //add in reserved map
                for(int i = (int)(unit.getPosition().getX() - unit.getRadius()) ; i < unit.getPosition().getX() + unit.getRadius() ; i++)
                    for(int j = (int)(unit.getPosition().getY() - unit.getRadius()) ; j < unit.getPosition().getY() + unit.getRadius() ; j++)
                        if( Math.pow(i - unit.getPosition().getX() ,2) + Math.pow(j - unit.getPosition().getY() ,2) <= Math.pow(unit.getRadius() ,2) )
                            reserved.put( hash( new Position(i ,j) ) , unit );
        } else {
            sortedUnits.get( pos ).getValue().add( unit );
        }   
    }
    
    public synchronized void removeUnit( Unit unit ){
        Long pos = hash( unit.getPosition() );
        if( unit instanceof GroundMovableAttackerUnit || unit instanceof GroundMovableUnAttackerUnit ||
            unit instanceof UnMovableAttackerUnit     || unit instanceof UnMovableUnAttackerUnit  ) {
            // remove from sortedUnits
            try{
                Pair<Boolean ,List< Unit > > temp = new Pair(false ,sortedUnits.get(pos).getValue());
                temp.getValue().remove( unit );
               
                sortedUnits.remove(pos);
                if( !temp.getValue().isEmpty() )
                     sortedUnits.put(pos ,temp);
            } catch( Exception ex ) {
                System.out.println("removeUnit1 Error !! " + ex.getMessage());
            }
            
            // remove from reserved    
                for(int i = (int)(unit.getPosition().getX() - unit.getRadius()) ; i < unit.getPosition().getX() + unit.getRadius() ; i++)
                    for(int j = (int)(unit.getPosition().getY() - unit.getRadius()) ; j < unit.getPosition().getY() + unit.getRadius() ; j++)
                        if( Math.pow(i - unit.getPosition().getX() ,2) + Math.pow(j - unit.getPosition().getY() ,2) <= Math.pow(unit.getRadius() ,2))
                            try{
                                reserved.remove( hash( new Position(i ,j) ) );
                            } catch( Exception ex ) {
                                System.out.println("removeUnit2 Error !! " + ex.getMessage());
                            }
        } else {
            try{
                sortedUnits.get(pos).getValue().remove( unit );
            } catch( Exception ex ) {
                System.out.println("removeUnit3 Error !! " + ex.getMessage());
            }
        }
    }
   
    public synchronized boolean isValid( Position pos ,Unit unit ){
        boolean result = isValid(pos.getX() ,pos.getY());
        
        if( !(unit instanceof AerialMovableUnAttackerUnit) && !(unit instanceof AerialMovableAttakerUnit) ){
            result &= (!closed.contains( hash(pos) ) || bridges.contains( hash(pos) ));
            for(int i = (int)(pos.getX() - unit.getRadius()) ; i < pos.getX() + unit.getRadius() ; i++)
                for(int j = (int)(pos.getY() - unit.getRadius()) ; j < pos.getY() + unit.getRadius() ; j++)
                    if( Math.pow(i - pos.getX() ,2) + Math.pow(j - pos.getY() ,2 ) <= Math.pow(unit.getRadius() ,2) )
                        result &= isValid(i ,j) &&
                                (!reserved.containsKey( hash( new Position(i ,j) ) ) ||
                                reserved.get( hash(new Position(i ,j)) ).getId() == unit.getId());
        }
        
        return result;
    }

    public boolean isValid(int x , int y){
        return (x >= 0 && y >= 0 && x < numOfCells && y < numOfCells);
    }

    public boolean isRiver( Position pos ){
        return rivers.contains( hash(pos) );
    }
    
    private Long hash( Position pos ){
        return (long)pos.getX() * 1000000l + pos.getY();
    }
    
    private Position deHash( Long hashVal ){
        int x = (int)(hashVal / 1000000l);
        int y = (int)(hashVal % 1000000l);
        return new Position(x ,y);
    }
    
    public synchronized List< Position > getClosed() {
        List< Position > closedPos = new ArrayList();
        
        for(Long i : closed) if( i != null )
            closedPos.add( deHash( i ) );
        
        return closedPos;
    }
    
    public synchronized List< Position > getRivers() {
        List< Position > riversPos = new ArrayList();
        
        for(Long i : rivers) if( i != null )
            riversPos.add( deHash( i ) );
        
        return riversPos;
    }
    
    public synchronized List< Position > getBridges() {
        List< Position > bridgesPos = new ArrayList();
        
        for(Long i : rivers) if( i != null )
            bridgesPos.add( deHash( i ) );
        
        return bridgesPos;
    }
    
    public synchronized List< Unit > getUnits() {
        List< Unit > units = new ArrayList();
        
        for(Map.Entry<Long , Pair<Boolean , List< Unit > > > i : sortedUnits.entrySet())
            if( i != null && i.getValue() != null && i.getValue().getValue() != null && i.getValue().getValue().size() > 0 )
                for(int j = 0 ; j < i.getValue().getValue().size() ; j++)
                    units.add( i.getValue().getValue().get(j) );
        
        return units;
    }

    /// --- Singelton DesignPattren --- ///
    private static Areana areana = null;
    
    public static Areana getOpj(){
        return Areana.areana;
    }
    
    private void makeSingelton() {
        Areana.areana = this;    
    }
}
