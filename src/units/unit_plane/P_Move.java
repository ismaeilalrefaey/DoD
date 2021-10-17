package units.unit_plane;

import tactics.move_tactics.MoveTactic;
import tactics.move_tactics.M_Plane;
import units.additional.Position;

public class P_Move extends PlaneOfUnit {
    private Position destination;
    private MoveTactic moveTactic;
    
    public P_Move(Position destination){
        this.destination = destination;
        this.moveTactic  = new M_Plane();
    }
    
    public MoveTactic getMoveTactic(){
        return this.moveTactic;
    }
    
    public Position getTargetPos(){
        return this.destination;
    }
}
