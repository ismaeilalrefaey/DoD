package tactics.attack_tactics;

import units.unit.Unit;

public abstract class AttackTactic {
    public abstract Unit getTarget( Unit unit );
}
