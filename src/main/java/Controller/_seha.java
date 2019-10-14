package Controller;

import Model.*;


public class _seha {
    public static void main(String[] args) {
        GamePlay gamePlay= GamePlay.getInstance();
        Graph g = gamePlay.getGraphObj();

//        g.showMap();
//        map.removeNeighbour("Greenland", "Ontario", g);
//        g.showMap();


//        System.out.println(Country.getCountryByName("Western-Australia",g).getNumber());
//        System.out.println(Country.getCountryByNumber(39,g).getName());
        System.out.println("player list size "+ Database.getInstance().getPlayerList().size());

        Player currrentPlayer= Player.addPlayer("Old Player ", 42);
        Player newPlayer= Player.addPlayer("New Player", 42);
        Player newss= Player.addPlayer("Newest Player", 42);
        Player.printAllPlayers();

//        System.out.println(gamePlay.placeArmy(currrentPlayer,"ssss", g));
        //gamePlay.placeArmy(currrentPlayer,"Western-Australia", g);
        //g.showMap();

        gamePlay.populateCountries(g);

        g.showMap();

        System.out.println(Player.getPlayerByName("New Player").getName());






    }
}
