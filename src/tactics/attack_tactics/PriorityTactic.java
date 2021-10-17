package tactics.attack_tactics;

import java.util.ArrayList;
import units.additional.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import units.unit.*;

public class PriorityTactic extends AttackTactic {
    @Override
    public Unit getTarget( Unit unit ) {
        List< Unit > unitsAround = ((AttackerUnit)(unit)).getUnitsAround();
        List< TypeOfUnit > targetTypes = ((AttackerUnit)(unit)).getTargetsTypes();
        
        Map<TypeOfUnit ,Integer> idxOfTargetPriority = new HashMap();
        for(int i = 0 ; i < targetTypes.size() ; i++)
            idxOfTargetPriority.put(targetTypes.get(i) ,i);
            
        List< List< Unit > > sortCnt = new ArrayList();
        for(int i = 0 ; i < targetTypes.size() ; i++) sortCnt.add( new ArrayList() );
        
        for(int i = 0 ; i < unitsAround.size() ; i++)
            sortCnt.get( idxOfTargetPriority.get( unitsAround.get(i).getTypeOfUnit() ) ).add( unitsAround.get(i) );
        
        Unit result = null;
        for(int i = 0 ; i < sortCnt.size() ; i++)
            if( sortCnt.get(i).size() > 0 ){
                result = sortCnt.get(i).get(0);
                break;
            }
        
        return result;
    }
}
