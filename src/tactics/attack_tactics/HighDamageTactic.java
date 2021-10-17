package tactics.attack_tactics;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import units.unit.*;

public class HighDamageTactic extends AttackTactic{
    @Override
    public Unit getTarget( Unit unit ) {
        List< Unit > units = ((AttackerUnit)(unit)).getUnitsAround();
       
        TreeMap<Integer , List< Unit > > treeMap = new TreeMap();
        for(int i = 0 ; i < units.size() ; i++){
            if( !treeMap.containsKey( ((AttackerUnit)(unit)).getAttackDamage()) )
                treeMap.put(((AttackerUnit)(unit)).getAttackDamage(), new ArrayList());
            
            treeMap.get(((AttackerUnit)(unit)).getAttackDamage()).add( units.get(i) );
        }
        
        List< Unit > sortedUnits = new ArrayList();
        for( Map.Entry<Integer ,List< Unit > > i : treeMap.entrySet() )
            for(int j = 0 ; j < i.getValue().size() ; j++)
                sortedUnits.add( i.getValue().get(j) );
        
        return sortedUnits.get( sortedUnits.size() - 1 );   
    }
}
