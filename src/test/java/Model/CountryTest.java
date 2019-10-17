package Model;

import Model.*;

import org.junit.*;

import static org.junit.Assert.*;

public class CountryTest {
    static GamePlay gamePlay;

    @Before
    public void setUp() throws Exception {
        gamePlay= GamePlay.getInstance();
        gamePlay.editMap("map.map");
        gamePlay.showMap();
//        Country.removeCountry("India", gamePlay.getGraphObj());
//        System.out.println("===========");
//        gamePlay.showMap();

        //gamePlay.showMap();
    }

    @After
    public void tearDown() throws Exception {
        gamePlay.getGraphObj().getAdjList().clear();
        gamePlay = null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }

    @Test
    public void addNeighbour() {
    }

    @Test
    public void removeNeighbour() throws Exception {

    }

    @Test
    public void addCountry() {
    }

    @Test
    public void addNeighbour1() {
    }

    @Test
    public void removeCountry() {
        Country.removeCountry("India", gamePlay.getGraphObj());
        Country india= Country.getCountryByName("India", gamePlay.getGraphObj());
        boolean removedCounryExists=false;
        if(india != null)
            removedCounryExists= true;
        assertEquals(removedCounryExists, false);
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
    public void getCountryByName() {
    }

    @Test
    public void getCountryByNumber() {
    }

    @Test
    public void assignOwner() {
    }

    @Test
    public void changeOwner() {
    }

    @Test
    public void checkExistenceOfCountry() {
        assertFalse(Country.checkExistenceOfCountry("Bir", gamePlay.getGraphObj()));
    }

    @Test
    public void checkExistenceOfCountry2() {
        assertTrue(Country.checkExistenceOfCountry("chINa", gamePlay.getGraphObj()));
    }

    @Test
    public void allCountriesPopulated() {
    }

    @Test
    public void addArmiesToCountry() {
    }

    @Test
    public void removeArmiesFromCountry() {
    }

    @Test
    public void fortify() {

    }

    @Test
    public void updatePlayerListAndDeclareWinner() {
    }
}