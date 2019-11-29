package GameLogic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

/**
 * It contains different Junit tests for the methods defined in
 * {@link GameLogic.Player}
 * 
 * @see GameLogic.Player
 * @author birjotsingh17
 */

public class PlayerTest {
	static GamePlay gamePlay;

	/**
	 * This method runs before every test method and creates an object for GamePlay
	 * and also loads a map
	 * 
	 * @throws Exception throws exception to caller
	 */
	@Before
	public void before() throws Exception {
		gamePlay = GamePlay.getInstance();
		gamePlay.editMap("map.map");
	}

	/**
	 * This method runs after each and every test method and clears the instance
	 * changed by the test method
	 * 
	 * @throws Exception  throws exception to caller
	 */
	@After
	public void after() throws Exception {
		gamePlay.getGraphObj().getAdjList().clear();
		gamePlay = null;
		Database.getInstance().getContinentList().clear();
		Database.getInstance().getPlayerList().clear();
	}

	/**
	 * This is a jUnit test for {@link GameLogic.GamePlay#addPlayer(String, String)}
	 */
	@Test
	public void addPlayer1() {
		boolean playerFound = false;
		GamePlay.getInstance().addPlayer("Birjot", "Human");
		playerFound = Database.getInstance().getPlayerList().contains(Database.getInstance().getPlayerByName("Birjot"));
		assertTrue(playerFound);
	}

	/**
	 * This is a jUnit test for {@link GameLogic.GamePlay#addPlayer(String, String)}
	 */
	@Test
	public void addPlayer4() {
		assertTrue(GamePlay.getInstance().addPlayer("SS", "Machine"));
	}

	/**
	 * This is a jUnit test for {@link GameLogic.GamePlay#addPlayer(String, String)}
	 */
	@Test
	public void addPlayer2() {
		assertFalse(gamePlay.addPlayer(" ", "human"));
	}
	/**
	 *This is a Junit test for {@link GameLogic.GamePlay#removePlayer(String)}
	 */
	@Test
	public void validRemovePlayer() {
		gamePlay.addPlayer("birjot", "human");
		gamePlay.addPlayer("sehaj", "human");
		Integer number = Database.playerList.size();
		gamePlay.removePlayer("sehaj");
		Integer numberAfter = Database.playerList.size();
		if(numberAfter<number){
			assertTrue(true);
		}else {
			assertTrue(false);
		}
	}

	/**
	 * This is a jUnit test for {@link GameLogic.GamePlay#addPlayer(String, String)}
	 */
	@Test
	public void addPlayer3() {
		assertTrue(gamePlay.addPlayer("birjot", ""));
	}

	/**
	*This is a Junit test for {@link GameLogic.GamePlay#removePlayer(String)}
	 */
	@Test
	public void InvalidRemovePlayer() {
		gamePlay.addPlayer("birjot", "human");
		gamePlay.addPlayer("sehaj", "human");
		Integer number = Database.playerList.size();
		gamePlay.removePlayer("sehaj");
		Integer numberAfter = Database.playerList.size();
		if(numberAfter>=number){
			assertTrue(false);
		}else {
			assertTrue(true);
		}
	}

	/**
	 * This is a JUnit test for
	 * {@link GameLogic.Player#normalAttack(String, String, Integer, Graph, CurrentPlayer)}
	 * attackCountryOutput would be false because attacker cannot attack to a
	 * non-adjacent country.
	 */
	@Test
	public void attackCountryNonAdjacentCountries() {
		GamePlay.getInstance().addPlayer("birjot", "Human");
		GamePlay.getInstance().addPlayer("jaskaran", "Human");
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Alberta", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("jaskaran");
		Country.addArmiesToCountry("Alberta", 6, Graph.getInstance());
		Country.addArmiesToCountry("Quebec", 4, Graph.getInstance());
		boolean attackCountryOutput = new Player().normalAttack("Alberta", "Quebec", 2, Graph.getInstance(),
				gamePlay.getCurrentPlayerObj());
		assertFalse(attackCountryOutput);
	}

