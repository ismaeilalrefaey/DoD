package units.unit_plane;

public class P_Wait extends PlaneOfUnit {
    private long remainingTime;
    
    public P_Wait(long remainingTime){
        this.remainingTime = remainingTime;
    }
    
    public void sub(long delta){
        this.remainingTime -= delta;
    }
    
    public long getRemainingTime(){
        return this.remainingTime;
    }
}
