package users.user;

import units.additional.TypeOfUnit;
import units.factory.UnitFactory;
import units.additional.Position;
import users.team.TeamType;
import users.team.Player;
import java.util.Scanner;
import javafx.util.Pair;
import units.unit.Unit;
import java.util.List;
import grid.Areana;

public class ConsoleUser extends User {

    public ConsoleUser(){ }

    @Override
    public void initGame() {
        numPlayers();
        buyUnitsForPlayers();
        deployUnitsOnGrid();
    }

    public void startGame(){
        dordGame.initTimeOfGame();
        
        // GameLoop
        while( dordGame.getRemainingTime() > 0 && dordGame.getBase().getIsAlife() && dordGame.getAttackerTeamUnits() > 0 ) {
            if( dordGame.getIsPaused() ) {
                dordGame.pause();
            } else {
                dordGame.update();
            }
            zoomIn();
        }
        
        TeamType winnerTeam = dordGame.getBase().getIsAlife() ? TeamType.Defender : TeamType.Attacker;
        
        System.out.println("\n--------------------------------");
        System.out.println(" |" + winnerTeam + " Team Win <3 |");
        System.out.println("--------------------------------");
        System.exit(0);
    }

    protected void render(int size , Position pos) {
        int x,y;
        char [][] units = new char[100][100];
        List < Unit > all = Areana.getOpj().getUnits();
             
        for( int i = 0 ; i < 100 ; i++)
            for( int j = 0 ; j <100 ; j++) 
                units[i][j] = '-' ;
        
        System.out.println("size of units array = " + all.size());
        for( int i = 0 ; i < all.size() ; i++) {
            x = ( (all.get(i).getPosition().getX() - pos.getX()) * 100 ) / size;
            y = ( (all.get(i).getPosition().getY() - pos.getY()) * 100 ) / size;
            if( (x >= pos.getX() && x< pos.getX() + size) && (y >= pos.getY() && x< pos.getY() + size))
                units[x][y] = 'u';
        }
            
        for( int i = 0 ; i < 100 ; i++){
            for( int j = 0 ; j < 100 ; j++)
               System.out.print(units[i][j]);
            System.out.println();
        }

    }

    protected void zoomIn() {
        System.out.println(" Choise The level of zoom ");
        int c ,i ,j;
        System.out.println(" 1 to show 10000,10000");
        System.out.println(" 2 to show 5000,5000 ");
        System.out.println(" 3 to show 1000,1000");
        System.out.print(" Enter Your Choise ");
        Scanner scanner = new Scanner(System.in);
        c = scanner.nextInt();
        switch(c){
            case 1:{
                render(10000,new Position(0, 0));
            }break;
            case 2:
            {
                System.out.print(" Enter the location of the first cell from which the zoom starts(x,y):\n ");
                i = scanner.nextInt();
                j = scanner.nextInt();
                render(5000 , new Position(i, j));
            }break;
            case 3:{
                System.out.print(" Enter the location of the first cell from which the zoom starts(x,y):\n ");
                i = scanner.nextInt();
                j = scanner.nextInt();
                render(1000 , new Position(i,j));
            } break;
        }
    }
    
    @Override
    public void numPlayers() {
        Scanner scanner = new Scanner(System.in);
        int numOfAttackerTeamPlayer ,numOfDefenderTeamPlayer;
        
        System.out.print(" Enter Number Of Attacker Team Players: ");
        do{
            numOfAttackerTeamPlayer = scanner.nextInt();
        }while( numOfAttackerTeamPlayer <= 0 );
        for(int i = 0 ; i < numOfAttackerTeamPlayer ; i++)
            dordGame.addPlayer( new Player(dordGame.getPlayersPoints() ,TeamType.Attacker) );
        
        System.out.print(" Enter Number Of Defender Team Players: ");
        do{
            numOfDefenderTeamPlayer = scanner.nextInt();
        }while( numOfAttackerTeamPlayer <= 0 );
        for(int i = 0 ; i < numOfDefenderTeamPlayer ; i++)
            dordGame.addPlayer( new Player(dordGame.getPlayersPoints() ,TeamType.Defender) );
    }