	/**
	 * This is a JUnit test for
	 * {@link GameLogic.Player#attackAllout(String, String, Graph, CurrentPlayer)}
	 * attackAlloutOutput would be false because attacker cannot attack to a
	 * non-adjacent country.
	 */
	@Test
	public void attackAllOutNonAdjacentCountries() {
		GamePlay.getInstance().addPlayer("birjot", "Human");
		GamePlay.getInstance().addPlayer("jaskaran", "Human");
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Alberta", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("jaskaran");
		Country.addArmiesToCountry("Alberta", 6, Graph.getInstance());
		Country.addArmiesToCountry("Quebec", 4, Graph.getInstance());
		boolean attackAlloutOutput = new Player().attackAllout("Alberta", "Quebec", Graph.getInstance(),
				gamePlay.getCurrentPlayerObj());
		assertFalse(attackAlloutOutput);
	}

/*	*//**
	 * This is a JUnit test for
	 * {@link GameLogic.Player#attackAllout(String, String, Graph, CurrentPlayer)}
	 * attackAlloutOutput would be true because everything is added according to the
	 * rules.
	 *//*
	@Test
	public void attackAllOut() {
		GamePlay.getInstance().addPlayer("birjot", "Human");
		GamePlay.getInstance().addPlayer("jaskaran", "Human");
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Greenland", Graph.getInstance()).setOwner("jaskaran");
		Country.addArmiesToCountry("Quebec", 6, Graph.getInstance());
		Country.addArmiesToCountry("Greenland", 4, Graph.getInstance());
		boolean attackAlloutOutput = new Player().attackAllout("Quebec", "Greenland", Graph.getInstance(),
				gamePlay.getCurrentPlayerObj());
		assertTrue(attackAlloutOutput);
	}*/

	/**
	 * This is a JUnit test for
	 * {@link GameLogic.Player#attackAllout(String, String, Graph, CurrentPlayer)}
	 * attackAlloutOutput would be false because attacker cannot attack on his own
	 * country.
	 */
	@Test
	public void attackAllOutSameOwner() {
		GamePlay.getInstance().addPlayer("birjot", "Human");
		GamePlay.getInstance().addPlayer("jaskaran", "Human");
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Greenland", Graph.getInstance()).setOwner("birjot");
		Country.addArmiesToCountry("Quebec", 6, Graph.getInstance());
		Country.addArmiesToCountry("Greenland", 4, Graph.getInstance());
		boolean attackAlloutOutput = new Player().attackAllout("Quebec", "Greenland", Graph.getInstance(),
				gamePlay.getCurrentPlayerObj());
		assertFalse(attackAlloutOutput);
	}
	

	/**
	 * This is a JUnit test for
	 * {@link GameLogic.Player#attackMove(Country, Country, Integer)} This checks if the
	 * attackMove is working properly like adds and remove right number of armies.
	 */
	@Test
	public void attackMove() {
		GamePlay.getInstance().addPlayer("birjot", "Human");
		GamePlay.getInstance().addPlayer("jaskaran", "Human");
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Greenland", Graph.getInstance()).setOwner("jaskaran");
		Country.getCountryByName("Quebec", Graph.getInstance()).setNumberOfArmies(10);
		Country.getCountryByName("Greenland", Graph.getInstance()).setNumberOfArmies(0);
		Integer AttackerArmiesBefore = Country.getCountryByName("Quebec", Graph.getInstance()).getNumberOfArmies();
		IPlayer attacker = Database.getInstance().getPlayerByName(Country.getCountryByName("Quebec", gamePlay.getGraphObj()).getOwner());
		IPlayer defender = Database.getInstance().getPlayerByName(Country.getCountryByName("Greenland", gamePlay.getGraphObj()).getOwner());
		Player.attackMove(Country.getCountryByName("Quebec", gamePlay.getGraphObj()),
				Country.getCountryByName("Greenland", gamePlay.getGraphObj()), 2);
		Integer AttackerArmiesAfter = Country.getCountryByName("Quebec", Graph.getInstance()).getNumberOfArmies();
		Integer DefenderArmiesAfter = Country.getCountryByName("Greenland", Graph.getInstance()).getNumberOfArmies();
		Integer attackMoveOutput = AttackerArmiesBefore - 2;
		Integer DefenderExpected = 2;
		assertEquals(attackMoveOutput, AttackerArmiesAfter);
		assertEquals(DefenderExpected, DefenderArmiesAfter);

	}

}
