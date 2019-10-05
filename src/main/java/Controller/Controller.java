package Controller;
import Model.Mapx;
import Model.Player;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import Model.*;

public class Controller {

	enum States {mapEditor,gamePlay,startupPhase,editPlayer,troopArmies,reinforcementPhase,attackPhase,fortificationPhase }
	enum Tasks {addcontinent,removecontinent,addcountry,removecountry,addneighbor,removeneighbor,savemap,editmap,validatemap,showmap,loadmap,addplayer,removeplayer,populatecountries}
	States currentState = States.mapEditor;
	Tasks currentTask;
	String continentName,countryName,neighborCountryName,mapFile,playerName;
	Integer controlValue,playerId=1;
	private Mapx mapObj;
	private ArrayList<Player> playerObjs;
	boolean editPlayerFinished = false;
	
	// parse input Instruction -> Command , Switch, Data
	boolean getCommand(){
		System.out.println("Enter Command");
		Scanner scan = new Scanner(System.in);
		String instruction = scan.nextLine().trim();
		String[] command = instruction.split(" ");

		if(command[0].equals("editcontinent")){
			if(command.length == 4 && command[1].equals("-add")){
				continentName = command[2];
				controlValue = Integer.parseInt(command[3]);	
				currentTask = Tasks.addcontinent;
				return true;
				// check control value number
			}
			else if(command.length == 3 && command[1].equals("-remove")){
				continentName = command[2];
				currentTask = Tasks.removecontinent;
				return true;
			}
			else{
				System.out.println("'wrong Command' ---> editcontinent -add continentname continentvalue");
				System.out.println("                ---> editcontinent -remove continentname");
				return false;
			}
		}
		else if(command[0].equals("editcountry")){
			if(command.length == 4 && command[1].equals("-add")){
				countryName = command[2];
				continentName = command[3];	
				currentTask = Tasks.addcountry;
				return true;
			}
			else if( command.length == 3 && command[1].equals("-remove")){
				countryName = command[2];
				currentTask = Tasks.removecountry;
				return true;
			}
			else{
				System.out.println("'wrong Command' ---> editcountry -add countryname continentname");
				System.out.println("                ---> editcountry -remove countryname");
				return false;
			}	
		}
		else if(command[0].equals("editneighbor")){
			if(command.length == 4 && command[1].equals("-add")){
				countryName = command[2];
				neighborCountryName = command[3];	
				currentTask = Tasks.addneighbor;
				return true;
			}
			else if(command.length == 4 && command[1].equals("-remove")){
				countryName = command[2];
				neighborCountryName = command[3];
				currentTask = Tasks.removeneighbor;
				return true;
			}
			else{
				System.out.println("'wrong Command' ---> editneighbor -add countryname neighborcountryname");
				System.out.println("                ---> editneighbor -remove countryname neighborcountryname");
				return false;
			}
		}
		else if(command[0].equals("savemap")){
			if( command.length == 2 ) {
				mapFile = command[1] + ".map";
				currentTask = Tasks.savemap;
				return true;
			}
			else {
				System.out.println("wrong Command ---> savemap filename");
				return false;
			}
		}
		else if(command[0].equals("editmap")){
			if( command.length == 2 ) {
				mapFile = command[1] + ".map";
				currentTask = Tasks.editmap;
				return true;
			}
			else {
				System.out.println("wrong Command ---> editmap filename");
				return false;
			}
		}
		else if(command[0].equals("validatemap")){
			currentTask = Tasks.validatemap;
			return true;
		}
		else if(command[0].equals("showmap")){
			currentTask = Tasks.showmap;
			return true;
		}
		else if(command[0].equals("loadmap")){
			if( command.length == 2 ) {
				mapFile = command[1] + ".map";
				currentTask = Tasks.loadmap;
				return true;
			}
			else {
				System.out.println("wrong Command ---> loadmap filename");
				return false;
			}
		}
		else if(command[0].equals("gameplayer")){
			if(command.length == 3 && command[1].equals("-add")) {
				playerName = command[2];
				currentTask = Tasks.addplayer;
				return true;
			}
			else if(command.length == 3 && command[1].equals("-remove")){
				playerName = command[2];
				currentTask = Tasks.removeplayer;
				return true;
			}
			else {
				System.out.println("wrong Command ---> gameplayer -add playername");
				System.out.println("                   gameplayer -remove playername");
				return false;
			}
		}
		else if(command[0].equals("populatecountries")){
			currentTask = Tasks.populatecountries;
			return true;
		}
		else {
			System.out.println("wrong Command");
			return false;
		}
	}
    public static void main(String[] args) throws IOException {
		try {
			Controller controller = new Controller();
			controller.playerObjs = new ArrayList<Player>();
		//	controller.startGame();

	    	boolean gameFinished = false;
	    	
			while(!gameFinished){
				if(!controller.getCommand())
					continue;	
			/*	if( controller.currentState == States.mapEditor){
					switch (controller.currentTask){
						case addcontinent:	
						//	map.addcontinent(continentName,controlValue);
							break;
						case removecontinent:		
						//	map.removecontinent(continentName);
							break;
						case addcountry:
						//	map.addcountry(countryName,continentName);
							break;
						case removecountry:
						//	map.removecountry(countryName);
							break;
						case addneighbor:
						//	map.addneighbor(countryName,neighborCountryName);
							break;
						case removeneighbor:
						//	map.addneighbor(countryName,neighborCountryName);
							break;
						case savemap:
						//	map.savemap(mapFile);
							break;
						case editmap:
						//	map.editmap(mapFile);
							break;
						case validatemap:
						//  if(map.checkValidityOfMap())   return true or false
							controller.currentState = States.gamePlay;
							break;
						
						default: System.out.println("Invalid Command. Please Enter Map Editor Command");
					}
				}
				else if( controller.currentState == States.gamePlay ){
					switch (controller.currentTask){
						case showmap:
						//	map.showmap();
							controller.currentState = States.startupPhase;
							break;
						default: System.out.println("Invalid Command. Please Enter Game Play Command");
				}
			}
			else if( controller.currentState == States.startupPhase ){
				switch (controller.currentTask){
					case loadmap:
					//	map.loadmap();
						controller.currentState = States.editPlayer;
						break;
					default: System.out.println("Invalid Command. Please Enter Startup Phase Command");
				}
			} */
			if( controller.currentState == States.mapEditor ){
				while(!controller.editPlayerFinished) {
					switch (controller.currentTask){
						case addplayer:{	
								Player playerobj = new Player();
								playerobj.setName(controller.playerName);
								playerobj.setId(controller.playerId);
								controller.playerObjs.add(playerobj);
								controller.playerId++;
								controller.getCommand();
								break;
						}
						case removeplayer:{
							for(Player itr : controller.playerObjs){
								if(itr.getName().equals(controller.playerName)) {
									controller.playerObjs.remove(itr);
							    }
								else {
									System.out.println("This Player does not exist. Please enter the correct Player");
								}
								controller.getCommand();
							}
						}
						case populatecountries:{
							controller.currentState = States.troopArmies;
							controller.editPlayerFinished= true;
							for(Player name:controller.playerObjs) {
					            System.out.println(name.getName());
					        } 
							break;
						}		
						default: System.out.println("Invalid Command. Please Enter Startup Phase Command");
					}
				}
			}
		/*	else if(CurrentState == Attack)
			{
			}
			else if(CurrentState == Fortification)
			{
			} */
			}
		}catch (Exception e)
		{
			System.out.println("An error occured: "+e.getMessage());
		}
    }
    void startGame() throws IOException{

        Mapx map= new Mapx();
        map.createGameGraph("src/main/resources/map.map").printGraph();
    	map.saveMap();
//        Scanner commandScanner= new Scanner(System.in);
//        System.out.print ("Enter number of Players: ");
//        Integer numberOfPlayers= Integer.parseInt(commandScanner.nextLine().trim());
       
    }   
}
