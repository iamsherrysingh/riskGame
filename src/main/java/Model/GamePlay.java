package Model;

import java.util.ArrayList;
import java.io.IOException;
import java.util.*; 
import Model.State;
import Model.Player;

public class GamePlay {
	
	private static GamePlay gamePlay = null;
	private State currentState;
	private Player currentPlayer;
	private Mapx mapxObj;
	private Graph graphObj;
	private Database databaseObj;
	
	
	private GamePlay() {
		currentState = State.mapEditor;
    	mapxObj = new Mapx();
    	graphObj = mapxObj.createGameGraph("src/main/resources/map.map");
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
			currentState = State.startupPhase;	
		}
		catch (IOException io ){
			System.out.println("IO Exception Occured");
			return false;
		}
		
		return true;
	}
	
	public boolean editMap(String fileName) {
		
		//Get object of Graph
		//call editMap() from Mapx
		
		return true;
	}
	
	public boolean validateMap() {
	
		if(! mapxObj.validateMap(graphObj))
			return false;
		
		return true;
	}
	
	public boolean loadMap(String fileName) {
		
		//call loadMap() from Mapx
		currentState = State.editPlayer;
		return true;
	}
	
	public boolean addPlayer(String playerName) {
		
		//check: if there is a player with this name, return false
	/*	for(Player itr:Database.playerList) {
			if(itr.getName().equals(playerName)) {
				return false;
			}
		}
		
		Integer newPlayerId;
		if(Database.playerList.isEmpty())
			newPlayerId = 1;
		else {
			newPlayerId = Database.playerList.get(Database.playerList.size() - 1).id + 1;
		}
			
		//add the object to the list of players
		Database.addPlayer(newPlayerId, playerName, 0);

		return true; */
	}
	
	public boolean removePlayer(String playerName) {
		
		//check: if there is not a player with this name, return false
		boolean checkExistence = false;
		for(Player itr:Database.playerList) {
			if( itr.getName().equals(playerName) ) {
				 checkExistence = true;
				 Database.removePlayer(playerName);
				 break;
			}
		}
		if(checkExistence == false) {
			return false;
		}
		
		return true;
	}
	
	public boolean populateCountries(Graph gameGraph) {
		

        Integer playerNumberToBeAssigned=1;
        while (! Country.allCountriesPopulated(gameGraph)) {
            double randomDouble = Math.random();
            randomDouble = randomDouble * gameGraph.getAdjList().size() + 1;
            int randomInt = (int) randomDouble;
            Integer randomCountryNumber = randomInt;

            Country countryToBePopulated= Country.getCountryByNumber(randomCountryNumber, gameGraph);
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
		currentState = State.troopArmies;
		return true;
	}
	
	public boolean placeArmy(Player currentPlayer, String country, Graph gameGraph) {
		
		//check: if there is not a a country with this name, return false
		//check: if the country does not belong to the current player or the country is not free, return false
		//if the country was free, change the owner of country to the player and update MyCountry list of player
		//add an army to the related country
		Country targetCountry= Country.getCountryByName(country, gameGraph);
		if(targetCountry==null){
			return false;
		}

		if(currentPlayer.getNumberOfArmies() <=0){
			return false;
		}

		if(targetCountry.getOwner()!=null){
			if(targetCountry.getOwner().equalsIgnoreCase(currentPlayer.getName()) == false){
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
	
	public boolean placeAll(Graph gameGraph) {
	
		//implement a loop for placing all army for players
		//update owning parameter of country and MyCountry parameter of player
		//change currentState

        while( ! Player.allPlayersRemainingArmiesExhausted()) {
            for (Country thisCountry : gameGraph.getAdjList()) {
                Player playerThatOwnsThisCountry= Player.getPlayerByName(thisCountry.getOwner());
                if (playerThatOwnsThisCountry.getNumberOfArmies() > 0 ){
                    thisCountry.setNumberOfArmies(thisCountry.getNumberOfArmies() + 1);
                    playerThatOwnsThisCountry.setNumberOfArmies(playerThatOwnsThisCountry.getNumberOfArmies() -1);
                }
            }
        }
        currentState = State.reinforcementPhase;
		
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