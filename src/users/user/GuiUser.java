package users.user;

import users.user.GameState.GameStateManager;
import files.read_from_file.ReadLineNum;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import units.additional.TypeOfUnit;
import java.awt.event.KeyListener;
import units.additional.Position;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.*;
import java.io.IOException;
import users.team.TeamType;
import users.team.Player;
import java.util.Scanner;
import units.unit.Unit;
import java.util.List;
import javax.swing.*;
import grid.Areana;
import java.awt.*;

public class GuiUser extends User implements Runnable, KeyListener, MouseListener {
    private GameStateManager gsm;
    private BufferedImage image;
    private boolean running;
    private Thread thread;
    private Graphics2D g;

    public GuiUser() {
        super();
        setPreferredSize(new Dimension(1100,  700));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void initGame() {
        image   = new BufferedImage(1100, 700, BufferedImage.TYPE_INT_RGB);
        g       = (Graphics2D) image.getGraphics();
        running = true;
        
        try {
            gsm = new GameStateManager( this );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void numPlayers() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future< String > futur = executorService.submit( new ReadLineNum("input2.txt" ,0));
        String line = null;

        try {
            line = futur.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner( line );

        int numOfAttackerTeamPlayer = scanner.nextInt();
        int numOfDefenderTeamPlayer = scanner.nextInt();
        for(int i = 0 ; i < numOfAttackerTeamPlayer ; i++) dordGame.addPlayer( new Player(dordGame.getPlayersPoints() ,TeamType.Attacker) );
        for(int i = 0 ; i < numOfDefenderTeamPlayer ; i++) dordGame.addPlayer( new Player(dordGame.getPlayersPoints() ,TeamType.Defender) );
    }

    @Override
    public void buyUnitsForPlayers() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future< String > futur = executorService.submit( new ReadLineNum("input2.txt" ,1));
        String line = null;

        try {
            line = futur.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner( line );
        
        java.util.List< Player > attackerTeamPlayers = dordGame.getAttackerTeam().getPlayersList();
        List< Player > defenderTeamPlayers = dordGame.getDefenderTeam().getPlayersList();
        
        for(int i = 0 ,choice ; i < attackerTeamPlayers.size() ; i++){
            Player player =  attackerTeamPlayers.get(i);
            
            do{
                choice = scanner.nextInt();

                switch( choice ){ 
                    case 1:  player.buyUnit(TypeOfUnit.TeslaTank);     break;
                    case 2:  player.buyUnit(TypeOfUnit.Sniper);        break;
                    case 3:  player.buyUnit(TypeOfUnit.MirageTank);    break;
                    case 4:  player.buyUnit(TypeOfUnit.Infantry);      break;
                    case 5:  player.buyUnit(TypeOfUnit.GrizzlyTank);   break;
                    case 6:  player.buyUnit(TypeOfUnit.NavySEAL);      break;
                    case 7:  player.buyUnit(TypeOfUnit.TankDestroyer); break;
                    case 8:  player.buyUnit(TypeOfUnit.PrismTank);     break;
                    case 9:  player.buyUnit(TypeOfUnit.BlackEagle);    break;
                    default: break;
                }
            } while( choice != 0 && player.getPoints() > 0 );
        }

        for(int i = 0 ,choice ; i < defenderTeamPlayers.size() ; i++) {
            Player player = defenderTeamPlayers.get(i);
            
            do{
                choice = scanner.nextInt();
                switch( choice ) {
                    case 1:  player.buyUnit(TypeOfUnit.TeslaTank);            break;
                    case 2:  player.buyUnit(TypeOfUnit.Sniper);               break;
                    case 3:  player.buyUnit(TypeOfUnit.MirageTank);           break;
                    case 4:  player.buyUnit(TypeOfUnit.Infantry);             break;
                    case 5:  player.buyUnit(TypeOfUnit.GrizzlyTank);          break;
                    case 6:  player.buyUnit(TypeOfUnit.NavySEAL);             break;
                    case 7:  player.buyUnit(TypeOfUnit.TankDestroyer);        break;
                    case 8:  player.buyUnit(TypeOfUnit.PrismTank);            break;
                    case 9:  player.buyUnit(TypeOfUnit.PatriotMissileSystem); break;
                    case 10: player.buyUnit(TypeOfUnit.GrandCannon);          break;
                    case 11: player.buyUnit(TypeOfUnit.PrismTower);           break;
                    case 12: player.buyUnit(TypeOfUnit.Pillbox);              break;
                    default: break;
                }
            } while( choice != 0 && player.getPoints() > 0 );
        }
    }

    @Override
    public void deployUnitsOnGrid() { // نشر جنود الفريقين
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future< String > futur = executorService.submit( new ReadLineNum("input2.txt" ,2));
        String line = null;

        try {
            line = futur.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner( line );
        
        List< Player > attackerTeamPlayers = dordGame.getAttackerTeam().getPlayersList();
        List< Player > defenderTeamPlayers = dordGame.getDefenderTeam().getPlayersList();
        
        Position result = new Position(scanner.nextInt() ,scanner.nextInt());
        dordGame.getBase().setPosition( result );
        Areana.getOpj().addUnit( dordGame.getBase() );

        for(int i = 0 ; i < Math.max(attackerTeamPlayers.size(),defenderTeamPlayers.size()) ; i++) {
            if( i < attackerTeamPlayers.size() ) {
                Player player1 = attackerTeamPlayers.get(i);
                List<Unit> unitsOfPlayer = player1.getUnits();
                for (int j = 0; j < unitsOfPlayer.size(); j++) {
                    result = new Position(scanner.nextInt(), scanner.nextInt());
                    unitsOfPlayer.get(j).setPosition(result);
                    Areana.getOpj().addUnit(unitsOfPlayer.get(j));
                }
            }

            if( i < defenderTeamPlayers.size() ) {
                Player player2 = defenderTeamPlayers.get(i);
                List<Unit>unitsOfPlayer = player2.getUnits();
                for (int j = 0; j < unitsOfPlayer.size(); j++) {
                    result = new Position(scanner.nextInt(), scanner.nextInt());
                    unitsOfPlayer.get(j).setPosition(result);
                    Areana.getOpj().addUnit(unitsOfPlayer.get(j));
                }
            }
        }
    }

    @Override
    public void run() {
        initGame();

        /// --- Program Loop --- ///
        while( running ) {
            update();
            render();
        }

        System.exit(0);
    }

    private void update() {
        gsm.update();
    }
    
    private void render() {
        draw();
        drawToScreen();
    }
    
    private void draw() {
        gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        if( thread == null ) {
            thread = new Thread( this );
            addMouseListener( this );
            addKeyListener( this );
            thread.start();
        }
    }
    
    /// ---------------------------------- ///
    
    @Override
    public void keyReleased(KeyEvent key) { 
        gsm.keyReleased( key.getKeyCode() ); 
    }
    
    @Override
    public void mouseClicked(MouseEvent m) { 
        gsm.Mouseclicked( m ); 
    }

    @Override
    public void keyPressed(KeyEvent key) {
        try {
            gsm.keyPressed( key.getKeyCode() );
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent key) { /* --- Do Nothing --- */ }
    @Override
    public void mousePressed(MouseEvent m) { /* --- Do Nothing --- */ }
    @Override
    public void mouseReleased(MouseEvent e) { /* --- Do Nothing --- */ }
    @Override
    public void mouseEntered(MouseEvent e) { /* --- Do Nothing --- */ }
    @Override
    public void mouseExited(MouseEvent e) { /* --- Do Nothing --- */ }

    /// ------------------   Main   ------------------ ///

    public static void main( String[] args ){
        JFrame window = new JFrame("DorD");
        window.setContentPane( new GuiUser() );
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
