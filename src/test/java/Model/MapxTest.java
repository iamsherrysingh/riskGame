package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;

/**
 * It contains different Junit tests for the methods defined in {@link Model.Mapx}
 *
 * @see Model.Mapx
 * @author birjotsingh17
 */

public class MapxTest {
    static GamePlay gamePlay;
    Mapx mapx;

    /**
	 * This method runs before every test method and creates an object for GamePlay and Mapx
	 */
    @Before
    public void setUp() {
        gamePlay = GamePlay.getInstance();
        mapx= GamePlay.getInstance().getMapxObj();
    }

    /**
	 * This method runs after each and every test method and clears the instance changed by the test method
	 */
    @After
    public void tearDown() {
        gamePlay.getGraphObj().getAdjList().clear();
        gamePlay = null;
    }

 
    /**
     *  This is a jUnit test for {@link Model.Mapx#loadMap(String, Graph)}
     * @throws Exception This throws exception to caller method
     */
    
/*
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
*/
    
    @Test
    public void loadMapConquest() throws Exception {

        gamePlay.loadGameMap("conquestmap.map");
		
        Integer numberOfCountries=0;
        numberOfCountries= gamePlay.getGraphObj().getAdjList().size();
        if(numberOfCountries > 0)
            assertTrue(true);
        else
            assertTrue(false);
    }

    
    
    /**
     *  This is a jUnit test for {@link Model.Mapx#saveMap(Graph, String)}
     * @throws IOException This throws IOexception to caller method
     */
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

    /**
     *  This is a jUnit test for {@link Model.Mapx#validateMap(Graph)}
     * @throws IOException This throws IOexception to caller method
     */
    @Test
    public void validateMap() throws IOException {
        //test if mapName is null
        gamePlay.loadGameMap("map.map");
        boolean validmap= mapx.validateMap(gamePlay.getGraphObj());
        assertTrue(validmap == true);
    }

    /**
     *  This is a jUnit test for {@link Model.Mapx#saveMap(Graph, String)}
     * @throws IOException This throws IOexception to caller method
     */
    @Test
    public void saveMap2() throws IOException {
        // test for overwriting the default map
        gamePlay.loadGameMap("map.map");
        boolean check = mapx.saveMap(gamePlay.getGraphObj(), "map.map");
        assertFalse(check);
    }

}
