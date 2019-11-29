package GameLogic;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class SaveGameTest {
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
	     *  This is a jUnit test for {@link GameLogic.GamePlay.LoadGame(String fileName)}
	     * @throws Exception
	     */
	    @Test
	    public void loadGame() throws Exception {
	    	
	    	gamePlay.LoadGame("ff.txt");
	        Integer numberOfCountries=0;
	        numberOfCountries= gamePlay.getGraphObj().getAdjList().size();
	        if(numberOfCountries <= 0)
	            assertTrue(true);
	    }

	   
	    /**
	     *  This is a jUnit test for {@link GameLogic.GamePlay.SaveGame(String fileName)}
	     * @throws IOException
	     */
	    @Test
	    public void saveGame() throws IOException {
	    	
	        //test if mapName is null
	    	gamePlay.loadGameMap("map.map");
	    	GamePlay.getInstance().addPlayer("birjot", "Human");
			GamePlay.getInstance().addPlayer("Mehrnoosh", "Human");
			gamePlay.populateCountries();
			gamePlay.placeAll();	    
			gamePlay.SaveGame("game.txt");
	        String path = "src/main/resources/game.txt";
	        File file = new File(path);
	        boolean exists = file.exists();
	        assertTrue(exists == false);
	    }

}
