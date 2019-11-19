package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * It contains different Junit tests for the methods defined in
 * {@link Model.Player}
 * 
 * @see Player.java
 * @author birjotsingh17
 */

public class PlayerTest {
	static GamePlay gamePlay;

	/**
	 * This method runs before every test method and creates an object for GamePlay
	 * and also loads a map
	 * 
	 * @throws Exception
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

	/**
	 * This is a JUnit test for
	 * {@link Model.Player#attackCountry(String, String, Integer, Graph, CurrentPlayer)}
	 * attackCountryOutput would be false because attacker cannot attack to a
	 * non-adjacent country.
	 */
	@Test
	public void attackCountryNonAdjacentCountries() {
		Player.addPlayer("birjot", 9);
		Player.addPlayer("jaskaran", 6);
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Alberta", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("jaskaran");
		Country.addArmiesToCountry("Alberta", 6, Graph.getInstance());
		Country.addArmiesToCountry("Quebec", 4, Graph.getInstance());
		boolean attackCountryOutput = Player.attackCountry("Alberta", "Quebec", 2, Graph.getInstance(),
				gamePlay.getCurrentPlayerObj());
		assertFalse(attackCountryOutput);
	}

	/**
	 * This is a JUnit test for
	 * {@link Model.Player#attackAllout(String, String, Graph, CurrentPlayer)}
	 * attackAlloutOutput would be false because attacker cannot attack to a
	 * non-adjacent country.
	 */
	@Test
	public void attackAllOutNonAdjacentCountries() {
		Player.addPlayer("birjot", 9);
		Player.addPlayer("jaskaran", 6);
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Alberta", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("jaskaran");
		Country.addArmiesToCountry("Alberta", 6, Graph.getInstance());
		Country.addArmiesToCountry("Quebec", 4, Graph.getInstance());
		boolean attackAlloutOutput = Player.attackAllout("Alberta", "Quebec", Graph.getInstance(),
				gamePlay.getCurrentPlayerObj());
		assertFalse(attackAlloutOutput);
	}

	/**
	 * This is a JUnit test for
	 * {@link Model.Player#attackAllout(String, String, Graph, CurrentPlayer)}
	 * attackAlloutOutput would be true because everything is added according to the
	 * rules.
	 */
	@Test
	public void attackAllOut() {
		Player.addPlayer("birjot", 9);
		Player.addPlayer("jaskaran", 6);
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Greenland", Graph.getInstance()).setOwner("jaskaran");
		Country.addArmiesToCountry("Quebec", 6, Graph.getInstance());
		Country.addArmiesToCountry("Greenland", 4, Graph.getInstance());
		boolean attackAlloutOutput = Player.attackAllout("Quebec", "Greenland", Graph.getInstance(),
				gamePlay.getCurrentPlayerObj());
		assertTrue(attackAlloutOutput);
	}

	/**
	 * This is a JUnit test for
	 * {@link Model.Player#attackAllout(String, String, Graph, CurrentPlayer)}
	 * attackAlloutOutput would be false because attacker cannot attack on his own
	 * country.
	 */
	@Test
	public void attackAllOutSameOwner() {
		Player.addPlayer("birjot", 9);
		Player.addPlayer("jaskaran", 6);
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Greenland", Graph.getInstance()).setOwner("birjot");
		Country.addArmiesToCountry("Quebec", 6, Graph.getInstance());
		Country.addArmiesToCountry("Greenland", 4, Graph.getInstance());
		boolean attackAlloutOutput = Player.attackAllout("Quebec", "Greenland", Graph.getInstance(),
				gamePlay.getCurrentPlayerObj());
		assertFalse(attackAlloutOutput);
	}
	

	/**
	 * This is a JUnit test for
	 * {@link Model.Player#attackMove(Country, Country, Integer)} This checks if the
	 * attackMove is working properly like adds and remove right number of armies.
	 */
	@Test
	public void attackMove() {
		Player.addPlayer("birjot", 9);
		Player.addPlayer("jaskaran", 6);
		gamePlay.populateCountries();
		gamePlay.placeAll();
		Country.getCountryByName("Quebec", Graph.getInstance()).setOwner("birjot");
		Country.getCountryByName("Greenland", Graph.getInstance()).setOwner("jaskaran");
		Country.getCountryByName("Quebec", Graph.getInstance()).setNumberOfArmies(10);
		Country.getCountryByName("Greenland", Graph.getInstance()).setNumberOfArmies(0);
		Integer AttackerArmiesBefore = Country.getCountryByName("Quebec", Graph.getInstance()).getNumberOfArmies();
		Player attacker = Player.getPlayerByName(Country.getCountryByName("Quebec", gamePlay.getGraphObj()).getOwner());
		Player defender = Player.getPlayerByName(Country.getCountryByName("Greenland", gamePlay.getGraphObj()).getOwner());
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
