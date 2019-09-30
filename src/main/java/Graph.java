import java.util.*;

public class Graph {
        ArrayList<Country> adjList;

    public Graph(ArrayList<Country> adjlist) {
        this.adjList = new ArrayList<Country>();
    }

    //Add Country
    void addCountry(Graph graph, Country newCountry)
    {
        // Add new Country
        graph.adjList.add(newCountry);

        //Add new Country as a border country to the countries it borders
        Integer newCountryNumber= newCountry.getNumber();
        for(Integer newCountryBorders: newCountry.getBorders()){
            for(Country existingCountry: adjList){
                if(existingCountry.getNumber()==newCountryBorders){
                    existingCountry.getBorders().add(newCountryNumber);
                }
            }
        }
    }
/*

//Print Graph
    static void printGraph(Graph graph)
    {
        for(int v = 0; v < graph.numberOfCountries; v++)
        {
            System.out.println("Adjacency list of vertex "+ v);
            System.out.print("head");
            for(Integer pCrawl: graph.adjListArray[v]){
                System.out.print(" -> "+pCrawl);
            }
            System.out.println("\n");
        }
    }
*/
}
