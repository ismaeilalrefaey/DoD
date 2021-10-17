package users.user.GameState;

import java.awt.image.BufferedImage;
import units.additional.TypeOfUnit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import units.additional.Position;
import units.unit.AttackerUnit;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import users.team.TeamType;
import javagame.util.DorD;
import users.user.GuiUser;
import units.unit.Unit;
import users.user.User;
import java.util.List;
import javax.swing.*;
import grid.Areana;
import java.awt.*;

public class BattleState extends GameState {

    private boolean gameisReady = false;
    private double rscale = 0.7;
    private Font scFont = null;
    private double recx = 940;
    private double recy = 547;
    private int scale = 50;
    private int dx = 4800;
    private int dy = 4800;
    private int maxloop;

    // Images ----------------------------------------------------------------------------------------------
    
    public BufferedImage TeslaTankA = ImageIO.read(this.getClass().getResource("/res/Tesla_TankA.png"));
    public BufferedImage TeslaTankD = ImageIO.read(this.getClass().getResource("/res/Tesla_TankD.png"));

    public BufferedImage SniperA = ImageIO.read(this.getClass().getResource("/res/SniperA.png"));
    public BufferedImage SniperD = ImageIO.read(this.getClass().getResource("/res/SniperD.png"));

    public BufferedImage Mirage_tankA = ImageIO.read(this.getClass().getResource("/res/Mirage_tankA.png"));
    public BufferedImage Mirage_tankD = ImageIO.read(this.getClass().getResource("/res/Mirage_tankD.png"));

    public BufferedImage InfantryA = ImageIO.read(this.getClass().getResource("/res/InfantryA.png"));
    public BufferedImage InfantryD = ImageIO.read(this.getClass().getResource("/res/InfantryD.png"));

    public BufferedImage Grizzly_TankA = ImageIO.read(this.getClass().getResource("/res/Grizzly_TankA.png"));
    public BufferedImage Grizzly_TankD = ImageIO.read(this.getClass().getResource("/res/Grizzly_TankD.png"));

    public BufferedImage Navy_SEALA = ImageIO.read(this.getClass().getResource("/res/Navy_SealA.png"));
    public BufferedImage Navy_SEALD = ImageIO.read(this.getClass().getResource("/res/Navy_SealD.png"));

    public BufferedImage Tank_destroyerA = ImageIO.read(this.getClass().getResource("/res/Tank_DestroyerA.png"));
    public BufferedImage Tank_destroyerD = ImageIO.read(this.getClass().getResource("/res/Tank_DestroyerD.png"));

    public BufferedImage Prism_tankA = ImageIO.read(this.getClass().getResource("/res/Prism_TankA.png"));
    public BufferedImage Prism_tankD = ImageIO.read(this.getClass().getResource("/res/Prism_TankD.png"));

    public BufferedImage Black_EagleA = ImageIO.read(this.getClass().getResource("/res/Black_EagleA.png"));
    public BufferedImage Black_EagleD = ImageIO.read(this.getClass().getResource("/res/Black_EagleD.png"));

    public BufferedImage Grand_Cannon = ImageIO.read(this.getClass().getResource("/res/Grand_Cannon.png"));
    public BufferedImage MAIN_BASE = ImageIO.read(this.getClass().getResource("/res/MAIN_BASE.png"));
    public BufferedImage Prism_Tower = ImageIO.read(this.getClass().getResource("/res/Prism_Tower.png"));
    public BufferedImage Patriot_Missile_System = ImageIO.read(this.getClass().getResource("/res/Patriot_Missile_System.png"));
    public BufferedImage Pillbox = ImageIO.read(this.getClass().getResource("/res/Pillbox.png"));
    public BufferedImage riverp = ImageIO.read(this.getClass().getResource("/res/riverp.png"));
    
    public BufferedImage bridgep = ImageIO.read(this.getClass().getResource("/res/bridgep.png"));
    public BufferedImage valleyp = ImageIO.read(this.getClass().getResource("/res/valleyp.png"));
    public BufferedImage panel = ImageIO.read(this.getClass().getResource("/res/background.png"));
    
    // Images ----------------------------------------------------------------------------------------------
    
