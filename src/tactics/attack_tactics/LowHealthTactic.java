package tactics.attack_tactics;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import units.unit.*;

public class LowHealthTactic extends AttackTactic{
    @Override
    public Unit getTarget( Unit unit ) {
        List< Unit > units = ((AttackerUnit)(unit)).getUnitsAround();
       
        TreeMap<Double , List< Unit > > treeMap = new TreeMap();
        for(int i = 0 ; i < units.size() ; i++){
            if( !treeMap.containsKey( unit.getHealth() ) )
                treeMap.put(unit.getHealth() , new ArrayList());
            
            treeMap.get( unit.getHealth() ).add( units.get(i) );
        }
        
        List< Unit > sortedUnits = new ArrayList();
        for( Map.Entry<Double ,List< Unit > > i : treeMap.entrySet() )
            for(int j = 0 ; j < i.getValue().size() ; j++)
                sortedUnits.add( i.getValue().get(j) );
        
        return sortedUnits.get(0);   
    }
}
