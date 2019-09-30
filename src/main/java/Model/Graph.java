package Model;

import java.util.*;

public class Graph {
        static private ArrayList<Country> adjList;

    public Graph(ArrayList<Country> adjlist) {
        this.adjList = new ArrayList<Country>();
    }

    public ArrayList<Country> getAdjList() {
        return adjList;
    }

    public void setAdjList(ArrayList<Country> adjList) {
        this.adjList = adjList;
    }

    //Add new Model.Country to the map
    //The object passed to this function can have anything as country.number
    void addCountryToMap(Country newCountry)
    {
        //TO DO
        //Add validations here

        // Add new Model.Country
        newCountry.setNumber(this.getAdjList().size() +1); //Generate the serial number for the new country
        this.adjList.add(newCountry);

        //Add new Model.Country as a border country to the countries it borders
        Integer newCountryNumber= newCountry.getNumber();
        for(Integer newCountryBorders: newCountry.getNeighbours()){
            for(Country existingCountry: adjList){
                if(existingCountry.getNumber()==newCountryBorders){
                    existingCountry.addNeighbour(newCountryNumber);
                }
            }
        }
    }

//Print Model.Graph
    public static void printGraph(Graph graph)
    {
        for(Country country: graph.adjList){
            System.out.println(country.getNumber() + " " +country.getName() +" "+ country.getNeighbours());
        }
    }
//Print Model.Graph
    public static void printGraph()
    {
        for(Country country: adjList){
            System.out.println(country.getNumber() + " " +country.getName() +" "+ country.getNeighbours());
        }
    }
}
