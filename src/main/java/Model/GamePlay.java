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
	 * @param newState
	 * @param newStateStr
	 */
	private void setCurrentState(State newState, String newStateStr) {
		//System.out.println("<== State of game changed to: " + newStateStr + " ==>");
		this.currentState = newState;
		notifyObservers();
	}

	/**
	 * Add Continent Function
	 * @param continentName
	 * @param controlValue
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
	 * @param continentName
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
	 * @param countryName
	 * @param continentName
	 * @return true if addCountry function return true in Country class
	 */
	public boolean addCountry(String countryName, String continentName) {

		if (!Country.addCountry(countryName, continentName, graphObj))
			return false;
		setCurrentOperation("Adding country: "+ countryName + " to Continent: "+continentName);
		return true;
	}

	/**
	 * Remove Country Function 
	 * @param countryName
	 * @return true if removeCountry function return true in Country class
	 */
	public boolean removeCountry(String countryName) {

		if (!Country.removeCountry(countryName, graphObj))
			return false;
		setCurrentOperation("Removing country: "+ countryName);
		return true;
	}

	/**
	 * Add Neighbor Function
	 * @param countryName
	 * @param neighborCountryName
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
	 * @param countryName
	 * @param neighborCountryName
	 * @return true if removeNeighbor function return true in Country class
	 */
	public boolean removeNeighbor(String countryName, String neighborCountryName) {

		if (!Country.removeNeighbour(countryName, neighborCountryName, graphObj))
			return false;
		setCurrentOperation("Removing country: "+ neighborCountryName +" as a neighbour from "+countryName);
		return true;
	}

	/**
	 * Show map Function
	 * @return true in any case
	 */
	public boolean showMap() {

		Graph.showMap();
		setCurrentOperation("Showing Map");
		return true;
	}

	/**
	 * Save Map Function
	 * @param fileName
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
	 * @param mapName
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
	 * @return true if the map is
	 */
	public boolean validateMap() {

		if (!Mapx.validateMap(graphObj))
			return false;

		return true;
	}

	/**
	 * Load Game Map Function
	 * @param fileName
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
        //setCurrentOperation("Loading Game Map "+fileName);
		return true;
	}

	/**
	 * Add Player Function
	 * 
	 * @param playerName
	 * @return
	 */
	public 	boolean addPlayer(String playerName) {

		if (!Player.addPlayer(playerName, 0))
			return false;
		setCurrentOperation("Adding Player "+playerName+" to the game.");
		return true;
	}

	/**
	 * Remove Player Function
	 * 
	 * @param playerName
	 * @return
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
	 * @return
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
	 * @param country
	 * @return
	 */
	public boolean placeArmy(String country) {

		Country targetCountry = Country.getCountryByName(country, graphObj);
		if (targetCountry == null) {

			return false;
		}

		if (currentPlayerObj.getCurrentPlayer().getNumberOfArmies() <= 0) {

			System.out.println("All armies are placed");
			attachObserver(cardExchangeView);
			// Change state of game
			setCurrentState(State.exchangeCards, "exchangeCards");
			currentPlayerObj.goToFirstPlayer(currentState, graphObj);
            setCurrentOperation("Performing PlaceArmy operation");
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
			currentPlayerObj.getCurrentPlayer().setNumberOfArmies(currentPlayerObj.getCurrentPlayer().getNumberOfArmies() - 1);
		}
		return true;
	}

	/**
	 * Place All Function
	 * 
	 * @return
	 */
	public boolean placeAll() {
		try {
			while (!Player.allPlayersRemainingArmiesExhausted()) {
				for (Country thisCountry : graphObj.getAdjList()) {
					Player playerThatOwnsThisCountry = Player.getPlayerByName(thisCountry.getOwner());
					if (playerThatOwnsThisCountry.getNumberOfArmies() > 0) {
						thisCountry.setNumberOfArmies(thisCountry.getNumberOfArmies() + 1);
						playerThatOwnsThisCountry.setNumberOfArmies(playerThatOwnsThisCountry.getNumberOfArmies() - 1);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("errrroeee: " + e.getMessage());
		}
		
    	attachObserver(cardExchangeView);
        // change state of game
        setCurrentState(State.exchangeCards, "exchangeCards");

        
        //Set current player to the first player
		currentPlayerObj.goToFirstPlayer(currentState, graphObj);

		setCurrentOperation("Placing armies on all countries");
		return true;
	}

	/**
	 * Exchange Cards Function
	 * 
	 * @param cardNumber1
	 * @param cardNumber2
	 * @param cardNumber3
	 * @return
	 */
	public boolean exchangeCards(Integer cardNumber1, Integer cardNumber2, Integer cardNumber3) {

		Player currentPlayer = currentPlayerObj.getCurrentPlayer();
		Integer currentPlayerCardsListSize = currentPlayer.playerCards.size();

		if (((cardNumber1 > currentPlayerCardsListSize) && (cardNumber1 < 1))
				|| ((cardNumber2 > currentPlayerCardsListSize) && (cardNumber2 < 1))
				|| ((cardNumber3 > currentPlayerCardsListSize) && (cardNumber3 < 1))) {

			System.out.println("Input Numbers is wrong");
			return false;
		}

		if (!cardPlayObj.checkExchangeCardsValidation(currentPlayer.playerCards.get(cardNumber1 - 1), currentPlayer.playerCards.get(cardNumber2 - 1), currentPlayer.playerCards.get(cardNumber3 - 1)))
			return false;

		Integer exchageArmies = (currentPlayer.exchangeCardsTimes + 1) * 5;
		currentPlayer.exchangeCardsTimes++;
		currentPlayerObj.setNumReinforceArmies(currentPlayerObj.getNumReinforceArmies() + exchageArmies);

		System.out.println("You exchanged your cards with " + exchageArmies + " armies.");

		Card card1 = currentPlayer.playerCards.get(cardNumber1 - 1);
		Card card2 = currentPlayer.playerCards.get(cardNumber2 - 1);
		Card card3 = currentPlayer.playerCards.get(cardNumber3 - 1);

		currentPlayer.playerCards.remove(cardNumber1 - 1);
		currentPlayer.playerCards.remove(cardNumber2 - 1);
		currentPlayer.playerCards.remove(cardNumber3 - 1);

		cardPlayObj.refundCard(card1);
		cardPlayObj.refundCard(card2);
		cardPlayObj.refundCard(card3);
		
		if( currentPlayer.playerCards.size() < 3 )	{	
			//Change current state to next state
			setCurrentState(State.reinforcementPhase, "Reinforcement");
			System.out.println("You have " + currentPlayerObj.getNumReinforceArmies() + " armies");
			detachObserver(cardExchangeView);
			currentPlayerObj.goToFirstPlayer(currentState, graphObj);
		}

		setCurrentOperation("Exchanging Cards");
		return true;
	}

	/**
	 * Fortify none Function
	 * 
	 * @return
	 */
	public boolean ignoreExchangeCards() {
		
		if( currentPlayerObj.getCurrentPlayer().playerCards.size() < 5 ) {
			//Change current state to next state

			setCurrentState(State.reinforcementPhase, "Reinforcement");
			System.out.println("You have " + currentPlayerObj.getNumReinforceArmies() + " armies");
			detachObserver(cardExchangeView);
		}
		else {
			System.out.println("You have more than 5 cards. You should exchange your cards.");
			return false;
		}
		setCurrentOperation("Player chose not to exchange cards");
		return true;
		
	}

	/**
	 * Reinforce Army Function
	 * 
	 * @param countryName
	 * @param numberOfArmies
	 * @return
	 */
	public boolean reinforceArmy(String countryName, Integer numberOfArmies) {

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
		if (  currentPlayerObj.getCurrentPlayer().defenderRemoved == true ) {
			
			Player defender = Player.getPlayerByName(defenderName);
			for(Card itr: defender.playerCards) {
				Card tempcard = itr;
				tempcard.setOwner(currentPlayerObj.getCurrentPlayer().number);
				currentPlayerObj.getCurrentPlayer().playerCards.add(tempcard);
			}
			Player.removePlayer(defenderName);		
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
		if (  currentPlayerObj.getCurrentPlayer().defenderRemoved == true ) {
			
			Player defender = Player.getPlayerByName(defenderName);
			for(Card itr: defender.playerCards) {
				Card tempcard = itr;
				tempcard.setOwner(currentPlayerObj.getCurrentPlayer().number);
				currentPlayerObj.getCurrentPlayer().playerCards.add(tempcard);
			}
			Player.removePlayer(defenderName);		
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
		if(currentPlayerObj.getCurrentPlayer().countryConquered) {
			currentPlayerObj.getCurrentPlayer().playerCards.add(cardPlayObj.pickCard(currentPlayerObj.getCurrentPlayer().number));
			currentPlayerObj.getCurrentPlayer().countryConquered = false;
		}
		
		// Change current state to next state
		setCurrentState(State.fortificationPhase, "Fortification");

		setCurrentOperation("Player " + CurrentPlayer.getCurrentPlayerObj().getCurrentPlayer().name + " decided not to attack");
		return true;
	}

	/**
	 * Fortify Army Function
	 * 
	 * @param sourceCountry
	 * @param destinationCountry
	 * @param numberOfArmy
	 * @return
	 */
	public boolean fortifyArmy(String sourceCountry, String destinationCountry, Integer numberOfArmy) {

		if (!Player.fortify(sourceCountry, destinationCountry, numberOfArmy, getGraphObj()))
			return false;

		// Change current state to next state
		setCurrentState(State.reinforcementPhase, "Reinforcement");

		// Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);
        setCurrentOperation("Fortify Amry");
		return true;
	}

	/**
	 * Fortify none Function
	 * @return true in any case
	 */
	public boolean ignoreFortifyArmy() {

		// Change current state to next state
		setCurrentState(State.reinforcementPhase, "Reinforcement");

		// Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);

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
            output+= continent.getName() + " : Multiple Owners\n";
//	        singleOwnerFound=0;
        }


	    return output;
    }
	
	@Override
	public void notifyObservers() {		
		for(IObserver itr:observerList) {
			itr.update(this);
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
