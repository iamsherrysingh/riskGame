package Model;

import java.util.ArrayList;

/**
 * This class maintains all the data members and methods related to each continent.
 */
public class Continent {
	
    String name, color;
    Integer number,controlValue;
    String owner;
    
    public Continent(Integer number, String name, Integer controlValue, String color) {
        this.number= number;
        this.name = name;
        this.color = color;
        this.controlValue = controlValue;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getControlValue() {
        return controlValue;
    }

    public void setControlValue(Integer controlValue) {
        this.controlValue = controlValue;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public String getOwner() {
    	return owner;
    }
    public void setOwner(String owner) {
    	this.owner = owner;
    }

    /**
	 * This checks whether a continent exist or not in Database.continentList
	 *
	 * @param continentToCheck
	 * @return true or false
	 */
    public static boolean checkExistenceOfContinent(String continentToCheck) {
		for (Continent singleContinent : Database.getInstance().getContinentList()) {
			if (singleContinent.getName().equalsIgnoreCase(continentToCheck)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This adds a new continent to the Database.continentList
	 *
	 * @param continentName
	 * @param controlValue
	 * @return true or false
	 */
	public static boolean addContinent(String continentName, Integer controlValue) {
		if (controlValue > 0 && controlValue!=null) {
			if (continentName.trim().length() == 0) {
				return false;
			}
			if (!Continent.checkExistenceOfContinent(continentName)) {
				Continent newContinent = new Continent(Database.getInstance().getContinentList().size() + 1,
						continentName, controlValue, "");
				Database.getInstance().getContinentList().add(newContinent);
				return true;
			}
			return false;
		} else {
			System.out.println("Please enter a valid control value");
			return false;
		}
	}

	/**
	 * if the entered continent exists, it removes all the countries under that continent and after that removes that continent from the Database.continentList
	 *
	 * @param continentToRemove
	 * @return true or false
	 */
	public static boolean removeContinent(String continentToRemove, Graph gameGraph) {
		Integer serialNumberOfContinentToRemove = -1;
		Continent continent = null;
		for (Continent singleContinent : Database.getInstance().getContinentList()) {
			if (singleContinent.getName().equalsIgnoreCase(continentToRemove)) {
				serialNumberOfContinentToRemove = singleContinent.getNumber();
				continent = singleContinent;
			}
		}
		if (serialNumberOfContinentToRemove == -1) {
			System.out.println("Continent: " + continentToRemove + " not found in the map!");
			return false;
		}
		ArrayList<String> countriesInContinentToRemove = new ArrayList<String>();
		for (Country country : gameGraph.getAdjList()) {
			if (country.getInContinent() == serialNumberOfContinentToRemove) {
				countriesInContinentToRemove.add(country.getName());
			}
		}

		// Start deleting all the countries in this continent
		for (String country : countriesInContinentToRemove) {
			Country.removeCountry(country, gameGraph);
		}
		Database.getInstance().getContinentList().remove(continent);
		return true;
	}

	
	/**
	 * This create and returns list of countries in a continent
	 *
	 *@return ArrayList of countries in a continent
	 */
	public static ArrayList<Country> getCountryList(Continent continent, Graph gameGraph) {
		ArrayList<Country> countryList = new ArrayList<Country>();
		for (Country country : gameGraph.getAdjList()) {
			Continent continentOfThisCountry = Continent.getContinentById(country.getInContinent());
			if (continentOfThisCountry.getName().equalsIgnoreCase(continent.getName())) {
				countryList.add(country);
			}
		}
		return countryList;
	}

	/**
	 * This provides with the instance at which a particular continent is present in Database.continentList  using continentNumber
	 *
	 * @param continentNumber
	 * @return instance for continentNumber
	 */
	public static Continent getContinentById(Integer continentNumber) {
		Continent continent = null;
		for (Continent continent1 : Database.getInstance().getContinentList()) {
			if (continent1.getNumber() == continentNumber) {
				return continent1;
			}
		}
		return continent;
	}

	/**
	 * This provides with the instance at which a particular continent is present in Database.continentList using continentName 
	 *
	 * @param continentName
	 * @return instance for continentName
	 */
	public static Continent getContinentByName(String continentName) {
		Continent continent = null;
		for (Continent continent1 : Database.getInstance().getContinentList()) {
			if (continent1.getName().equalsIgnoreCase(continentName)) {
				return continent1;
			}
		}
		return continent;
	}


	public static boolean continentBelongToPlayer(String playerName, String continentName, Graph gameGraph) {
		Continent continentInQuestion = Continent.getContinentByName(continentName);

		for(Country country: gameGraph.getAdjList()){
			if(country.getInContinent() == continentInQuestion.number){
				if(! country.owner.equalsIgnoreCase(playerName)){
					return false;
				}
			}
		}

		return true;
	}
    
	
	/**
	 * This updates the owner of the continent
	 * @param gameGraph
	 */
	
    public static void updateContinitsOwner(Graph gameGraph) {
    	
    	for(Continent continentItr : Database.continentList) {
    		
    		String tempOwner = "";
    		boolean continentHasOwner = true;
    		
    		for(Country countryItr : gameGraph.getAdjList()) {
    			
    			if(countryItr.getInContinent() == continentItr.getNumber()) {
    				
    				if(tempOwner.isEmpty()){
    					tempOwner = countryItr.getOwner();
    				}
    				else if(tempOwner.equals(countryItr.getOwner())) {
    					continue;
    				}
    				else {
    					continentHasOwner = false;
    					break;
    				}
    			}
    		}
    		
    		if(continentHasOwner)
				continentItr.setOwner(tempOwner);
			else
				continentItr.setOwner("");
    		
    	}
    }

}
