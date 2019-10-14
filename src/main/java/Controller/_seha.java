package Controller;

import Model.*;


public class _seha {
    public static void main(String[] args) {
        GamePlay gamePlay= GamePlay.getInstance();
        Graph g = gamePlay.getGraphObj();

//        g.showMap();
 //      Country.removeNeighbour("Greenland", "Ontario", g);
//        g.showMap();


        System.out.println(Country.getCountryByName("Western-Australia",g).getNumber());
        System.out.println(Country.getCountryByNumber(39,g).getName());
        System.out.println("player list size "+ Database.getInstance().getPlayerList().size());

        Player.addPlayer("Sherry", 3);
       Player.addPlayer("Kammu", 3);
        Player.addPlayer("Abhi", 3);
        Player.printAllPlayers();

//        System.out.println(gamePlay.placeArmy(currrentPlayer,"ssss", g));

        //g.showMap();

        gamePlay.populateCountries(g);
        gamePlay.placeAll(g);


        g.showMap();
        System.out.println();

        try {
            gamePlay.getMapxObj().saveMap(g, "savemap");
        }
        catch(Exception e){

        }





    }
}
