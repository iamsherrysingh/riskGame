package Model;

import java.util.ArrayList;

public class Player {
    String name;
    Integer id, numberOfArmies;
    ArrayList<Integer> myCountries = new ArrayList<Integer>();;

    public String getName() {
        return name;
    }

    public Player(Integer id, String name, Integer numberOfArmies) {
        this.name = name;
        this.id = id;
        this.numberOfArmies = numberOfArmies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfArmies() {
        return numberOfArmies;
    }

    public void setNumberOfArmies(Integer numberOfArmies) {
        this.numberOfArmies = numberOfArmies;
    }

    public ArrayList<Integer> getMyCountries() {
        return myCountries;
    }

    public void setMyCountries(Integer number) {
    	myCountries.add(number); 
    }
}
