package Model;

import java.io.IOException;

public class ConquestMapAdapter implements IMap {
    @Override
    public boolean loadMap(String mapFile, Graph gameGraph) throws IOException {
        return false;
    }

    @Override
    public boolean saveMap(Graph gameGraph, String mp) throws IOException {
        return false;
    }
}
