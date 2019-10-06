package database;

import Model.Continent;

import java.util.ArrayList;

public class Database {

	private static Database dbobj;

	static ArrayList<Continent> continentList= new ArrayList<Continent>();

	public ArrayList<Continent> getContinentList() {
		return continentList;
	}

	public void setContinentList(ArrayList<Continent> continentList) {
		this.continentList = continentList;
	}

	// private constructor to force use of
	// getInstance() to create Singleton object
	private Database() {
	}

	public static Database getInstance() {
		if (dbobj == null)
			dbobj = new Database();
		return dbobj;
	}

	//Holds continent names
	ArrayList<String> continentNames;
	ArrayList<String> continentDetails;

	public ArrayList<String> getcontinentDetails() {
		return continentDetails;
	}

	public void setcontinentDetails(ArrayList<String> continentDetails) {
		this.continentDetails = continentDetails;
	}

	public ArrayList<String> getcontinentNames() {
		return continentNames;
	}

	public void setcontinentNames(ArrayList<String> continentNames) {
		this.continentNames = continentNames;
	}



}
