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
	tournament,
	savegame,
	loadgame,
	loadmap,
	addplayer,
	removeplayer,
	populatecountries,
	placearmy,
	placeall,
	exchangecards,
	ignoreexchangecards,
	reinforce,
	normalattack,
	alloutattack,
	ignoreattack,
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
	
	
	/**
	 * parse input Instruction -> Command , Switch, Data
	 * @param tasksList It is an Array List that has all the commands
	 * @return true(If the method runs successfully) or false(If the command entered is invalid)
	 */
	boolean getCommand(ArrayList<ExtractedTasks> tasksList) {

		System.out.print("Enter Command: ");
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
		if(cmdStr.equalsIgnoreCase("editcontinent")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			while(cmdItr.hasNext())
			{
				//get and check the switches
				cmdStr = cmdItr.next();
				if(cmdStr.equals("-add")) {
					ExtractedTasks eTask = new ExtractedTasks();
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
					ExtractedTasks eTask = new ExtractedTasks();
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
		else if(cmdStr.equalsIgnoreCase("editcountry")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			while(cmdItr.hasNext())
			{
				//get and check the switches
				cmdStr = cmdItr.next();
				if(cmdStr.equals("-add")) {
					ExtractedTasks eTask = new ExtractedTasks();
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
					ExtractedTasks eTask = new ExtractedTasks();
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
		else if(cmdStr.equalsIgnoreCase("editneighbor")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			while(cmdItr.hasNext())
			{
				//get and check the switches
				cmdStr = cmdItr.next();
				if(cmdStr.equals("-add")) {
					ExtractedTasks eTask = new ExtractedTasks();
					eTask.name = tasksEnum.addneighbor;
					
					//get data related to addneighbor task
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
					ExtractedTasks eTask = new ExtractedTasks();
					eTask.name = tasksEnum.removeneighbor;
					
					//get data related to removeneighbor task
					for(int i=0;i<2;i++) {
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
		else if(cmdStr.equalsIgnoreCase("savemap")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.savemap;
			
			//get data related to savemap task
			eTask.taskData.add(cmdItr.next());
			
			tasksList.add(eTask);
		}
		//Command editmap
		else if(cmdStr.equalsIgnoreCase("editmap")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.editmap;
			
			//get data related to editmap task
			eTask.taskData.add(cmdItr.next());
			
			tasksList.add(eTask);
		}
		//Command validatemap
		else if(cmdStr.equalsIgnoreCase("validatemap")){
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.validatemap;
			tasksList.add(eTask);
		}
		//Command showmap
		else if(cmdStr.equalsIgnoreCase("showmap")){
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.showmap;
			tasksList.add(eTask);
		}
		
		//Command Tournament
		else if(cmdStr.equalsIgnoreCase("tournament")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.tournament;	
			boolean flagM = false, flagP = false, flagG = false, flagD = false;
			cmdStr = cmdItr.next();
			
			// command should start with switch
			if( cmdStr.charAt(0) != '-') {
				System.out.println("wrong Command");
				return false;
			}
			
			// Check if -M, -P, -G, -D switches exist and there is no duplicate switches.
			while(true){	
				
				if( cmdStr.charAt(0) == '-') {
					if( cmdStr.equals("-M") ) {
						if( flagM ){
							System.out.println("wrong Command");
							return false;
						}	
						flagM = true;
					}
					else if( cmdStr.equals("-P") ) {
						if( flagP ){
							System.out.println("wrong Command");
							return false;
						}
						flagP = true;
					}
					else if( cmdStr.equals("-G") ) {
						if( flagG ){
							System.out.println("wrong Command");
							return false;
						}
						flagG = true;
					}
					else if( cmdStr.equals("-D") ) {
						if( flagD ){
							System.out.println("wrong Command");
							return false;
						}
						flagD = true;
					}
					else {
						System.out.println("wrong Command");
						return false;
					}
				}
				eTask.taskData.add(cmdStr);	
				if( !cmdItr.hasNext() ) 
					break;
				
				cmdStr = cmdItr.next();
			}
			if( flagM == false || flagP == false || flagG == false || flagD == false ) {
				System.out.println("wrong Command");
				return false;
			}
			tasksList.add(eTask);
		}
			
		//Command loadmap
		else if(cmdStr.equalsIgnoreCase("loadmap")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.loadmap;
			
			//get data related to loadmap task
			eTask.taskData.add(cmdItr.next());
			
			tasksList.add(eTask);
		}
		//Command gameplayer
		else if(cmdStr.equalsIgnoreCase("gameplayer")){
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			while(cmdItr.hasNext())
			{
				//get and check the switches
				cmdStr = cmdItr.next();
				if(cmdStr.equals("-add")) {
					ExtractedTasks eTask = new ExtractedTasks();
					eTask.name = tasksEnum.addplayer;
					
					//get data related to addPlayer task
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
					ExtractedTasks eTask = new ExtractedTasks();
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
		else if(cmdStr.equalsIgnoreCase("populatecountries")){
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.populatecountries;
			tasksList.add(eTask);
		}
		//Command placearmy
		else if(cmdStr.equalsIgnoreCase("placearmy")) {
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.placearmy;
			
			//get data related to placearmy task
			eTask.taskData.add(cmdItr.next());
			
			tasksList.add(eTask);
		}
		//Command placeall
		else if(cmdStr.equalsIgnoreCase("placeall")) {
			ExtractedTasks eTask = new ExtractedTasks();
			eTask.name = tasksEnum.placeall;
			tasksList.add(eTask);
		}
		
		//Command exchangeCards
		else if(cmdStr.equalsIgnoreCase("exchangecards")) {
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			ExtractedTasks eTask = new ExtractedTasks();
			if( command.size() == 4 || command.size() == 2 ) {
				String firstData = cmdItr.next();
				if(!cmdItr.hasNext()) {
					eTask.name = tasksEnum.ignoreexchangecards;
					eTask.taskData.add(firstData);
				}
				else {
					eTask.name = tasksEnum.exchangecards;
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
			else {
				System.out.println("wrong Command");
				return false;
			}

		}
		
		//Command reinforce
		else if(cmdStr.equalsIgnoreCase("reinforce")) {
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			ExtractedTasks eTask = new ExtractedTasks();
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
		
		//Command attack
		else if(cmdStr.equalsIgnoreCase("attack")) {
			
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			ExtractedTasks eTask = new ExtractedTasks();
			
			if( command.size() == 4 ) {	
				if( command.get(command.size() - 1 ).equalsIgnoreCase("-allout") ){
					
					eTask.name = tasksEnum.alloutattack;
					//get data related to attack all out task
					for(int i=0;i<2;i++) {
						eTask.taskData.add(cmdItr.next());
					}
					tasksList.add(eTask);
				}
				else {
					eTask.name = tasksEnum.normalattack;
					//get data related to normal attack task
					for(int i=0; i<3; i++) {
						eTask.taskData.add(cmdItr.next());
					}
					tasksList.add(eTask);
				}
			}
			else if( command.size() == 2 ) {
				if( command.get(1).equalsIgnoreCase("-noattack") ){	
					
					eTask.name = tasksEnum.ignoreattack;
					tasksList.add(eTask);
				}
				else {
					System.out.println("wrong Command");
					return false;
				}
			}
			else {
				System.out.println("wrong Command");
				return false;
			}
		}
		
		//Command fortify (both types of command)
		else if(cmdStr.equalsIgnoreCase("fortify")) {
			if(!cmdItr.hasNext()) {
				System.out.println("wrong Command");
				return false;
			}
			
			//Detect type of fortify according its number of data
			//If it has only 1 data, the task will be "ignorefortify"
			//If it has 3 data, the task will be "fortify"
			//Otherwise, it is a wrong command
			ExtractedTasks eTask = new ExtractedTasks();
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
		else {
			System.out.println("wrong Command");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check if the command is valid In the current state of the game or not.
	 * @param tasksList
	 * @return
	 */
	boolean checkValidityOfTasksList(ArrayList<ExtractedTasks> tasksList) {
		
		// check commands that are valid in state of startGame
		if( gamePlayObj.getCurrentState() == State.initializeGame) {
			for(ExtractedTasks itr:tasksList) {
				switch (itr.name){		
					case tournament:
						break;
					case editmap:
						break;
					case loadmap:
						break;
					case loadgame:
						break;
					default:
						System.out.println("Invalid command in the current state");
						return false;
				}
			}
		}
		// check commands that are valid in state of mapEditor
		else if( gamePlayObj.getCurrentState() == State.mapEditor) {		
			for(ExtractedTasks itr:tasksList) {
				switch (itr.name){	
					case addcontinent:	
						String controlValueStr = itr.taskData.get(1);	
						//check if control value is numeric
						for(int i=0; i<controlValueStr.length(); i++) {
							if(!Character.isDigit(controlValueStr.charAt(i))){
								System.out.println("Invalid Command: Your input for Control Value is not valid.");
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
					case showmap:
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
		// check commands that are valid in state of editPlayer
		else if(gamePlayObj.getCurrentState() == State.editPlayer ){
			for(ExtractedTasks itr:tasksList) {
				switch (itr.name){
				case showmap:
					break;
				case addplayer:	
					if( !itr.taskData.get(1).equalsIgnoreCase("aggressive") && !itr.taskData.get(1).equalsIgnoreCase("benevolent") && !itr.taskData.get(1).equalsIgnoreCase("cheater") && !itr.taskData.get(1).equalsIgnoreCase("human") && !itr.taskData.get(1).equalsIgnoreCase("random") ) {
						System.out.println(itr.taskData.get(1)+" Strategy is not valid.");
						return false;
					}
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
		// check commands that are valid in state of troopArmies
		else if(gamePlayObj.getCurrentState() == State.troopArmies){
  			for(ExtractedTasks itr:tasksList) {
				switch (itr.name){
					case showmap:
						break;
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
		// check commands that are valid in state of exchangeCardsPhase
		else if(gamePlayObj.getCurrentState() == State.exchangeCards){
  			for(ExtractedTasks itr:tasksList) {
				switch (itr.name){
					case showmap:
						break;
					case exchangecards:
						for(int j=0; j < 3; j++) {
							String cardNumber = itr.taskData.get(j);	
							//check if the data related to the card's numbers in exchange cards command is numeric
							for(int i=0; i<cardNumber.length(); i++) {
								if(!Character.isDigit(cardNumber.charAt(i))){
									System.out.println("Invalid command: Your Input for Card's Number is not valid.");
									return false;
								}
							}
						}
						break;	
					case ignoreexchangecards:
						String tmpData = itr.taskData.get(0);
						if(!tmpData.equals("-none")) {
							System.out.println("Invalid Command: For withdraw of exchanging cards should use \"-none\"");
							return false;
						}
						break;
					default:
						System.out.println("Invalid command in the current state");
						return false;
				}
			}
		}
		// check commands that are valid in state of reinforcementPhase
		else if(gamePlayObj.getCurrentState() == State.reinforcementPhase){
			
  			for(ExtractedTasks itr:tasksList) {
				switch (itr.name){
					case showmap:
						break;
					case reinforce:
						String rnfcNumber = itr.taskData.get(2);	
						//check if the data related to the number of dice in attack command is numeric
						for(int i=0; i<rnfcNumber.length(); i++) {
							if(!Character.isDigit(rnfcNumber.charAt(i))){
								System.out.println("Invalid command: Your Input for number is not valid.");
								return false;
							}
						}
						break;	
					default:
						System.out.println("Invalid command in the current state");
						return false;
				}
			}
		}
		// check commands that are valid in state of Attack
		else if(gamePlayObj.getCurrentState() == State.attackPhase){
			
  			for(ExtractedTasks itr:tasksList) {
				switch (itr.name){
					case showmap:
						break;
					case alloutattack:
						break;
					case normalattack:
						String numOfDice = itr.taskData.get(2);	
						//check if the data related to the number of dice in attack command is numeric
						for(int i=0; i<numOfDice.length(); i++) {
							if(!Character.isDigit(numOfDice.charAt(i))){
								System.out.println("Invalid command: Your Input for number is not valid.");
								return false;
							}
						}
						break;
					case ignoreattack:
						break;
					default:
						System.out.println("Invalid command in the current state");
						return false;
				}
			}
		}
		// check commands that are valid in state of fortificationPhase
		else if(gamePlayObj.getCurrentState() == State.fortificationPhase){
  			for(ExtractedTasks itr:tasksList) {
				switch (itr.name){
					case showmap:
						break;	
					case fortify:
						String numOfFortify = itr.taskData.get(2);	
						//check if the data related to the number of army in fortify command is numeric
						for(int i=0; i<numOfFortify.length(); i++) {
							if(!Character.isDigit(numOfFortify.charAt(i))){
								System.out.println("Invalid command: Your Input for number is not valid.");
								return false;
							}
						}
						break;	
					case ignorefortify:
						String tmpData = itr.taskData.get(0);
						if(!tmpData.equals("-none")) {
							System.out.println("Invalid Command: For withdraw of moving in fortify state should use \"-none\"");
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
	
	
	/**
	 * Invokes the corresponding function according to the command 
	 * @param tasksList
	 * @return
	 */
	boolean cmdController(ArrayList<ExtractedTasks> tasksList){	
		if(!checkValidityOfTasksList(tasksList))
			return false; 
		
		for(ExtractedTasks itr:tasksList) {		
			
			switch (itr.name){	
			
				case addcontinent:{
					if(!gamePlayObj.addContinent(itr.taskData.get(0),Integer.parseInt(itr.taskData.get(1))))
						return false;
				
					break;
				}
				case removecontinent:{	
					if(!gamePlayObj.removeContinent(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case addcountry:{
					if(!gamePlayObj.addCountry(itr.taskData.get(0),itr.taskData.get(1)))
						return false;
					
					break;
				}
				case removecountry:{
					if(!gamePlayObj.removeCountry(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case addneighbor:{
					if(!gamePlayObj.addNeighbor(itr.taskData.get(0),itr.taskData.get(1)))
						return false;
					
					break;
				}
				case removeneighbor:{
					if(!gamePlayObj.removeNeighbor(itr.taskData.get(0),itr.taskData.get(1)))
						return false;
					
					break;
				}
				case savemap:{
					if(!gamePlayObj.saveMap(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case editmap:{
					if(!gamePlayObj.editMap(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case tournament:{
					if(!gamePlayObj.tournament(itr.taskData))
						return false;			
					break;
				}
				case validatemap:{
					if(!gamePlayObj.validateMap())
						return false;
					
					break;	
				}
				case showmap:{
					if(!gamePlayObj.showMap())
						return false;
					
					break;
				}
				case loadmap:{
					if(!gamePlayObj.loadGameMap(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case addplayer:{
					if(!gamePlayObj.addPlayer(itr.taskData.get(0),itr.taskData.get(1)))
						return false;
					
					break;
				}
				case removeplayer:{
					if(!gamePlayObj.removePlayer(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case populatecountries:{
					if(!gamePlayObj.populateCountries())
						return false;
					
					break;
				}
				case placearmy:{
					if(!gamePlayObj.placeArmy(itr.taskData.get(0)))
						return false;
					
					break;
				}
				case placeall:{
					if(!gamePlayObj.placeAll())
						return false;

					break;
				}
				case exchangecards:{
					if(!gamePlayObj.exchangeCards(Integer.parseInt(itr.taskData.get(0)),Integer.parseInt(itr.taskData.get(1)),Integer.parseInt(itr.taskData.get(2))))
						return false;
					
					break;
				}
				case ignoreexchangecards:{
					if(!gamePlayObj.ignoreExchangeCards())
						return false;
					
					break;
				}
				case reinforce:{
					if(!gamePlayObj.reinforceArmy(itr.taskData.get(0),Integer.parseInt(itr.taskData.get(1))))
						return false;
					
					break;
				}
				case alloutattack:{
					if(!gamePlayObj.alloutAttack(itr.taskData.get(0),itr.taskData.get(1)))
						return false;			
					break;
				}
				case normalattack:{
					if(!gamePlayObj.normalAttack(itr.taskData.get(0),itr.taskData.get(1),Integer.parseInt(itr.taskData.get(2))))
						return false;			
					break;
				}
				case ignoreattack:{
					if(!gamePlayObj.ignoreAttack())
						return false;			
					break;
				}
				case fortify:{
					if(!gamePlayObj.fortifyArmy(itr.taskData.get(0),itr.taskData.get(1),Integer.parseInt(itr.taskData.get(2))))
						return false;
					
					break;
				}
				case ignorefortify:{
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
	
	public void handleGame() {
		
		gamePlayObj.getCurrentPlayerObj().goToFirstPlayer(gamePlayObj.getGraphObj());
		//maybe exchanging cards state here and world domination view
		gamePlayObj.CardobserverOperations();
		
		while(gamePlayObj.getCurrentState() != State.gameFinished) {
			
			PlayerStrategy currentPlayerStrategy = gamePlayObj.getCurrentPlayerObj().getCurrentPlayer().getPlayerStrategy(); 
			gamePlayObj.setPlayerStrategy();
			
			if( currentPlayerStrategy == PlayerStrategy.human ) {
				
				ArrayList<ExtractedTasks> tasksList = new ArrayList<ExtractedTasks>();
				
				gamePlayObj.setCurrentState(State.exchangeCards, "Exchange Cards");
				
				while( gamePlayObj.getCurrentState() != State.newTurn ) {
					
					if(!getCommand(tasksList))
						continue;
					if(!cmdController(tasksList)) {
						continue;
					} 
				
				}
			}
			else {
				
				gamePlayObj.autoExchangeCards();
				gamePlayObj.reinforceArmy();
				// gamePlayObj.attack();
				// gamePlayObj.fortify();
				
				if( gamePlayObj.checkEndGame())
					break;
				
				gamePlayObj.getCurrentPlayerObj().goToNextPlayer(gamePlayObj.getGraphObj());
				gamePlayObj.CardobserverOperations();
					
			}
				
			if( gamePlayObj.checkEndGame())
				break;
				
			gamePlayObj.getCurrentPlayerObj().goToNextPlayer(gamePlayObj.getGraphObj());
			gamePlayObj.CardobserverOperations();
		}	
	}
	
    public static void main(String[] args) throws IOException {
		try {
			
			Controller controller = new Controller();
			controller.gamePlayObj = GamePlay.getInstance();
			
			if( controller.gamePlayObj.getCurrentState() == State.initializeGame ) {
				System.out.println("Specify your game mode with below commands:\n");
				System.out.println("_____Single Mode_____    _____Tournament Mode_____");
				System.out.println("_ editmap                - tournament");
				System.out.println("_ loadmap");
				System.out.println("_ loadgame");
				System.out.println("__________________________________________________");
			}
			
			while( ( controller.gamePlayObj.getCurrentState() != State.startupPhase ) || ( controller.gamePlayObj.getCurrentState() != State.gameFinished ) ) {
				
				ArrayList<ExtractedTasks> tasksList = new ArrayList<ExtractedTasks>();
				
				if(!controller.getCommand(tasksList))
					continue;
				if(!controller.cmdController(tasksList)) {
					continue;
				} 
				
			}
			
			if(controller.gamePlayObj.getCurrentState() == State.gameFinished) {
				return;
			}
			
			controller.handleGame();
			
			if(controller.gamePlayObj.getCurrentState() == State.gameFinished) {
				System.out.println("===================================");
				System.out.println("======== THe Game Finished ========");
				System.out.println("======== " + controller.gamePlayObj.getCurrentPlayerName() + " is the WINNER ========");
				System.out.println("===================================");
			}
			
		}
		catch (Exception e){
			System.out.println("An error occured: "+e.getMessage());
		}
    }
    
}

/**
 * This class extracts each command 
 */
class ExtractedTasks {
	
	public tasksEnum name;
	public ArrayList<String> taskData;
	
	public ExtractedTasks(){
		taskData = new ArrayList<String>();
	}
}

