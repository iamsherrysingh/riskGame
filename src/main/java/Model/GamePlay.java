package Model;

import java.io.File;
import java.io.IOException;
import java.util.*;
import View.CardExchange;
import View.*;
import com.sun.org.apache.xerces.internal.impl.dv.DatatypeValidator;

import javax.xml.crypto.Data;

/** 
 * This Class maintains the state of the game and current player.
 * The methods of this class are called by Controller.
 * This class has singleton implementation.
 */
public class GamePlay implements ISubject{
	
	/**
	 * This file holds most of the utility functions that call other methods for
	 * implementation in gamePlay mode
	 */

	ArrayList<IObserver> observersOfGamePlay;

	public PhaseView getPhaseView() {
		return phaseView;
	}

	public void setPhaseView(PhaseView phaseView) {
		this.phaseView = phaseView;
	}

	PhaseView phaseView= new PhaseView();
	WorldDominationView worldDominationView = new WorldDominationView();

	public String getCurrentOperation() {
		return currentOperation;
	}

	public void setCurrentOperation(String currentOperation) {
		this.currentOperation = currentOperation;
		notifyObservers();
	}

	private String currentOperation;


	private static GamePlay gamePlay = null;
	private State currentState;
	private Mapx mapxObj;
	private Graph graphObj;
	private Database databaseObj;
	private CardPlay cardPlayObj;
	private CurrentPlayer currentPlayerObj;
	CardExchange cardExchangeView;
	ArrayList<IObserver> observerList = new ArrayList<IObserver>(); 


	public CurrentPlayer getCurrentPlayerObj() {
		return currentPlayerObj;
	}


	public String getCurrentPlayerName(){ return currentPlayerObj.currentPlayer.getName();}

	private GamePlay() {
		
		currentState = State.initializeGame;
    	mapxObj = new Mapx();
    	databaseObj = Database.getInstance();
    	currentPlayerObj = CurrentPlayer.getInstance();
    	graphObj=Graph.getInstance();
    	cardPlayObj = CardPlay.getInstance();
    	cardExchangeView = new CardExchange();
		observersOfGamePlay= new ArrayList<IObserver>();
		this.attachObserver(phaseView);
		this.attachObserver(worldDominationView);
    }

    public static GamePlay getInstance() {
        if(gamePlay==null)
        	gamePlay= new GamePlay();
        return gamePlay;
    }

	public Graph getGraphObj() {
		return graphObj;
	}

	public Mapx getMapxObj() {
		return mapxObj;
	}

	public State getCurrentState() {
		return currentState;
	}

	/**
	 * Set Current State of the game
	 * @param newState Object of the class state
	 * @param newStateStr The new state
	 */
	private void setCurrentState(State newState, String newStateStr) {
		
		this.currentState = newState;
		
		if( newState == State.exchangeCards)
			notifyObservers();
	}
	
	public void handleAutoPlayer() {
		autoExchangeCards();
		autoReinforceArmy();
		autoAttack();
		autoFortify();
	}

