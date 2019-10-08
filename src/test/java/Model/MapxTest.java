package Model;

import Database.Database;
import org.junit.*;
import static org.junit.Assert.*;

public class MapxTest {
    static Mapx map;
    static Graph g;

    @Before
    public void setUp() throws Exception {
        map= new Mapx();
        g=map.createGameGraph("src/main/resources/map.map");
    }

    @BeforeClass
    public static void setUpClass() throws Exception{
        map= new Mapx();
        g=map.createGameGraph("src/main/resources/map.map");

    }

    @After
    public void tearDownClass() throws Exception {
        map=null;
        g=null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }

    @AfterClass
    public static void tearDown() throws Exception{
        map=null;
        g=null;
        Database.getInstance().getContinentList().clear();
        Database.getInstance().getPlayerList().clear();
    }

    @Test
    public void createGameGraph() {
    }

    @Test
    public void createFile() {
    }

    @Test
    public void saveMap() {
    }

    @Test
    public void validateMap() {
    }

    @Test
    public void addCountry1() {
        map.addCountry("Tchala","Africa",g);
        Country retrievedCountry= null;
        for(Country country: g.getAdjList()){
            if(country.getName().equalsIgnoreCase("Tchala")){
                retrievedCountry= country;
            }
        }
        assertTrue(retrievedCountry.getName().equalsIgnoreCase("Tchala"));
    }

    @Test
    public void addCountry2() {
        map.addCountry("Tchala2","NonExistentContinent",g);
        Country retrievedCountry= new Country(00,"",-1,"",-1,-1,-1,null);
        for(Country country: g.getAdjList()){
            if(country.getName().equalsIgnoreCase("Tchala2")){
                retrievedCountry= country;
            }
        }
        assertFalse(retrievedCountry.getName().equalsIgnoreCase("Tchala2"));
    }

    @Test
    public void addNeighbour() {
    }

    @Test
    public void removeNeighbour() {
    }

    @Test
    public void removeCountry() {
    }

    @Test
    public void addContinent() {
    }

    @Test
    public void removeContinent() {
    }

    @Test
    public void addArmiesToCountry() {
    }

    @Test
    public void removeArmiesFromCountry() {
    }
}