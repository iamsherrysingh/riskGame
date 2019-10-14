package Model;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class Player {
    String name;
    Integer number, numberOfArmies;
    ArrayList<Integer> myCountries = new ArrayList<Integer>();;

    public String getName() {
        return name;
    }

    private Player(Integer number, String name, Integer numberOfArmies) {
        this.number = number;
        this.name = name;
        this.numberOfArmies = numberOfArmies;
    }

    public Integer getNumber() {
        return number;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Integer getNumberOfArmies() {
        return numberOfArmies;
    }

    public void setNumberOfArmies(Integer numberOfArmies) {
        this.numberOfArmies = numberOfArmies;
    }

    public ArrayList<Integer> getMyCountries() {
        return myCountries;
    }

    public void setMyCountries(Integer number) {
    	myCountries.add(number); 
    }

    public static boolean addPlayer(String playerName, Integer noOfArmies){
        if(Player.getPlayerByName(playerName)!=null){
            System.out.println("=======> This player exists <========");
            return false;
        }
        Integer id= Database.getInstance().getPlayerList().size() + 1;

        Player player= new Player(id, playerName,noOfArmies);
        Database.playerList.add(player);
        return true;
    }

    public static boolean removePlayer(String playerName){
        Player player= Player.getPlayerByName(playerName);
        if(player==null){
            return false;
        }
        Database.playerList.remove(player);

        Integer playerNumber=1;
        for(Player player1: Database.getInstance().getPlayerList() ){
            player1.number=playerNumber;
            playerNumber++;
        }

        return true;
    }

    public static Player getPlayerByName(String playerName){
        for(Player player: Database.playerList){
            if(player.getName().equalsIgnoreCase(playerName)){
                return player;
            }
        }
        return null;
    }

    public static Player getPlayerByNumber(Integer playerNumber){
        for(Player player: Database.getInstance().getPlayerList()){
            if(player.getNumber() == playerNumber){
                return player;
            }
        }
        return null;
    }

    public static boolean allPlayersRemainingArmiesExhausted(){
        for(Player player: Database.getInstance().getPlayerList()){
            if(player.getNumberOfArmies()>0){
                return false;
            }
        }
        return true;
    }

    public static void printAllPlayers(){
        for(Player player: Database.getInstance().getPlayerList()){
            System.out.println(player.getNumber()+ " " + player.getName() +" "+ player.getNumberOfArmies() );
        }
        System.out.println();
    }

}
