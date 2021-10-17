package users.user.GameState;

import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.*;

public class MenuState extends GameState {

    public BufferedImage menu = ImageIO.read(this.getClass().getResource("/java-01.jpg"));
    private String[] options  = { "Start" ,"Quit" };
    private int currentChoice = 0;
    private Font font;

    public MenuState(GameStateManager gsm) throws IOException {
        this.gsm = gsm;
        try {
            Font scFont = Font.createFont(Font.TRUETYPE_FONT,getClass().getResourceAsStream("/res/impact.ttf"));
            font        = scFont.deriveFont(30f);
        } catch(FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() { /* Do Nothing */ }
    @Override
    public void update() { /* Do Nothing */ }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(menu,0,0,null);

        /// --- draw menu options --- ///
        g.setFont(font);
        for(int i = 0; i < options.length; i++) {
            g.setColor((i == currentChoice) ? Color.WHITE : Color.DARK_GRAY);
            g.drawString(options[i], 500, 250+i*60);
        }
    }

    private void select() throws IOException, FontFormatException {
        if(currentChoice == 0) { // start
            gsm.setState(1);
        }
        if(currentChoice == 1) { // quit
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) throws IOException, FontFormatException {
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        
        if(k == KeyEvent.VK_UP) {
            currentChoice--;
            if(currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        
        if(k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }
    
    @Override
    public void keyReleased(int k) { /* Do Nothing */ }
    @Override
    public void Mouseclicked(MouseEvent m) { /* Do Nothing */ }
}