	/**
	 * Extract data related to tournament and check validation of data
	 * This method also do the automatic game.
	 * @param tournamentData maintain a list of data with all the switches.
	 * @return true if the whole data is valid otherwise return false.
	 */
	public boolean tournament(ArrayList<String> tournamentData) {
		
		ArrayList<String> mapList = new ArrayList<String>();
		ArrayList<PlayerStrategy> StrategyList = new ArrayList<PlayerStrategy>();
		int gameNumber;
		int gameTurn;
		
		Iterator<String> itr = tournamentData.iterator(); 
		String itrList = itr.next();
		boolean aggressiveFlag = false, benevolentFlag = false, randomFlag = false, cheaterFlag = false;
		
		// Extract all the data related to 4 parts of tournament and check validation of data.
		for(int i=0; i < 4; i++) {
			
			if( itrList.equals("-M")) {
				
				// if there is no data for map list return false.
				if(!itr.hasNext()) {
					System.out.println("wrong Command");
					return false;
				}
				
				// Get all the map list.
				while(true) {
					itrList = itr.next();
					if( itrList.charAt(0) == '-') {
						if( mapList.size() < 1 || mapList.size() > 5) {
							System.out.println("Wrong command. The number of map file is out of range.");
							return false;
						}
						break;
					}
					mapList.add(itrList);
				}
			}
			else if( itrList.equals("-P")) {
				
				// if there is no data for Strategy return false.
				if(!itr.hasNext()) {
					System.out.println("wrong Command");
					return false;
				}
				
				// Get all the Strategy list.
				while(true) {
					itrList = itr.next();
					if( itrList.charAt(0) == '-') {
						if( StrategyList.size() < 2 || StrategyList.size() > 4) {
							System.out.println("Wrong command. The number of Strategy is out of range.");
							return false;
						}
						break;
					}
					
					if( itrList.equalsIgnoreCase("Aggressive")) {
						if( aggressiveFlag ) {
							System.out.println("Wrong command. Aggresive Strategy cannot be more than one.");
							return false;
						}
						aggressiveFlag = true;
						StrategyList.add(PlayerStrategy.aggressive);
					}
					else if( itrList.equalsIgnoreCase("Benevolent")) {
						if( benevolentFlag ) {
							System.out.println("Wrong command. Benevolent Strategy cannot be more than one.");
							return false;
						}
						benevolentFlag = true;
						StrategyList.add(PlayerStrategy.benevolent);
					}
					else if( itrList.equalsIgnoreCase("Random")) {
						if( randomFlag ) {
							System.out.println("Wrong command. Random Strategy cannot be more than one.");
							return false;
						}
						randomFlag = true;
						StrategyList.add(PlayerStrategy.random);
					}
					else if( itrList.equalsIgnoreCase("Cheater")) {
						if( cheaterFlag ) {
							System.out.println("Wrong command. Cheater Strategy cannot be more than one.");
							return false;
						}
						cheaterFlag = true;
						StrategyList.add(PlayerStrategy.cheater);
					}
					else {
						System.out.println("Wrong command. Player Strategies can only be Aggressive, Benevolent, Random or Cheater.");
						return false;
					}

				}
			}
			else if( itrList.equals("-G")) {
				
				// if there is no data for number of games return false and this switch is the last one.
				if(!itr.hasNext()) {
					System.out.println("wrong Command");
					return false;
				}		
				
				itrList = itr.next();
				
				// if there is no data for number of games return false and this switch is not the last one.
				if( itrList.charAt(0) == '-') {
					System.out.println("Wrong command. You should enter a digit for number of games.");
					return false;
				}
				else {	
					
					// Check if the number of games is numeric.
					for(int j=0; j<itrList.length(); j++) {
						if(!Character.isDigit(itrList.charAt(j))){
							System.out.println("Wrong command. The input is incorrect for the number of games.");
							return false;
						}
					}			
					gameNumber = Integer.parseInt(itrList);
					
					// Check if the number of games is out of range.
					if( gameNumber < 1 || gameNumber > 5 ) {
						System.out.println("Wrong command. The number of games can only be between 1 and 5");
						return false;
					}
				}
				
				// Check if the data related to number of games is more than one.
				if( itr.hasNext() ) {
					itrList = itr.next();
					if( itrList.charAt(0) != '-') {
						System.out.println("Wrong command. The input related to number of games is incorrect.");
						return false;
					}
				}

			}
			else if( itrList.equals("-D")) {
				
				// if there is no data for number of turns for each game return false and this switch is the last one.
				if(!itr.hasNext()) {
					System.out.println("wrong Command");
					return false;
				}		
				
				itrList = itr.next();
				
				// if there is no data for number of turns for each game return false and this switch is not the last one.
				if( itrList.charAt(0) == '-') {
					System.out.println("Wrong command. You should enter number of turns for each game.");
					return false;
				}
				else {	
					
					// Check if the number of turns is numeric.
					for(int j=0; j<itrList.length(); j++) {
						if(!Character.isDigit(itrList.charAt(j))){
							System.out.println("Wrong command. The input is incorrect for the number turns.");
							return false;
						}
					}			
					gameTurn = Integer.parseInt(itrList);
					
					// Check if the number of games is out of range.
					if( gameTurn < 10 || gameTurn > 50 ) {
						System.out.println("Wrong command. The number of turns can only be between 10 and 50");
						return false;
					}
				}
				
				// Check if the data related to number of turns is more than one.
				if( itr.hasNext() ) {
					itrList = itr.next();
					if( itrList.charAt(0) != '-') {
						System.out.println("Wrong command. The input related to number of turns is incorrect.");
						return false;
					}
				}

			}
		}

	//	setCurrentOperation("Adding continent "+ continentName + " with control value "+controlValue);
		return true;
	}
	
	/**
	 * Add Continent Function
	 * @param continentName The name of the continent to be added
	 * @param controlValue The integer control value of the continent
	 * @return true if addContinent function return true in Continent class
	 */
	public boolean addContinent(String continentName, Integer controlValue) {

		if (!Continent.addContinent(continentName, controlValue))
			return false;
		setCurrentOperation("Adding continent "+ continentName + " with control value "+controlValue);
		return true;
	}

