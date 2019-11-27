package Model;

import java.io.IOException;

public interface IMap {
    public boolean loadMap(String mapFile, Graph gameGraph) throws IOException;
    public boolean saveMap(Graph gameGraph, String mp)throws IOException;
}
