package Model;

import java.io.IOException;

public class ConquestMapAdapter implements IMap {
    ConquestMap conquestMap = new ConquestMap();

    @Override
    public boolean loadMap(String mapFile, Graph gameGraph) throws IOException {
        try{
        conquestMap.loadMap(mapFile,gameGraph);
        return true;}
        catch(Exception e){
            System.out.println("Error loading ConquestMap");
            return false;
        }
    }

    @Override
    public boolean saveMap(Graph gameGraph, String mp) throws IOException {
        return false;
    }
}
