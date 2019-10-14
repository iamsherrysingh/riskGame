package Model;

import java.util.*;

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
	 * @param newNeighbour
	 */
	void addNeighbour(Integer newNeighbour) {
		this.neighbours.add(newNeighbour);
	}

	/**
	 * removes an existing neighbour from the Country.neighbour adjacency list
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

	public static Country getCountryByName(String countryName, Graph gameGraph){
		Country returnedCountry=null;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(countryName)) {
				returnedCountry = country;
			}
		}
		return returnedCountry;
	}

	public static Country getCountryByNumber(Integer countryNumber, Graph gameGraph){
		Country returnedCountry=null;
		for (Country country : gameGraph.getAdjList()) {
			if (country.getNumber()==countryNumber) {
				returnedCountry = country;
			}
		}
		return returnedCountry;
	}

	public static boolean assignOwner(String ownerName, String countryName, Graph g){
		Country country= Country.getCountryByName(countryName, g);
		if(country==null){
			return false;
		}
		if(country.getOwner()==null){
			country.setOwner(ownerName);
		}
		else {
			System.out.println("Country assigned to owner: " + country.owner);
			return false;
		}
		return true;
	}

	public static boolean changeOwner(String newOwnerName, String countryName, Graph g){
		Country country= Country.getCountryByName(countryName, g);
		if(country==null){
			return false;
		}
		country.setOwner(newOwnerName);
		return true;
	}

	public static boolean checkExistenceOfCountry(String countryToCheck, Graph gameGraph){
		for (Country country : gameGraph.getAdjList()) {
			if (country.getName().equalsIgnoreCase(countryToCheck)) {
				return true;
			}
		}
		return false;
	}



}


