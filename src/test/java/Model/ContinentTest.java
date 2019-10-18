package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContinentTest {
	static GamePlay gamePlay;

	@Before
	public void before() throws Exception {
		 gamePlay= GamePlay.getInstance();
		 gamePlay.editMap("map.map");
	}

	@After
	public void after() throws Exception {
	    gamePlay.getGraphObj().getAdjList().clear();
		gamePlay = null;
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

	@Test
	public void addContinent(){
		Continent.addContinent("Kammulaska", 8);
		assertTrue(Database.getInstance().getContinentList().contains(Continent.getContinentByName("kAMMUlasKa")));
	}

    @Test
    public void removeContinent(){
        Continent.removeContinent("Europe", gamePlay.getGraphObj());
        Continent continent= Continent.getContinentByName("Europe");
        if(continent == null){
            assertTrue(true);
        }
        else{
            assertFalse(true);
        }
    }
}
