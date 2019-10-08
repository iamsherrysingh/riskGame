package Model;

import org.junit.*;

import static org.junit.Assert.*;

public class MapxTest {
    static Mapx map;
    static Graph g;

    @Before
    public void setUp() throws Exception {
    }

    @BeforeClass
    public static void setUpClass() throws Exception{
        map= new Mapx();
        g=map.createGameGraph("src/main/resources/map.map");

    }

    @After
    public void tearDownClass() throws Exception {
    }

    @AfterClass
    public static void tearDown() throws Exception{
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
    public void addCountry() {
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