package tactics.attack_tactics;

import java.util.List;
import units.unit.*;

public class RandomTactic extends AttackTactic{
    @Override
    public Unit getTarget( Unit unit ) {
        List< Unit > units = ((AttackerUnit)(unit)).getUnitsAround();
        int randomIdx = (int)( Math.random() * (double)(units.size() - 1) );
        return units.get(randomIdx);
    }
}