    @Override
    public void buyUnitsForPlayers() {
        List< Player > attackerTeamPlayers = dordGame.getAttackerTeam().getPlayersList();
        List< Player > defenderTeamPlayers = dordGame.getDefenderTeam().getPlayersList();
        
        for(int i = 0 ; i < attackerTeamPlayers.size() ; i++)
            buyUnits( attackerTeamPlayers.get(i) );
        
        for(int i = 0 ; i < defenderTeamPlayers.size() ; i++)
            buyUnits( defenderTeamPlayers.get(i) );
    }
    
    protected void buyUnits(Player player){
        System.out.println();
        System.out.println("Buy Units For Player " + player.getId() + " From " + player.getTeamType() + "Team.");
        System.out.println("Choose From The List");
        
        Scanner scanner = new Scanner( System.in );
        int choice;
                
        if( player.getTeamType() == TeamType.Attacker ) {
            do{
                System.out.println("1- Tesla Tank");
                System.out.println("2- Sniper");
                System.out.println("3- Mirage Tank");
                System.out.println("4- Infantry");
                System.out.println("5- Grizzly Tank");
                System.out.println("6- Navy SEAL");
                System.out.println("7- Tank Destroyer");
                System.out.println("8- Prism Tank");
                System.out.println("9- Black Eagle");
                System.out.println("0- To End Buying Units !!!");
                System.out.print("\nPonts = " + player.getPoints() + ".\nEnter Your Choice:");
                choice = scanner.nextInt();
                
                switch( choice ){
                    case 1:
                        if( UnitFactory.getOpj().teslaTank.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.TeslaTank);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 2:
                        if( UnitFactory.getOpj().sniper.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.Sniper);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 3:
                        if( UnitFactory.getOpj().mirageTank.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.MirageTank);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 4:
                        if( UnitFactory.getOpj().infantry.getPrice() <= player.getPoints() ) 
                            player.buyUnit(TypeOfUnit.Infantry);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n");
                        break;
                    case 5:
                        if( UnitFactory.getOpj().grizzlyTank.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.GrizzlyTank);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 6:
                        if( UnitFactory.getOpj().navySEAL.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.NavySEAL);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 7:
                        if( UnitFactory.getOpj().tankDestroyer.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.TankDestroyer);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 8:
                        if( UnitFactory.getOpj().prismTank.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.PrismTank);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 9:
                        if( UnitFactory.getOpj().blackEagle.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.BlackEagle);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    default:
                        System.out.println("UnValid Choice !!\n");
                        break;
                }
            } while( choice != 0 && player.getPoints() > 0 );
        } else {
            do{
                System.out.println(" 1- Tesla Tank");
                System.out.println(" 2- Sniper");
                System.out.println(" 3- Mirage Tank");
                System.out.println(" 4- Infantry");
                System.out.println(" 5- Grizzly Tank");
                System.out.println(" 6- Navy SEAL");
                System.out.println(" 7- Tank Destroyer");
                System.out.println(" 8- Prism Tank");
                System.out.println(" 9- Patriot Missile System");
                System.out.println("10- Grand Cannon");
                System.out.println("11- Prism Tower");
                System.out.println("12- Pill box");
                System.out.println(" 0- To End Buying Units !!!");
                System.out.print("\nPonts = " + player.getPoints() + ".\nEnter Your Choice:");
                choice = scanner.nextInt();
                
                switch( choice ){
                    case 1:
                        if( UnitFactory.getOpj().teslaTank.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.TeslaTank);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 2:
                        if( UnitFactory.getOpj().sniper.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.Sniper);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 3:
                        if( UnitFactory.getOpj().mirageTank.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.MirageTank);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 4:
                        if( UnitFactory.getOpj().infantry.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.Infantry);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 5:
                        if( UnitFactory.getOpj().grizzlyTank.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.GrizzlyTank);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 6:
                        if( UnitFactory.getOpj().navySEAL.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.NavySEAL);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 7:
                        if( UnitFactory.getOpj().tankDestroyer.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.TankDestroyer);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 8:
                        if( UnitFactory.getOpj().prismTank.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.PrismTank);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 9:
                        if( UnitFactory.getOpj().patriotMissileSystem.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.PatriotMissileSystem);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 10:
                        if( UnitFactory.getOpj().grandCannon.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.GrandCannon);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 11:
                        if( UnitFactory.getOpj().prismTower.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.PrismTower);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    case 12:
                        if( UnitFactory.getOpj().pillbox.getPrice() <= player.getPoints() )
                            player.buyUnit(TypeOfUnit.Pillbox);
                        else System.out.println("You Dont Have Enough Money For This Type Of Units !!!\n"); 
                        break;
                    default:
                        System.out.println("UnValid Choice !!\n");
                        break;
                }
            } while( choice != 0 && player.getPoints() > 0 );
        }
    }

