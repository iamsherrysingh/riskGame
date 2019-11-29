package GameLogic;

import java.io.IOException;

/**
 * This is an interface that is used to implement adapter pattern
 */
public interface IMap {
    public boolean loadMap(String mapFile, Graph gameGraph) throws IOException;
    public boolean saveMap(Graph gameGraph, String mp)throws IOException;
}
