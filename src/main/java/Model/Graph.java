package Model;

import java.util.*;

public class Graph {
    static public ArrayList<Country> adjList= new ArrayList<Country>();
    private static Graph gameGraph;
    private Graph() {
    }

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

//Print Model.Graph
    public void printGraph(Graph graph)
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
