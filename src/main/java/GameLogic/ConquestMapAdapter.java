package GameLogic;
import java.io.File;

import java.io.IOException;

/**
 * This class implements Adapter design pattern for ConquestMap class in order to connect the different maps together.
 *  @return true(If the map loads) or false(If the map does not load)
 */
public class ConquestMapAdapter implements IMap {
    ConquestMap conquestMap = new ConquestMap();

    @Override
    /**
     * This method loads the saved map.
     */
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
    /**
     * This method saves the map created.
     * @return true(If the map is saved) or false(If the map does not save)
     */
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
