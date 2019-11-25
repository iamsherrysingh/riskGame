package Model;

import java.util.*;

/**
 * This class maintains all the data members and methods related to each
 * country.
 */
public class Country {
	Integer number, coOrdinate1, getCoOrdinate2, inContinent, numberOfArmies;
	String name, owner;
	ArrayList<Integer> neighbours;

	public Country(Integer number, String name, Integer inContinent, String owner, Integer numberOfArmies,
			Integer coOrdinate1, Integer getCoOrdinate2, ArrayList<Integer> neighbours) {
		this.number = number;
		this.coOrdinate1 = coOrdinate1;
		this.getCoOrdinate2 = getCoOrdinate2;
		this.inContinent = inContinent;
		this.numberOfArmies = numberOfArmies;
		this.name = name;
		this.owner = owner;
		this.neighbours = neighbours;
	}

	/**
	 * adds new neighbour to the Country.neighbour adjacency list
	 * 
	 * @param newNeighbour
	 */
	void addNeighbour(Integer newNeighbour) {
		this.neighbours.add(newNeighbour);
	}

	/**
	 * removes an existing neighbour from the Country.neighbour adjacency list
	 * 
	 * @param deletedNeighbour
	 */
	void removeNeighbour(Integer deletedNeighbour) {
		this.neighbours.remove(deletedNeighbour);
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getCoOrdinate1() {
		return coOrdinate1;
	}

	public void setCoOrdinate1(Integer coOrdinate1) {
		this.coOrdinate1 = coOrdinate1;
	}

	public Integer getGetCoOrdinate2() {
		return getCoOrdinate2;
	}

	public void setGetCoOrdinate2(Integer getCoOrdinate2) {
		this.getCoOrdinate2 = getCoOrdinate2;
	}

	public Integer getInContinent() {
		return inContinent;
	}

	public void setInContinent(Integer inContinent) {
		this.inContinent = inContinent;
	}

	public Integer getNumberOfArmies() {
		return numberOfArmies;
	}

	public void setNumberOfArmies(Integer numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public ArrayList<Integer> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(ArrayList<Integer> neighbours) {
		this.neighbours = neighbours;
	}

	/**
	 * Add a new country object to gameGraph adjacency list
	 *
	 * @param newCountry Specifies the name of the country to be added
	 * @param inContinent Specifies the name of the continent in which the country is to be added
	 * @param gameGraph It is the object of class Graph
	 * @return true(If addCountry method runs successfully) or false(If continent is not found or the country already exists)
	 */
	public static boolean addCountry(String newCountry, String inContinent, Graph gameGraph) {
		if (newCountry.length() == 0) {
			System.out.println("Enter a name for the new country");
			return false;
		}
		Integer continentNumber = -1;
		for (Continent continent : Database.getInstance().getContinentList()) {
			if (continent.getName().equalsIgnoreCase(inContinent)) {
				continentNumber = continent.getNumber();
			}
		}
		if (continentNumber == -1) {
			System.out.println("Continent: " + inContinent + " not found!");
			System.out.println("Perhaps add one first");
			return false;
		}
		
		if(Country.getCountryByName(newCountry, gameGraph) !=null) {
			System.out.println("Country "+newCountry+" already exists");
			return false;
		}
		
		
		Country country = new Country(gameGraph.getAdjList().size() + 1, newCountry, continentNumber, null, null, 0, 0,
				new ArrayList<Integer>());
		gameGraph.getAdjList().add(country);
		System.out.println(newCountry+ " added.");
		return true;
	}

	/**
	 * This adds new neighbour country number in ArrayList of neighbors in
	 * country.adjlist.neighbours and also adds the country's number in new
	 * neighbor's Arraylist of neighbors
	 *
	 * @param countryWithNewNeighbour Name of the country to which neighbours are to be added
	 * @param neighbour Name of the neighbour to be added
	 * @param gameGraph gameGraph It is the object of class Graph
	 * @return true(If addNeighbour runs and neighbour is added successfully) or false(If either country or the neighbour is absent from the map)
	 */
	public static boolean addNeighbour(String countryWithNewNeighbour, String neighbour, Graph gameGraph) {
		Integer numberOfCountryWithNewNeighbour = -1;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(countryWithNewNeighbour)) {
				numberOfCountryWithNewNeighbour = country.getNumber();
			}
		}
		if (numberOfCountryWithNewNeighbour == -1) {
			System.out.println("Country: " + countryWithNewNeighbour + " not in the map!");
			System.out.println("Perhaps add one first.");
			return false;
		}
		Integer neighbourNumber = -1;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(neighbour)) {
				neighbourNumber = country.getNumber();
			}
		}
		if (neighbourNumber == -1) {
			System.out.println("Neighbour Country: " + neighbour + " not in the map!");
			return false;
		}
		gameGraph.getAdjList().get(numberOfCountryWithNewNeighbour - 1).getNeighbours().add(neighbourNumber); // Added
																												// new
																												// neighbour
																												// for
																												// this
																												// country
		gameGraph.getAdjList().get(neighbourNumber - 1).getNeighbours().add(numberOfCountryWithNewNeighbour); // Added
																												// this
																												// country
																												// as
																												// neighbour
																												// to
																												// it'sneighbour
		return true;
	}

	/**
	 * This method removes a country from the gameGraph list, and makes the
	 * necessary changes to the list
	 *
	 * @param NameOfCountryToRemove Name of a country that is to be removed
	 * @param gameGraph Object of the class Graph
	 * @return true(If removeCountry executes successfully) or false(If the country is not present in the map)
	 */
	public static boolean removeCountry(String NameOfCountryToRemove, Graph gameGraph) {
		Country countryToRemove = null;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(NameOfCountryToRemove)) {
				countryToRemove = country;
			}
		}
		if (countryToRemove == null) {
			System.out.println("Country: " + NameOfCountryToRemove + " not in the map!");
			return false;
		}
		for (Integer neighbour : countryToRemove.getNeighbours()) {
			ArrayList<Integer> neighbourListOfNeighbour = gameGraph.getAdjList().get(neighbour - 1).getNeighbours();
			neighbourListOfNeighbour.remove(countryToRemove.getNumber());
		}
		gameGraph.getAdjList().remove(countryToRemove);

		// Now fixing the serial number mess after a country is removed
		// serial number for every country after the removedd country is decremented
		for (int i = countryToRemove.getNumber() - 1; i < gameGraph.getAdjList().size(); i++) {
			Integer oldSerialNumber = gameGraph.getAdjList().get(i).getNumber();
			Integer newSerialNumber = oldSerialNumber - 1;
			gameGraph.getAdjList().get(i).setNumber(newSerialNumber);
		}

		// neighbour list updated for every affected country
		for (Country country : gameGraph.getAdjList()) {
			ArrayList<Integer> newNeighbourList = new ArrayList<Integer>();
			for (Integer singleNeighbour : country.getNeighbours()) {
				if (singleNeighbour > countryToRemove.getNumber()) {
					newNeighbourList.add(singleNeighbour - 1);
				} else {
					newNeighbourList.add(singleNeighbour);
				}
			}
			country.setNeighbours(newNeighbourList);
		}
		System.out.println(NameOfCountryToRemove+" removed.");
		return true;
	}

	/**
	 * This method removes a neighbour from a country that exists in the gameGraph
	 * variable
	 *
	 * @param firstCountryName Name of the first country
	 * @param secondCountryName Name of the second country
	 * @param gameGraph It is the object of class Graph
	 * @return true(If the method runs successfully and the neighbour is removed as required) or false(If the country is absent from the map or if the one of the countries is not the neighbour of the other
	 */
	public static boolean removeNeighbour(String firstCountryName, String secondCountryName, Graph gameGraph) {

		Country firstCountry = null;
		Country secndCountry = null;

		boolean firstCountryExist = false;
		boolean secndCountryExist = false;

		for (Country country : gameGraph.getAdjList()) {

			if (country.getName().equalsIgnoreCase(firstCountryName)) {
				firstCountryExist = true;
				firstCountry = country;
			}

			if (country.getName().equalsIgnoreCase(secondCountryName)) {
				secndCountryExist = true;
				secndCountry = country;
			}

		}

		if (!firstCountryExist) {
			System.out.println("Country: " + firstCountryName + " is not in the map!");
			return false;
		}

		if (!secndCountryExist) {
			System.out.println("Country: " + secondCountryName + " is not in the map!");
			return false;
		}

		boolean mutualNeighbour = false;
		int neighbourIndex2 = -1;
		for (int index = 0; index < firstCountry.getNeighbours().size(); index++) {

			int indx = firstCountry.neighbours.get(index);
			Country neib = gameGraph.getAdjList().get(indx - 1);

			if (secndCountry.getName().equalsIgnoreCase(neib.getName())) {
				mutualNeighbour = true;
				neighbourIndex2 = index;
			}

		}

		if (!mutualNeighbour) {
			System.out.println("First country is not neighbour of second country");
			return false;
		}

		mutualNeighbour = false;
		int neighbourIndex1 = -1;
		for (int index = 0; index < secndCountry.getNeighbours().size(); index++) {

			int indx = secndCountry.neighbours.get(index);
			Country neib = gameGraph.getAdjList().get(indx - 1);

			if (firstCountry.getName().equalsIgnoreCase(neib.getName())) {
				mutualNeighbour = true;
				neighbourIndex1 = index;
			}

		}

		if (!mutualNeighbour) {
			System.out.println("Second country is not neighbour of first country");
			return false;
		}

		int firstCountryInx = firstCountry.getNumber() - 1;
		int secndCountryInx = secndCountry.getNumber() - 1;

		gameGraph.getAdjList().get(firstCountryInx).getNeighbours().remove(neighbourIndex2);
		gameGraph.getAdjList().get(secndCountryInx).getNeighbours().remove(neighbourIndex1);

		return true;
	}

	/**
	 * this returns the instance where the country is saved in Database.adjlist
	 *
	 * @param countryName Name of the country 
	 * @param gameGraph It is the object of class Graph
	 * @return instance of country
	 */
	public static Country getCountryByName(String countryName, Graph gameGraph) {
		Country returnedCountry = null;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(countryName)) {
				returnedCountry = country;
			}
		}
		return returnedCountry;
	}

	/**
	 * this returns the instance where the country is saved in Database.adjlist
	 *
	 * @param countryNumber The number assigned to the country
	 * @param gameGraph It is the object of class Graph
	 * @return instance of country
	 */
	public static Country getCountryByNumber(Integer countryNumber, Graph gameGraph) {
		Country returnedCountry = null;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getNumber() == countryNumber) {
				returnedCountry = country;
			}
		}

		return returnedCountry;
	}

	/**
	 * this assign an owner to a country without owner
	 *
	 * @param ownerName The name of the owner to whom the country is to be assigned
	 * @param countryName The name of country to be assigned
	 * @param g It is the object of class Graph
	 * @return true(If the method runs successfully and the country is assigned an owner) or false(If the country name or owner name is not present)
	 */
	public static boolean assignOwner(String ownerName, String countryName, Graph g) {
		Country country = Country.getCountryByName(countryName, g);
		if (country == null) {
			return false;
		}
		if (country.getOwner() == null) {
			country.setOwner(ownerName);
		} else {
			System.out.println("Country assigned to owner: " + country.owner);
			return false;
		}
		return true;
	}

	/**
	 * this updates the owner name for a country in Database.adjlist
	 *
	 * @param newOwnerName Name of the new owner of a country
	 * @param countryName Name of the country
	 * @param g It is the object of class Graph
	 * @return true(If the method executes and an owner is assigned) or false(If the country is not present)
	 */
	public static boolean changeOwner(String newOwnerName, String countryName, Graph g) {
		Country country = Country.getCountryByName(countryName, g);
		if (country == null) {
			return false;
		}
		country.setOwner(newOwnerName);
		return true;
	}

	/**
	 * checks the existence of the country in Database.adjlist
	 *
	 * @param countryToCheck Name of the country
	 * @param gameGraph It is the object of class Graph
	 * @return true(If the country is present) or false(If the method runs successfully)
	 */
	public static boolean checkExistenceOfCountry(String countryToCheck, Graph gameGraph) {
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(countryToCheck)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks if all the countries has an owner
	 *
	 * @param gameGraph It is the object of class Graph
	 * @return true(If all the countries have an owner) or false(IF any country does not have an owner)
	 */
	public static boolean allCountriesPopulated(Graph gameGraph) {
		for (Country country : gameGraph.getAdjList()) {
			if (country.getOwner() == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method adds additional army units to a country object in gameGraph
	 *
	 * @param countryName Name of the country
	 * @param numberOfArmies Number of army units to be added
	 * @param gameGraph It is the object of class Graph
	 * @return true(If the method runs successfully and the countries are reinforced) or false(If the country is absent)
	 */
	public static boolean addArmiesToCountry(String countryName, Integer numberOfArmies, Graph gameGraph) {
		Country countryToReinforce = null;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(countryName)) {
				countryToReinforce = country;
			}
		}
		if (countryToReinforce == null) {
			System.out.println("Country: " + countryName + " not in the map!");
			return false;
		}
		countryToReinforce.setNumberOfArmies(countryToReinforce.getNumberOfArmies() + numberOfArmies);
		return true;
	}

	/**
	 * This method removes army units from a country object in the gameGraph
	 *
	 * @param countryName Name of the country
	 * @param numberOfArmies Number of army units 
	 * @param gameGraph It is the object of class Graph
	 * @return true(If the method executes successfully and armies are removed) or false(If the country is not on the map)
	 */
	public static boolean removeArmiesFromCountry(String countryName, Integer numberOfArmies, Graph gameGraph) {
		Country countryToWeaken = null;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(countryName)) {
				countryToWeaken = country;
			}
		}
		if (countryToWeaken == null) {
			System.out.println("Country: " + countryName + " not in the map!");
			return false;
		}
		if ((countryToWeaken.getNumberOfArmies() - numberOfArmies) <= 0) {
			countryToWeaken.setNumberOfArmies(countryToWeaken.getNumberOfArmies() - numberOfArmies);
		} else {
			System.out.println("Cannot have less than 1 army in a country!");
		}
		return true;
	}

	/**
	 * updates the player list, remove the player if 0 country is owned by that
	 * player or declares winner if every country in Database.adjlist is owned by a
	 * single player
	 *
	 * @param g It is the object of class Graph
	 */
	public static void updatePlayerListAndDeclareWinner(Graph g) {

		for (IPlayer player : Database.getInstance().getPlayerList()) {
			Integer NumberOfCountriesOwned = 0;
			for (Country country : g.getAdjList()) {

				if (country.getOwner().equalsIgnoreCase(player.getName())) {
					NumberOfCountriesOwned++;
				}
			}

			if (NumberOfCountriesOwned == 0) {
				GamePlay.getInstance().removePlayer(player.getName());
			} else if (NumberOfCountriesOwned == g.getAdjList().size()) {
				System.out.println(player.getName() + "wins the game!");
			}
		}

	}

}
