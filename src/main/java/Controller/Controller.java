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
	enum tasksEnum {unknown,addcontinent,removecontinent,addcountry,removecountry,addneighbor,removeneighbor,savemap,editmap,validatemap,showmap,loadmap,addplayer,removeplayer,populatecountries,placearmy,placeall,reinforce,fortifycountry,fortifynone}
	States currentState = States.mapEditor;
	tasksEnum currentTask;
	String continentName,countryName,neighborCountryName,mapFile,playerName;
	Integer controlValue,playerId=1;
	private Mapx mapObj;
	private ArrayList<Player> playerObjs;
	boolean editPlayerFinished = false;
	boolean gameFinished = false;
	
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
	
	boolean checkValidityOfTasksList(ArrayList<extractedTasks> tasksList) {
		
		// check commands that are valid in state of mapEditor
		if( currentState == States.gamePlay) {		
			for(extractedTasks itr:tasksList) {
				switch (itr.name){	
					case addcontinent:	
						String controlValueStr = itr.taskData.get(1);	
						//check if control value is numeric
						for(int i=0; i<controlValueStr.length(); i++) {
							if(!Character.isDigit(controlValueStr.charAt(i))){
								System.out.println("Wrong Control Value");
								return false;
							}
						}
						break;
					case removecontinent:		
						break;
					case addcountry:
						break;
					case removecountry:
						break;
					case addneighbor:
						break;
					case removeneighbor:
						break;
					case editmap:
						break;
					case validatemap:
						break;
					case savemap:
						currentState = States.gamePlay;
						break;
					default:
						System.out.println("Invalid Command. Please Enter Map Editor Command");
						return false;
				}
			}			
		} 
		else if( currentState == States.gamePlay ){
			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case showmap:
						currentState = States.startupPhase;
						break;
					default: 
						System.out.println("Invalid Command. Please Enter Game Play Command");
						return false;
				}
			}
		}
		else if(currentState == States.startupPhase){
			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case loadmap:
						currentState = States.editPlayer;
						break;
					default: 
						System.out.println("Invalid Command. Please Enter Startup Phase Command");
						return false;
				}
			}
		} 
		else if( currentState == States.editPlayer ){
			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case addplayer:	
					break;
				case removeplayer:
					break;
				case populatecountries:
					currentState = States.troopArmies;
					break;		
				default:
					System.out.println("Invalid Command. Please Enter Startup Phase Command");
					return false;		
				}	
			}	
		}
		else if(currentState == States.troopArmies){
  			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case placearmy:
						break;	
					case placeall:
						//	currentState = States.reinforcementPhase;
						break;	
					default:
						System.out.println("Invalid Command. Please Enter Troop Armies Phase Command");
						return false;
				}
  			}
		}
		else if(currentState == States.reinforcementPhase){
  			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case reinforce:
						break;	
					default:
						System.out.println("Invalid Command. Please Enter Troop Armies Phase Command");
						return false;
				}
			}
		}
	/*	else if(currentState == States.fortificationPhase){
  			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case reinforce:
						break;	
					default:
						System.out.println("Invalid Command. Please Enter Troop Armies Phase Command");
						return false;
				}
			}
		} */  
		return true;
	}
	
	boolean cmdController(ArrayList<extractedTasks> tasksList){	
		if(!checkValidityOfTasksList(tasksList))
			return false; 
		
		for(extractedTasks itr:tasksList) {		
			
			switch (itr.name){	
			
				case addcontinent:	
					continentName = itr.taskData.get(0);
					controlValue = Integer.parseInt(itr.taskData.get(1));
				//	System.out.println("-add " + continentName + " " +controlValue);
					//addContinent(continentName,controlValue);
					break;
				case removecontinent:
					continentName = itr.taskData.get(0);
				//	System.out.println("-remove " + continentName);
					//removeContinent();
					break;
				case addcountry:
					//addCountry();
					break;
				case removecountry:
					break;
				case addneighbor:
					break;
				case removeneighbor:
					break;
				case savemap:
					break;
				case editmap:
					break;
				case validatemap:
					break;	
				case showmap:
					break;
				case loadmap:
					break;
				case addplayer:
					Player playerobj = new Player();
					playerName = itr.taskData.get(0);
					playerobj.setName(playerName);
					playerobj.setId(playerId);
					playerObjs.add(playerobj);
					playerId++;
					System.out.println(playerobj.getName());
					break;
				case removeplayer:
					for(Player itrPlayerObjs : playerObjs){
						playerName = itr.taskData.get(0);
						if(itrPlayerObjs.getName().equals(playerName)) {
							playerObjs.remove(itrPlayerObjs);
						}
						else {
							System.out.println("This Player does not exist. Please enter the correct Player");
						}
					}
					break;
				case populatecountries:
					for(Player name:playerObjs) {
						System.out.println(name.getName());
					} 
					break;
				default:
					System.out.println("Invalid Command. Please Enter Map Editor Command");
				return false;
			}
			
		} 
		
		return true;
	}
		
    public static void main(String[] args) throws IOException {
		try {
			Controller controller = new Controller();
			controller.playerObjs = new ArrayList<Player>();
		//	controller.startGame();
	    	
			while(!controller.gameFinished){
				ArrayList<extractedTasks> tasksList = new ArrayList<extractedTasks>();
				if(!controller.getCommand(tasksList))
					continue;
				if(!controller.cmdController(tasksList)) {
					continue;
				} 
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