    @Override
    public void deployUnitsOnGrid() { // نشر جنود الفريقين
        List< Player > attackerTeamPlayers = dordGame.getAttackerTeam().getPlayersList();
        List< Player > defenderTeamPlayers = dordGame.getDefenderTeam().getPlayersList();
        
        System.out.println("Defender Team!! Deploy Your Base On Grid ..");
        
        Pair<Boolean ,Position> result;
        boolean flag = true;
        do{
            if( flag == false ) System.out.println("This Position Is Not Valid.. Enter Another One!!\n");
            result = readPosition(dordGame.getBase() ,defenderTeamPlayers.get(0));

            flag = result.getKey();
        } while( flag == false );

        dordGame.getBase().setPosition( result.getValue() );
        Areana.getOpj().addUnit( dordGame.getBase() );
        
        System.out.println("");
        for(int i = 0 ; i < Math.max(attackerTeamPlayers.size(),defenderTeamPlayers.size()); i++) {
            if( i < attackerTeamPlayers.size() )
                deployUnits( attackerTeamPlayers.get(i) );
            if( i < defenderTeamPlayers.size() )
                deployUnits( defenderTeamPlayers.get(i) );
        }
    }

    protected void deployUnits( Player player ) {
        System.out.println();
        System.out.println("Deploy Units For Player " + player.getId() + " From " + player.getTeamType());
        System.out.println("Note That Attacker Positions in The TopRight Corner ,Defender Positions in The BottomLeft Corner\n");
        
        List< Unit > unitsOfPlayer = player.getUnits();
       
        for(int i = 0 ; i < unitsOfPlayer.size() ; i++){
            Pair<Boolean ,Position> result;
            boolean flag = true;
            
            do{
                if( flag == false ) System.out.println("This Position Is Not Valid.. Enter Another One!!\n");
                result = readPosition(unitsOfPlayer.get(i) ,player);
                
                flag = result.getKey();
            } while( flag == false );
            
            unitsOfPlayer.get(i).setPosition( result.getValue() );
            Areana.getOpj().addUnit( unitsOfPlayer.get(i) );
        }
    }

    protected Pair<Boolean ,Position> readPosition(Unit unit ,Player player){
        Scanner scanner = new Scanner(System.in);
        int xAxis ,yAxis; 
        boolean flag;
        
        System.out.println("Enter xAxis Of Unit " + unit.getTypeOfUnit() + ":");
        xAxis = scanner.nextInt();
        System.out.println("Enter yAxis Of Unit " + unit.getTypeOfUnit() + ":");
        yAxis = scanner.nextInt();

        flag  = Areana.getOpj().isValid( new Position(xAxis ,yAxis) , unit);
        return new Pair(flag ,new Position(xAxis ,yAxis));
    }
    
    public static void main( String []args ){
        ConsoleUser consoleUser = new ConsoleUser();
        consoleUser.newGame(3000);
        consoleUser.initGame();
        consoleUser.startGame();
    }
}
