import java.util.*;

public class Country {
    Integer number,coOrdinate1,getCoOrdinate2,inContinent,numberOfArmies;
    String name,owner;
    ArrayList<Integer> neighbours;

    public Country(Integer number, String name, Integer inContinent, String owner, Integer numberOfArmies, Integer coOrdinate1, Integer getCoOrdinate2, ArrayList<Integer> neighbours) {
        this.number = number;
        this.coOrdinate1 = coOrdinate1;
        this.getCoOrdinate2 = getCoOrdinate2;
        this.inContinent = inContinent;
        this.numberOfArmies = numberOfArmies;
        this.name = name;
        this.owner = owner;
        this.neighbours = neighbours;
    }

    void addNeighbour(Integer newNeighbour){
        this.neighbours.add(newNeighbour);
    }

    void removeNeighbour(Integer deletedNeighbour){
        this.neighbours.remove(deletedNeighbour);
    }
    
    //Getters

    public Integer getNumberOfArmies() {
        return numberOfArmies;
    }

    public String getOwner() {
        return owner;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getCoOrdinate1() {
        return coOrdinate1;
    }

    public Integer getGetCoOrdinate2() {
        return getCoOrdinate2;
    }

    public Integer getInContinent() {
        return inContinent;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getNeighbours() {
        return neighbours;
    }


    //Setters

    public void setNumberOfArmies(Integer numberOfArmies) {
        this.numberOfArmies = numberOfArmies;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setNeighbours(ArrayList<Integer> neighbours) {
        this.neighbours = neighbours;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setCoOrdinate1(Integer coOrdinate1) {
        this.coOrdinate1 = coOrdinate1;
    }

    public void setGetCoOrdinate2(Integer getCoOrdinate2) {
        this.getCoOrdinate2 = getCoOrdinate2;
    }

    public void setInContinent(Integer inContinent) {
        this.inContinent = inContinent;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