	/**
	 * Remove Continent Function
	 * @param continentName The name of the continent to be removed
	 * @return true if removeContinent function return true in Continent class
	 */
	public boolean removeContinent(String continentName) {

		if (!Continent.removeContinent(continentName, graphObj))
			return false;
		setCurrentOperation("Removing continent "+ continentName );
		return true;
	}

	/**
	 * Add Country Function
	 * @param countryName The name of the country to be added
	 * @param continentName The name of the continent to which the country is to be added
	 * @return true(if addCountry function return true in Country class)
	 */
	public boolean addCountry(String countryName, String continentName) {

		if (!Country.addCountry(countryName, continentName, graphObj))
			return false;
		setCurrentOperation("Adding country: "+ countryName + " to Continent: "+continentName);
		return true;
	}

	/**
	 * Remove Country Function 
	 * @param countryName The name if the country to be removed
	 * @return(true if removeCountry function returns true in Country class)
	 */
	public boolean removeCountry(String countryName) {

		if (!Country.removeCountry(countryName, graphObj))
			return false;
		setCurrentOperation("Removing country: "+ countryName);
		return true;
	}

	/**
	 * Add Neighbor Function
	 * @param countryName The name of the country to which a neighbor is to be added
	 * @param neighborCountryName The name of the neighbor country
	 * @return true if addNeighbour function return true in Country class
	 */
	public boolean addNeighbor(String countryName, String neighborCountryName) {

		if (!Country.addNeighbour(countryName, neighborCountryName, graphObj))
			return false;
		setCurrentOperation("Adding country: "+ neighborCountryName +" as a neighbour to "+countryName);
		return true;
	}

	/**
	 * Remove Neighbor Function
	 * @param countryName The name of the country who's neighbor has to be removed
	 * @param neighborCountryName The name of the neighboring country that is to be removed
	 * @return true(if removeNeighbor function return true in Country class)
	 */
	public boolean removeNeighbor(String countryName, String neighborCountryName) {

		if (!Country.removeNeighbour(countryName, neighborCountryName, graphObj))
			return false;
		setCurrentOperation("Removing country: "+ neighborCountryName +" as a neighbour from "+countryName);
		return true;
	}

	/**
	 * Show map Function
	 * @return true(in any case)
	 */
	public boolean showMap() {

		Graph.showMap();
		setCurrentOperation("Showing Map");
		return true;
	}

	/**
	 * Save Map Function
	 * @param fileName The name of the file that is to be saved
	 * @return true if file successfully saved and IO Exception does not occur.
	 */
	public boolean saveMap(String fileName) {

		try {
			if (!mapxObj.saveMap(graphObj, fileName))
				return false;
			setCurrentState(State.editPlayer, "Edit Player");
		} catch (IOException io) {
			System.out.println("IO Exception Occured");
			return false;
		}
		setCurrentOperation("Saving Map to file: \""+fileName+"\"");
		return true;
	}

	/**
	 * Edit Map Function
	 * @param mapName The name of the map that is to be edited
	 * @return true if IO exception does not occur.
	 */
	public boolean editMap(String mapName) {
		try {
			File file = new File("src/main/resources/" + mapName);
			if (file.exists()) {
				mapxObj.loadMap("src/main/resources/" + mapName, graphObj);
				setCurrentOperation("Map: "+mapName+" found. Loaded for editing.");
			} else {
				graphObj = Graph.getInstance();
				setCurrentOperation("New Game Graph created");

			}

			// change current state
			setCurrentState(State.mapEditor, "Map Editor");
		} catch (IOException e) {
			System.out.println("IOException occured");
		}

		return true;
	}

	/**
	 * Validate Map Function 
	 * @return true if the map is validated
	 */
	public boolean validateMap() {

		if (!Mapx.validateMap(graphObj))
			return false;

		return true;
	}

	/**
	 * Load Game Map Function
	 * @param fileName The name of the map file that is to be loaded
	 * @return true if file exist
	 */
	public boolean loadGameMap(String fileName) {
		try {
			mapxObj.loadMap("src/main/resources/" + fileName, graphObj);
			setCurrentState(State.editPlayer, "Edit Player");
		} catch (Exception e) {
			System.out.println("File not found");
			return false;
		}
        setCurrentOperation("Loading Game Map "+fileName);
		return true;
	}

	/**
	 * Add Player Function
	 * 
	 * @param playerName The name of the player that is to be added
	 * @return true(If the player is successfully added) or false (If the player already exists)
	 */
	public 	boolean addPlayer(String playerName, String Strategy) {

		if (!Player.addPlayer(playerName, 0))
			return false;
		setCurrentOperation("Adding Player "+playerName+" to the game.");
		return true;
	}

