import java.util.*;

public class Country {
    Integer number,coOrdinate1,getCoOrdinate2,inContinent;
    String name;
    ArrayList<Integer> neighbours;

    public Country(Integer number, Integer coOrdinate1, Integer getCoOrdinate2, String name, Integer inContinent, ArrayList<Integer> neighbours) {
        this.number = number;
        this.coOrdinate1 = coOrdinate1;
        this.getCoOrdinate2 = getCoOrdinate2;
        this.name = name;
        this.inContinent = inContinent;
        this.neighbours = neighbours;
    }

    void addNeighbour(Integer newNeighbour){
        this.neighbours.add(newNeighbour);
    }

    void removeNeighbour(Integer deletedNeighbour){
        this.neighbours.remove(deletedNeighbour);
    }
    
    //Getters

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

    public void setneighbours(ArrayList<Integer> neighbours) {
        this.neighbours = neighbours;
    }
    
    
}
