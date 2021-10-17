package users.user.GameState;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.*;

public abstract class GameState {
    protected GameStateManager gsm;

    public abstract void keyPressed(int k) throws IOException, FontFormatException;
    public abstract void init() throws IOException, FontFormatException;
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void Mouseclicked(MouseEvent m);
    public abstract void keyReleased(int k);
    public abstract void update();
}
