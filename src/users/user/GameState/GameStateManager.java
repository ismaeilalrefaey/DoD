package users.user.GameState;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import users.user.User;
import java.awt.*;

public class GameStateManager {
    private ArrayList< GameState > gameStates; //array of game state : menu ,store ,game,
    private int currentState;

    public static final int menuState   = 0;   // index of menu state
    public static final int BattleState = 1;

    public GameStateManager(User user) throws IOException {
        gameStates   = new ArrayList();
        currentState = menuState;
        
        gameStates.add( new MenuState( this ) );
        gameStates.add( new BattleState(this ,user) );
    }

    public void setState( int state ) throws IOException, FontFormatException {
        currentState = state;
        gameStates.get( currentState ).init();
    }

    public void update() {
        gameStates.get( currentState ).update();
    }

    public void draw(java.awt.Graphics2D g) {
        gameStates.get( currentState ).draw(g);
    }

    public void keyPressed( int k ) throws IOException, FontFormatException {
        gameStates.get( currentState ).keyPressed(k);
    }

    public void keyReleased( int k ) {
        gameStates.get( currentState ).keyReleased(k);
    }

    public void Mouseclicked( MouseEvent m ) {
        gameStates.get( currentState ).Mouseclicked(m);
    }
}









