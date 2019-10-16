package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContinentTest {

	static Mapx map;
	static Graph g;

	@Before
	public void before() throws Exception {
		map = new Mapx();
		g = map.loadMap("src/main/resources/map.map");
	}

	@After
	public void after() throws Exception {
		map = null;
		g = null;
		Database.getInstance().getContinentList().clear();
		Database.getInstance().getPlayerList().clear();
	}

	

	@Test
	public  void checkExistence() {
		
		assertFalse(Continent.checkExistenceOfContinent("Bir"));
		
	}
	
	@Test
	public  void checkExistence2() {
		
		assertTrue(Continent.checkExistenceOfContinent("Europe"));
		
	}
	
	@Test
	public  void checkExistence3() {
		
		assertFalse(Continent.checkExistenceOfContinent("    "));
		
	}
	


}
