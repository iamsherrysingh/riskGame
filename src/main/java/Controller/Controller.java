package Controller;
import Model.Mapx;
import Model.Player;
import java.io.IOException;
import java.util.Scanner;
import Controller.Controller.tasksEnum;
import java.util.ArrayList;
import java.util.*; 
import Model.*;

public class Controller {

	enum States {mapEditor,gamePlay,startupPhase,editPlayer,troopArmies,reinforcementPhase,attackPhase,fortificationPhase }
	enum tasksEnum {Unknown,addcontinent,removecontinent,addcountry,removecountry,addneighbor,removeneighbor,savemap,editmap,validatemap,showmap,loadmap,addplayer,removeplayer,populatecountries}
	States currentState = States.mapEditor;
	tasksEnum currentTask;
	String continentName,countryName,neighborCountryName,mapFile,playerName;
	Integer controlValue,playerId=1;
	private Mapx mapObj;
	private ArrayList<Player> playerObjs;
	boolean editPlayerFinished = false;
	
	// parse input Instruction -> Command , Switch, Data
	boolean getCommand(ArrayList<extractedTasks> tasksList){
		
		System.out.println("Enter Command");
		Scanner scan = new Scanner(System.in);
		String instruction = scan.nextLine().trim();
		List<String> command = Arrays.asList(instruction.split(" "));
		ListIterator<String> cmdItr = command.listIterator();
		
		//get command part of instruction
		String cmdStr;
		if(!cmdItr.hasNext()) {
			System.out.println("wrong Command");
			return false;
		}
		cmdStr = cmdItr.next();	
		
		//command editcontinent
		if(cmdStr.equals("editcontinent")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			while(cmdItr.hasNext())
			{
				//get and check the switches
				cmdStr = cmdItr.next();
				if(cmdStr.equals("-add")) {
					extractedTasks eTask = new extractedTasks();
					eTask.name = tasksEnum.addcontinent;
					
					//get data related to addContinent task
					for(int i=0;i<2;i++) {
						if(!cmdItr.hasNext()) {
							System.out.println("wrong Command");
							return false;
						}
						eTask.taskData.add(cmdItr.next());
					}
					tasksList.add(eTask);
				}
				else if(cmdStr.equals("-remove")) {
					extractedTasks eTask = new extractedTasks();
					eTask.name = tasksEnum.removecontinent;
					
					//get data related to removeContinent task
					if(!cmdItr.hasNext()) {
						System.out.println("wrong Command");
						return false;
					}
					eTask.taskData.add(cmdItr.next());
					tasksList.add(eTask);
				}
				else {
					System.out.println("wrong Command");
					return false;
				}
			}			
		}
		//command editcountry
		else if(cmdStr.equals("editcountry")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			while(cmdItr.hasNext())
			{
				//get and check the switches
				cmdStr = cmdItr.next();
				if(cmdStr.equals("-add")) {
					extractedTasks eTask = new extractedTasks();
					eTask.name = tasksEnum.addcountry;
					
					//get data related to addCountry task
					for(int i=0;i<2;i++) {
						if(!cmdItr.hasNext()) {
							System.out.println("wrong Command");
							return false;
						}
						eTask.taskData.add(cmdItr.next());
					}
					tasksList.add(eTask);
				}
				else if(cmdStr.equals("-remove")) {
					extractedTasks eTask = new extractedTasks();
					eTask.name = tasksEnum.removecountry;
					
					//get data related to removeCountry task
					if(!cmdItr.hasNext()) {
						System.out.println("wrong Command");
						return false;
					}
					eTask.taskData.add(cmdItr.next());
					tasksList.add(eTask);
				}
				else {
					System.out.println("wrong Command");
					return false;
				}
			}			
		}
		//command editneighbor
		else if(cmdStr.equals("editneighbor")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			while(cmdItr.hasNext())
			{
				//get and check the switches
				cmdStr = cmdItr.next();
				if(cmdStr.equals("-add")) {
					extractedTasks eTask = new extractedTasks();
					eTask.name = tasksEnum.addneighbor;
					
					//get data related to addneighbor task
					for(int i=0;i<1;i++) {
						if(!cmdItr.hasNext()) {
							System.out.println("wrong Command");
							return false;
						}
						eTask.taskData.add(cmdItr.next());
					}
					tasksList.add(eTask);
				}
				else if(cmdStr.equals("-remove")) {
					extractedTasks eTask = new extractedTasks();
					eTask.name = tasksEnum.removeneighbor;
					
					//get data related to removeneighbor task
					for(int i=0;i<1;i++) {
						if(!cmdItr.hasNext()) {
							System.out.println("wrong Command");
							return false;
						}
						eTask.taskData.add(cmdItr.next());
					}
					tasksList.add(eTask);
				}
				else {
					System.out.println("wrong Command");
					return false;
				}
			}			
		}
		//Command savemap
		else if(cmdStr.equals("savemap")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.savemap;
			
			//get data related to savemap task
			eTask.taskData.add(cmdItr.next());
			
			tasksList.add(eTask);
		}
		//Command editmap
		else if(cmdStr.equals("editmap")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.editmap;
			
			//get data related to editmap task
			eTask.taskData.add(cmdItr.next());
			
			tasksList.add(eTask);
		}
		//Command validatemap
		else if(cmdStr.equals("validatemap")){
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.validatemap;
			tasksList.add(eTask);
		}
		//Command showmap
		else if(cmdStr.equals("showmap")){
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.showmap;
			tasksList.add(eTask);
		}
		//Command loadmap
		else if(cmdStr.equals("loadmap")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.loadmap;
			
			//get data related to loadmap task
			eTask.taskData.add(cmdItr.next());
			
			tasksList.add(eTask);
		}
		//Command gameplayer
		else if(cmdStr.equals("gameplayer")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			while(cmdItr.hasNext())
			{
				//get and check the switches
				cmdStr = cmdItr.next();
				if(cmdStr.equals("-add")) {
					extractedTasks eTask = new extractedTasks();
					eTask.name = tasksEnum.addplayer;
					
					//get data related to addPlayer task
					if(!cmdItr.hasNext()) {
						System.out.println("wrong Command");
						return false;
					}
					eTask.taskData.add(cmdItr.next());
					tasksList.add(eTask);
				}
				else if(cmdStr.equals("-remove")) {
					extractedTasks eTask = new extractedTasks();
					eTask.name = tasksEnum.removeplayer;
					
					//get data related to removePlayer task
					if(!cmdItr.hasNext()) {
						System.out.println("wrong Command");
						return false;
					}
					eTask.taskData.add(cmdItr.next());
					tasksList.add(eTask);
				}
				else {
					System.out.println("wrong Command");
					return false;
				}
			}			
		}
		//Command populatecountries
		else if(cmdStr.equals("populatecountries")){
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.populatecountries;
			tasksList.add(eTask);
		}
		
		return true;
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
						default: {
							System.out.println("Invalid Command. Please Enter Startup Phase Command");
							controller.getCommand();
						}
						
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
class extractedTasks{
	public tasksEnum name;
	public ArrayList<String> taskData;
	
	public extractedTasks(){
		taskData = new ArrayList<String>();
	}
}
