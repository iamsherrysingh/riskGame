import java.util.*;

public class Graph {
        private ArrayList<Country> adjList;

    public Graph(ArrayList<Country> adjlist) {
        this.adjList = new ArrayList<Country>();
    }

    public ArrayList<Country> getAdjList() {
        return adjList;
    }

    public void setAdjList(ArrayList<Country> adjList) {
        this.adjList = adjList;
    }

    //Add Country
    void addCountry(Country newCountry)
    {
        // Add new Country
        this.adjList.add(newCountry);

        //Add new Country as a border country to the countries it borders
        Integer newCountryNumber= newCountry.getNumber();
        for(Integer newCountryBorders: newCountry.getNeighbours()){
            for(Country existingCountry: adjList){
                if(existingCountry.getNumber()==newCountryBorders){
                    existingCountry.addNeighbour(newCountryNumber);
                }
            }
        }
    }

//Print Graph
    void printGraph()
    {
        for(Country country: adjList){
            System.out.println(country.getNumber() + " " +country.getName() +" "+ country.getNeighbours());
        }
    }
}
