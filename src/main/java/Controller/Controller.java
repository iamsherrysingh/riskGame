
package Controller;
import Model.Mapx;
import java.util.Scanner;

public class Controller {
	enum States {mapEditor,gamePlay,startupPhase,reinforcementPhase,attackPhase,fortificationPhase }
	enum Tasks {addcontinent,removecontinent,addcountry,removecountry,addNeighbor,removeNeighbor,saveMap,editMap,validateMap,showMap,loadMap}
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
			if( command.length == 4 ) {
				if(command[1].equals("-add")){
					continentName = command[2];
					controlValue = Integer.parseInt(command[3]);	
					currentTask = Tasks.addcontinent;
					return true;
					// check control value number
				}
				else if(command[1].equals("-remove")){
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
			else {
				System.out.println("'wrong Command' ---> editcontinent -add continentname continentvalue");
				System.out.println("                ---> editcontinent -remove continentname");
				return false;
			}
		}
		else if(command[0].equals("editcountry")){
			if( command.length == 4 ) {
				if(command[1].equals("-add")){
					countryName = command[2];
					continentName = command[3];	
				    currentTask = Tasks.addcountry;
				    return true;
				}
				else if(command[1].equals("-remove")){
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
			else {
				System.out.println("'wrong Command' ---> editcountry -add countryname continentname");
				System.out.println("                ---> editcountry -remove countryname");
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
		else if(command[0].equals("loadmap")){
			if( command.length == 2 ) {
				mapFile = command[1] + ".map";
				currentTask = Tasks.loadMap;
				return true;
			}
			else {
				System.out.println("wrong Command ---> loadmap filename");
				return false;
			}
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
						controller.currentState = States.gamePlay;
						break;
					
					default: System.out.println("Invalid Command. Please Enter Map Editor Command");
				}
			}
			else if( controller.currentState == States.gamePlay )
			{
				switch (controller.currentTask){
					case showMap:
					//	map.showMap();
						controller.currentState = States.gamePlay;
						break;
					default: System.out.println("Invalid Command. Please Enter Game Play Command");
				}
			}
			else if( controller.currentState == States.startupPhase )
			{
				switch (controller.currentTask){
					case loadMap:
						//	map.loadMap();
						controller.currentState = States.startupPhase;
						break;
					default: System.out.println("Invalid Command. Please Enter Startup Phase Command");
				}
			}
		/*	else if(CurrentState == Attack)
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
