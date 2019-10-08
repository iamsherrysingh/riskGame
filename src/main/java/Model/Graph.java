
package Model;

import java.util.*;

public class Graph {
    static public ArrayList<Country> adjList= new ArrayList<Country>();
    private static Graph gameGraph;

    /**
     * private constructor so that a object of public class Database cannot be created
     */
    private Graph() {
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
     * This method is an implementation of showMap command
     * @param graph
     */
    public void printGraph(Graph graph)
    {
        for(Country country: graph.adjList){
            System.out.println(country.getNumber() + " " +country.getName() +" "+ country.getNeighbours());
        }
    }

    /**
     * This method is another implementation of the showMap command
     */
    public static void printGraph()
    {

        for(Country country: adjList){
            System.out.println(country.getNumber() + " " +country.getName() +" "+ country.getNeighbours());
        }
    }
}
