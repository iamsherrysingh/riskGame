package Model;

import java.util.ArrayList;

public class Continent {
    String name, color;
    Integer number,controlValue;
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

    public static boolean checkExistenceOfContinent(String continentToCheck){
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
     */
    public static boolean addContinent(String continentName, Integer controlValue) {
        if (continentName.length() == 0) {
            return false;
        }
        if (!Continent.checkExistenceOfContinent(continentName)) {
            Continent newContinent = new Continent(Database.getInstance().getContinentList().size() + 1, continentName,
                    controlValue, "");
            Database.getInstance().getContinentList().add(newContinent);
            return true;
        }
        return false;
    }

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


}