    public BufferedImage TeslaTank = ImageIO.read(this.getClass().getResource("/res/Tesla_Tank.png"));
    public BufferedImage Sniper = ImageIO.read(this.getClass().getResource("/res/Sniper.png"));
    public BufferedImage Mirage_tank = ImageIO.read(this.getClass().getResource("/res/Mirage_tank.png"));
    public BufferedImage Infantry = ImageIO.read(this.getClass().getResource("/res/Infantry.png"));
    public BufferedImage Grizzly_Tank = ImageIO.read(this.getClass().getResource("/res/Grizzly_Tank.png"));
    public BufferedImage Navy_SEAL = ImageIO.read(this.getClass().getResource("/res/Navy_SEAL.png"));
    public BufferedImage Tank_destroyer = ImageIO.read(this.getClass().getResource("/res/Tank_destroyer.png"));
    public BufferedImage Prism_tank = ImageIO.read(this.getClass().getResource("/res/Prism_tank.png"));
    public BufferedImage Pillboxl = ImageIO.read(this.getClass().getResource("/res/Pillboxl.png"));
    public BufferedImage Prism_Towerl = ImageIO.read(this.getClass().getResource("/res/Prism_Towerl.png"));
    public BufferedImage Grand_Cannonl = ImageIO.read(this.getClass().getResource("/res/Grand_Cannonl.png"));
    public BufferedImage MAIN_BASEl = ImageIO.read(this.getClass().getResource("/res/MAIN_BASEl.png"));
    public BufferedImage Black_Eagle = ImageIO.read(this.getClass().getResource("/res/Black_Eagle.png"));
    public BufferedImage Patriot_Missile_Systeml = ImageIO.read(this.getClass().getResource("/res/Patriot_Missile_Systeml.png"));
    public BufferedImage arrow_1 = ImageIO.read(this.getClass().getResource("/res/arrow_1.png"));
    public BufferedImage arrow_2 = ImageIO.read(this.getClass().getResource("/res/arrow_2.png"));
    public BufferedImage arrow_3 = ImageIO.read(this.getClass().getResource("/res/arrow_3.png"));
    public BufferedImage arrow_4 = ImageIO.read(this.getClass().getResource("/res/arrow_4.png"));
    
    public BufferedImage paused = ImageIO.read(this.getClass().getResource("/res/pause.png"));
    
    // Lables --------------------------------------------------------------------------------------------------

    BufferedImage image;
    JLabel Map = new JLabel();
    JLabel Time = new JLabel();
    JLabel Base = new JLabel();
    JLabel type = new JLabel();
    JLabel Team = new JLabel();
    JLabel Player = new JLabel();
    JLabel Health = new JLabel();
    JLabel Armour = new JLabel();
    JLabel Damage = new JLabel();
    JLabel Position = new JLabel();

    // Lables --------------------------------------------------------------------------------------------------

    private List<Position> bridges;
    private List<Position> rivers;
    private List<Position> valley;
    private List<Unit> units;
    private Unit info;

    private DorD dordGame;
    private Areana areana;
    private User user;

    Dist distb = Dist.Up;
    boolean distbol = false;