	/**
	 * Remove Player Function
	 * 
	 * @param playerName The name of the player that is to be removed
	 * @return true(If the player is removed from the game successfully) or false(If the player name is absent and the method can not perform the desired operation)
	 */
	public boolean removePlayer(String playerName) {

		if (!Player.removePlayer(playerName))
			return false;
		setCurrentOperation("Removing Player "+playerName+" from the game.");
		return true;
	}

	/**
	 * Populate Countries Function
	 * 
	 * @return true(If the method executes in the desired way and populates the countries or false(If the number of players are invalid)
	 */
	public boolean populateCountries() {

		// set number of armies for each player
		Integer numArmies;
		switch (Database.playerList.size()) {
		case 2:
			numArmies = 40;
			break;
		case 3:
			numArmies = 35;
			break;
		case 4:
			numArmies = 30;
			break;
		case 5:
			numArmies = 25;
			break;
		case 6:
			numArmies = 20;
			break;
		default:
			System.out.println("Number of players should be between 2 and 6");
			return false;
		}

		for (Player iter : Database.playerList) {
			iter.numberOfArmies = numArmies;
			iter.numberOfFreeArmies = numArmies;
		}

		Integer playerNumberToBeAssigned = 1;
		while (!Country.allCountriesPopulated(graphObj)) {
			double randomDouble = Math.random();
			randomDouble = randomDouble * graphObj.getAdjList().size() + 1;
			int randomInt = (int) randomDouble;
			Integer randomCountryNumber = randomInt;

			Country countryToBePopulated = Country.getCountryByNumber(randomCountryNumber, graphObj);
			if (countryToBePopulated.getOwner() == null) {
				Player assignedPlayer = Player.getPlayerByNumber(playerNumberToBeAssigned);
				countryToBePopulated.setOwner(assignedPlayer.getName());
				assignedPlayer.setMyCountries(countryToBePopulated.getNumber());
				playerNumberToBeAssigned++;
			} else {
				continue;
			}

			if (playerNumberToBeAssigned > Database.getInstance().getPlayerList().size()) {
				playerNumberToBeAssigned = 1;
			}

		}

		// Change current state to next state
		setCurrentState(State.troopArmies, "Troop Armies");

		// Set current player to the first player
		currentPlayerObj.goToFirstPlayer(currentState, graphObj);

		setCurrentOperation("Populating all countries");
		return true;
	}

	/**
	 * Place Army Function
	 * 
	 * @param country The name of the country in which armies are to be placed
	 * @return true(If all the requirements are fulfilled and the countries are populated) or false(If the country is absent or the country does not belong to the player in turn)
	 */
	public boolean placeArmy(String country) {

		Country targetCountry = Country.getCountryByName(country, graphObj);
		if (targetCountry == null) {

			return false;
		}

		if (currentPlayerObj.getCurrentPlayer().getNumberOfArmies() <= 0) {

			System.out.println("All armies are placed");
			
			this.detachObserver(phaseView);
			this.detachObserver(worldDominationView);
			
	        setCurrentState(State.exchangeCards, "exchangeCards");
	        
	        this.attachObserver(phaseView);
	        this.attachObserver(worldDominationView);
	        
			setCurrentOperation("Performing PlaceArmy operations");
			
	        this.attachObserver(cardExchangeView);
			this.detachObserver(phaseView);
			this.detachObserver(worldDominationView);
			
	        setCurrentState(State.exchangeCards, "exchangeCards");
	        
//	        this.detachObserver(cardExchangeView);
	        this.attachObserver(phaseView);
	        this.attachObserver(worldDominationView);
	        
			currentPlayerObj.goToFirstPlayer(currentState, graphObj);
			
			// Call auto playing game for computer players.
			while( currentPlayerObj.currentPlayer.getPlayerStrategy() != PlayerStrategy.human ) {
				handleAutoPlayer();
			}

			return false;
		}

		if (targetCountry.getOwner() != null) {
			if (targetCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName()) == false) {

				System.out.println("The country does not belong to the current player");

				return false;
			}
		}

