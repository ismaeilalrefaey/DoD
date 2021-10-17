package tactics.attack_types;

import files.logFile.WriteToLogFile;
import units.unit.AttackerUnit;
import units.unit.Unit;

public class NormalAttack extends AttackType {
    @Override
    public void doAttack( Unit attacker , Unit target ) {
        target.hitUnit( ((AttackerUnit)(attacker)).getAttackDamage() );
        WriteToLogFile.write(attacker.getTypeOfUnit()+ " attacks " +  target.getTypeOfUnit() +
                " \t " + " the health of" +target.getTypeOfUnit()+ " is :" + target.getHealth()
                + " information about" + target.getTypeOfUnit() + " [" + target.getTeamType() + " , " + target.getUnitId()+ " ] " );
    }
}
