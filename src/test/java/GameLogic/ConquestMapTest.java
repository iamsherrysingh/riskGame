package GameLogic;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a JUnit test for {@link GameLogic.ConquestMapAdapter}
 */
public class ConquestMapTest {
	
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

	
	ConquestMapAdapter cq = new ConquestMapAdapter();

	/**
     *  This is a jUnit test for {@link GameLogic.ConquestMapAdapter.loadMap(String mapFile, Graph gameGraph)}
     * @throws Exception
     */
    @Test
    public void loadMap() throws Exception {
    	
    	
    	cq.loadMap("map2.map",GamePlay.getInstance().getGraphObj());
        Integer numberOfCountries=0;
        numberOfCountries= gamePlay.getGraphObj().getAdjList().size();
        if(numberOfCountries <= 0)
            assertTrue(true);
        else
            assertTrue(false);
    }

   
    /**
     *  This is a jUnit test for {@link GameLogic.ConquestMapAdapter.loadMap(String mapFile, Graph gameGraph)}
     * @throws IOException
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
	
	
}
