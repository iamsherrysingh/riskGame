package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.IOException;
import java.util.*; 
import Model.State;
import Model.Player;

public class GamePlay {
	
	private static GamePlay gamePlay = null;
	private State currentState;
	private ListIterator<Player> currentPlayerItr;
	private Mapx mapxObj;
	private Graph graphObj;
	private Database databaseObj;
	
	
	private GamePlay() {
		currentState = State.mapEditor;
    	mapxObj = new Mapx();
    	graphObj = Graph.getInstance();
    	databaseObj = Database.getInstance();
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
			
			setCurrentState(State.startupPhase, "Startup");
		}
		catch (IOException io ){
			System.out.println("IO Exception Occured");
			return false;
		}
		
		return true;
	}
	
	public boolean editMap(String mapName,Graph gameGraph) {
		
		//Get object of Graph
		//call editMap() from Mapx
		File file = new File("src/main/resources/" + mapName);
		if (file.exists()) {
			gameGraph=mapxObj.createGameGraph("src/main/resources/"+mapName);
		}
		else{
			System.out.println("New Game Graph created");
			gameGraph=Graph.getInstance();
		}
		return true;
	}
	
	public boolean validateMap() {
	
		if(! Mapx.validateMap(graphObj))
			return false;
		
		return true;
	}
	
	public boolean loadMap(String fileName) {
		
		mapxObj.createGameGraph(fileName);
		
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
        
        //create iterator of players
        currentPlayerItr = Database.playerList.listIterator();
      
        //Change current state to next state
		setCurrentState(State.troopArmies, "Troop Armies");
		
		return true;
	}
	
	public boolean placeArmy(String country) {
		
		//Get current player
		Player currentPlayer;
		if(currentPlayerItr.hasNext())
			currentPlayer = currentPlayerItr.next();
		else {
			currentPlayerItr = Database.playerList.listIterator();
			currentPlayer = currentPlayerItr.next();
		}
		
		Country targetCountry= Country.getCountryByName(country, graphObj);
		if(targetCountry==null){
			
			if(currentPlayerItr.hasPrevious())
				currentPlayerItr.previous();
			
			return false;
		}

		if(currentPlayer.getNumberOfArmies() <=0){
			
			System.out.println("All armies are placed");
			
			//Change state of game
			setCurrentState(State.reinforcementPhase, "Reinforcement");
			
			//Set current player to the first player
			currentPlayerItr = Database.playerList.listIterator();
			
			return false;
		}

		if(targetCountry.getOwner()!=null){
			if(targetCountry.getOwner().equalsIgnoreCase(currentPlayer.getName()) == false){
				
				System.out.println("The country is not belong to the current player");
				if(currentPlayerItr.hasPrevious())
					currentPlayerItr.previous();
				
				return false;
			}
		}

		if(targetCountry.getOwner()==currentPlayer.getName()     ||      targetCountry.getOwner()==null){
			targetCountry.setOwner(currentPlayer.getName());
			targetCountry.setNumberOfArmies(targetCountry.getNumberOfArmies() +1);
			currentPlayer.setNumberOfArmies(currentPlayer.getNumberOfArmies() -1);
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
		currentPlayerItr = Database.playerList.listIterator();
        
		return true;
	}
	
	public boolean reinforceArmy(String countryName, Integer numberOfArmy) {
		
		//check: if country does not belong to the currentPlayer, return false
		//check: if numberOfArmy is more than allocated army, return false
		//if the numberOfArmy is less than allocated army, stay in this state and return true
		//if the number of army is equal to the allocated army, change the state and return true
		//increase the number of armies belong to the player
		
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
		
		return true;
	}
	
	public boolean ignoreFortifyArmy() {
		
		//change the currentState
		
		return true;
	}

}