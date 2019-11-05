package View;

import Model.Database;
import Model.GamePlay;
import Model.Player;

public class WorldDominationView implements IObserver{
    String header=          "================================================"  + "\n" +
                            "==============WORLD DOMINATION VIEW=============";
    String footer=          "________________________________________________";


    String data="";
    @Override
    public void update(GamePlay gamePlay) {
        data= "Ownership:";
        try {
            for(Player player: Database.getInstance().getPlayerList()) {
                data += "\nPlayer " + player.getName() + " " + gamePlay.getPercentageOfMapOwnedByPlayer(player.getName()) + "%";
            }
        }catch(Exception e){  }


        data+="\nArmies on map:";
        try {
            for(Player player: Database.getInstance().getPlayerList()) {
                data += "\nPlayer " + player.getName() + " has " + gamePlay.getTotalNumberOfArmies(player.getName()) + " armies on map";
            }
        }catch(Exception e){  }

        System.out.println(header + "\n" + data+"\n"+footer);

    }

    public void printPhaseView(){
        System.out.println(header + "\n" + data+"\n"+footer);

    }
}

