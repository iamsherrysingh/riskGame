
package Model;

import java.util.*;

public class Graph {
    static public ArrayList<Country> adjList= new ArrayList<Country>();
    private static Graph gameGraph;

    /**
     * private constructor so that a object of public class Database cannot be created
     */
    public Graph() {
    }

    /**
     * Singleton implementation of Database class
     * @return
     */
    public static Graph getInstance(){
        if(gameGraph==null)
            gameGraph= new Graph();
        return gameGraph;
    }

    public ArrayList<Country> getAdjList() {
        return adjList;
    }

    public void setAdjList(ArrayList<Country> adjList) {
        this.adjList = adjList;
    }


    /**
     * This method is another implementation of the showMap command
     */
    public static void showMap()
    {

        for(Country country: adjList){
            System.out.print(country.getNumber() + " " +country.getName() +" "+ country.getNeighbours());
            if(country.getOwner() == null){
                System.out.print(" Owner: None");
            }
            else{
                System.out.print(" Owner: "+country.getOwner());
            }
            System.out.println(" Armies: "+country.getNumberOfArmies());
        }
    }
}
