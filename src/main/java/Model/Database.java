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
	static ArrayList<Player> playerList= new ArrayList<Player>();

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		Database.playerList = playerList;
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

	static void addPlayer(Integer id, String Name, Integer noOfArmies){
		Player player= new Player(id, Name,noOfArmies);
		playerList.add(player);
	}

	static void removePlayer(String Name){
		for(Player player: playerList){
			if(player.getName().equalsIgnoreCase(Name)){
				playerList.remove(player);
			}
		}
	}

}