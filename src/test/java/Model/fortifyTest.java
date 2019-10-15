package Model;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class fortifyTest {


	static Mapx map;
	static Graph g;

	@Before
	public void before() throws Exception {
		map = new Mapx();
		g = map.createGameGraph("src/main/resources/map.map");
	}

	@After
	public void after() throws Exception {
		map = null;
		g = null;
		Database.getInstance().getContinentList().clear();
		Database.getInstance().getPlayerList().clear();
	}

	@Test
	public void fortifyTest() {

		g.getAdjList().get(3).setNumberOfArmies(13);
		g.getAdjList().get(3).setOwner("sc");
		g.getAdjList().get(7).setOwner("sc");
		g.getAdjList().get(7).setNumberOfArmies(3);
		assertTrue(Country.fortify("Alberta", "Ontario", 11, g));
		
	}

}
