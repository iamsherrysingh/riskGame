package View;

import GameLogic.*;

public class WorldDominationView implements IObserver{
    String header=          "================================================"  + "\n" +
                            "==============WORLD DOMINATION VIEW=============";
    String footer=          "________________________________________________";


    String data="";
    @Override
    public void update(GamePlay gamePlay, IPlayer playerobj) {

        try {
            data= "Map Ownership:";
            for(IPlayer player: Database.getInstance().getPlayerList()) {
                data += "\nPlayer " + player.getName() + " : " + gamePlay.getPercentageOfMapOwnedByPlayer(player.getName()) + "%";
            }
            if(Database.getInstance().getPlayerList().size() ==0)
                data += "\nPlayers not yet added";
        }catch(Exception e){  data+= "\nCountries not populated yet" ; }

        if(Database.getInstance().getContinentList().size() !=0){
        try {
            data+= "\n\nContinent Ownership:\n";
            data += gamePlay.getContinentOwnership();
        }catch(Exception e){  }}


        try {
            data+="\nArmies on map:";
            for(IPlayer player: Database.getInstance().getPlayerList()) {
                data += "\nPlayer " + player.getName() + " has " + gamePlay.getTotalNumberOfArmies(player.getName()) + " armies on map";
            }
            if(Database.getInstance().getPlayerList().size() ==0)
                data += "\nNo Players. So no armies on the map";
        }catch(Exception e){  data+= "\nCountries not populated yet";  }

        System.out.println(header + "\n" + data+"\n"+footer);

    }

    public void printWorldDomiantionView(){
        System.out.println(header + "\n" + data+"\n"+footer);

    }
}

