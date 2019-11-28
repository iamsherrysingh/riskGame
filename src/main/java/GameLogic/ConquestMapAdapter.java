package GameLogic;
import java.io.File;

import java.io.IOException;

public class ConquestMapAdapter implements IMap {
    ConquestMap conquestMap = new ConquestMap();

    @Override
    public boolean loadMap(String mapFile, Graph gameGraph) throws IOException {
        try{
        conquestMap.loadConquestMap(mapFile,gameGraph);
        return true;}
        catch(Exception e){
            System.out.println("Error loading ConquestMap");
            return false;
        }
    }

    @Override
    public boolean saveMap(Graph gameGraph, String mp) throws IOException {

        try {
            File file = new File("src/main/resources/" + mp);
            conquestMap.writeMapFile(gameGraph, file);
            return true;
        }catch (Exception e){
            System.out.println("Error loading ConquestMap");
            return false;
        }
    }
}
