package Controller;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*; 
import Model.*;

enum tasksEnum {
	unknown,
	addcontinent,
	removecontinent,
	addcountry,
	removecountry,
	addneighbor,
	removeneighbor,
	savemap,
	editmap,
	validatemap,
	showmap,
	loadmap,
	addplayer,
	removeplayer,
	populatecountries,
	placearmy,
	placeall,
	reinforce,
	fortify,
	ignorefortify
	}
/**
 * THis is the main Controller class.
 * This interfaces with the console/terminal and processes the commands.
 * Most of these commands have corresponding methods in Model package.
 */
public class Controller {
	
	tasksEnum currentTask;
	String continentName,countryName,neighborCountryName,mapFile,playerName;
	boolean editPlayerFinished = false;
	
	private GamePlay gamePlayObj = null;
	
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
		//Command placearmy
		else if(cmdStr.equals("placearmy")) {
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.placearmy;
			
			//get data related to placearmy task
			eTask.taskData.add(cmdItr.next());
			
			tasksList.add(eTask);
		}
		//Command placeall
		else if(cmdStr.equals("placeall")) {
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.placeall;
			tasksList.add(eTask);
		}
		//Command reinforce
		else if(cmdStr.equals("reinforce")) {
			
			extractedTasks eTask = new extractedTasks();
			eTask.name = tasksEnum.reinforce;
			
			//get data related to reinforce task
			for(int i=0;i<2;i++) {
				if(!cmdItr.hasNext()) {
					System.out.println("wrong Command");
					return false;
				}
				eTask.taskData.add(cmdItr.next());
			}
			tasksList.add(eTask);
		}
		//Command fortify (both types of command)
		else if(cmdStr.equals("fortify")) {
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			//Detect type of fortify according its number of data
			//If it has only 1 data, the task will be "ignorfortify"
			//If it has 3 data, the task will be "fortify"
			//Otherwise, it is a wrong command
			extractedTasks eTask = new extractedTasks();
			String firstData = cmdItr.next();
			if(!cmdItr.hasNext()) {
				eTask.name = tasksEnum.ignorefortify;
				eTask.taskData.add(firstData);
			}
			else {
				eTask.name = tasksEnum.fortify;
				eTask.taskData.add(firstData);
				eTask.taskData.add(cmdItr.next());
				
				if(!cmdItr.hasNext()) {
					System.out.println("wrong Command");
					return false;
				}
				eTask.taskData.add(cmdItr.next());
			}
			
			tasksList.add(eTask);
		}
		
