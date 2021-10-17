package tactics.attack_types;

import units.unit.Unit;

public abstract class AttackType {
    public abstract void doAttack( Unit attacker ,Unit target );
}