		if (targetCountry.getOwner() == currentPlayerObj.getCurrentPlayer().getName()
				|| targetCountry.getOwner() == null) {
			targetCountry.setOwner(currentPlayerObj.getCurrentPlayer().getName());
			targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + 1);
			currentPlayerObj.getCurrentPlayer().setNumberOfFreeArmies(currentPlayerObj.getCurrentPlayer().getNumberOfFreeArmies() - 1);
			setCurrentOperation("Performing PlaceArmy operations");
		}
		return true;
	}

	/**
	 * Place All Function
	 * 
	 * @return true(If the method executes and armies are placed successfully
	 */
	public boolean placeAll() {
		try {
			while (!Player.allPlayersRemainingArmiesExhausted()) {
				for (Country thisCountry : graphObj.getAdjList()) {
					Player playerThatOwnsThisCountry = Player.getPlayerByName(thisCountry.getOwner());
					if (playerThatOwnsThisCountry.getNumberOfArmies() > 0) {
						thisCountry.setNumberOfArmies(thisCountry.getNumberOfArmies() + 1);
						playerThatOwnsThisCountry.setNumberOfFreeArmies(playerThatOwnsThisCountry.getNumberOfFreeArmies() - 1);
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("errrroeee: " + e.getMessage());
		}
        
		this.detachObserver(phaseView);
		this.detachObserver(worldDominationView);
		
        setCurrentState(State.exchangeCards, "exchangeCards");
        
        this.attachObserver(phaseView);
        this.attachObserver(worldDominationView);
        
		setCurrentOperation("Placing armies on all countries");
		
        this.attachObserver(cardExchangeView);
		this.detachObserver(phaseView);
		this.detachObserver(worldDominationView);
		
        setCurrentState(State.exchangeCards, "exchangeCards");
        
        this.attachObserver(phaseView);
        this.attachObserver(worldDominationView);
        
		currentPlayerObj.goToFirstPlayer(currentState, graphObj);   
		
		// Call auto playing game for computer players.
		while( currentPlayerObj.currentPlayer.getPlayerStrategy() != PlayerStrategy.human ) {
			handleAutoPlayer();
		}
		return true;
	}
	
	/**
	 * This method is used to perform the exchange cards function automatically for computer players. 
	 */
	public void autoExchangeCards() {

		Player currentPlayer = currentPlayerObj.getCurrentPlayer();
		
		if( currentPlayer.playerCards.size() < 3 )	{		
			System.out.println( currentPlayer.name +" does not have enough cards for exchange.");
			return;			
		}
		
		while(  currentPlayer.playerCards.size() >= 3) {
			
			Integer currentPlayerCardsListSize = currentPlayer.playerCards.size();
			int card1=0, card2= card1 + 1, card3= card1 + 2;
			boolean findMatchCards = false;
			
			for(card1=0; card1 < currentPlayerCardsListSize-2; card1++) {
				for(card2= card1 + 1; card2 < currentPlayerCardsListSize-1; card2++) {
					for(card3= card2 + 1; card3 < currentPlayerCardsListSize; card3++) {
						if (cardPlayObj.checkExchangeCardsValidation(currentPlayer.playerCards.get(card1), currentPlayer.playerCards.get(card2), currentPlayer.playerCards.get(card3))) {
							findMatchCards = true;
							break;
						}
					}
					if(findMatchCards)
						break;
				}
				if(findMatchCards)
					break;
			}
			
			if(findMatchCards) {
				Integer exchageArmies = (currentPlayer.exchangeCardsTimes + 1) * 5;
				currentPlayer.exchangeCardsTimes++;
				currentPlayerObj.setNumReinforceArmies(currentPlayerObj.getNumReinforceArmies() + exchageArmies);
				System.out.println(currentPlayer.name + " exchanged his cards with " + exchageArmies + " armies.");
				
				Card[] cardItem = new Card[3];
				
				cardItem[0] = currentPlayer.playerCards.get(card1);
				cardItem[1] = currentPlayer.playerCards.get(card2);
				cardItem[2] = currentPlayer.playerCards.get(card3);
				
				for(int item=0; item<3; item++) {
					for(int Index=0; Index <currentPlayer.playerCards.size(); Index++) {
						if( cardItem[item].getIdCard() == currentPlayer.playerCards.get(Index).getIdCard()) {
							currentPlayer.playerCards.remove(Index);
						}
					}
				}
				
				for(int item=0; item<3; item++) {
					cardPlayObj.refundCard(cardItem[item]);
				}
			}
			else {
				System.out.println("No match cards found.");
				break;
			}
			
			
		}	
		setCurrentOperation("Exchanging Cards");
	}

	/**
	 * This method is used to perform the exchange cards function
	 * 
	 * @param cardNumber1 The integer number of the first card
	 * @param cardNumber2 The integer number of the second card
	 * @param cardNumber3 The integer number of the third card
	 * @return true(If the method executes and cards are exchanged successfully) or false(Cards entered are duplicate or can not be exchanged or if any other validation fails)
	 */
	public boolean exchangeCards(Integer cardNumber1, Integer cardNumber2, Integer cardNumber3) {

		Player currentPlayer = currentPlayerObj.getCurrentPlayer();
		Integer currentPlayerCardsListSize = currentPlayer.playerCards.size();

		if( currentPlayer.playerCards.size() < 3 )	{	
			
			System.out.println("You do not have enough cards for exchange. You should choose exchangecards -none to skip this state.");
		//	System.out.println("You have " + currentPlayerObj.getNumReinforceArmies() + " armies");
			
		}
		else {
			
			if (((cardNumber1 > currentPlayerCardsListSize) || (cardNumber1 < 1))
					|| ((cardNumber2 > currentPlayerCardsListSize) || (cardNumber2 < 1))
					|| ((cardNumber3 > currentPlayerCardsListSize) || (cardNumber3 < 1))) {

				System.out.println("Input Numbers is wrong");
				return false;
			}
			
			if ( (cardNumber1 == cardNumber2) || (cardNumber1 == cardNumber3) || (cardNumber3 == cardNumber2) ) {
				System.out.println("You entered duplicate cards.");
				return false;
			}
			
			if (!cardPlayObj.checkExchangeCardsValidation(currentPlayer.playerCards.get(cardNumber1 - 1), currentPlayer.playerCards.get(cardNumber2 - 1), currentPlayer.playerCards.get(cardNumber3 - 1))) {
				System.out.println("These cards do not match for exchanging.");
				return false;
			}
			
			Integer exchageArmies = (currentPlayer.exchangeCardsTimes + 1) * 5;
			currentPlayer.exchangeCardsTimes++;
			currentPlayerObj.setNumReinforceArmies(currentPlayerObj.getNumReinforceArmies() + exchageArmies);

			System.out.println("You exchanged your cards with " + exchageArmies + " armies.");

			Card[] cardItem = new Card[3];
			
			cardItem[0] = currentPlayer.playerCards.get(cardNumber1 - 1);
			cardItem[1] = currentPlayer.playerCards.get(cardNumber2 - 1);
			cardItem[2] = currentPlayer.playerCards.get(cardNumber3 - 1);
			
			for(int item=0; item<3; item++) {
				for(int Index=0; Index <currentPlayer.playerCards.size(); Index++) {
					if( cardItem[item].getIdCard() == currentPlayer.playerCards.get(Index).getIdCard()) {
						currentPlayer.playerCards.remove(Index);
					}
				}
			}

			for(int item=0; item<3; item++) {
				cardPlayObj.refundCard(cardItem[item]);
			}

			setCurrentOperation("Exchanging Cards");
		
		}
		
		return true;
	}

	/**
	 * Fortify none Function used to cover the function of the player not wanting to exchange the cards
	 * 
	 * @return true(If the player does not want to exchange cards and is allowed to do so) or false(If the player has more than 5 cards)
	 */
	public boolean ignoreExchangeCards() {
		
		if( currentPlayerObj.getCurrentPlayer().playerCards.size() < 5 ) {
			
			//Change current state to next state
			detachObserver(cardExchangeView);
			setCurrentState(State.reinforcementPhase, "Reinforcement");
			setCurrentOperation("Player chose not to exchange cards");	
			System.out.println("You have " + currentPlayerObj.getNumReinforceArmies() + " armies for reinforcement");
		}
		else {
			System.out.println("You have more than 5 cards. You should exchange your cards.");
			return false;
		}
		return true;
		
	}
	
	/**
	 * This method is used to perform Reinforcement function automatically for computer players. 
	 */
	public void autoReinforceArmy() {
		
		PlayerStrategy strategy = currentPlayerObj.currentPlayer.getPlayerStrategy();
		
		if( strategy == PlayerStrategy.aggressive) {
		//	aggressivePlayer.reinforcement();
		}
		if( strategy == PlayerStrategy.benevolent) {
		//	benevolentPlayer.reinforcement();
		}
		if( strategy == PlayerStrategy.cheater) {
		//	cheaterPlayer.reinforcement();
		}
		if( strategy == PlayerStrategy.random) {
		//	randomPlayer.reinforcement();
		}
	}

	/**
	 * Reinforce Army Function
	 * 
	 * @param countryName The name of the country that is to be reinforced
	 * @param numberOfArmies The number of armies that are to be reinforced
	 * @return true(If the player succeeds in reinforcing their armies) or false(If the countries are not adjacent or the country does not belong to the player or if any other validation fails)
	 */
	public boolean reinforceArmy(String countryName, Integer numberOfArmies) {
	    if(numberOfArmies <= 0) {
            System.out.println("Reinforcement armies should be a positive number");
            return false;
        }

		if (!Player.reinforcement(countryName, numberOfArmies, graphObj, currentPlayerObj))
			return false;

		if (currentPlayerObj.getNumReinforceArmies() > 0) {

			System.out.println("Please reinforce the remain " + currentPlayerObj.getNumReinforceArmies() + "armies");
		} else {
			// Change current state to next state
			setCurrentState(State.attackPhase, "Attacking");
		}
		setCurrentOperation("Country "+countryName+" reinforced with "+numberOfArmies+" armies.");
		return true;
	}
	
	public boolean checkEndGame() {
		if( Database.playerList.size() == 1) {
			setCurrentState(State.gameFinished, "game Finished");
			return true;
		}
		return false;
	}
	
	/**
	 * This method is used to perform attack function automatically for computer players. 
	 */
	public void autoAttack() {
		
		PlayerStrategy strategy = currentPlayerObj.currentPlayer.getPlayerStrategy();
		
		if( strategy == PlayerStrategy.aggressive) {
		//	aggressivePlayer.attack();
		}
		if( strategy == PlayerStrategy.benevolent) {
		//	benevolentPlayer.attack();
		}
		if( strategy == PlayerStrategy.cheater) {
		//	cheaterPlayer.attack();
		}
		if( strategy == PlayerStrategy.random) {
		//	randomPlayer.attack();
		}
	}
	
	/**
	 * when player decided normal attack
	 * @return true if implemented
	 */
	public boolean normalAttack(String originCountry, String destinationCountry, Integer numeOfDice) {
		
		Country defenderCountry = Country.getCountryByName(destinationCountry, graphObj);
		String defenderName = defenderCountry.getOwner();
		
		if (!Player.attackCountry(originCountry, destinationCountry, numeOfDice, graphObj, currentPlayerObj))
			return false;
		
		// if defender lost all of his country, attacker will owned all of his cards.
		if (  Player.defenderRemoved == true ) {
			
			Player defender = Player.getPlayerByName(defenderName);
			for(Card itr: defender.playerCards) {
				Card tempcard = itr;
				tempcard.setOwner(currentPlayerObj.getCurrentPlayer().number);
				currentPlayerObj.getCurrentPlayer().playerCards.add(tempcard);
			}
			Player.removePlayer(defenderName);	
			
			boolean checkEndGameStatus = checkEndGame();
			if(checkEndGameStatus==true) {
				return true;
			}
//			if( Database.playerList.size() == 1) {
//				setCurrentState(State.gameFinished, "game Finished");
//				return true;
//			}
			Player.defenderRemoved = false;
		}

		setCurrentOperation("Performing normal attack form "+originCountry+ " to "+ destinationCountry);
		return true;
	}
	/**
	 * when player decided all out attack
	 * @return true if implemented
	 */
	public boolean alloutAttack(String originCountry, String destinationCountry) {
		
		Country defenderCountry = Country.getCountryByName(destinationCountry, graphObj);
		String defenderName = defenderCountry.getOwner();

		
		if (!Player.attackAllout(originCountry, destinationCountry, graphObj, currentPlayerObj))
			return false;
		
		// if defender lost all of his country, attacker will owned all of his cards.
		if (  Player.defenderRemoved == true ) {
			
			Player defender = Player.getPlayerByName(defenderName);
			for(Card itr: defender.playerCards) {
				Card tempcard = itr;
				tempcard.setOwner(currentPlayerObj.getCurrentPlayer().number);
				currentPlayerObj.getCurrentPlayer().playerCards.add(tempcard);
			}
			Player.removePlayer(defenderName);
			boolean checkEndGameStatus = checkEndGame();
			if(checkEndGameStatus==true) {
				return true;
			}
//			if( Database.playerList.size() == 1) {
//				setCurrentState(State.gameFinished, "game Finished");
//				return true;
//			}
			Player.defenderRemoved = false;
		}

		setCurrentOperation("Performing all-out attack form "+originCountry+ " to "+ destinationCountry);
		return true;
	}
	
	/**
	 * when player decided no attack
	 * @return true if implemented
	 */
	public boolean ignoreAttack() {
		
		// handle picking card at turn of each player
		if(Player.countryConquered) {
			currentPlayerObj.getCurrentPlayer().playerCards.add(cardPlayObj.pickCard(currentPlayerObj.getCurrentPlayer().number));
			Player.countryConquered = false;
		}
		
		// Change current state to next state
		setCurrentState(State.fortificationPhase, "Fortification");

		setCurrentOperation("Player " + CurrentPlayer.getCurrentPlayerObj().getCurrentPlayer().name + " decided not to attack");
		return true;
	}

	/**
	 * This method is used to perform Fortification function automatically for computer players. 
	 */
	public void autoFortify() {
		
		PlayerStrategy strategy = currentPlayerObj.currentPlayer.getPlayerStrategy();
		
		if( strategy == PlayerStrategy.aggressive) {
		//	aggressivePlayer.fortify();
		}
		if( strategy == PlayerStrategy.benevolent) {
		//	benevolentPlayer.fortify();
		}
		if( strategy == PlayerStrategy.cheater) {
		//	cheaterPlayer.fortify();
		}
		if( strategy == PlayerStrategy.random) {
		//	randomPlayer.fortify();
		}
	}
	
	/**
	 * Fortify Army Function
	 * 
	 * @param sourceCountry The name of the origin country
	 * @param destinationCountry The name of the destination country
	 * @param numberOfArmy The total number of armies in the form of an integer
	 * @return true(If Fortify Army is implemented successfully)
	 */
	public boolean fortifyArmy(String sourceCountry, String destinationCountry, Integer numberOfArmy) {

		if (!Player.fortify(sourceCountry, destinationCountry, numberOfArmy, getGraphObj()))
			return false;

		// Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);
		
		// Call auto playing game for computer players.
		while( currentPlayerObj.currentPlayer.getPlayerStrategy() != PlayerStrategy.human ) {
			handleAutoPlayer();
		}
		
		this.detachObserver(phaseView);
		this.detachObserver(worldDominationView);
		
        setCurrentState(State.exchangeCards, "exchangeCards");
        
        this.attachObserver(phaseView);
        this.attachObserver(worldDominationView);
        
		setCurrentOperation("Fortify Army");
		
        this.attachObserver(cardExchangeView);
		this.detachObserver(phaseView);
		this.detachObserver(worldDominationView);
		
        setCurrentState(State.exchangeCards, "exchangeCards");
        
        this.attachObserver(phaseView);
        this.attachObserver(worldDominationView);
        
		return true;
	}

	/**
	 * Fortify none Function
	 * @return true in any case
	 */
	public boolean ignoreFortifyArmy() {
		
		// Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);
		
		// Call auto playing game for computer players.
		while( currentPlayerObj.currentPlayer.getPlayerStrategy() != PlayerStrategy.human ) {
			handleAutoPlayer();
		}
		
		this.detachObserver(phaseView);
		this.detachObserver(worldDominationView);
		
        setCurrentState(State.exchangeCards, "exchangeCards");
        
        this.attachObserver(phaseView);
        this.attachObserver(worldDominationView);
        
		setCurrentOperation("Performing Fortify None");
		
        this.attachObserver(cardExchangeView);
		this.detachObserver(phaseView);
		this.detachObserver(worldDominationView);
		
        setCurrentState(State.exchangeCards, "exchangeCards");
        
        this.attachObserver(phaseView);
        this.attachObserver(worldDominationView);

		return true;
	}

	public double getPercentageOfMapOwnedByPlayer(String playerName){
		if(Player.getPlayerByName(playerName) == null)
			return -1.0;
		 return (currentPlayerObj.getCurrentPlayer().getNumberOfCountriesOwned(playerName, getGraphObj()) * 100.00) / gamePlay.getGraphObj().getAdjList().size();
	}

 	public Integer getTotalNumberOfArmies(String playerName){
		try{
//			return Player.getPlayerByName(playerName).getTotalArmiesOwnedByPlayer(gamePlay.getGraphObj()) + 0;
			return Player.getPlayerByName(playerName).getNumberOfArmies();
		}
		catch(Exception e){
			return -1;
		}
	}

	public String getContinentOwnership(){
	    String output="";
//	    int singleOwnerFound= 0;
	    for(Continent continent: Database.getInstance().getContinentList()){
	        for(Player player: Database.getInstance().getPlayerList()){
                if(Continent.continentBelongToPlayer(player.getName(), continent.getName(), gamePlay.getGraphObj()) ==true){
                    output+=continent.getName() +" : "+player.getName() +"\n";
//                    singleOwnerFound =1;
                    break;
                }
	        }
            output+= continent.getName() + " : No Owner\n";
//	        singleOwnerFound=0;
        }


	    return output;
    }
	
	@Override
	public void notifyObservers() {		
		for(IObserver itr:observerList) {
			itr.update(this, this.getCurrentPlayerObj().currentPlayer);
		}
	}

	@Override
	public void attachObserver(IObserver observerObj) {
		observerList.add(observerObj);
	}

	@Override
	public void detachObserver(IObserver observerObj) {
		observerList.remove(observerObj);
	}
}
