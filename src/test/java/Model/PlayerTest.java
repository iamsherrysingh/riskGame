package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * It contains different Junit tests for the methods defined in {@link Model.Player}
 * 
 * @see Player.java
 * @author birjotsingh17
 */

public class PlayerTest {
	static GamePlay gamePlay;

	/**
	 * This method runs before every test method and creates an object for GamePlay and also loads a map
	 * 
	 * @throws Exception
	 */
	@Before
	public void before() throws Exception {
		 gamePlay= GamePlay.getInstance();
		 gamePlay.editMap("map.map");
	}

	/**
	 * This method runs after each and every test method and clears the instance changed by the test method
	 * @throws Exception
	 */
	@After
	public void after() throws Exception {
        gamePlay.getGraphObj().getAdjList().clear();
		gamePlay = null;
		Database.getInstance().getContinentList().clear();
		Database.getInstance().getPlayerList().clear();
	}

	/**
	 * This is a jUnit test for {@link Model.Player#addPlayer(String, Integer)}
	 */
	@Test
	public void addPlayer1() {
		boolean playerFound = false;
		Player.addPlayer("Birjot", 20);
		playerFound = Database.getInstance().getPlayerList().contains(Player.getPlayerByName("Birjot"));
		assertTrue(playerFound);
	}

	/**
	 * This is a jUnit test for {@link Model.Player#addPlayer(String, Integer)}
	 */
	@Test
	public void addPlayer2() {
		assertFalse(Player.addPlayer(" ", 20));
	}

}
