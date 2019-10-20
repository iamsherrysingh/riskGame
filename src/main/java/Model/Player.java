package Model;

import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * This file holds data members and all the methods which are related to player.
 * This has singleton implementation.
 */
public class Player {
    String name;
    Integer number, numberOfArmies;
    ArrayList<Integer> myCountries = new ArrayList<Integer>();

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

    /**
     * This adds a new player in Database.playerlist
     * @param playerName
     * @param noOfArmies
	 * @return true or false
     */
    public static boolean addPlayer(String playerName, Integer noOfArmies){
        if(Player.getPlayerByName(playerName)!=null){
            System.out.println("=======> This player exists <========");
            return false;
        }
        else if(playerName.trim().length() ==0){
            System.out.println("=======> Please enter a name for the player <========");
            return false;
        }
        Integer id= Database.getInstance().getPlayerList().size() + 1;

        Player player= new Player(id, playerName,noOfArmies);
        Database.playerList.add(player);
        return true;
    }

    /**
     * This removes a player from Database.playerlist
     * @param playerName
	 * @return true or false
     */
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

    /**
     * This returns the instance of the player where a player is saved in Database.playerlist using player's name
     * @param playerName
     * @return instance of the player
     */
    public static Player getPlayerByName(String playerName){
        for(Player player: Database.playerList){
            if(player.getName().equalsIgnoreCase(playerName)){
                return player;
            }
        }
        return null;
    }

    /**
     * This returns the instance of the player where a player is saved in Database.playerlist using player's number
     * @param playerNumber
     * @return instance of the player
     */
    public static Player getPlayerByNumber(Integer playerNumber){
        for(Player player: Database.getInstance().getPlayerList()){
            if(player.getNumber() == playerNumber){
                return player;
            }
        }
        return null;
    }

    
    /**
     *  check if the number of remaining armies that can be placed is equal to zero for every player
     * 
	 * @return true or false
     */
    public static boolean allPlayersRemainingArmiesExhausted(){
        for(Player player: Database.getInstance().getPlayerList()){
            if(player.getNumberOfArmies()>0){
                return false;
            }
        }
        return true;
    }

    /**
     * This prints all the details for each and every player in Database.playerlist 
     */
    public static void printAllPlayers(){
        for(Player player: Database.getInstance().getPlayerList()){
            System.out.println(player.getNumber()+ " " + player.getName() +" "+ player.getNumberOfArmies() );
        }
        System.out.println();
    }

    
    /**
     * this provides the list of countries owned by a particular player
     * @param playerName
     * @param gameGraph
     * @return list of countries owned by a player
     */
    public static ArrayList<Country> getOwnedCountryList(String playerName, Graph gameGraph){
        ArrayList<Country> countryList= new ArrayList<Country>();
        for(Country country: gameGraph.getAdjList()){
            if(country.getOwner().equalsIgnoreCase(playerName)){
                countryList.add(country);
            }
        }
        return countryList;
    }
}
