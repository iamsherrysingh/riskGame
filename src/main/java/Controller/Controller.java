package Controller;
import Model.Mapx;
import java.util.Scanner;

public class Controller {
	enum States {mapEditor,gamePlay,StartupPhase,ReinForcement,Attack,Fortification }
	enum Tasks {addContinent,removeContinent,addCountry,removeCountry,addNeighbor,removeNeighbor,saveMap,editMap,validateMap,showMap}
	States currentState = States.mapEditor;
	Tasks currentTask;
	String continentName;
	Integer controlValue;
	String countryName;
	String neighborCountryName;
	String mapFile;
	// parse input Instruction -> Command , Switch, Data
	boolean GetCommand(){
		System.out.println("Enter Command");
		Scanner scan = new Scanner(System.in);
		String instruction = scan.nextLine().trim();
		String[] command = instruction.split(" ");

		if(command[0].equals("editcontinent")){
			if(command[1].equals("-add")){
				continentName = command[2];
				controlValue = Integer.parseInt(command[3]);	
				currentTask = Tasks.addContinent;
				return true;
				// check control value number
			}
			else if(command[1].equals("-remove")){
				continentName = command[2];
				currentTask = Tasks.removeContinent;
				return true;
			}
			else{
				System.out.println("wrong Command");
				return false;
			}
		}
		else if(command[0].equals("editcountry")){
			if(command[1].equals("-add")){
				countryName = command[2];
				continentName = command[3];	
			    currentTask = Tasks.addCountry;
			    return true;
			}
			else if(command[1].equals("-remove")){
				countryName = command[2];
				currentTask = Tasks.removeCountry;
				return true;
			}
			else{
				System.out.println("wrong Command");
				return false;
			}
		}
		else if(command[0].equals("editneighbor")){
			if(command[1].equals("-add")){
				countryName = command[2];
				neighborCountryName = command[3];	
				currentTask = Tasks.addNeighbor;
				return true;
			}
			else if(command[1].equals("-remove")){
				countryName = command[2];
				neighborCountryName = command[3];
				currentTask = Tasks.removeNeighbor;
				return true;
			}
			else{
				System.out.println("wrong Command");
				return false;
			}
		}
		else if(command[0].equals("savemap")){
			if( command.length == 2 ) {
				mapFile = command[1] + ".map";
				currentTask = Tasks.saveMap;
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
				currentTask = Tasks.editMap;
				return true;
			}
			else {
				System.out.println("wrong Command ---> editmap filename");
				return false;
			}
		}
		else if(command[0].equals("validatemap")){
			currentTask = Tasks.validateMap;
			return true;
		}
		else if(command[0].equals("showmap")){
			currentTask = Tasks.showMap;
			return true;
		}
		else {
			System.out.println("wrong Command");
			return false;
		}
	}
	
    public static void main(String[] args) {
        Controller controller= new Controller();
    //    controller.startGame();
    	boolean gameFinished = false;
    	
		while(!gameFinished){
			if(!controller.GetCommand())
				continue;	
			if( controller.currentState == States.mapEditor){
				switch (controller.currentTask){
					case addContinent:	
					//	map.addContinent(continentName,controlValue);
						break;
					case removeContinent:		
					//	map.removeContinent(continentName);
						break;
					case addCountry:
					//	map.addCountry(countryName,continentName);
						break;
					case removeCountry:
					//	map.removeCountry(countryName);
						break;
					case addNeighbor:
					//	map.addNeighbor(countryName,neighborCountryName);
						break;
					case removeNeighbor:
					//	map.addNeighbor(countryName,neighborCountryName);
						break;
					case saveMap:
					//	map.saveMap(mapFile);
						break;
					case editMap:
					//	map.editMap(mapFile);
						break;
					case validateMap:
					//  if(map.checkValidityOfMap())   return true or false
					//	controller.currentState = States.gamePlay;
						break;
					
					default: System.out.println("Invalid Command. Please Enter Map Editor Command");
				}
			}
			else if( controller.currentState == States.gamePlay )
			{
				switch (controller.currentTask){
					case showMap:
					//	map.showMap();
					//	controller.currentState = States.gamePlay;
						break;
					default: System.out.println("Invalid Command. Please Enter Game Play Command");
				}
			}
		/*	else if(CurrentState == ReinForcement)
			{
			}
			else if(CurrentState == Attack)
			{
			}
			else if(CurrentState == Fortification)
			{
			} */
		}
    }
    void startGame(){
        Mapx map= new Mapx();
        map.createGameGraph("src/main/resources/map.map").printGraph();
        Scanner commandScanner= new Scanner(System.in);
        System.out.print ("Enter number of Players: ");
        Integer numberOfPlayers= Integer.parseInt(commandScanner.nextLine().trim());

    }
}
