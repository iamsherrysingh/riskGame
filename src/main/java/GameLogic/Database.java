package GameLogic;

import java.util.ArrayList;

/**
 * This is a Database that holds the continent details, and player details
 * This has singleton implementation so that the list of continent/player is not duplicated in a single game run
 */
public class Database {
	private static Database dbobj;

	/**
	 * 	private constructor to force use of  getInstance() to create Singleton object
	 */
	private Database() {
	}

	public static Database getInstance() {
		if (dbobj == null)
			dbobj = new Database();
		return dbobj;
	}

	static ArrayList<Continent> continentList= new ArrayList<Continent>();
	static ArrayList<IPlayer> playerList= new ArrayList<IPlayer>();

	public ArrayList<IPlayer> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<IPlayer> playerList) {
		Database.playerList = playerList;
	}
	
	/**
	 * This returns the instance of the player where a player is saved in
	 * Database.playerlist using player's name
	 * 
	 * @param playerName The name of the player
	 * @return instance of the player
	 */
	public static IPlayer getPlayerByName(String playerName) {
		for (IPlayer player : playerList) {
			if (player.getName().equalsIgnoreCase(playerName)) {
				return player;
			}
		}
		return null;
	}
	
	/**
	 * This returns the instance of the player where a player is saved in
	 * Database.playerlist using player's number
	 * 
	 * @param playerNumber The integer number of the player
	 * @return instance of the player
	 */
	public static IPlayer getPlayerByNumber(Integer playerNumber) {
		for (IPlayer player : getInstance().getPlayerList()) {
			if (player.getNumber() == playerNumber) {
				return player;
			}
		}
		return null;
	}
	
	/**
	 * check if the number of remaining armies that can be placed is equal to zero
	 * for every player
	 * 
	 * @return true(If there are no more armies present) or false(If the number of armies to be placed are still not zero)
	 */
	public static boolean allPlayersRemainingArmiesExhausted() {
		for (IPlayer player : getInstance().getPlayerList()) {
			if (player.getNumberOfFreeArmies() > 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This prints all the details for each and every player in Database.playerlist
	 */
	public static void printAllPlayers() {
		for (IPlayer player : getInstance().getPlayerList()) {
			System.out.println(player.getNumber() + " " + player.getName() + " " + player.getNumberOfArmies());
		}
		System.out.println();
	}
	
	/**
	 * This removes a player from Database.playerlist
	 * 
	 * @param playerName The name of the player to be removed
	 * @return true(If the player is removed successfully) or false(The player is
	 *         absent)
	 */
	public static boolean removePlayer(String playerName) {

		IPlayer player = getPlayerByName(playerName);
		if (player == null) {
			return false;
		}
		playerList.remove(player);

		Integer playerNumber = 1;
		for (IPlayer player1 : getInstance().getPlayerList()) {
			player1.setNumber(playerNumber);
			playerNumber++;
		}
		return true;
	}
	
	/**
	 * this provides the list of countries owned by a particular player
	 * 
	 * @param playerName The name of the player
	 * @param gameGraph This the object of the class Graph
	 * @return list of countries owned by a player
	 */
	public static ArrayList<Country> getOwnedCountryList(String playerName, Graph gameGraph) {
		ArrayList<Country> countryList = new ArrayList<Country>();
		for (Country country : gameGraph.getAdjList()) {
			if (country.getOwner().equalsIgnoreCase(playerName)) {
				countryList.add(country);
			}
		}
		return countryList;
	}

	public ArrayList<Continent> getContinentList() {
		return continentList;
	}

	public void setContinentList(ArrayList<Continent> continentList) {
		this.continentList = continentList;
	}

	public void printContinentList(){
		for(Continent continent: continentList){
			System.out.println(continent.getNumber()+" "+continent.getName()+" "+continent.getControlValue()+" "+continent.getColor());
		}
	}



}
