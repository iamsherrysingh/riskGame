package Database;

import Model.Continent;

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

	public ArrayList<Continent> getContinentList() {
		return continentList;
	}

	public void setContinentList(ArrayList<Continent> continentList) {
		this.continentList = continentList;
	}


}
