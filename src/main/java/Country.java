import java.util.*;

public class Country {
    Integer number,coOrdinate1,getCoOrdinate2,inContinent;
    String name;
    ArrayList<Integer> borders;

    public Country(Integer number, Integer coOrdinate1, Integer getCoOrdinate2, String name, Integer inContinent, ArrayList<Integer> borders) {
        this.number = number;
        this.coOrdinate1 = coOrdinate1;
        this.getCoOrdinate2 = getCoOrdinate2;
        this.name = name;
        this.inContinent = inContinent;
        this.borders = borders;
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

    public ArrayList<Integer> getBorders() {
        return borders;
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

    public void setBorders(ArrayList<Integer> borders) {
        this.borders = borders;
    }
}
