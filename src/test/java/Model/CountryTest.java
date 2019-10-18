package Model;

import org.junit.*;

import static org.junit.Assert.*;

public class CountryTest {
    static GamePlay gamePlay;

    @Before
    public void setUp() throws Exception {
        gamePlay= GamePlay.getInstance();
        gamePlay.editMap("map.map");
    }

    @After
    public void tearDown() throws Exception {
        gamePlay.getGraphObj().getAdjList().clear();
        gamePlay = null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }

    @Test
    public void removeCountry() {
        boolean removedCounryExists=false;
        Country.removeCountry("India", gamePlay.getGraphObj());
        Country indiaObject= Country.getCountryByName("India", gamePlay.getGraphObj());

        if(indiaObject != null)
            removedCounryExists= true;
        assertFalse(removedCounryExists);
    }

    @Test
    public void removeCountry2() {
        Country india= Country.getCountryByName("nonExistentCountry", gamePlay.getGraphObj());
        boolean removedCounryExists=false;
        if(india != null)
            removedCounryExists= true;
        assertEquals(removedCounryExists, false);
    }

    @Test
    public void removeNeighbour1() {
        boolean neighbourStillExists= true;
        Country.removeNeighbour("EgyPT","Southern-Europe",gamePlay.getGraphObj());
        neighbourStillExists= Country.getCountryByName("Egypt", gamePlay.getGraphObj()).neighbours.contains(20);
        assertFalse(neighbourStillExists);
    }

    @Test
    public void removeNeighbour2() {
        assertFalse(Country.removeNeighbour("EgyPT","Alaska",gamePlay.getGraphObj()));
    }

    @Test
    public void checkExistenceOfCountry() {
        assertFalse(Country.checkExistenceOfCountry("Bir", gamePlay.getGraphObj()));
    }

    @Test
    public void checkExistenceOfCountry2() {
        assertTrue(Country.checkExistenceOfCountry("chINa", gamePlay.getGraphObj()));
    }

}