package Controller;

import Model.*;

public class _seha {
    public static void main(String[] args) {
        Mapx map= new Mapx();
        Graph g=map.createGameGraph("src/main/resources/map.map");

        g.showMap();
        map.removeNeighbour("Greenland", "Ontario", g);
        g.showMap();


        System.out.println(Country.getCountryByName("Western-Australia",g).getNumber());
        System.out.println(Country.getCountryByNumber(39,g).getName());

        Player currrentPlayer= Player.addPlayer(1, "Player1 ", 42);


        GamePlay gamePlay= GamePlay.getInstance();
        System.out.println(gamePlay.placeArmy(currrentPlayer,"ssss", g));
        gamePlay.placeArmy(currrentPlayer,"Western-Australia", g);
        g.showMap();


    }
}