    public BattleState(GameStateManager gsm, User user) throws IOException {
        try {
            scFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/res/impact.ttf"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

        this.areana   = Areana.getOpj();
        this.dordGame = DorD.getOpj();
        this.user     = (GuiUser)user;
        this.gsm      = gsm;
    }


    @Override
    public void init() {
        ((GuiUser) user).newGame(3000);
        ((GuiUser) user).numPlayers();
        ((GuiUser) user).buyUnitsForPlayers();
        ((GuiUser) user).deployUnitsOnGrid();

        this.dordGame = DorD.getOpj();
        this.areana   = Areana.getOpj();
        dordGame.initTimeOfGame();
        gameisReady = true;
    }

    @Override
    public void update() {
        if( gameisReady ) {
            if (dordGame.getIsPaused()) {
                dordGame.pause();
            } else {
                dordGame.update();
            }

            if (dordGame.getRemainingTime() <= 0 || !dordGame.getBase().getIsAlife() || dordGame.getAttackerTeamUnits() <= 0) {
                gameisReady = false;
                info        = null;
                
                TeamType winnerTeam = dordGame.getBase().getIsAlife() ? TeamType.Defender : TeamType.Attacker;
                System.out.println("\n--------------------------------");
                System.out.println(" |" + winnerTeam + " Team Win <3 |");
                System.out.println("--------------------------------");
                
                try {
                    gsm.setState( 0 );
                } catch (IOException | FontFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if( gameisReady ) {
            units   = areana.getUnits();
            rivers  = areana.getRivers();
            bridges = areana.getBridges();
            valley  = areana.getClosed();

            g.drawImage(panel, 0, 0, null);

            maxloop = Math.max(Math.max(units.size(), rivers.size()), Math.max(bridges.size(), valley.size()));

            for (int i = 0; i < maxloop; i++) {
                if (i < rivers.size())  drawriver(g, i);
                if (i < bridges.size()) drawbridg(g, i);
                if (i < valley.size())  drawvalley(g, i);
                if (i < units.size())   drawunits(g, i);
            }

            g.setFont(scFont.deriveFont(15f));

            try {
                labelsdraw(g);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            distnotify(g);
            g.setColor(Color.getHSBColor(48, 21, 90));
            g.drawRect((int) recx, (int) recy, 5, 5);
            mapdis(g);
        }
    }

    @Override
    public void keyPressed(int k) throws IOException, FontFormatException {
        if (k == KeyEvent.VK_ESCAPE) {
            gameisReady = false;
            info = null;
            dx   = 4800;
            dy   = 4800;
            recx = 940;
            recy = 547;
            gsm.setState(0);
        }

        if (k == KeyEvent.VK_UP) {
            if (dy > 0) {
                dy     -= scale;
                recy   -= rscale;
                distbol = true;
                distb   = Dist.Up;
            }
        }

        if (k == KeyEvent.VK_DOWN) {
            if (dy < 9500) {
                dy     += scale;
                recy   += rscale;
                distbol = true;
                distb   = Dist.Down;
            }
        }

        if (k == KeyEvent.VK_RIGHT) {
            if (dx < 9500) {
                dx     += scale;
                recx   += rscale;
                distbol = true;
                distb   = Dist.Right;
            }
        }

        if (k == KeyEvent.VK_LEFT) {
            if (dx > 0) {
                dx     -= scale;
                recx   -= rscale;
                distbol = true;
                distb   = Dist.Left;
            }
        }
        
        if (k == KeyEvent.VK_SPACE) {
            dordGame.setIsPaused();
        }
    }

    @Override
    public void keyReleased(int k) { }

    @Override
    //Mouse Event
    public void Mouseclicked(MouseEvent m) {

        //select unit by click
        if ((m.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            if (m.getX() >= 50 && m.getX() <= 650 && m.getY() >= 50 && m.getY() <= 650) {
                for (Unit a : units) {
                    if (m.getX() + dx - 100 >= a.getPosition().getX() - a.getRadius() &&
                        m.getX() + dx - 100 <= a.getPosition().getX() + a.getRadius() &&
                        m.getY() + dy - 100 >= a.getPosition().getY() - a.getRadius() &&
                        m.getY() + dy - 100 <= a.getPosition().getY() + a.getRadius()) {
                            info = a;
                    }
                }
            }

            //move in radar by click
            else if (m.getX() >= 873 && m.getX() <= 1013 && m.getY() >= 481 && m.getY() <= 621) {
                if (m.getX() >= 1006 && m.getY() >= 614) {
                    dx = 9500;
                    dy = 9500;
                } else if (m.getX() >= 1006) {
                    dx = 9500;
                    dy = ((m.getY() - 481) * 10000 / 140) - ((m.getY() - 481) * 10000 / 140) % 50;
                } else if (m.getY() >= 614) {
                    dx = ((m.getX() - 873) * 10000 / 140) - ((m.getX() - 873) * 10000 / 140) % 50;
                    dy = 9500;
                } else {
                    dx = ((m.getX() - 873) * 10000 / 140) - ((m.getX() - 873) * 10000 / 140) % 50;
                    dy = ((m.getY() - 481) * 10000 / 140) - ((m.getY() - 481) * 10000 / 140) % 50;
                }

                recx = (dx * 140 / 10000) + 873 - ((dx * 140 / 10000) + 873) % 0.7;
                recy = (dy * 140 / 10000) + 481 - ((dy * 140 / 10000) + 481) % 0.7;
            }
        }
    }

    public void distnotify(Graphics g2) {
        if(distbol) {
            switch (distb) {
                case Up:
                    g2.drawImage(arrow_1,341,60,null);
                    break;
                case Down:
                    g2.drawImage(arrow_3,342,628,null);
                    break;
                case Left:
                    g2.drawImage(arrow_2,60,342,null);
                    break;
                case Right:
                    g2.drawImage(arrow_4,623,342,null);
                    break;
            }
            distbol = false;
        }
    }

    public void drawunits(Graphics g,int i) {
        if (units.get(i).getPosition().getX() >= dx && units.get(i).getPosition().getX()  <= dx + 500 &&
                units.get(i).getPosition().getY() >= dy && units.get(i).getPosition().getY()  <= dy + 500 ) {
            switch ( units.get(i).getTypeOfUnit().toString() ) {

                case "TankDestroyer":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(Tank_destroyerA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(Tank_destroyerD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;

                case "GrizzlyTank":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(Grizzly_TankA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(Grizzly_TankD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "PrismTower":
                    g.drawImage(Prism_Tower, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "MirageTank":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(Mirage_tankA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(Mirage_tankD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                        
                case "PrismTank":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(Prism_tankA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(Prism_tankD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                        
                case "TeslaTank":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(TeslaTankA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(TeslaTankD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "Infantry":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(InfantryA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(InfantryD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "NavySEAL":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(Navy_SEALA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(Navy_SEALD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "Pillbox":
                    g.drawImage(Pillbox, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "Sniper":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(SniperA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(SniperD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "MAINBASE":
                    g.drawImage(MAIN_BASE, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "PatriotMissileSystem":
                    g.drawImage(Patriot_Missile_System, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "GrandCannon":
                    g.drawImage(Grand_Cannon, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
                    
                case "BlackEagle":
                    if (units.get(i).getTeamType()== TeamType.Attacker)
                         g.drawImage(Black_EagleA, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    else g.drawImage(Black_EagleD, units.get(i).getGuiPosition().getX() - dx + 100, units.get(i).getGuiPosition().getY() - dy + 100, null);
                    break;
            }
        }
    }

    public void drawbridg(Graphics g,int i) {
        if (bridges.get(i).getX() >= dx &&  bridges.get(i).getX() <= dx + 500 &&
            bridges.get(i).getY() >= dy &&  bridges.get(i).getY() <= dy + 500 ) {
                g.setColor(Color.darkGray);
                g.fillRect(bridges.get(i).getX() - dx + 100,bridges.get(i).getX() - dy + 100,1,1);
        }
    }

    public void drawriver(Graphics g,int i) {
        if (rivers.get(i).getX() >= dx &&  rivers.get(i).getX() <= dx + 500 &&
            rivers.get(i).getY() >= dy &&  rivers.get(i).getY() <= dy + 500 ) {
                g.setColor(new Color(0, 102, 128));
                g.drawRect(rivers.get(i).getX() - dx + 100,rivers.get(i).getY() - dy + 100,1,1);
        }
    }

    public void drawvalley(Graphics g,int i) {
        if (valley.get(i).getX() >= dx &&  valley.get(i).getX() <= dx + 500 &&
            valley.get(i).getY() >= dy &&  valley.get(i).getY() <= dy + 500 ) {
                g.setColor(Color.ORANGE);
                g.fillRect(valley.get(i).getX() - dx + 100,valley.get(i).getY() - dy + 100,1,1);
        }
    }

    public void labelsdraw(Graphics g) throws IOException { //تعيين مواقع
        if(info != null && info.getIsAlife() ) {
            type.setText(info.getTypeOfUnit().toString());
            Team.setText(info.getTeamType().toString());
            Health.setText(Double.toString(info.getHealth()));
            Armour.setText(Double.toString(info.getArmor()));
            if (info.getTypeOfUnit() == TypeOfUnit.MAINBASE) Damage.setText("- -");
            else Damage.setText(Double.toString(((AttackerUnit)info).getAttackDamage()));
            Position.setText(String.format("( %s ,, %s )",Integer.toString(info.getPosition().getX()),Integer.toString(info.getPosition().getY())));
            
            switch (info.getTypeOfUnit().toString()) {
                case "PatriotMissileSystem":
                    image = ImageIO.read(this.getClass().getResource("/res/Patriot_Missile_Systeml.png"));
                    break;
                case "TankDestroyer":
                    image = ImageIO.read(this.getClass().getResource("/res/Tank_Destroyer.png"));
                    break;
                case "GrandCannon":
                    image = ImageIO.read(this.getClass().getResource("/res/Grand_Cannonl.png"));
                    break;
                case "GrizzlyTank":
                    image = ImageIO.read(this.getClass().getResource("/res/Grizzly_Tank.png"));
                    break;
                case "PrismTower":
                    image = ImageIO.read(this.getClass().getResource("/res/Prism_Towerl.png"));
                    break;
                case "BlackEagle":
                    image = ImageIO.read(this.getClass().getResource("/res/Black_Eagle.png"));
                    break;
                case "MirageTank":
                    image = ImageIO.read(this.getClass().getResource("/res/Mirage_Tank.png"));
                    break;
                case "PrismTank":
                    image = ImageIO.read(this.getClass().getResource("/res/Prism_Tank.png"));
                    break;
                case "TeslaTank":
                    image = ImageIO.read(this.getClass().getResource("/res/Tesla_Tank.png"));
                    break;
                case "Infantry":
                    image = ImageIO.read(this.getClass().getResource("/res/Infantry.png"));
                    break;
                case "NavySEAL":
                    image = ImageIO.read(this.getClass().getResource("/res/Navy_SEAL.png"));
                    break;
                case "MAINBASE":
                    image = ImageIO.read(this.getClass().getResource("/res/MAIN_BASEl.png"));
                    break;
                case "Pillbox":
                    image = ImageIO.read(this.getClass().getResource("/res/Pillboxl.png"));
                    break;
                case "Sniper":
                    image = ImageIO.read(this.getClass().getResource("/res/Sniper.png"));
                    break;
            }
        } else {
            type.setText("- -");
            Team.setText("- -");
            Health.setText("- -");
            Armour.setText("- -");
            Damage.setText("- -");
            Position.setText(String.format("( %s ,, %s )","- -","- -"));
        }
        
        Map.setText(String.format("( %s ,, %s )",Integer.toString(dx),Integer.toString(dy)));
        Base.setText(Double.toString(DorD.getOpj().getBase().getHealth()));
        Time.setText(Integer.toString(dordGame.getRemainingTime()));
        
        g.setColor(Color.white);
        g.setFont(scFont.deriveFont(17f));
        g.drawString(type.getText(),777,164+15);
        g.drawString(Team.getText(),782,207+15);
        g.drawString(Damage.getText(),795,334+15);
        g.drawString(Health.getText(),790,250+15);
        g.drawString(Armour.getText(),788,293+15);
        g.drawString(Position.getText(),805,377+15);
        g.drawImage(image,912,222,null);
        g.setFont(scFont.deriveFont(12f));
        g.drawString(Time.getText(),967,90+10);
        g.drawString(Base.getText(),968,113+10);
        g.drawString(Map.getText(),912,461+10);

        if (dordGame.getIsPaused()) g.drawImage(paused,281,50,null);
    }

    public void mapdis(Graphics g) {
        for (Unit a:units) {
            if(a.getTeamType()== TeamType.Attacker) {
                g.setColor(Color.RED);
                g.fillRect(((a.getGuiPosition().getX()*140)/10000)+873 ,((a.getGuiPosition().getY()*140)/10000)+481,2,2);
            } else if (a.getTeamType()== TeamType.Defender) {
                g.setColor(Color.blue);
                g.fillRect(((a.getGuiPosition().getX()*140)/10000)+873 ,((a.getGuiPosition().getY()*140)/10000)+481,2,2);
            }
        }
    }
}