		return true;
	}
	
	boolean checkValidityOfTasksList(ArrayList<extractedTasks> tasksList) {
		
		// check commands that are valid in state of mapEditor
		if( gamePlayObj.getCurrentState() == State.mapEditor) {		
			for(extractedTasks itr:tasksList) {
				switch (itr.name){	
					case addcontinent:	
						String controlValueStr = itr.taskData.get(1);	
						//check if control value is numeric
						for(int i=0; i<controlValueStr.length(); i++) {
							if(!Character.isDigit(controlValueStr.charAt(i))){
								System.out.println("Invalid Command: Control Value shoud be digit");
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
						break;
					default:
						System.out.println("Invalid command in the current state");
						return false;
				}
			}			
		} 
		else if(gamePlayObj.getCurrentState() == State.gamePlay ){
			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case showmap:
						break;
					default: 
						System.out.println("Invalid command in the current state");
						return false;
				}
			}
		}
		else if(gamePlayObj.getCurrentState() == State.startupPhase){
			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case loadmap:
						break;
					default: 
						System.out.println("Invalid command in the current state");
						return false;
				}
			}
		} 
		else if(gamePlayObj.getCurrentState() == State.editPlayer ){
			for(extractedTasks itr:tasksList) {
				switch (itr.name){
				case addplayer:	
					break;
				case removeplayer:
					break;
				case populatecountries:
					break;		
				default:
					System.out.println("Invalid command in the current state");
					return false;		
				}	
			}	
		}
		else if(gamePlayObj.getCurrentState() == State.troopArmies){
  			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case placearmy:
						break;	
					case placeall:
						break;	
					default:
						System.out.println("Invalid command in the current state");
						return false;
				}
  			}
		}
		else if(gamePlayObj.getCurrentState() == State.reinforcementPhase){
  			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case reinforce:
						break;	
					default:
						System.out.println("Invalid command in the current state");
						return false;
				}
			}
		}
		else if(gamePlayObj.getCurrentState() == State.fortificationPhase){
  			for(extractedTasks itr:tasksList) {
				switch (itr.name){
					case fortify:
						String numOfFortify = itr.taskData.get(2);	
						//check if the data related to the number of army in fortify command is numeric
						for(int i=0; i<numOfFortify.length(); i++) {
							if(!Character.isDigit(numOfFortify.charAt(i))){
								System.out.println("Invalid command: Number of army should be digit");
								return false;
							}
						}
						break;	
					case ignorefortify:
						String tmpData = itr.taskData.get(0);
						if(!tmpData.equals("none")) {
							System.out.println("Invalid Command: For withdraw of moving in fortify state should use \"none\"");
							return false;
						}
						break;
					default:
						System.out.println("Invalid command in the current state");
						return false;
				}
			}
		}  
		return true;
	}
	
	boolean cmdController(ArrayList<extractedTasks> tasksList){	
		if(!checkValidityOfTasksList(tasksList))
			return false; 
		
		for(extractedTasks itr:tasksList) {		
			
			switch (itr.name){	
			
				case addcontinent:
				{
					if(!gamePlayObj.addContinent(itr.taskData.get(0),Integer.parseInt(itr.taskData.get(1))))
						return false;
				
					break;
				}
				case removecontinent:
				{	
					if(!gamePlayObj.removeContinent(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case addcountry:
				{
					if(!gamePlayObj.addCountry(itr.taskData.get(0),itr.taskData.get(1)))
						return false;
					
					break;
				}
				case removecountry:
				{
					if(!gamePlayObj.removeCountry(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case addneighbor:
				{
					if(!gamePlayObj.addNeighbor(itr.taskData.get(0),itr.taskData.get(1)))
						return false;
					
					break;
				}
				case removeneighbor:
				{
					if(!gamePlayObj.removeNeighbor(itr.taskData.get(0),itr.taskData.get(1)))
						return false;
					
					break;
				}
				case savemap:
				{
					if(!gamePlayObj.saveMap(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case editmap:
				{
					if(!gamePlayObj.editMap(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case validatemap:
				{
					if(!gamePlayObj.validateMap())
						return false;
					
					break;	
				}
				case showmap:
				{
					if(!gamePlayObj.showMap())
						return false;
					
					break;
				}
				case loadmap:
				{
					if(!gamePlayObj.loadMap(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case addplayer:
				{
					if(!gamePlayObj.addPlayer(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case removeplayer:
				{
					if(!gamePlayObj.removePlayer(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case populatecountries:
				{
					if(!gamePlayObj.populateCountries())
						return false;
					
					break;
				}
				case placearmy:
				{
//					if(!gamePlayObj.placeArmy(itr.taskData.get(0)))
//						return false;
					// Need to change the function call to placeArmy
					
					break;
				}
				case placeall:
				{
//					if(!gamePlayObj.placeAll())
//						return false;
//					placeAll() method defined. Modify call as required
					break;
				}
				case reinforce:
				{
					if(!gamePlayObj.reinforceArmy(itr.taskData.get(0),Integer.parseInt(itr.taskData.get(1))))
						return false;
					
					break;
				}
				case fortify:
				{
					if(!gamePlayObj.fortifyArmy(itr.taskData.get(0),itr.taskData.get(1),Integer.parseInt(itr.taskData.get(2))))
						return false;
					
					break;
				}
				case ignorefortify:
				{
					if(!gamePlayObj.ignoreFortifyArmy())
						return false;
					
					break;
				}
				default:
					return false;
			}
		} 	
		return true;
	}
		
    public static void main(String[] args) throws IOException {
		try {
			Controller controller = new Controller();
			controller.gamePlayObj = GamePlay.getInstance();
	    	
			while(controller.gamePlayObj.getCurrentState() != State.gameFinished){
				
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


	/**
	 * This method starts the execution of the game.
	 * This method is run by Controller.main()
	 * @throws IOException
	 */
/*    void startGame() throws IOException{
        Mapx map= new Mapx();
		Graph g=map.createGameGraph("src/main/resources/map.map");
		g.showMap();
		System.out.println("======"+map.validateMap(g));
		map.saveMap(g);
		System.out.println("======");
		for(Continent c: Database.getInstance().getContinentList()){
			System.out.println(c.getName());
		}
		System.out.println("====");
		System.out.println(Database.getInstance().getContinentList().size());

		map.addCountry("Havanna","Australia",g);
		g.showMap();
		System.out.println(g.getAdjList().get(42).getInContinent());
		map.addNeighbour("Havanna","India", g);
		g.showMap();
		map.addNeighbour("Swarg","Pataal",g);
		map.removeCountry("japan",g);
		g.showMap();

		map.addContinent("Jupiter",67);
		Database.getInstance().printContinentList();

		map.removeContinent("Asia",g);
		g.showMap();
		Database.getInstance().printContinentList();

		map.removeNeighbour("Congo", "North-africa",g);
        g.showMap();
        map.addArmiesToCountry("Egypt",100,g);
        System.out.println(g.getAdjList().get(21).getNumberOfArmies());
		System.out.println(map.validateMap(g));
        Scanner commandScanner= new Scanner(System.in);
        System.out.print ("Enter number of Players: ");
        Integer numberOfPlayers= Integer.parseInt(commandScanner.nextLine().trim());       
    }
*/
    
}

class extractedTasks{
	public tasksEnum name;
	public ArrayList<String> taskData;
	
	public extractedTasks(){
		taskData = new ArrayList<String>();
	}

}

