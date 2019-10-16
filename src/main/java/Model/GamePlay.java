package Model;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GamePlay {
	
	private static GamePlay gamePlay = null;
	private State currentState;
	private Mapx mapxObj;
	private Graph graphObj;
	private Database databaseObj;
	private CurrentPlayer currentPlayerObj;
	
	
	private GamePlay() {
		currentState = State.initializeGame;
    	mapxObj = new Mapx();
    	databaseObj = Database.getInstance();
    	currentPlayerObj = CurrentPlayer.getInstance();
    }

    public static GamePlay getInstance(){
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
    
    public State getCurrentState(){
    	return currentState;
    }

    private void setCurrentState(State newState, String newStateStr) {
    	System.out.println("State of game changed to: " + newStateStr);
    	currentState = newState;
    }
    
	public boolean addContinent(String continentName, Integer controlValue) {
		
		if(!Continent.addContinent(continentName, controlValue))
			return false;
		
		return true;
	}
	
	public boolean removeContinent(String continentName) {
		
		if(!Continent.removeContinent(continentName, graphObj))
			return false;
		
		return true;
	}
	
	public boolean addCountry(String countryName, String continentName) {
			
		if(!Country.addCountry(countryName, continentName, graphObj) )
			return false;
		
		return true;
	}
	
	public boolean removeCountry(String countryName) {
		
		if(!Country.removeCountry(countryName, graphObj))
			return false;
		
		return true;
	}
	
	public boolean addNeighbor(String countryName, String neighborCountryName) {
		
		if(!Country.addNeighbour(countryName, neighborCountryName, graphObj))
			return false;
		
		return true;
	}
	
	public boolean removeNeighbor(String countryName, String neighborCountryName) {
		
		if(!Country.removeNeighbour(countryName, neighborCountryName, graphObj) )
			return false;
		
		return true;
	}
	
	public boolean showMap() {
		
		Graph.showMap();		
		return true;
	}
	
	public boolean saveMap(String fileName) {
		
		try {
			if(! mapxObj.saveMap(graphObj, fileName))
				return false;
			
			setCurrentState(State.initializeGame, "Initialize Game");
		}
		catch (IOException io ){
			System.out.println("IO Exception Occured");
			return false;
		}
		
		return true;
	}
	
	public boolean editMap(String mapName) {
		
		File file = new File("src/main/resources/" + mapName);
		if (file.exists()) {
			graphObj=mapxObj.loadMap("src/main/resources/"+mapName);
		}
		else{
			System.out.println("New Game Graph created");
			graphObj=Graph.getInstance();
		}
		
		//change current state
		setCurrentState(State.mapEditor, "Map Editor");
		
		return true;
	}
	
	public boolean validateMap() {
	
		if(! Mapx.validateMap(graphObj))
			return false;
		
		return true;
	}
	
	public boolean loadGameMap(String fileName) {
		
		graphObj = mapxObj.loadMap("src/main/resources/" + fileName);
		
		setCurrentState(State.editPlayer, "Edit Player");
		
		return true;
	}
	
	public boolean addPlayer(String playerName) {
		
		if(!Player.addPlayer(playerName, 0))
			return false;
		
		return true;
	}
	
	public boolean removePlayer(String playerName) {
		
		if(!Player.removePlayer(playerName))
			return false;
		
		return true;
	}
	
	public boolean populateCountries() {
		
		//set number of armies for each player
        Integer numArmies;
        switch(Database.playerList.size()) {
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
        
        for(Player iter: Database.playerList) {
        	iter.numberOfArmies = numArmies;
        }

        Integer playerNumberToBeAssigned=1;
        while (! Country.allCountriesPopulated(graphObj)) {
            double randomDouble = Math.random();
            randomDouble = randomDouble * graphObj.getAdjList().size() + 1;
            int randomInt = (int) randomDouble;
            Integer randomCountryNumber = randomInt;

            Country countryToBePopulated= Country.getCountryByNumber(randomCountryNumber, graphObj);
            //System.out.println("Player "+ playerNumberToBeAssigned +" turn");
            if(countryToBePopulated.getOwner()==null){
                Player assignedPlayer= Player.getPlayerByNumber(playerNumberToBeAssigned);
                countryToBePopulated.setOwner(assignedPlayer.getName());
            }
            else{
                continue;
            }
            playerNumberToBeAssigned++;

            if(playerNumberToBeAssigned > Database.getInstance().getPlayerList().size()){
                playerNumberToBeAssigned =1;
            }
            
        }
      
        //Change current state to next state
		setCurrentState(State.troopArmies, "Troop Armies");
		
		//Set current player to the first player
		currentPlayerObj.goToFirstPlayer(currentState, graphObj);
		
		return true;
	}
	
	public boolean placeArmy(String country) {
		
		Country targetCountry= Country.getCountryByName(country, graphObj);
		if(targetCountry==null){
			
			return false;
		}

		if(currentPlayerObj.getCurrentPlayer().getNumberOfArmies() <=0){
			
			System.out.println("All armies are placed");
			
			//Change state of game
			setCurrentState(State.reinforcementPhase, "Reinforcement");
			
			//Set current player to the first player
			currentPlayerObj.goToFirstPlayer(currentState, graphObj);
			
			return false;
		}

		if(targetCountry.getOwner()!=null){
			if(targetCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName()) == false){
				
				System.out.println("The country is not belong to the current player");
				
				return false;
			}
		}

		if(targetCountry.getOwner()==currentPlayerObj.getCurrentPlayer().getName()     ||      targetCountry.getOwner()==null){
			targetCountry.setOwner(currentPlayerObj.getCurrentPlayer().getName());
			targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() +1);
			currentPlayerObj.getCurrentPlayer().setNumberOfArmies(currentPlayerObj.getCurrentPlayer().getNumberOfArmies() -1);
		}
		return true;
	}
	
	public boolean placeAll() {

        while( ! Player.allPlayersRemainingArmiesExhausted()) {
            for (Country thisCountry : graphObj.getAdjList()) {
                Player playerThatOwnsThisCountry= Player.getPlayerByName(thisCountry.getOwner());
                if (playerThatOwnsThisCountry.getNumberOfArmies() > 0 ){
                    thisCountry.setNumberOfArmies(thisCountry.getNumberOfArmies() + 1);
                    playerThatOwnsThisCountry.setNumberOfArmies(playerThatOwnsThisCountry.getNumberOfArmies() -1);
                }
            }
        }
        
        // change state of game
        setCurrentState(State.reinforcementPhase, "Reinforcement");
        
        //Set current player to the first player
		currentPlayerObj.goToFirstPlayer(currentState, graphObj);
        
		return true;
	}
	
	public boolean reinforceArmy(String countryName, Integer numberOfArmies) {
		
		//check: if target country is not exist, return false
		Country targetCountry= Country.getCountryByName(countryName, graphObj);
		if(targetCountry==null){
			return false;
		}
		
		//check: if country does not belong to the currentPlayer, return false
		if(targetCountry.getOwner()!=null){
			if(targetCountry.getOwner().equalsIgnoreCase(currentPlayerObj.getCurrentPlayer().getName()) == false){
				
				System.out.println("The country is not belong to the current player");
				return false;
			}
		}
		//check: if numberOfArmy is more than allocated army, return false
		if(numberOfArmies > currentPlayerObj.getNumReinforceArmies()) {
			System.out.println("The current player can reinforce just " + currentPlayerObj.getNumReinforceArmies() + "armies");
			return false;
		}
		
		//Reinforce armies in the target country
		targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() + numberOfArmies);
		
		//increase the number of armies belong to the player
		currentPlayerObj.increaseCurrentPlayerArmies(numberOfArmies);
		currentPlayerObj.decreaseReinforceentArmies(numberOfArmies);
		
		if(currentPlayerObj.getNumReinforceArmies() > 0) {

			System.out.println("Please reinforce the remain " + currentPlayerObj.getNumReinforceArmies() + "armies");
		}
		else {
			//Change current state to next state
			setCurrentState(State.fortificationPhase, "Fortification");
		}
		
		return true;
	}
	
	public boolean fortifyArmy(String sourceCountry, String destinationCOuntry, Integer numberOfArmy) {
		
		//check: if sourceCountry does not belong to the currentPlayer, return false
		//check: if destinationCountry does not belong to the currentPlayer, return false
		//check: if the numberOfArmy is more than army that placed in sourceCountry, return flase
		//find if there is a way between source and destination (use BFS algorithm for finding ways
		//check: if there is not a way between source and destination, return false
		//reduce the numberOfArmy form source country and add them to the destination country
		//change the currentState
		
		if(!Country.fortify(sourceCountry, destinationCOuntry, numberOfArmy, graphObj))
			return false;
		
		//Change current state to next state
		setCurrentState(State.reinforcementPhase, "Reinforcement");
				
		//Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);
		
		return true;
	}
	
	public boolean ignoreFortifyArmy() {
		
		//Change current state to next state
		setCurrentState(State.reinforcementPhase, "Reinforcement");
		
		//Change current player
		currentPlayerObj.goToNextPlayer(currentState, graphObj);
		
		return true;
	}

}


