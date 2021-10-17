package users.user;

import javagame.util.DorD;
import javax.swing.*;

public abstract class User extends JPanel {
    protected DorD dordGame;
    
    public void newGame(int points){
        dordGame = new DorD(10000,points);
    }

    public User(){ super(); }

    public abstract void initGame();
    public abstract void numPlayers();
    public abstract void deployUnitsOnGrid();
    public abstract void buyUnitsForPlayers();
}

// 1 - new Game 
// 2 - init Game
// 3 - Dord.Start Game