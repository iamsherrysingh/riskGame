package Model;

import java.io.IOException;

/**
 * This class carries out the implementation of Adapter pattern on the class ConquestMap.
 */
public class ConquestMapAdapter implements IMap {
    ConquestMap conquestMap = new ConquestMap();

    @Override
    public boolean loadMap(String mapFile, Graph gameGraph) throws IOException {
        return false;
    }

    @Override
    public boolean saveMap(Graph gameGraph, String mp) throws IOException {
        return false;
    }
}
