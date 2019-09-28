import java.util.*;

public class Graph {
        int numberOfCountries;
        ArrayList<Integer> adjListArray[];

        Graph(int numberOfCountries) {
            this.numberOfCountries = numberOfCountries;

            adjListArray = new ArrayList[numberOfCountries];

            // Create a new list for each node
            // such that adjacent nodes can be stored
            for (int i = 0; i < numberOfCountries; i++) {
                adjListArray[i] = new ArrayList<>();
            }
        }

//Add Edge
    static void addEdge(Graph graph, int src, int dest)
    {
        // Add an edge from src to dest and vice versa.
        graph.adjListArray[src].add(dest);
        graph.adjListArray[dest].add(src);
    }

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

}