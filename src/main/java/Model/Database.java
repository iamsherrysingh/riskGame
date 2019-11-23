package Model;

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
