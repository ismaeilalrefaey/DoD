package units.additional;

public class Position {
    private int xAxis;
    private int yAxis;
    
    public Position(int xAxis ,int yAxis){
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }
    
    public int getX(){
        return this.xAxis;
    }
    
    public int getY(){
        return this.yAxis;
    }
}
