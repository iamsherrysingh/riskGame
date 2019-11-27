package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class SaveGame {
    public static void main(String[] args)throws Exception {
        GamePlay.getInstance().loadGameMap("map.map");
        saveGame("game.save");
    }

    public static boolean saveGame(String saveFile)throws Exception
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/SavedGames/"+saveFile));
        writer.write("[CurrentState]\ninitializationState\n");

        writer.write("\n[Players]\n");
        for(IPlayer player:Database.getInstance().getPlayerList()){
            writer.write(player.getNumber()+" "+player.getName()+" "+player.getNumberOfArmies());
        }

        writer.write("\n[GameGraph]\n");
        for(Country country: GamePlay.getInstance().getGraphObj().getAdjList()) {
            writer.write(country.getNumber() + " " + country.getName() + " " + country.getInContinent() + " " + country.getOwner() + " " + country.getNumberOfArmies() + " " + country.getCoOrdinate1() + " " + country.getGetCoOrdinate2() + " " + country.getNeighbours().toString()+"\n");
        }

        writer.close();
        return true;
    }
}
