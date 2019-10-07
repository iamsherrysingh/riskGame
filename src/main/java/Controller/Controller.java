package Controller;
import java.io.IOException;
import java.util.*;
import Model.*;
import Database.Database;

public class Controller {
	 
	enum States {EditMap,StartupPhase,ReinForcement,Attack,Fortification }
	enum Tasks {addContinent,removeContinent,addCountry,removeCountry,addNeighbor,ShowMap }
	States currentState = States.EditMap;
	
	Tasks currentTask;
	String continentName;
	Integer controlValue;
	String countryName;
	String neighborCountryName;
	
	// parse input Instruction -> Command , Switch, Data
	boolean GetCommand(){
		System.out.println("Enter Command");
		Scanner scan = new Scanner(System.in);
		String instruction = scan.nextLine();
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
		if(command[0].equals("editneighbor")){
			if(command[1].equals("-add")){
				countryName = command[2];
				neighborCountryName = command[3];	
				currentTask = Tasks.addCountry;
				return true;
			}
			else if(command[1].equals("-remove")){
				countryName = command[2];
				neighborCountryName = command[3];
				currentTask = Tasks.removeCountry;
				return true;
			}
			else{
				System.out.println("wrong Command");
				return false;
			}
		}
		else {
			System.out.println("wrong Command");
			return false;
		}
	}
	
    public static void main(String[] args) throws IOException {
		try {
			Controller controller = new Controller();
			controller.startGame();

			boolean gameFinished = false;

			while (!gameFinished) {
				if (!controller.GetCommand())
					continue;
				if (controller.currentState == States.EditMap) {
					switch (controller.currentTask) {
						case addContinent:
							gameFinished = true;
							//	map.AddContinent(continentName, controlValue); // ContinentName and ContinentValue extracted form User command
							//	System.out.println("continentName="+controller.continentName+" controlValue="+controller.controlValue);
							break;
						//	case RemoveContinet:

						//			map.RemoveContinent(ConrinentNAme);
						//	break;
						//	case AddCountry:

						//		break;
						//	case RemoveCountry:

						//		break;
						//	case ValidateMap:
						//		break;
						//	if(map.CheckvalidityOfMap())
						//Currenttate = StartupPhase;

						default:
							System.out.println("Invalid Command");
					}
				}
		/*	else if(CurrentState == StartupPhase)
			{
				switch CurrentTask
				{
					case
				}
			}
			else if(CurrentState == ReinForcement)
			{
			}
			else if(CurrentState == Attack)
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
		Graph g=map.createGameGraph("src/main/resources/map.map");
		g.printGraph();
		System.out.println("======"+map.validateMap(g));
		map.saveMap(g);
		System.out.println("======");
		for(Continent c: Database.getInstance().getContinentList()){
			System.out.println(c.getName());
		}
		System.out.println("====");
		System.out.println(Database.getInstance().getContinentList().size());

		map.addCountry("Havanna","Australia",g);
		g.printGraph();
		System.out.println(g.getAdjList().get(42).getInContinent());
		map.addNeighbour("Havanna","India", g);
		g.printGraph();
		map.addNeighbour("Swarg","Pataal",g);
		map.removeCountry("japan",g);
		g.printGraph();

		map.addContinent("Jupiter",67);
		Database.getInstance().printContinentList();

		map.removeContinent("Asia",g);
		g.printGraph();
		Database.getInstance().printContinentList();

		map.removeNeighbour("Congo", "North-africa",g);
        g.printGraph();
        map.addArmiesToCountry("Egypt",100,g);
        System.out.println(g.getAdjList().get(21).getNumberOfArmies());
		System.out.println(map.validateMap(g));
//        Scanner commandScanner= new Scanner(System.in);
//        System.out.print ("Enter number of Players: ");
//        Integer numberOfPlayers= Integer.parseInt(commandScanner.nextLine().trim());

    }
    
   
    
    
}