class CurrentPlayer{
	
	private static CurrentPlayer currentPlayerObj = null;
	private ListIterator<Player> currentPlayerItr;
	private Player currentPlayer;
	private Integer numReinforceArmies;
	private GamePlay gamePlayObj;
	
	private CurrentPlayer() {
	//	gamePlayObj = GamePlay.getInstance();
		currentPlayerItr = Database.playerList.listIterator();
	}
	
	public static CurrentPlayer getInstance(){
        if(currentPlayerObj==null)
        	currentPlayerObj= new CurrentPlayer();
        return currentPlayerObj;
    }
	
	public Integer getNumReinforceArmies() {
		return this.numReinforceArmies;
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public void goToNextPlayer(State currentState, Graph gameGraph) {
    	
    	if(currentPlayerItr.hasNext())
    		currentPlayer = currentPlayerItr.next();
		else {
			goToFirstPlayer(currentState, gameGraph);
			return;
		}
    	
    	System.out.println("Current player is " + currentPlayer.getName());
    	
    	//At reinforcement state of each player calculate its reinforcement armies
    	if( currentState == State.reinforcementPhase) {
    		Continent.updateContinitsOwner(gameGraph);
    		calculateReinforceentArmies();
    		System.out.println("You have " + getNumReinforceArmies() + "armies" );
    	}
    }
	
	public void goToFirstPlayer(State currentState, Graph gameGraph) {
		currentPlayerItr = Database.playerList.listIterator();
		currentPlayer = currentPlayerItr.next();
		
		System.out.println("Current player is " + currentPlayer.getName());
		
		//At reinforcement state of each player calculate its reinforcement armies
    	if( currentState == State.reinforcementPhase) {
    		Continent.updateContinitsOwner(gameGraph);
    		calculateReinforceentArmies();
    		System.out.println("You have " + getNumReinforceArmies() + "armies" );
    	}
	}
	
	private void calculateReinforceentArmies() {
    	Integer numArmies = 0;
    	
    	Integer numOfCountries = currentPlayer.myCountries.size();
    	numArmies += (numOfCountries/3);
    	
    	for(Continent continentItr : Database.continentList) {
    		if(continentItr.getOwner().equals(currentPlayer.name))
    			numArmies += continentItr.getControlValue();
    	}
    	
    	//Each player has at least 3 armies for reinforcement
    	numReinforceArmies = (numArmies>3) ? numArmies : 3;
    }
	
	public void decreaseReinforceentArmies(Integer numOfArmies) {
		numReinforceArmies -= numOfArmies;
	}
	
	public void increaseCurrentPlayerArmies(Integer numArmies) {
		currentPlayer.setNumberOfArmies(currentPlayer.getNumberOfArmies() + numArmies);
		currentPlayerItr.set(currentPlayer);
	}
}