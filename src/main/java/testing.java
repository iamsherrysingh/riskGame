import Model.*;


public class testing {

    public static void main(String[] args) {
        GamePlay.getInstance().loadGameMap("map.map");
        Database.getInstance().getPlayerList().add(new AggressivePlayer(1,"p1",4));
        Database.getInstance().getPlayerList().add(new AggressivePlayer(2,"p2",4));
        GamePlay.getInstance().populateCountries();
        GamePlay.getInstance().placeAll();

//        boolean output= Mapx.checkPath("Eastern-United-States", "Venezuela", Graph.getInstance());
//        System.out.println(output);
        boolean output2=false;
/*        for(Country country:Graph.getInstance().getAdjList()){
            country.setOwner("p1");
        }*/
        GamePlay.getInstance().showMap();
        do {
            output2 = Mapx.checkPath("New-Guinea", "Central-America", Graph.getInstance());
        }while(output2 == false);
        System.out.println(output2);
    }
}
