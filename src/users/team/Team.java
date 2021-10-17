package users.team;

import java.util.ArrayList;
import java.util.List;

public abstract class Team {
    protected List< Player > players = new ArrayList();
    
    public void addPlayer( Player player ){
        this.players.add( player );
    }
    
    public List< Player > getPlayersList(){
        return this.players;
    }
    
}
