package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;

public class MapxTest {
    static GamePlay gamePlay;
    Mapx mapx;

    @Before
    public void setUp() {
        gamePlay = GamePlay.getInstance();
        mapx= GamePlay.getInstance().getMapxObj();
    }

    @After
    public void tearDown() {
        gamePlay.getGraphObj().getAdjList().clear();
        gamePlay = null;
    }

    @Test
    public void loadMap() throws Exception {
        gamePlay.loadGameMap("map.map");
        Integer numberOfCountries=0;
        numberOfCountries= gamePlay.getGraphObj().getAdjList().size();
        if(numberOfCountries > 0)
            assertTrue(true);
        else
            assertTrue(false);
    }

    @Test
    public void saveMap1() throws IOException {
        //test if mapName is null
        gamePlay.loadGameMap("map.map");
        mapx.saveMap(gamePlay.getGraphObj(), " ");
        String path = "src/main/resources/.map";
        File file = new File(path);
        boolean exists = file.exists();
        assertTrue(exists == false);
    }

    @Test
    public void validateMap() throws IOException {
        //test if mapName is null
        gamePlay.loadGameMap("map.map");
        boolean validmap= mapx.validateMap(gamePlay.getGraphObj());
        assertTrue(validmap == true);
    }

    @Test
    public void saveMap2() throws IOException {
        // test for overwriting the default map
        gamePlay.loadGameMap("map.map");
        boolean check = mapx.saveMap(gamePlay.getGraphObj(), "map.map");
        assertFalse(check);
    }

}
