package Model;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class Player {
    String name;
    Integer id, numberOfArmies;
    ArrayList<Integer> myCountries = new ArrayList<Integer>();;

    public String getName() {
        return name;
    }

    public Player(Integer id, String name, Integer numberOfArmies) {
        this.name = name;
        this.id = id;
        this.numberOfArmies = numberOfArmies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    static boolean addPlayer(Integer id, String Name, Integer noOfArmies){
        Player player= new Player(id, Name,noOfArmies);
        Database.playerList.add(player);
        return true;
    }

    static boolean removePlayer(String playerName){
        Player player= Player.getPlayerByName(playerName);
        if(player==null){
            return false;
        }
        Database.playerList.remove(player);
        return true;
    }

    static Player getPlayerByName(String playerName){
        for(Player player: Database.playerList){
            if(player.getName().equalsIgnoreCase(playerName)){
                return player;
            }
        }
        return null;
    }


}
