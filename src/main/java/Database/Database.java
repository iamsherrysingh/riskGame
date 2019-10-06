package Database;

import Model.*;

import java.util.ArrayList;

public class Database {
	private static Database dbobj;

	// private constructor to force use of
	// getInstance() to create Singleton object
	private Database() {
	}

	public static Database getInstance() {
		if (dbobj == null)
			dbobj = new Database();
		return dbobj;
	}

	static ArrayList<Continent> continentList= new ArrayList<Continent>();
	static ArrayList<Player> playerList= new ArrayList<Player>();

	public static ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public static void setPlayerList(ArrayList<Player> playerList) {
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
		dbobj.playerList.add(player);
	}

}
