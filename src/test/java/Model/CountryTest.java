package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CountryTest {

	static Mapx map;
	static Graph g;

	@Before
	public void before() throws Exception {
		map = new Mapx();
		map.loadMap("src/main/resources/map.map", g);
	}

	@After
	public void after() throws Exception {
		map = null;
		g = null;
		Database.getInstance().getContinentList().clear();
		Database.getInstance().getPlayerList().clear();
	}

	

	
	@Test
	public void removeCountry() {

		boolean check = true;
		Country.removeCountry("Alaska", g);
		for (Country country : g.getAdjList()) {
			if (country.getName().equalsIgnoreCase("Alaska")) {
				check = true;
			} else {
				check = false;
			}
		}
		assertEquals(check, false);

	}

	@Test
	public void removeCountry2() {

		boolean check = Country.removeCountry("birjot", g);
		assertEquals(false, check);

	}

	
}
