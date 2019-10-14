package Controller;

import Model.*;

public class _seha {
    public static void main(String[] args) {
        Mapx map= new Mapx();
        Graph g=map.createGameGraph("src/main/resources/map.map");

        g.printGraph();
        map.removeNeighbour("Greenland", "Ontario", g);
        g.printGraph();


        System.out.println(Country.getCountryByName("Western-Australia",g).getNumber());
        System.out.println(Country.getCountryByNumber(39,g).getName());

    }
}
