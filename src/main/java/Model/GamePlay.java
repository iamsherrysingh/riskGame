package Model;

import java.util.ArrayList;
import java.util.*; 
import Model.State;
import Model.Player;

public class GamePlay {
	
	private static GamePlay gamePlay = null;
	private State currentState;
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
    
    public State getCurrentState(){
    	return currentState;
    }
	
	public boolean addContinent(String continentName, Integer controlValue) {
		
		//check: if there is a continent with this name, return false (call checkExistenceOfContinent(String continentName))
		//call addContinent() from Mapx
		mapxObj.addContinent(continentName, controlValue);
		return true;
	}
	
	public boolean removeContinent(String continentName) {
		
		//Get object of Graph
		//check: if there is not a continent with this name, return false (call checkExistenceOfContinent(String continentName))
		//get the list of countries belongs to this continent(call getCountriesOfContinent(String continentName))
		//remove all countries belong to this continent (call removeCountry())
		//remove neighbors according to removed countri
		//es (call removeNeighbors())
		//Call removeContinent() from Mapx
		
		return true;
	}
	
	public boolean addCountry(String countryName, String continentName) {
			
		//Get object of Graph
		//check: if there is a country with this name, return false (call checkExistenceOfCountry(String countryName))
		//check: if there is not a continent with this name, return false (call checkExistenceOfContinent(String continentName))
		//Call addCountry() form Mapx
		
		return true;
	}
	
	public boolean removeCountry(String countryName) {
		
		//Get object of Graph
		//check: if there is not a country with this name, return false (call checkExistenceOfCountry(String countryName))
		//remove neighbors (call removeNeighbors())
		//Call removeCountry from Mapx
		
		return true;
	}
	
	public boolean addNeighbor(String countryName, String neighborCountryName) {
		
		//Get object of Graph
		//check: if there is not a country with this name, return false (call checkExistenceOfCountry(String countryName))
		//check: if there is not a neighbor with this name, return false (call checkExistenceOfCountry(String countryName))
		//Call addNeighbor() from Mapx
		
		return true;
	}
	
	public boolean removeNeighbor(String countryName, String neighborCountryName) {
		
		//Get object of Graph
		//check: if there is not a country with this name, return false (call checkExistenceOfCountry(String countryName))
		//check: if there is not a neighbor with this name, return false (call checkExistenceOfCountry(String countryName))
		//Call addNeighbor() from Mapx
		
		return true;
	}
	
	public boolean showMap() {
		
		//Get object of Graph
		//call showMap from Mapx
		
		return true;
	}
	
	public boolean saveMap(String fileName) {
		
		//Get object of Graph
		//call saveMap from Mapx
		currentState = State.gamePlay;
		
		return true;
	}
	
	public boolean editMap(String fileName) {
		
		//Get object of Graph
		//call editMap() from Mapx
		
		return true;
	}
	
	public boolean validateMap() {
	
		//Get object of Graph
		//call validateMap() from Mapx
		
		return true;
	}
	
	public boolean loadMap(String fileName) {
		
		//call loadMap() from Mapx
		currentState = State.editPlayer;
		return true;
	}
	
	public boolean addPlayer(String playerName) {
		
		//check: if there is a player with this name, return false
		for(Player itr:Database.playerList) {
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

		return true;
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
	
	public boolean populateCountries() {
		
		//change the currentState
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
	
	public boolean placeAll(Player currentPlayer, Graph gameGraph) {
	
		//implement a loop for placing all army for players
		//update owning parameter of country and MyCountry parameter of player
		//change currentState
<<<<<<< HEAD
		currentState = State.reinforcementPhase;
=======
        while( ! Player.allPlayersRemainingArmiesExhausted()) {
            for (Country thisCountry : gameGraph.getAdjList()) {
                Player playerThatOwnsThisCountry= Player.getPlayerByName(thisCountry.getOwner());
                if (playerThatOwnsThisCountry.getNumberOfArmies() > 0 ){
                    thisCountry.setNumberOfArmies(thisCountry.getNumberOfArmies() + 1);
                    playerThatOwnsThisCountry.setNumberOfArmies(playerThatOwnsThisCountry.getNumberOfArmies() -1);
                }
            }
        }
>>>>>>> master
		
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